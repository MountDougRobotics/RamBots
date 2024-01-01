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
        RedBotRight = bot.drive.trajectorySequenceBuilder(new Pose2d(-36.21, -60.00, Math.toRadians(90.00)))
                .splineTo(new Vector2d(-35.58, -30.49), Math.toRadians(0.00))
                .addDisplacementMarker(() -> {
                    // * Drop Purple
                })
                .splineTo(new Vector2d(-47.54, -30.18), Math.toRadians(180.00))
                .splineTo(new Vector2d(-9.11, -11.00), Math.toRadians(0.40))
                .splineTo(new Vector2d(27.53, -11.00), Math.toRadians(0.00))
                .splineToConstantHeading(new Vector2d(50.19, -30.07), Math.toRadians(0.00))
                .addDisplacementMarker(() -> {
                    // * Check if right place?
                })
                .splineToConstantHeading(new Vector2d(42.99, -29.86), Math.toRadians(0.00))
                .splineToConstantHeading(new Vector2d(32.93, -12.49), Math.toRadians(180.00))
                .splineToConstantHeading(new Vector2d(61.73, -11.96), Math.toRadians(0.00))
                .build();


    }

    @Override
    void describePath() {
        if(bot.camera.detectLocation() == ColourMassDetectionProcessor.PropPositions.RIGHT) {
            bot.drive.followTrajectorySequence(RedBotRight);
            bot.drive.setPoseEstimate(RedBotRight.start());
        }

        else if(bot.camera.detectLocation() == ColourMassDetectionProcessor.PropPositions.MIDDLE) bot.drive.followTrajectorySequence(RedBotRight);
    }
}
