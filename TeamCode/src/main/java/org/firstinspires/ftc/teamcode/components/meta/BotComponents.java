package org.firstinspires.ftc.teamcode.components.meta;

/* ?
? * Bot Component
? * Big bot component where all components all assembled
 * TODO: N/A
? */


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.hardware.*;

public abstract class BotComponents {

    // Components for both TeleOp and Auton
    protected final Arm arm;
    protected final Claw claw;
    protected final Lift lift;

    protected final Wrist wrist;


    public BotComponents(HardwareMap hardwareMap, Telemetry telemetry) {
        this.arm = new Arm(hardwareMap, telemetry);
        this.lift = new Lift(hardwareMap, telemetry);
        this.claw = new Claw(hardwareMap, telemetry);
        this.wrist = new Wrist(hardwareMap, telemetry);

    }

    // Abstract method to be implemented by subclasses
    public abstract void updateComponents(boolean useLiftDeadzone);
}


