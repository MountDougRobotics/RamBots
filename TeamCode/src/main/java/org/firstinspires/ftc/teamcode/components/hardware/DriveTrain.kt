@file:Suppress("LocalVariableName")
@file:Config

package org.firstinspires.ftc.teamcode.components.hardware

/* ?
? * Drivetrain Component
! * Don't call this directly if using BotComponents
? *
 * TODO: N/A
? */

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
//import ftc.rogue.blacksmith.util.kt.pow
import org.firstinspires.ftc.teamcode.components.device.getDriveSticks
import org.firstinspires.ftc.teamcode.components.device.isAnyJoystickTriggered
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
    private val frontLeft  = hardwareMap.get(DcMotorEx::class.java, DeviceNames.DRIVE_FL).apply { direction = Direction.REVERSE } // Motor vars
    private val frontRight = hardwareMap.get(DcMotorEx::class.java, DeviceNames.DRIVE_FR).apply { direction = Direction.REVERSE } // ! added a flip cuz of anderson powerpole being flipped
    private val backLeft   = hardwareMap.get(DcMotorEx::class.java, DeviceNames.DRIVE_BL).apply { direction = Direction.REVERSE }
    private val backRight  = hardwareMap.get(DcMotorEx::class.java, DeviceNames.DRIVE_BR)

    init {
        withEachMotor {
            zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        }
    }

    fun drive(gamepad: Gamepad, powerMulti: Double) { // ? drive function
        val (x, y, _r) = gamepad.getDriveSticks() // look at com.qualcomm.robotcore.hardware.Gamepad

        val r = _r * 1.0f // * doing the math
        val theta = atan2(y, x)
        val power = hypot(x, y) // sqrt(x.pow(2) + y.pow(2)).coerceAtMost(1F)

        var xComponent = power * cos(theta - PI / 4) // * more math
        var yComponent = power * sin(theta - PI / 4)

        // * unused tip correction function
//        if(tipCorrection){
//             Strafe correction
//            if(abs(b) > tipDeadzone) {
//                xComponent += tipMult * b
//                yComponent -= tipMult * b
//            }
//        }
//

        val max = maxMagnitudeAbs<Double>(xComponent, yComponent, 1e-16) // truly one of the most legible functions of all time, peek at package org.firstinspires.ftc.teamcode.components.utils.MathUtils if you wanna suffer

        val powers = doubleArrayOf( // wow power array
            power * (xComponent / max) + r,
            power * (yComponent / max) - r,
            power * (yComponent / max) + r,
            power * (xComponent / max) - r,
        )

        if (power + abs(r) > 1) { // reduce under 1 for power
            powers.mapInPlace { it / (power + abs(r)) }
        }

        val _powerMulti = if (!gamepad.isAnyJoystickTriggered()) 0.0 else powerMulti

        for (i in powers.indices) { // cube all powers for more precise manuevering
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