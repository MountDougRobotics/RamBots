package org.firstinspires.ftc.teamcode.components.meta;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.function.Consumer;

public class MotorGroup {
    public int motorIndex = 0;
    private final DcMotorEx[] motors;

    public MotorGroup(DcMotorEx... motors) {
        this.motors = motors;
    }

    public void applyToMotors(Consumer<DcMotorEx> motorFunction) {
        for (DcMotorEx motor : motors) {
            motorFunction.accept(motor);
            motorIndex++;
        }
        // Reset index for the next iteration
        motorIndex = 0;
    }
}