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

class PIDFArm (hardwareMap: HardwareMap, telemetry: Telemetry) {
    val multipleTelemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)


    private val arm = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR)
    private val arm2 = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR2)

    private val p = 0.0; val i = 0.0; val d = 0.0; val f = 0.0 // ? PID Constants
    private val controller: PIDController = PIDController(p, i, d) // ? PID Controller
    private var target = 0.0 // ? PID Target

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

        val ff = cos(Math.toRadians(target/(280/360.0))) * f
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
        if (gamepad.a) target -= 10
        else if (gamepad.y) target += 10
    }

    private fun withEachMotor(transformation: DcMotorEx.(Int) -> Unit) {
        arm .transformation(0)
        arm2.transformation(1)
    }
}