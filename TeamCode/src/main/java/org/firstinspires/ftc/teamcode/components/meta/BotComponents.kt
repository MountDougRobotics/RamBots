package org.firstinspires.ftc.teamcode.components.meta

/* ?
? * Bot Component
? * Big bot component where all components all assembled
 * TODO: N/A
? */

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.components.device.Camera
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.components.hardware.*

abstract class BaseBotComponents (hardwareMap: HardwareMap,telemetry: Telemetry) { // ? Components for both TeleOp and Auton
    val claw   = Claw(hardwareMap)
    val arm    = PIDFArm(hardwareMap, telemetry)
    val lift    = Lift(hardwareMap, telemetry)

    open fun updateComponents(useLiftDeadzone: Boolean) { // * Functions that update each tick

    }
}

fun createTeleOpBotComponents(hardwareMap: HardwareMap, telemetry: Telemetry) =
    TeleOpBotComponents(
        DriveTrain(hardwareMap),
        hardwareMap,
        telemetry,
    ) // ? TeleOp component builder

data class TeleOpBotComponents (
    val drivetrain: DriveTrain,
    val hardwareMap: HardwareMap,
    val telemetry: Telemetry,
    ) : BaseBotComponents(hardwareMap, telemetry) { // ? TeleOp
    override fun updateComponents(useLiftDeadzone: Boolean) {
        super.updateComponents(useLiftDeadzone)
    }
}

fun createAutoBotComponents(hardwareMap: HardwareMap, telemetry: Telemetry) =
    AutoBotComponents(
        SampleMecanumDrive(
            hardwareMap
        ),
        Camera(hardwareMap, telemetry),
            hardwareMap,
            telemetry,
    ) // ? Auton component builder

data class AutoBotComponents(
    val drive: SampleMecanumDrive,
    val camera: Camera,
    val hardwareMap: HardwareMap,
    val telemetry: Telemetry,
    ) : BaseBotComponents(hardwareMap, telemetry) { // ? Auton
    override fun updateComponents(useLiftDeadzone: Boolean) {
        super.updateComponents(useLiftDeadzone)
    }
}