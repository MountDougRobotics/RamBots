package org.firstinspires.ftc.teamcode.components.meta

import com.qualcomm.hardware.rev.RevColorSensorV3
import ftc.rogue.blacksmith.BlackOp.Companion.hwMap
import ftc.rogue.blacksmith.util.kt.invoke
//import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.components.*
import org.firstinspires.ftc.teamcode.components.hardware.drivetrain


abstract class BaseBotComponents {
//    val claw   = Claw()
//    val intake = Intake()
//    val arm    = Arm()
//    val wrist  = Wrist()
//    val lift   = Lift()

    open fun updateComponents(useLiftDeadzone: Boolean) {
//        claw.update()
//        arm.update()
//        wrist.update()
    }
}

fun createTeleOpBotComponents() =
    TeleOpBotComponents(
        hwMap(deviceNames.COLOR_SENSOR),
        drivetrain(),
    )

data class TeleOpBotComponents(
    val rcs: RevColorSensorV3,
    val drivetrain: drivetrain,
) : BaseBotComponents() {
    override fun updateComponents(useLiftDeadzone: Boolean) {
        super.updateComponents(useLiftDeadzone)
        //lift.updateTeleopNormalPID(false)
    }
}

//fun createAutoBotComponents() =
//    AutoBotComponents(
//        SampleMecanumDrive(hwMap),
//        Camera(),
//    )
//
//data class AutoBotComponents(
//    val drive: SampleMecanumDrive,
//    val camera: Camera,
//) : BaseBotComponents() {
//    override fun updateComponents(useLiftDeadzone: Boolean) {
//        super.updateComponents(useLiftDeadzone)
//        camera.update()
//        drive.update()
//        lift.updateAutoLiftNormalPID()
//    }
//}