package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

public class Traj {
    HardwareMap hardwareMap;
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


    TrajectorySequence RedBotRight = drive.trajectorySequenceBuilder(new Pose2d(-36.00, -63.00, Math.toRadians(90.00)))
            .splineTo(new Vector2d(-35.58, -30.49), Math.toRadians(0.00))
            .splineTo(new Vector2d(-47.54, -30.18), Math.toRadians(180.00))
            .splineTo(new Vector2d(-9.11, -11.00), Math.toRadians(0.40))
            .splineTo(new Vector2d(27.53, -11.00), Math.toRadians(0.00))
            .splineToConstantHeading(new Vector2d(49.02, -42.25), Math.toRadians(0.00))
            .splineToConstantHeading(new Vector2d(40.45, -42.46), Math.toRadians(0.00))
            .splineToConstantHeading(new Vector2d(32.93, -12.49), Math.toRadians(180.00))
            .splineToConstantHeading(new Vector2d(61.73, -11.96), Math.toRadians(0.00))
            .build();

    TrajectorySequence BlueBotLeft = drive.trajectorySequenceBuilder(new Pose2d(-36.00, 63.00, Math.toRadians(270.00)))
            .splineTo(new Vector2d(-35.58, 30.49), Math.toRadians(360.00))
            .splineTo(new Vector2d(-47.54, 30.18), Math.toRadians(180.00))
            .splineTo(new Vector2d(-9.11, 11.00), Math.toRadians(359.60))
            .splineTo(new Vector2d(27.53, 11.00), Math.toRadians(360.00))
            .splineToConstantHeading(new Vector2d(49.02, 42.25), Math.toRadians(360.00))
            .splineToConstantHeading(new Vector2d(40.45, 42.46), Math.toRadians(360.00))
            .splineToConstantHeading(new Vector2d(32.93, 12.49), Math.toRadians(180.00))
            .splineToConstantHeading(new Vector2d(61.73, 11.96), Math.toRadians(360.00))
            .build();

    TrajectorySequence RedBotCenter = drive.trajectorySequenceBuilder(new Pose2d(-36.00, -63.00, Math.toRadians(90.00)))
            .splineTo(new Vector2d(-36.64, -38.44), Math.toRadians(90.00))
            .splineToConstantHeading(new Vector2d(-55.80, -31.24), Math.toRadians(90.00))
            .splineTo(new Vector2d(-9.11, -11.00), Math.toRadians(0.40))
            .splineTo(new Vector2d(27.53, -11.00), Math.toRadians(0.00))
            .splineToConstantHeading(new Vector2d(49.55, -36.32), Math.toRadians(0.00))
            .splineToConstantHeading(new Vector2d(40.45, -42.46), Math.toRadians(0.00))
            .splineToConstantHeading(new Vector2d(32.93, -12.49), Math.toRadians(180.00))
            .splineToConstantHeading(new Vector2d(61.73, -11.96), Math.toRadians(0.00))
            .build();

    TrajectorySequence BlueBotCenter = drive.trajectorySequenceBuilder(new Pose2d(-36.00, 63.00, Math.toRadians(270.00)))
            .splineTo(new Vector2d(-36.64, 38.44), Math.toRadians(270.00))
            .splineToConstantHeading(new Vector2d(-55.80, 31.24), Math.toRadians(270.00))
            .splineTo(new Vector2d(-9.11, 11.00), Math.toRadians(359.60))
            .splineTo(new Vector2d(27.53, 11.00), Math.toRadians(360.00))
            .splineToConstantHeading(new Vector2d(49.55, 36.32), Math.toRadians(360.00))
            .splineToConstantHeading(new Vector2d(40.45, 42.46), Math.toRadians(360.00))
            .splineToConstantHeading(new Vector2d(32.93, 12.49), Math.toRadians(180.00))
            .splineToConstantHeading(new Vector2d(61.73, 11.96), Math.toRadians(360.00))
            .build();

    TrajectorySequence RedBotLeft = drive.trajectorySequenceBuilder(new Pose2d(-36.00, -63.00, Math.toRadians(90.00)))
            .splineTo(new Vector2d(-48.39, -43.52), Math.toRadians(90.00))
            .splineToConstantHeading(new Vector2d(-34.09, -46.16), Math.toRadians(90.00))
            .splineTo(new Vector2d(-21.81, -11.01), Math.toRadians(0.40))
            .splineTo(new Vector2d(27.53, -11.00), Math.toRadians(0.00))
            .splineToConstantHeading(new Vector2d(48.81, -29.75), Math.toRadians(0.00))
            .splineToConstantHeading(new Vector2d(40.45, -42.46), Math.toRadians(0.00))
            .splineToConstantHeading(new Vector2d(32.93, -12.49), Math.toRadians(180.00))
            .splineToConstantHeading(new Vector2d(61.73, -11.96), Math.toRadians(0.00))
            .build();

    TrajectorySequence BlueBotRight = drive.trajectorySequenceBuilder(new Pose2d(-36.00, 63.00, Math.toRadians(270.00)))
            .splineTo(new Vector2d(-48.39, 43.52), Math.toRadians(270.00))
            .splineToConstantHeading(new Vector2d(-34.09, 46.16), Math.toRadians(270.00))
            .splineTo(new Vector2d(-21.81, 11.01), Math.toRadians(359.60))
            .splineTo(new Vector2d(27.53, 11.00), Math.toRadians(360.00))
            .splineToConstantHeading(new Vector2d(48.81, 29.75), Math.toRadians(360.00))
            .splineToConstantHeading(new Vector2d(40.45, 42.46), Math.toRadians(360.00))
            .splineToConstantHeading(new Vector2d(32.93, 12.49), Math.toRadians(180.00))
            .splineToConstantHeading(new Vector2d(61.73, 11.96), Math.toRadians(360.00))
            .build();

    TrajectorySequence RedTopRight = drive.trajectorySequenceBuilder(new Pose2d(12.00, -63.00, Math.toRadians(90.00)))
            .splineToConstantHeading(new Vector2d(23.19, -38.22), Math.toRadians(90.00))
            .splineToConstantHeading(new Vector2d(22.87, -43.62), Math.toRadians(-90.00))
            .splineToConstantHeading(new Vector2d(50.51, -43.31), Math.toRadians(0.00))
            .lineTo(new Vector2d(41.61, -61.20))
            .splineToConstantHeading(new Vector2d(62.15, -61.73), Math.toRadians(0.00))
            .build();

    TrajectorySequence BlueTopLeft = drive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(270.00)))
            .splineToConstantHeading(new Vector2d(23.19, 38.22), Math.toRadians(270.00))
            .splineToConstantHeading(new Vector2d(22.87, 43.62), Math.toRadians(450.00))
            .splineToConstantHeading(new Vector2d(50.51, 43.31), Math.toRadians(360.00))
            .lineTo(new Vector2d(41.61, 61.20))
            .splineToConstantHeading(new Vector2d(62.15, 61.73), Math.toRadians(360.00))
            .build();

    TrajectorySequence RedTopCenter = drive.trajectorySequenceBuilder(new Pose2d(12.00, -63.00, Math.toRadians(90.00)))
            .splineToConstantHeading(new Vector2d(12.60, -36.53), Math.toRadians(90.00))
            .splineToConstantHeading(new Vector2d(22.87, -43.62), Math.toRadians(-90.00))
            .splineToConstantHeading(new Vector2d(50.51, -35.79), Math.toRadians(0.00))
            .lineTo(new Vector2d(41.61, -61.20))
            .splineToConstantHeading(new Vector2d(62.15, -61.73), Math.toRadians(0.00))
            .build();

    TrajectorySequence BlueTopCenter = drive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(270.00)))
            .splineToConstantHeading(new Vector2d(12.60, 36.53), Math.toRadians(270.00))
            .splineToConstantHeading(new Vector2d(22.87, 43.62), Math.toRadians(450.00))
            .splineToConstantHeading(new Vector2d(50.51, 35.79), Math.toRadians(360.00))
            .lineTo(new Vector2d(41.61, 61.20))
            .splineToConstantHeading(new Vector2d(62.15, 61.73), Math.toRadians(360.00))
            .build();

    TrajectorySequence RedTopLeft = drive.trajectorySequenceBuilder(new Pose2d(12.00, -63.00, Math.toRadians(90.00)))
            .splineToConstantHeading(new Vector2d(10.91, -34.52), Math.toRadians(180.00))
            .splineToConstantHeading(new Vector2d(22.87, -43.62), Math.toRadians(-90.00))
            .splineToConstantHeading(new Vector2d(50.08, -29.75), Math.toRadians(0.00))
            .lineTo(new Vector2d(41.61, -61.20))
            .splineToConstantHeading(new Vector2d(62.15, -61.73), Math.toRadians(0.00))
            .build();

    TrajectorySequence BlueTopRight = drive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(270.00)))
            .splineToConstantHeading(new Vector2d(10.91, 34.52), Math.toRadians(180.00))
            .splineToConstantHeading(new Vector2d(22.87, 43.62), Math.toRadians(450.00))
            .splineToConstantHeading(new Vector2d(50.08, 29.75), Math.toRadians(360.00))
            .lineTo(new Vector2d(41.61, 61.20))
            .splineToConstantHeading(new Vector2d(62.15, 61.73), Math.toRadians(360.00))
            .build();





}
