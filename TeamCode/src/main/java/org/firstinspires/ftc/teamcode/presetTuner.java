package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Preset Tuner")
public class presetTuner extends OpMode {
    Servo clawWrist, clawServo1, clawServo2;
    DcMotor armLiftMotor1, armLiftMotor2;

    double pickupW = 0.0; // TODO tune values for wrist
    double dropW = 0.0;
    double pickupC = 0.0; // TODO tune values for claw
    double dropC = 0.0;
    @Override
    public void init() {
        clawWrist = hardwareMap.get(Servo.class, "CW");
        clawServo1 = hardwareMap.get(Servo.class, "CL1");
        clawServo2 = hardwareMap.get(Servo.class, "Cl2");
        armLiftMotor1 = hardwareMap.dcMotor.get("AL1");
        armLiftMotor2 = hardwareMap.dcMotor.get("AL2");
    }

    @Override
    public void loop() {
        if (gamepad2.x) { // TODO map controls
            clawWrist.setPosition(pickupW);
            clawServo1.setPosition(pickupC);
        } else if (gamepad2.b) {
            clawWrist.setPosition(dropW);
            clawServo1.setPosition(dropC);
        }

        // Tuner
        telemetry.addData("Pickup Wrist", pickupW);
        telemetry.addData("Pickup Claw", pickupC);
        telemetry.addData("Drop Wrist", dropW);
        telemetry.addData("Drop Claw", dropC);
        telemetry.addData("Wrist Position", clawWrist.getPosition());
        telemetry.addData("Claw 1 Position", clawServo1.getPosition());
        telemetry.addData("Claw 2 Position", clawServo2.getPosition());



        if (gamepad2.dpad_up) {
            armLiftMotor1.setPower(1);
            armLiftMotor2.setPower(1);
        } else if (gamepad2.dpad_down) {
            armLiftMotor1.setPower(-1);
            armLiftMotor2.setPower(-1);
        }

    }
}
