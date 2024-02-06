package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.components.device.ColourMassDetectionProcessor;
import org.firstinspires.ftc.teamcode.components.meta.Hardware;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous
public class javaAuton extends javaBaseAuton{
    TrajectorySequence RedBotRight;
    TrajectorySequence BlueBotLeft;
    DcMotorEx frontLeft, frontRight, backLeft, backRight;

    @Override
    void initialize() {
//        RedBotRight = bot.drive.trajectorySequenceBuilder(new Pose2d(-36.21, -60.00, Math.toRadians(90.00)))
//                .splineTo(new Vector2d(-35.58, -30.49), Math.toRadians(0.00))
//                .addDisplacementMarker(() -> {
//                    // * Drop Purple
//                })
//                .splineTo(new Vector2d(-47.54, -30.18), Math.toRadians(180.00))
//                .splineTo(new Vector2d(-9.11, -11.00), Math.toRadians(0.40))
//                .splineTo(new Vector2d(27.53, -11.00), Math.toRadians(0.00))
//                .splineToConstantHeading(new Vector2d(50.19, -30.07), Math.toRadians(0.00))
//                .addDisplacementMarker(() -> {
//                    // * Check if right place?
//                })
//                .splineToConstantHeading(new Vector2d(42.99, -29.86), Math.toRadians(0.00))
//                .splineToConstantHeading(new Vector2d(32.93, -12.49), Math.toRadians(180.00))
//                .splineToConstantHeading(new Vector2d(61.73, -11.96), Math.toRadians(0.00))
//                .build();
        frontLeft = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_FL);// Motor vars
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_FR);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE); // ! FIX ANDERSON POWERPOLE

        backLeft = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_BL);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_BR);

    }

    @Override
    void describePath() {
        switch (bot.camera.detectLocation()) {
            case LEFT:
                telemetry.addData("POS", "LEFT");
                rotate(-1, 100);
                move(-1, 200);
                break;
            case MIDDLE:
                telemetry.addData("POS", "MIDDLE");
                move(-1, 400);
                break;
            case RIGHT:
                telemetry.addData("POS", "RIGHT");
                rotate(1, 100);
                move(-1, 200);
                break;
        }
        telemetry.update();
//        if(bot.camera.detectLocation() == ColourMassDetectionProcessor.PropPositions.RIGHT) {
//            bot.drive.followTrajectorySequence(RedBotRight);
//            bot.drive.setPoseEstimate(RedBotRight.start());
//        }
//
//        else if(bot.camera.detectLocation() == ColourMassDetectionProcessor.PropPositions.MIDDLE) bot.drive.followTrajectorySequence(RedBotRight);
    }
    public void move(double pow, long dur){
        frontLeft.setPower(pow);
        backLeft.setPower(pow);
        frontRight.setPower(pow);
        backRight.setPower(pow);
        sleep(dur);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    public void rotate(double pow, long dur){
        frontLeft.setPower(pow);
        backLeft.setPower(pow);
        frontRight.setPower(-pow);
        backRight.setPower(-pow);
        sleep(dur);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }
}
