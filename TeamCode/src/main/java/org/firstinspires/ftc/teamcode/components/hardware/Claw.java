package org.firstinspires.ftc.teamcode.components.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.meta.Hardware;

public class Claw {
    static double CLAW_INTAKE_NARROW = 0.578; // ! values for tuning claw pos
    static double CLAW_DEPOSIT = 0.686;
    static double CLAW_CLOSE = 0.425;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    Servo claw;
    Servo claw2;
    private double targetPos = CLAW_CLOSE; // postion of claw


    public Claw(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        claw = hardwareMap.get(Servo.class, Hardware.CLAW_SERVO);
        claw2 = hardwareMap.get(Servo.class, Hardware.CLAW_SERVO2);

    }

    void clawControl() {
        claw.setPosition(targetPos);
        claw2.setPosition(1 - targetPos);
    }

    public void update(Gamepad driver, Gamepad codriver) { // ? Teleop claw control
        if (codriver.right_bumper || driver.right_bumper) {
            if (targetPos == CLAW_CLOSE) targetPos = CLAW_INTAKE_NARROW;
            else if (targetPos == CLAW_INTAKE_NARROW) targetPos = CLAW_DEPOSIT;
        } else if (codriver.left_bumper || driver.left_bumper) {
            if (targetPos == CLAW_DEPOSIT) targetPos = CLAW_INTAKE_NARROW;
            else if (targetPos == CLAW_INTAKE_NARROW) targetPos = CLAW_CLOSE;
        }
        clawControl();
    }
}