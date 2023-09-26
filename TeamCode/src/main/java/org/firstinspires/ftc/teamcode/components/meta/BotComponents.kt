package org.firstinspires.ftc.teamcode.components.meta

// ! All the code in this file should not be tampered with


import com.qualcomm.hardware.rev.RevColorSensorV3
import ftc.rogue.blacksmith.BlackOp.Companion.hwMap
import ftc.rogue.blacksmith.util.kt.invoke
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.components.hardware.*
import org.firstinspires.ftc.teamcode.components.vision.VisionPortal


abstract class BaseBotComponents {
    val claw   = Claw()
//    val intake = Intake()
//    val arm    = Arm()
//    val wrist  = Wrist()
//    val lift   = Lift()

    open fun updateComponents(useLiftDeadzone: Boolean) {
        claw.update()
//        arm.update()
//        wrist.update()
    }
}

fun createTeleOpBotComponents() =
    TeleOpBotComponents(
        hwMap(DeviceNames.COLOR_SENSOR),
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

fun createAutoBotComponents() =
    AutoBotComponents(
        SampleMecanumDrive(hwMap),
        VisionPortal(),
    )

data class AutoBotComponents(
    val drive: SampleMecanumDrive,
    val camera: VisionPortal,
) : BaseBotComponents() {
    override fun updateComponents(useLiftDeadzone: Boolean) {
        super.updateComponents(useLiftDeadzone)
        //VisionPortal.update()
        drive.update()
        //lift.updateAutoLiftNormalPID()
    }
}