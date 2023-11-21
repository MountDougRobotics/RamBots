package org.firstinspires.ftc.teamcode.components.hardware

/* ?
? * Lift Component
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

class Lift (hardwareMap: HardwareMap, telemetry: Telemetry) {
    private val lift = hardwareMap.get(DcMotorEx::class.java, DeviceNames.LIFT_MOTOR) // lift var

    private val telemetry = telemetry

    init {
        withEachMotor {
            zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            //mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }
    }

    fun update(gamepad: Gamepad) { // ? update function
        var dir = 0

        if (gamepad.dpad_up) { // input
            dir = 1

        }//y ctrl
        else if (gamepad.dpad_down) {
            dir = -1
        }//a ctrl

        withEachMotor { // output
            power = 0.8 * dir
        }

    }

    private fun withEachMotor(transformation: DcMotorEx.(Int) -> Unit) {
        lift .transformation(0)
    }
}