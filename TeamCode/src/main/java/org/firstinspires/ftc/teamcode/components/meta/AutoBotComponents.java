package org.firstinspires.ftc.teamcode.components.meta;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.device.Camera;

public class AutoBotComponents extends BotComponents {

    //private final SampleMecanumDrive drive;
    private final Camera camera;

    public AutoBotComponents(HardwareMap hardwareMap, Telemetry telemetry) {
        super(hardwareMap, telemetry);
        //this.drive = new SampleMecanumDrive(hardwareMap);
        this.camera = new Camera(hardwareMap, telemetry);
    }

    @Override
    public void updateComponents(boolean useLiftDeadzone) {
    }

    public AutoBotComponents createTeleOpBotComponents(HardwareMap hardwareMap, Telemetry telemetry, Gamepad driver, Gamepad codriver) {
        return new AutoBotComponents(
                hardwareMap,
                telemetry
        ); // ? TeleOp component builder
    }
}
