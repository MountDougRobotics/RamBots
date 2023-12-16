package org.firstinspires.ftc.teamcode.components.hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.meta.Hardware;

public class Arm {
    HardwareMap hardwareMap;
    Telemetry telemetry;

    DcMotorEx arm;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        arm = hardwareMap.get(DcMotorEx.class, Hardware.ARM_MOTOR);
    }

    public void update(Gamepad driver, Gamepad codriver) {
        double dir = 0.0;

        if (codriver.dpad_up) { // * input
            dir = 1.0;
        }//y ctrl
        else if (codriver.dpad_down) {
            dir = -1.0;
        }//a ctrl

        arm.setPower(dir * 0.8);
    }
}