package org.firstinspires.ftc.teamcode.components.hardware

/* ?
? * Arm Component
! * Don't call this directly if using BotComponents
? *
 * TODO: Rerouting most work to PIDF if PIDF control is needed
? */

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

class Arm (hardwareMap: HardwareMap, telemetry: Telemetry) {
    private val arm = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR) // Arm variable
    private val arm2 = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR2)

    private val telemetry = telemetry // telemetry variable

    init {
        withEachMotor {
            zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            //mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }
    }

    fun update(driver: Gamepad, codriver: Gamepad) { // ? main update function
        var dir = 0.0

        if (codriver.dpad_up) { // * input
            dir = 1.0

        }//y ctrl
        else if (codriver.dpad_down) {
            dir = -1.0
        }//a ctrl


        withEachMotor { // * output
//            mode = DcMotor.RunMode.RUN_TO_POSITION
            power = 0.8 * dir
        }

    }

    private fun withEachMotor(transformation: DcMotorEx.(Int) -> Unit) {
        arm .transformation(0)
        arm2.transformation(1)
    }
}