package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.components.device.Camera;
import org.firstinspires.ftc.teamcode.components.device.ColourMassDetectionProcessor;
import org.firstinspires.ftc.teamcode.components.meta.Hardware;
import org.firstinspires.ftc.teamcode.components.meta.MotorGroup;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Scalar;

/*
* temporary hard coded auton with color mass on camera
 */

@Autonomous
public class hardCodedAuton extends LinearOpMode {
    Scalar lowerRed = new Scalar(150.0, 100.0, 100.0); // the lower hsv threshold for your detection
    Scalar upperRed = new Scalar(180.0, 255.0, 255.0); // the upper hsv threshold for your detection
    // TODO: Allow Blue and Red Switching with Hardcoded Opmodes
    Scalar lowerBlue = new Scalar(90.0, 100.0, 100.0); // the lower hsv threshold for your detection
    Scalar upperBlue = new Scalar(120.0, 255.0, 255.0); // the upper hsv threshold for your detection

    double minArea = 100.0; // the minimum area for the detection to consider for your prop

    ColourMassDetectionProcessor colourMassDetectionProcessor;
    AprilTagProcessor aprilTagProcessor;
    public VisionPortal visionPortal;

    DcMotorEx frontLeft, frontRight, backLeft, backRight;

    @Override
    public void runOpMode() throws InterruptedException {
        colourMassDetectionProcessor = new ColourMassDetectionProcessor(
                lowerRed,
                upperRed,
                () -> minArea, // these are lambda methods, in case we want to change them while the match is running, for us to tune them or something
                () -> 213, // the left dividing line, in this case the left third of the frame
                () -> 426 // the left dividing line, in this case the right third of the frame
        );

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, Hardware.WEBCAM1))
                .addProcessor(colourMassDetectionProcessor)
                .addProcessor(aprilTagProcessor)
                .build();


        frontLeft = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_FL);// Motor vars
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_FR);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE); // ! FIX ANDERSON POWERPOLE

        backLeft = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_BL);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_BR);

        telemetry.addData("Currently Recorded Position", detectLocation());
        telemetry.addData("Camera State", visionPortal.getCameraState());
        waitForStart();

// shuts down the camera once the match starts, we dont need to look any more
        if (visionPortal.getCameraState() == VisionPortal.CameraState.STREAMING) {
            visionPortal.stopLiveView();
            visionPortal.stopStreaming();
        }

        // gets the recorded prop position
        ColourMassDetectionProcessor.PropPositions recordedPropPosition = detectLocation();

        // now we can use recordedPropPosition to determine where the prop is! if we never saw a prop, your recorded position will be UNFOUND.
        // if it is UNFOUND, you can manually set it to any of the other positions to guess
        if (recordedPropPosition == ColourMassDetectionProcessor.PropPositions.UNFOUND) {
            recordedPropPosition = ColourMassDetectionProcessor.PropPositions.MIDDLE;
        }
        switch (recordedPropPosition) {
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

    }
    public ColourMassDetectionProcessor.PropPositions detectLocation() { // ? actual function used to detect Prop position, call this when needed
        // gets the recorded prop position

        // gets the recorded prop position
        ColourMassDetectionProcessor.PropPositions recordedPropPosition = colourMassDetectionProcessor.getRecordedPropPosition();

        // now we can use recordedPropPosition to determine where the prop is! if we never saw a prop, your recorded position will be UNFOUND.
        // if it is UNFOUND, you can manually set it to any of the other positions to guess

        // now we can use recordedPropPosition to determine where the prop is! if we never saw a prop, your recorded position will be UNFOUND.
        // if it is UNFOUND, you can manually set it to any of the other positions to guess
        if (recordedPropPosition == ColourMassDetectionProcessor.PropPositions.UNFOUND) {
            recordedPropPosition = ColourMassDetectionProcessor.PropPositions.MIDDLE;
        }

        return recordedPropPosition;
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
