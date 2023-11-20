
package org.firstinspires.ftc.teamcode.components.hardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

class Lift (hardwareMap: HardwareMap, telemetry: Telemetry) {
    private val lift = hardwareMap.get(DcMotorEx::class.java, DeviceNames.LIFT_MOTOR)

    val telemetry = telemetry

    init {
        withEachMotor {
            zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            //mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }
    }

    fun update(gamepad: Gamepad) {
        var dir = 0

        if (gamepad.dpad_up) {
            dir = 1

        }//y ctrl
        else if (gamepad.dpad_down) {
            dir = -1
        }//a ctrl
//
//        withEachMotor {
//            targetPosition = currentPosition + ( (288 / 360) * 5 * dir ) // * 288
//        }

        withEachMotor {
//            mode = DcMotor.RunMode.RUN_TO_POSITION
            power = 0.8 * dir
        }

    }

    private fun withEachMotor(transformation: DcMotorEx.(Int) -> Unit) {
        lift .transformation(0)
    }
}