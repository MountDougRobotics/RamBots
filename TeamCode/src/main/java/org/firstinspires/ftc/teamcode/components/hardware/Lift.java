package org.firstinspires.ftc.teamcode.components.hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.meta.Hardware;

public class Lift {
    HardwareMap hardwareMap;
    Telemetry telemetry;

    DcMotorEx lift;

    public Lift(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        lift = hardwareMap.get(DcMotorEx.class, Hardware.ARM_MOTOR);
    }

    public void update(Gamepad driver, Gamepad codriver) {
        double dir = -codriver.left_stick_y;
        lift.setPower(dir);
    }
}