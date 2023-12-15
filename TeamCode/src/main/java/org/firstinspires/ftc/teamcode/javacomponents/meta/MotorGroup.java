package org.firstinspires.ftc.teamcode.javacomponents.meta;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.function.Consumer;

public class MotorGroup {
    private DcMotorEx[] motors;
    public int index = 0;

    public MotorGroup(DcMotorEx... motors) {
        this.motors = motors;
    }

    public void applyToMotors(Consumer<DcMotorEx> motorFunction) {
        for (DcMotorEx motor : motors) {
            motorFunction.accept(motor);
            index++;
        }
        // Reset index for the next iteration
        index = 0;
    }
}