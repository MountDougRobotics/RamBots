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


abstract class testdrive : BlackOp() {
    protected val driver   by createOnGo<ReforgedGamepad> { gamepad1 }
    protected val codriver by createOnGo<ReforgedGamepad> { gamepad2 }

//    protected val robot by createOnGo<robot>()
    protected val bot by evalOnGo(::createTeleOpBotComponents)

    protected var powerMulti = 0.0


    lateinit var driverOp: GamepadEx

    override fun go() { // go() instead of runOpMode()
        Scheduler.launchOnStart(this) {
            mTelemetry.addData("Hello", "World!") // Logs to both driver station and FTCDashboard
        }

        waitForStart()

        Scheduler.debug({ opModeIsActive() && !isStopRequested }) {
            bot.drivetrain.drive(driver.gamepad, powerMulti)
            bot.updateComponents(useLiftDeadzone = true)

            // bot.lift.printLiftTelem()
            mTelemetry.addData("Loop time", loopTime)
            mTelemetry.update()
        }
    }



}