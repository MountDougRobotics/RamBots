package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.components.meta.TeleOpBotComponents;

public abstract class javaBaseTeleOp extends LinearOpMode {

    /*
     * As an extension of the linear opmode provided by the FTC RC, this program uses a linear progress system instead of the asynchronous Iterative Opmode.
     * To use, extend BaseOpMode and override functions as needed
     */

    Gamepad driver;
    Gamepad codriver;

    TeleOpBotComponents bot;
    double powerMulti = 0.0;

    @Override
    public void runOpMode() {
        driver = gamepad1;
        codriver = gamepad2;
        bot = TeleOpBotComponents.createTeleOpBotComponents(hardwareMap, telemetry, driver, codriver);

        describeControls();

        telemetry.addData("Hello", "World!");

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            bot.updateComponents(true);
        }
    }

    abstract void describeControls();
}