@file:Config

package org.firstinspires.ftc.teamcode.components.hardware

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames
import org.firstinspires.ftc.teamcode.components.meta.TeleOpBotComponents
import kotlin.math.cos

@JvmField var p = 0.004
@JvmField var i = 0.0
@JvmField var d = 0.0001
@JvmField var f = 0.1 // ? PID Constants
@JvmField var target = 0.0 // ? PID Target

class PIDFArm (hardwareMap: HardwareMap, telemetry: Telemetry) {
    val multipleTelemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)


    private val arm = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR)
    private val arm2 = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR2)



    private val controller: PIDController = PIDController(p, i, d) // ? PID Controller

    init {

        withEachMotor {
            zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            //mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }
    }

    fun updatePID() { // * PID Control
        controller.setPID(p, i, d)
        val pos = arm.currentPosition.toDouble()
        val pid = controller.calculate(pos, target)

        val ff = cos(Math.toRadians(target/(288/180.0))) * f
        val p = pid + ff

        withEachMotor {
            power = p
        }

        multipleTelemetry.addData("pos, ", pos)
        multipleTelemetry.addData("target, ", target)
        multipleTelemetry.update()
    }

    fun update(gamepad: Gamepad) {
        updatePID()
        if (gamepad.a) target -= 1
        else if (gamepad.y) target += 1
    }

    private fun withEachMotor(transformation: DcMotorEx.(Int) -> Unit) {
        arm .transformation(0)
        arm2.transformation(1)
    }
}