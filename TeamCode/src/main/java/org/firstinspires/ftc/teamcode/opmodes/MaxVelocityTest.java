package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.components.meta.DeviceNames;

@TeleOp
public class MaxVelocityTest extends LinearOpMode {

    DcMotorEx motor;
    DcMotorEx motor2;

    double currentVelocity;
    double currentVelocity2;

    double maxVelocity = 0.0;


    @Override

    public void runOpMode() {

        motor = hardwareMap.get(DcMotorEx.class, DeviceNames.ARM_MOTOR);
        motor2 = hardwareMap.get(DcMotorEx.class, DeviceNames.ARM_MOTOR2);

        waitForStart();


        while (opModeIsActive()) {

            currentVelocity = motor.getVelocity();
            currentVelocity2 = motor2.getVelocity();



            if (currentVelocity > maxVelocity) {

                maxVelocity = currentVelocity;

            }



            telemetry.addData("current velocity", currentVelocity);

            telemetry.addData("maximum velocity", maxVelocity);

            telemetry.update();

        }

    }

}
