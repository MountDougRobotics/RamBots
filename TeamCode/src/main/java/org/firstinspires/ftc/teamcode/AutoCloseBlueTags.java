package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Size;
import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "Auto Blue Close w/ Tags")
public class AutoCloseBlueTags extends LinearOpMode {
    static final int STREAM_WIDTH = 1920; // modify for your camera
    static final int STREAM_HEIGHT = 1080; // modify for your camera
    OpenCvWebcam webcam;
    PropDetectionBlue pipeline;
    Rev2mDistanceSensor distance;

    static final double FEET_PER_METER = 3.28084;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    double tagsize = 0.166;
    int ID_TAG_OF_INTEREST = 3;
    AprilTagDetection tagOfInterest = null;

    public static Scalar scalarLowerYCrCb = new Scalar(0.0, 5.0, 130.0);
    public static Scalar scalarUpperYCrCb = new Scalar(255.0, 100.0, 255.0);

    public static double borderLeftX    = 0.0;   //fraction of pixels from the left side of the cam to skip
    public static double borderRightX   = 0.0;   //fraction of pixels from the right of the cam to skip
    public static double borderTopY     = 0.0;   //fraction of pixels from the top of the cam to skip
    public static double borderBottomY  = 0.0;
    public String propLocation = "center";


    private DcMotor backRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor armLiftMotor;
    private DcMotor armLiftMotorExtra;
    private Servo clawServo1;
    private Servo clawServo2;
    private Servo hook;


    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    @SuppressLint("DefaultLocale")
    void tagToTelemetry(AprilTagDetection detection)
    {
        Orientation rot = Orientation.getOrientation(detection.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);

        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", rot.firstAngle));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", rot.secondAngle));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", rot.thirdAngle));
    }

    public void detectTag() {

        ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

        if(currentDetections.size() != 0)
        {
            boolean tagFound = false;

            for(AprilTagDetection tag : currentDetections)
            {
                if(tag.id == ID_TAG_OF_INTEREST)
                {
                    tagOfInterest = tag;
                    tagFound = true;
                    break;
                }
            }

            if(tagFound)
            {
                telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                tagToTelemetry(tagOfInterest);
            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }
            }

        }
        else
        {
            telemetry.addLine("Don't see tag of interest :(");

            if(tagOfInterest == null)
            {
                telemetry.addLine("(The tag has never been seen)");
            }
            else
            {
                telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                tagToTelemetry(tagOfInterest);
            }

        }

        telemetry.update();
    }

    private void dropPixel() {
        hook.setPosition(1);
    }

    @Override
    public void runOpMode() {

        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();

        backRightMotor = hardwareMap.get(DcMotor.class, "BR");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BL");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FR");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FL");
        armLiftMotor = hardwareMap.get(DcMotor.class, "AL1");
        armLiftMotorExtra = hardwareMap.get(DcMotor.class, "AL2");
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        distance = hardwareMap.get(Rev2mDistanceSensor.class, "R2D");
        hook = hardwareMap.get(Servo.class, "HK");
        hook.setPosition(0.25);


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = null;
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        FtcDashboard.getInstance().startCameraStream(webcam, 0);

        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        webcam.setPipeline(aprilTagDetectionPipeline);
        webcam.setPipeline(pipeline = new PropDetectionBlue(borderLeftX,borderRightX,borderTopY,borderBottomY));
        // Configuration of Pipeline
        pipeline.configureScalarLower(scalarLowerYCrCb.val[0],scalarLowerYCrCb.val[1],scalarLowerYCrCb.val[2]);
        pipeline.configureScalarUpper(scalarUpperYCrCb.val[0],scalarUpperYCrCb.val[1],scalarUpperYCrCb.val[2]);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(STREAM_WIDTH, STREAM_HEIGHT, OpenCvCameraRotation.UPRIGHT);
                webcam.setPipeline(pipeline);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Failed", "");
                telemetry.update();
            }
        });
        waitForStart(); // Waiting for start button

        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // VISION CODE START
        waitForStart();
        pipeline.configureBorders(borderLeftX, borderRightX, borderTopY, borderBottomY);

        double propCoords = pipeline.getRectMidpointX();

        if (propCoords < 500.0) propLocation = "left";
        else if (propCoords > 1200) propLocation = "right";
        else propLocation = "center";

        telemetry.addData("Prop Location", propLocation);
        dashboardTelemetry.addData("Prop Location", propLocation);
        dashboardTelemetry.update();
        telemetry.update();

        // 2 ft (one foam square) is close to 1100 ticks
        // for some reason, robot not driving perfectly straight

//            propLocation = "left";
        if (propLocation.equals("left")) {
            ID_TAG_OF_INTEREST = 1; // TODO 4 for Red
            driveForward(1100); //TODO tune
            strafeLeft(400);

        } else if (propLocation.equals("center")) {
            driveForward(1400);
            ID_TAG_OF_INTEREST = 2; // TODO 5 for Red
        } else if (propLocation.equals("right")) {
            driveForward(1100);
            strafeRight(400);
            ID_TAG_OF_INTEREST = 3; // TODO 6 for Red
        }


        turnLeft(1440); // TODO tune // turn towards backdrop
        // TODO turn towards the board
        // ! added stuff to go to right dist?

        // TODO: move arm to place pos
        armLiftMotor.setPower(0.3);
        armLiftMotorExtra.setPower(0.3);
        sleep(100); // tune
        armLiftMotor.setPower(0);
        armLiftMotorExtra.setPower(0);


        while(distance.getDistance(DistanceUnit.CM) >= 10) { // TODO adjust distance if needed
            backRightMotor.setPower(-0.1); // drive forwards until reach
            backLeftMotor.setPower(-0.1);
            frontLeftMotor.setPower(-0.1);
            frontRightMotor.setPower(0.1);

        }

        while (tagOfInterest == null) {
            detectTag();
            sleep(20);
        }

        // e.g.
        while (tagOfInterest.pose.x >= -0.05 && tagOfInterest.pose.x <= 0.05 && !isStopRequested()) {
            detectTag();
            if(tagOfInterest.pose.x <= -0.05)
            {
                backRightMotor.setPower(0.2); // ! strafe right
                backLeftMotor.setPower(-0.2);
                frontLeftMotor.setPower(0.2);
                frontRightMotor.setPower(-0.2);
            }
            else if(tagOfInterest.pose.x >= 0.05)
            {
                backRightMotor.setPower(-0.2); // ! strafe left
                backLeftMotor.setPower(0.2);
                frontLeftMotor.setPower(-0.2);
                frontRightMotor.setPower(0.2);
            }
            sleep(20);
        }
        stopAllMotors();



    }

    public void driveBack(int ticks) {
        backRightMotor.setTargetPosition(-ticks);
        backLeftMotor.setTargetPosition(-ticks);
        frontLeftMotor.setTargetPosition(-ticks);
        frontRightMotor.setTargetPosition(-ticks);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setPower(.2);
        backLeftMotor.setPower(.2);
        frontLeftMotor.setPower(.2);
        frontRightMotor.setPower(.2);

        while (opModeIsActive() && backLeftMotor.isBusy() && backRightMotor.isBusy() && frontLeftMotor.isBusy() && frontRightMotor.isBusy()) {}

        stopAndResetEncoder();
    }

    public void driveForward(int ticks) {
        backRightMotor.setTargetPosition(ticks);
        backLeftMotor.setTargetPosition(ticks);
        frontLeftMotor.setTargetPosition(ticks);
        frontRightMotor.setTargetPosition(ticks);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setPower(.3);
        backLeftMotor.setPower(.3);
        frontLeftMotor.setPower(.3);
        frontRightMotor.setPower(.3);

        while (opModeIsActive() && backLeftMotor.isBusy() && backRightMotor.isBusy() && frontLeftMotor.isBusy() && frontRightMotor.isBusy()) {}

        stopAndResetEncoder();
    }

    public void strafeLeft(int ticks) {
        backRightMotor.setTargetPosition(-ticks);
        backLeftMotor.setTargetPosition(ticks);
        frontLeftMotor.setTargetPosition(-ticks);
        frontRightMotor.setTargetPosition(ticks);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setPower(.3);
        backLeftMotor.setPower(.3);
        frontLeftMotor.setPower(.3);
        frontRightMotor.setPower(.3);

        while (opModeIsActive() && backLeftMotor.isBusy() && backRightMotor.isBusy() && frontLeftMotor.isBusy() && frontRightMotor.isBusy()) {}

        stopAndResetEncoder();
    }
    //
    public void strafeRight(int ticks) {
        backRightMotor.setTargetPosition(ticks);
        backLeftMotor.setTargetPosition(-ticks);
        frontLeftMotor.setTargetPosition(ticks);
        frontRightMotor.setTargetPosition(-ticks);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setPower(.3);
        backLeftMotor.setPower(.3);
        frontLeftMotor.setPower(.3);
        frontRightMotor.setPower(.3);

        while (opModeIsActive() && backLeftMotor.isBusy() && backRightMotor.isBusy() && frontLeftMotor.isBusy() && frontRightMotor.isBusy()) {}

        stopAndResetEncoder();
    }

    public void turnLeft(int ticks) {
        backRightMotor.setTargetPosition(ticks);
        backLeftMotor.setTargetPosition(-ticks);
        frontLeftMotor.setTargetPosition(-ticks);
        frontRightMotor.setTargetPosition(-ticks);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setPower(.3);
        backLeftMotor.setPower(.3);
        frontLeftMotor.setPower(.3);
        frontRightMotor.setPower(.3);

        while (opModeIsActive() && backLeftMotor.isBusy() && backRightMotor.isBusy() && frontLeftMotor.isBusy() && frontRightMotor.isBusy()) {}

        stopAndResetEncoder();
    }
    //
    public void turnRight(int ticks) {
        backRightMotor.setTargetPosition(-ticks);
        backLeftMotor.setTargetPosition(ticks);
        frontLeftMotor.setTargetPosition(ticks);
        frontRightMotor.setTargetPosition(ticks);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setPower(.3);
        backLeftMotor.setPower(.3);
        frontLeftMotor.setPower(.3);
        frontRightMotor.setPower(.3);

        while (opModeIsActive() && backLeftMotor.isBusy() && backRightMotor.isBusy() && frontLeftMotor.isBusy() && frontRightMotor.isBusy()) {}

        stopAndResetEncoder();
    }

    public void stopAndResetEncoder() {
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void stopAllMotors() {
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
    }


}