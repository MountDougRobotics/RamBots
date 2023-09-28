/*
* TestDrive - A simple stand-alone holonomic drive program
*
*  */


package org.firstinspires.ftc.teamcode.opmodes

import com.arcrobotics.ftclib.gamepad.GamepadEx
import ftc.rogue.blacksmith.BlackOp
import ftc.rogue.blacksmith.Scheduler
import ftc.rogue.blacksmith.listeners.ReforgedGamepad
import org.firstinspires.ftc.teamcode.components.meta.createTeleOpBotComponents

abstract class baseOpMode : BlackOp() {

    /*
    * This is a base opmode using the BlackSmith BlackOp opmode system.
    * As an extension of the linear opmode provided by the FTC RC, this program uses a linear progress system instead of the asynchronous Iterative Opmode.
    * To use, extend baseOpMode and override functions as needed
    */

    protected val driver   by createOnGo<ReforgedGamepad> { gamepad1 }
    protected val codriver by createOnGo<ReforgedGamepad> { gamepad2 }

//    protected val robot by createOnGo<robot>()
    protected val bot by evalOnGo(::createTeleOpBotComponents)

    protected var powerMulti = 0.0


    lateinit var driverOp: GamepadEx

    final override fun go() { // ? go() instead of runOpMode()

        describeControls()

        Scheduler.launchOnStart(this) { // * Standard Schedular model
            mTelemetry.addData("Hello", "World!") // ? Logs to both driver station and FTCDashboard
        }

        waitForStart()

        Scheduler.debug({ opModeIsActive() && !isStopRequested }) { // * Schedular model using debug for loop time and other extra info
            bot.drivetrain.drive(driver.gamepad, powerMulti) // * Drive Code Here
            bot.updateComponents(useLiftDeadzone = true)

            // bot.lift.printLiftTelem()
            mTelemetry.addData("Loop time", loopTime)
            mTelemetry.update()
        }
    }

    abstract fun describeControls()




}