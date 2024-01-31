package org.firstinspires.ftc.teamcode.components.hardware;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.meta.Hardware;

public class Wrist {

    HardwareMap hardwareMap;
    Telemetry telemetry;
    Servo wrist;
    private double targetPos = 0.8; // postion of claw


    public Wrist(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        wrist = hardwareMap.get(Servo.class, Hardware.WRIST_SERVO);
    }

    void wristControl() {
        wrist.setPosition(targetPos);
    }

    public void update(Gamepad driver, Gamepad codriver) { // ? Teleop claw control
        if ((driver.right_stick_x < 0 || codriver.right_stick_x < 0) && wrist.getPosition() >= 0.3) wrist.setPosition(wrist.getPosition() - 0.008);
        else if ((driver.right_stick_x > 0 || codriver.right_stick_x > 0) && wrist.getPosition() <= 0.8) wrist.setPosition(wrist.getPosition() + 0.008);
       // wristControl();


        telemetry.addData("WR", wrist.getPosition());
        telemetry.addData("TG", targetPos);

        telemetry.update();

    }
}