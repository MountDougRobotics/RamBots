package org.firstinspires.ftc.teamcode.components.meta;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.hardware.DriveTrain;

public class TeleOpBotComponents extends BotComponents {

    private final DriveTrain drivetrain;
    private final Gamepad driver;
    private final Gamepad codriver;

    public TeleOpBotComponents(HardwareMap hardwareMap, Telemetry telemetry, Gamepad driver, Gamepad codriver) {
        super(hardwareMap, telemetry);
        this.drivetrain = new DriveTrain(hardwareMap, telemetry);
        this.driver = driver;
        this.codriver = codriver;
    }

    public static TeleOpBotComponents createTeleOpBotComponents(HardwareMap hardwareMap, Telemetry telemetry, Gamepad driver, Gamepad codriver) {
        return new TeleOpBotComponents(
                hardwareMap,
                telemetry,
                driver,
                codriver
        ); // ? TeleOp component builder
    }

    @Override
    public void updateComponents(boolean useLiftDeadzone) {
        this.drivetrain.drive(driver, 1.0); // Drive Code Here
        this.arm.update(driver, codriver);
        this.lift.update(driver, codriver);
        this.claw.update(driver, codriver);
        this.wrist.update(driver, codriver);


    }

}
