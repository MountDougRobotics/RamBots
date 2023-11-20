
package org.firstinspires.ftc.teamcode.components.hardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

class Arm (hardwareMap: HardwareMap, telemetry: Telemetry) {
    private val arm = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR)
    private val arm2 = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR2)

    val telemetry = telemetry

    init {
        withEachMotor {
            zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            //mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }
    }

    fun update(gamepad: Gamepad) {
        var dir = 0

        if (gamepad.y) {
            dir = 1

        }//y ctrl
        else if (gamepad.a) {
            dir = -1
        }//a ctrl
//
//        withEachMotor {
//            targetPosition = currentPosition + ( (288 / 360) * 5 * dir ) // * 288
//        }

        withEachMotor {
//            mode = DcMotor.RunMode.RUN_TO_POSITION
            power = 1.0 * dir
        }

    }

    private fun withEachMotor(transformation: DcMotorEx.(Int) -> Unit) {
        arm .transformation(0)
        arm2.transformation(1)
    }
}