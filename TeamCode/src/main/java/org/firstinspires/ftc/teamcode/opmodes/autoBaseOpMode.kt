package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.components.meta.AutoBotComponents
import org.firstinspires.ftc.teamcode.components.meta.TeleOpBotComponents
import org.firstinspires.ftc.teamcode.components.meta.createAutoBotComponents
import org.firstinspires.ftc.teamcode.components.meta.createTeleOpBotComponents

open class autoBaseOpMode : LinearOpMode() {

    /*
    * As an extension of the linear opmode provided by the FTC RC, this program uses a linear progress system instead of the asynchronous Iterative Opmode.
    * To use, extend baseOpMode and override functions as needed
    */

    lateinit var driver: Gamepad
    lateinit var codriver: Gamepad

    //    protected val robot by createOnGo<robot>()
    lateinit var bot: AutoBotComponents//by evalOnGo(::createTeleOpBotComponents)

    protected var powerMulti = 0.0
    final override fun runOpMode() {
        driver = gamepad1
        codriver = gamepad2
        bot = createAutoBotComponents(hardwareMap, telemetry)

        describeInit()

        telemetry.addData("Hello", "World!")


        waitForStart()

        describePath()

    }

    open fun describePath() {}
    open fun describeInit() {}




}