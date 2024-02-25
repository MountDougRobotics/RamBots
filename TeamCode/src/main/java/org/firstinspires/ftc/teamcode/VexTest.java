package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class VexTest extends OpMode {
    Servo vex;

    @Override
    public void init() {
        vex = hardwareMap.get(Servo.class, "VEX");
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            vex.setPosition(1);
        } else if (gamepad1.y) {
            vex.setPosition(0);
        }
    }
}
