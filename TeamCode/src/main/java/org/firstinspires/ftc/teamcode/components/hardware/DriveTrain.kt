@file:Suppress("LocalVariableName")
@file:Config

package org.firstinspires.ftc.teamcode.components.hardware

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames
import org.firstinspires.ftc.teamcode.components.utils.maxMagnitudeAbs
import org.firstinspires.ftc.teamcode.components.utils.pow
import kotlin.math.*

@JvmField
var tipCorrection = true
@JvmField
var tipMult = 7.0
@JvmField
var tipDeadzone = 0.05

class DriveTrain (hardwareMap: HardwareMap) {
    private val frontLeft  = hardwareMap.get(DcMotorEx::class.java, DeviceNames.DRIVE_FL).apply { direction = Direction.REVERSE }
    private val frontRight = hardwareMap.get(DcMotorEx::class.java, DeviceNames.DRIVE_FR)
    private val backLeft   = hardwareMap.get(DcMotorEx::class.java, DeviceNames.DRIVE_BL).apply { direction = Direction.REVERSE }
    private val backRight  = hardwareMap.get(DcMotorEx::class.java, DeviceNames.DRIVE_BR)

    init {
        withEachMotor {
            zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        }
    }

    fun drive(gamepad: Gamepad, powerMulti: Double) {
        driveRC(gamepad, powerMulti)
    }

    private fun driveRC(gamepad: Gamepad, powerMulti: Double) {
        val (x, y, _r) = gamepad.getDriveSticks()

//        var (a, b, c) = Imu.angles
//        b += PI.toFloat() / 2

        // Angle a is strafe tip correction
        // Angle b is forward/back tip correction however its positive in both directions - negative b = strafe right, add a deadzone


//        mTelemetry.addData("Angle a", 0.0)
//        mTelemetry.addData("Angle b",b)

        val r = _r * .9f

        val theta = atan2(y, x)
        val power = hypot(x, y)

        var xComponent = power * cos(theta - PI / 4)
        var yComponent = power * sin(theta - PI / 4)

//        if(tipCorrection){
//
//             Strafe correction
//            if(abs(b) > tipDeadzone) {
//                xComponent += tipMult * b
//                yComponent -= tipMult * b
//            }
//        }
//

        val max = maxMagnitudeAbs<Double>(xComponent, yComponent, 1e-16)

        val powers = doubleArrayOf(
            power * (xComponent / max) + r,
            -power * (yComponent / max) - r,
            power * (yComponent / max) + r,
            -power * (xComponent / max) - r,
        )

        if (power + abs(r) > 1) {
            powers.mapInPlace { it / (power + abs(r)) }
        }

        val _powerMulti = if (!gamepad.isAnyJoystickTriggered()) 0.0 else powerMulti

        for (i in powers.indices) {
            powers[i] = powers[i].pow(3.0) * _powerMulti
        }

        withEachMotor {
            this.power = powers[it]
        }
    }

    private fun DoubleArray.mapInPlace(transform: (Double) -> Double) = repeat(size) {
        this[it] = transform(this[it])
    }

    private fun withEachMotor(transformation: DcMotorEx.(Int) -> Unit) {
        frontLeft .transformation(0)
        frontRight.transformation(1)
        backLeft  .transformation(2)
        backRight .transformation(3)
    }
}