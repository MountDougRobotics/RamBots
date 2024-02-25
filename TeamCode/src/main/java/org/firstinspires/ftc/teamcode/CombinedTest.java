package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.FtcDashboard;
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

@Autonomous(name = "Combined Test based on Blue Close")
public class CombinedTest extends LinearOpMode {
    static final int STREAM_WIDTH = 1920; // modify for your camera
    static final int STREAM_HEIGHT = 1080; // modify for your camera
    OpenCvWebcam webcam;
    PropDetectionBlue pipeline;

    static final double FEET_PER_METER = 3.28084;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    double tagsize = 0.166;
    int ID_TAG_OF_INTEREST = 3; // TODO dynamic set ID
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

    @Override
    public void runOpMode() {

        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();

        backRightMotor = hardwareMap.get(DcMotor.class, "BR");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BL");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FR");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FL");

        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = null;
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        FtcDashboard.getInstance().startCameraStream(webcam, 0);


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




        if (propLocation.equals("left")) {
            strafeLeft(0.5, 500);
            driveBack(0.5, 800);
            turnLeft(0.5, 785);
            driveBack(0.5, 1150);
            strafeLeft(0.5, 100);
            strafeRight(0.5, 1700);
            driveBack(0.5, 600);
        } else if (propLocation.equals("center")) {
            driveBack(0.5, 1000);
            turnLeft(0.5, 785);
            driveBack(0.5, 1600);
            strafeLeft(0.5, 150);
            driveBack(0.5, 300);
            strafeRight(.5, 1000);
            driveBack(0.5, 600);
        } else {
            driveBack(.5, 900);
            turnRight(.5, 500);
            driveBack(.5, 300);
            driveForward(.5, 300);
            turnRight(.5, 1750);
            driveBack(.5, 1770);
            strafeRight(.5, 370);
            driveBack(.5, 300);
            driveForward(.5, 300);
            strafeRight(.5, 850);
            driveBack(.5, 600);
        }


        // ! ADDED STUFF
        while (!isStopRequested() && !isStarted()) {
            detectTag();
            sleep(20);
        }

        // TODO go to pos

        /* Actually do something useful */
        if(tagOfInterest != null)
        {
            /*
             * Insert your autonomous code here, probably using the tag pose to decide your configuration.
             */
            // e.g.
            while (tagOfInterest.pose.x >= -0.05 && tagOfInterest.pose.x <= 0.05 && !isStopRequested()) {
                if(tagOfInterest.pose.x <= -0.05)
                {
                    // TODO Strafe Right
                }
                else if(tagOfInterest.pose.x >= 0.05)
                {
                    // TODO Strafe Left
                }
            }

        }

    }

    public void driveBack(double power, long duration) {
        backRightMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(-power);
        sleep(duration);
        stopAllMotors();
    }

    public void driveForward(double power, long duration) {
        backRightMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        sleep(duration);
        stopAllMotors();
    }

    public void strafeLeft(double power, long duration) {
        backRightMotor.setPower(-power);
        backLeftMotor.setPower(power);
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        sleep(duration);
        stopAllMotors();
    }

    public void strafeRight(double power, long duration) {
        backRightMotor.setPower(power);
        backLeftMotor.setPower(-power);
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        sleep(duration);
        stopAllMotors();
    }

    public void turnRight(double power, long duration) {
        backRightMotor.setPower(-power);
        backLeftMotor.setPower(power);
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        sleep(duration);
        stopAllMotors();
    }

    public void turnLeft(double power, long duration) {
        backRightMotor.setPower(power);
        backLeftMotor.setPower(-power);
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        sleep(duration);
        stopAllMotors();
    }

    public void stopAllMotors() {
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
    }


}
