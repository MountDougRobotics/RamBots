package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.Trajectory

class simpleAuton: autoBaseOpMode() {
    lateinit var trajectoryA: Trajectory

    override fun describeInit() {
        var trajectoryA = bot.drive.trajectoryBuilder(Pose2d())
                .strafeRight(10.0)
                .forward(5.0)
                .build();

        bot.drive.followTrajectory(trajectoryA)
    }

    override fun describePath() {
        bot.drive.followTrajectory(trajectoryA)
    }

}
