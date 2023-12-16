package org.firstinspires.ftc.teamcode.components.meta;

/* ?
? * Bot Component
? * Big bot component where all components all assembled
 * TODO: N/A
? */


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.hardware.Arm;
import org.firstinspires.ftc.teamcode.components.hardware.Claw;

public abstract class BotComponents {

    // Components for both TeleOp and Auton
    protected final Arm arm;
    protected final Claw claw;

    public BotComponents(HardwareMap hardwareMap, Telemetry telemetry) {
        this.arm = new Arm(hardwareMap, telemetry);
        this.claw = new Claw(hardwareMap, telemetry);

    }

    // Abstract method to be implemented by subclasses
    public abstract void updateComponents(boolean useLiftDeadzone);
}


