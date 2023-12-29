package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.components.device.ColourMassDetectionProcessor;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

public class javaAuton extends javaBaseAuton{
    TrajectorySequence RedBotRight;
    TrajectorySequence BlueBotLeft;

    @Override
    void initialize() {
        RedBotRight = bot.drive.trajectorySequenceBuilder(new Pose2d(-35.79, -62.15, Math.toRadians(90.00)))
                .splineTo(new Vector2d(-34.94, -30.60), Math.toRadians(0.00))
                .lineToSplineHeading(new Pose2d(-51.04, -30.60, Math.toRadians(180.00)))
                .splineTo(new Vector2d(-48.81, -11.96), Math.toRadians(2.05))
                .splineTo(new Vector2d(38.01, -36.42), Math.toRadians(-90.00))
                .splineTo(new Vector2d(49.98, -44.05), Math.toRadians(0.00))
                .splineTo(new Vector2d(46.16, -43.94), Math.toRadians(180.00))
                .splineTo(new Vector2d(60.67, -12.71), Math.toRadians(2.08))
                .build();
        bot.drive.setPoseEstimate(RedBotRight.start());

        BlueBotLeft = bot.drive.trajectorySequenceBuilder(new Pose2d(-35.79, 62.15, Math.toRadians(270.00)))
                .splineTo(new Vector2d(-34.94, 30.60), Math.toRadians(360.00))
                .lineToSplineHeading(new Pose2d(-51.04, 30.60, Math.toRadians(180.00)))
                .splineTo(new Vector2d(-48.81, 11.96), Math.toRadians(357.95))
                .splineTo(new Vector2d(38.01, 36.42), Math.toRadians(450.00))
                .splineTo(new Vector2d(49.98, 44.05), Math.toRadians(360.00))
                .splineTo(new Vector2d(46.16, 43.94), Math.toRadians(180.00))
                .splineTo(new Vector2d(60.67, 12.71), Math.toRadians(357.92))
                .build();
        bot.drive.setPoseEstimate(BlueBotLeft.start());
    }

    @Override
    void describePath() {
        if(bot.camera.detectLocation() == ColourMassDetectionProcessor.PropPositions.RIGHT) bot.drive.followTrajectorySequence(RedBotRight);
        else if(bot.camera.detectLocation() == ColourMassDetectionProcessor.PropPositions.MIDDLE) bot.drive.followTrajectorySequence(RedBotRight);

    }
}
