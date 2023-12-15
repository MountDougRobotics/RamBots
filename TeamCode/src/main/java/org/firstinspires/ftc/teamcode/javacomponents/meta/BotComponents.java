package org.firstinspires.ftc.teamcode.javacomponents.meta;

/* ?
? * Bot Component
? * Big bot component where all components all assembled
 * TODO: N/A
? */


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.device.Camera;
import org.firstinspires.ftc.teamcode.javacomponents.hardware.*;

public abstract class BotComponents {

    // Components for both TeleOp and Auton
    protected final Arm arm;

    public BotComponents(HardwareMap hardwareMap, Telemetry telemetry) {
        this.arm = new Arm(hardwareMap, telemetry);
    }

    // Abstract method to be implemented by subclasses
    public abstract void updateComponents(boolean useLiftDeadzone);
}


