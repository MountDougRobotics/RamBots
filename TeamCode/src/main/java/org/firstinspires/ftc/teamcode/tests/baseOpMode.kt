/*
* TestDrive - A simple stand-alone holonomic drive program
*
*  */


package org.firstinspires.ftc.teamcode.opmodes

import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import ftc.rogue.blacksmith.BlackOp
import ftc.rogue.blacksmith.Scheduler
import ftc.rogue.blacksmith.listeners.ReforgedGamepad
import org.firstinspires.ftc.teamcode.components.meta.TeleOpBotComponents
import org.firstinspires.ftc.teamcode.components.meta.createTeleOpBotComponents

open class baseOpMode : LinearOpMode() {

    /*
    * This is a base opmode using the BlackSmith BlackOp opmode system.
    * As an extension of the linear opmode provided by the FTC RC, this program uses a linear progress system instead of the asynchronous Iterative Opmode.
    * To use, extend baseOpMode and override functions as needed
    */

    lateinit var driver: ReforgedGamepad
    lateinit var codriver: ReforgedGamepad

//    protected val robot by createOnGo<robot>()
    lateinit var bot: TeleOpBotComponents//by evalOnGo(::createTeleOpBotComponents)

    protected var powerMulti = 0.0


    lateinit var driverOp: GamepadEx

    final override fun runOpMode() {
        driver = ReforgedGamepad(gamepad1)
        codriver = ReforgedGamepad(gamepad2)
        bot = createTeleOpBotComponents(hardwareMap)

        describeControls()

        Scheduler.launchOnStart(this) { // * Standard Schedular model
            telemetry.addData("Hello", "World!")
        }

        waitForStart()

        Scheduler.debug({ opModeIsActive() && !isStopRequested }) { // * Schedular model using debug for loop time and other extra info
            bot.drivetrain.drive(driver.gamepad, powerMulti) // * Drive Code Here
            bot.updateComponents(useLiftDeadzone = true)

            // bot.lift.printLiftTelem()
            telemetry.addData("Loop time", loopTime)
            telemetry.update()
        }
    }

    open fun describeControls() {}




}