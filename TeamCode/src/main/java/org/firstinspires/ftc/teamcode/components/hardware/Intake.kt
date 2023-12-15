package org.firstinspires.ftc.teamcode.components.hardware

/* ?
? * Intake Component
! * Don't call this directly if using BotComponents
? *
 * TODO: N/A
? */

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

class Intake (hardwareMap: HardwareMap, telemetry: Telemetry) {
    private val intake = hardwareMap.get(DcMotorEx::class.java, DeviceNames.INTAKE) // Arm variable

    private val telemetry = telemetry // telemetry variable

    init {
        withEachMotor {
            zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            //mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }
    }

    fun update(driver: Gamepad, codriver: Gamepad) { // ? main update function
        var dir = 0.0

        if (codriver.x || driver.x) { // * input
            dir = 1.0

        }//y ctrl
        else if (codriver.b || driver.x) {
            dir = -1.0
        }//a ctrl


        withEachMotor { // * output
//            mode = DcMotor.RunMode.RUN_TO_POSITION
            power = 0.8 * dir
        }

    }

    private fun withEachMotor(transformation: DcMotorEx.(Int) -> Unit) {
        intake .transformation(0)
    }
}