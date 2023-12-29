package org.firstinspires.ftc.teamcode.components.meta;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.device.Camera;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

public class AutoBotComponents extends BotComponents {

    public final SampleMecanumDrive drive;
    public final Camera camera;


    public AutoBotComponents(HardwareMap hardwareMap, Telemetry telemetry) {
        super(hardwareMap, telemetry);
        this.drive = new SampleMecanumDrive(hardwareMap);
        this.camera = new Camera(hardwareMap, telemetry);
    }

    @Override
    public void updateComponents(boolean useLiftDeadzone) {
    }

    public static AutoBotComponents createAutoBotComponents(HardwareMap hardwareMap, Telemetry telemetry) {
        return new AutoBotComponents(
                hardwareMap,
                telemetry
        ); // ? TeleOp component builder
    }
}
