package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.components.meta.AutoBotComponents;
import org.firstinspires.ftc.teamcode.components.meta.TeleOpBotComponents;

public abstract class javaBaseAuton extends LinearOpMode {
    /*
     * As an extension of the linear opmode provided by the FTC RC, this program uses a linear progress system instead of the asynchronous Iterative Opmode.
     * To use, extend BaseAuton and override functions as needed
     */


    AutoBotComponents bot;
    double powerMulti = 0.0;

    @Override
    public void runOpMode() {
        bot = AutoBotComponents.createAutoBotComponents(hardwareMap, telemetry);
        telemetry.addData("Hello", "World!");

        initialize();

        waitForStart();

        describePath();
    }

    abstract void initialize();

    abstract void describePath();
}
