/*
package org.firstinspires.ftc.teamcode.components.hardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

class Intake(hardwareMap: HardwareMap, telemetry: Telemetry) {

    private val intake = hardwareMap.get(DcMotorEx::class.java, DeviceNames.INTAKE_SERVO)
    var dir = 0
    val telemetry = telemetry
    var toggleStat = false //boolean that tracks the left bumper press to prevent retoggling
                            //(retoggling = toggling the intake on every compute cycle)

    // Initializes the motor's Power to 0
    init {
        withEachMotor {
            zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            //mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }
    } // init

    // Updates the power and direction of the intake
    fun update(gamepad: Gamepad) {

        //Stops the robot from toggling the intake until
        //bumper is released
        if (gamepad.left_bumper && !toggleStat) {

            toggleStat = true

            // Reverses the intake, on right bumper press
            if (gamepad.right_bumper) {
                dir *= -1;

            }//y ctrl

            // Toggles the intake, on left bumper press
            if (gamepad.left_bumper) {
                dir = 1 - Math.abs(dir)
            }//a ctrl

        }

        // resets toggleStat when bumper is released
        if (!gamepad.left_bumper) {
            toggleStat = false
        }

//
//
        // Sets the Power based on the direction
        withEachMotor {
//            mode = DcMotor.RunMode.RUN_TO_POSITION
            power = 1.0 * dir
        }

    } // update

    // Turns the motor physically based on the direction
    private fun withEachMotor(transformation: DcMotorEx.(Int) -> Unit) {
        intake .transformation(0)
    } // withEachMotor
}

 */