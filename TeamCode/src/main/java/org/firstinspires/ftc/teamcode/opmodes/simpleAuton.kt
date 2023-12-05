package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.components.device.ColourMassDetectionProcessor.PropPositions

@Disabled
@Autonomous
class simpleAuton: autoBaseOpMode() {
    lateinit var trajectoryA: Trajectory
    lateinit var propPosition: PropPositions

    override fun describeInit() {
        val startPose = Pose2d(0.0, 0.0, Math.toRadians(0.0))

        //bot.drive.poseEstimate= startPose

        propPosition= bot.camera.detectLocation()


        //bot.drive.followTrajectory(trajectoryA)
    }

    override fun describePath() {
//        when (propPosition) {
//            PropPositions.RIGHT -> {
//                trajectoryA = bot.drive.trajectoryBuilder(Pose2d())
//                    .splineTo(Vector2d(6.0, 8.0), Math.toRadians(0.0))
//                    .build()
//            }
//            PropPositions.LEFT -> {
//                trajectoryA = bot.drive.trajectoryBuilder(Pose2d())
//                    .splineTo(Vector2d(-6.0, 8.0), Math.toRadians(0.0))
//                    .build()
//            }
//            else -> {
//                trajectoryA = bot.drive.trajectoryBuilder(Pose2d())
//                    .splineTo(Vector2d(0.0, 12.0), Math.toRadians(0.0))
//                    .build()
//            }
//        }
//
//        bot.drive.followTrajectory(trajectoryA)
    }

}
