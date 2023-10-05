package org.firstinspires.ftc.teamcode.components.meta

import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.components.hardware.*

abstract class BaseBotComponents {
//    val claw   = Claw()
//    val intake = Intake()
//    val arm    = Arm()
//    val wrist  = Wrist()
//    val lift   = Lift()
//
//    open fun updateComponents(useLiftDeadzone: Boolean) {
//        claw.update()
//        arm.update()
//        wrist.update()
//    }
}

fun createTeleOpBotComponents(hardwareMap: HardwareMap) =
    TeleOpBotComponents(
        //hardwareMap.get(RevColorSensorV3::class.java, DeviceNames.COLOR_SENSOR),
        DriveTrain(hardwareMap),
    )

data class TeleOpBotComponents(
    //val rcs: RevColorSensorV3,
    val drivetrain: DriveTrain,
) : BaseBotComponents() {
//    override fun updateComponents(useLiftDeadzone: Boolean) {
//        super.updateComponents(useLiftDeadzone)
//        lift.updateTeleopNormalPID(false)
//    }
}

//fun createAutoBotComponents(hardwareMap: HardwareMap) =
//    AutoBotComponents(
//        SampleMecanumDrive(
//            hardwareMap
//        ),
//        //Camera(),
//    )
//
//data class AutoBotComponents(
//    val drive: SampleMecanumDrive,
//    //val camera: Camera,
//) : BaseBotComponents() {
////    override fun updateComponents(useLiftDeadzone: Boolean) {
////        super.updateComponents(useLiftDeadzone)
////        camera.update()
////        drive.update()
////        lift.updateAutoLiftNormalPID()
////    }
//}