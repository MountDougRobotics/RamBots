package org.firstinspires.ftc.teamcode.components.meta

import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.components.hardware.*

abstract class BaseBotComponents (hardwareMap: HardwareMap,telemetry: Telemetry) {
    val claw   = Claw(hardwareMap)
//    val intake = Intake()
    val arm    = Arm(hardwareMap, telemetry)
//    val wrist  = Wrist()
//    val lift   = Lift()
//
    open fun updateComponents(useLiftDeadzone: Boolean) {
        claw.update()
//        arm.update()
//        wrist.update()
    }
}

fun createTeleOpBotComponents(hardwareMap: HardwareMap, telemetry: Telemetry) =
    TeleOpBotComponents(
        //hardwareMap.get(RevColorSensorV3::class.java, DeviceNames.COLOR_SENSOR),
        DriveTrain(hardwareMap),
        hardwareMap,
        telemetry,
    )

data class TeleOpBotComponents (
    //val rcs: RevColorSensorV3,
    val drivetrain: DriveTrain,
    val hardwareMap: HardwareMap,
    val telemetry: Telemetry,

    ) : BaseBotComponents(hardwareMap, telemetry) {
    override fun updateComponents(useLiftDeadzone: Boolean) {
        super.updateComponents(useLiftDeadzone)
//        lift.updateTeleopNormalPID(false)
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
    )

data class AutoBotComponents(
    val drive: SampleMecanumDrive,
    val camera: Camera,
    val hardwareMap: HardwareMap,
    val telemetry: Telemetry,
    ) : BaseBotComponents(hardwareMap, telemetry) {
    override fun updateComponents(useLiftDeadzone: Boolean) {
        super.updateComponents(useLiftDeadzone)
        camera.update()
        drive.update()
//        lift.updateAutoLiftNormalPID()
    }
}