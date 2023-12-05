//@file:Config

package org.firstinspires.ftc.teamcode.components.hardware

/* ?
? * PIDFArm Component
! * Don't call this directly if using BotComponents
? *
 * TODO: Fix if needed
? */

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.arcrobotics.ftclib.controller.PIDFController
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames
import org.firstinspires.ftc.teamcode.components.meta.TeleOpBotComponents
import kotlin.math.cos


//@Config
class PIDFArm (hardwareMap: HardwareMap, telemetry: Telemetry) {
    val multipleTelemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)


    private val arm = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR)
    private val arm2 = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR2)


    private val controller: PIDFController = PIDFController(p, i, d, f) // ? PID Controller

    init {

        withEachMotor {
            zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

            mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }
    }

    fun updatePID() { // * PID Control
        controller.setPIDF(pidCoeefs.p, pidCoeefs.i, pidCoeefs.d, pidCoeefs.f)

        val pos = arm.currentPosition.toDouble()
        val pid = controller.calculate(pos, target)


        withEachMotor {
            power = pid
        }

        multipleTelemetry.addData("pos, ", pos)
        multipleTelemetry.addData("target, ", target)
        multipleTelemetry.addData("p, ", p)
        multipleTelemetry.addData("i, ", i)
        multipleTelemetry.addData("d, ", d)
        multipleTelemetry.addData("f, ", f)

        multipleTelemetry.update()
    }

    fun update(driver: Gamepad, codriver: Gamepad) {
        updatePID()
        if (codriver.dpad_down && target > -50) target -= 1
        else if (codriver.dpad_up && target < 0) target += 1
    }

    private fun withEachMotor(transformation: DcMotorEx.(Int) -> Unit) {
        arm .transformation(0)
        arm2.transformation(1)
    }

    @Config
    companion object {

        @JvmField var p = 0.1
        @JvmField var i = 0.0
        @JvmField var d = 0.002
        @JvmField var f = 0.15 // ? PID Constants
        @JvmField var target = 0.0 // ? PID Target
        var pidCoeefs = PIDFCoefficients(p,i,d,f)
        // ? old constants
//        @JvmField var p = 0.05
//        @JvmField var i = 0.0
//        @JvmField var d = 0.0
//        @JvmField var f = 1 // 0.15 // ? PID Constants
//        @JvmField var target = 0.0 // ? PID Target

    }
}