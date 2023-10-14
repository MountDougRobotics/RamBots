package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.components.meta.TeleOpBotComponents
import org.firstinspires.ftc.teamcode.components.meta.createTeleOpBotComponents

open class baseOpMode : LinearOpMode() {

    /*
    * This is a base opmode using the BlackSmith BlackOp opmode system.
    * As an extension of the linear opmode provided by the FTC RC, this program uses a linear progress system instead of the asynchronous Iterative Opmode.
    * To use, extend baseOpMode and override functions as needed
    */

    lateinit var driver: Gamepad
    lateinit var codriver: Gamepad

    //    protected val robot by createOnGo<robot>()
    lateinit var bot: TeleOpBotComponents//by evalOnGo(::createTeleOpBotComponents)

    protected var powerMulti = 0.0
    final override fun runOpMode() {
        driver = gamepad1
        codriver = gamepad2
        bot = createTeleOpBotComponents(hardwareMap, telemetry)

        //describeControls()

        telemetry.addData("Hello", "World!")


        waitForStart()

        while ( opModeIsActive() && !isStopRequested ) { // * Schedular model using debug for loop time and other extra info
            bot.drivetrain.drive(driver, 1.0) // * Drive Code Here
            bot.arm.update(driver)
            //bot.updateComponents(useLiftDeadzone = true)
        }
    }

    //open fun describeControls() {}




}