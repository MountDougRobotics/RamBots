package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;
import java.util.List;

@Disabled

@Autonomous(name = "Auto Red Far")
public class AutonPeriodRedFar extends LinearOpMode {
    static final int STREAM_WIDTH = 1280; // modify for your camera
    static final int STREAM_HEIGHT = 720; // modify for your camera
    OpenCvWebcam webcam;
    PropDetectionRed pipeline;
    public static Scalar scalarLowerYCrCb = new Scalar(60.0, 170.0, 100.0);
    public static Scalar scalarUpperYCrCb = new Scalar(255.0, 255.0, 255.0);

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

    @Override
    public void runOpMode() {

        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();

        backRightMotor = hardwareMap.get(DcMotor.class, "BR");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BL");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FR");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FL");
        hook = hardwareMap.get(Servo.class, "HK");

        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        hook.setPosition(0.25);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = null;
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        FtcDashboard.getInstance().startCameraStream(webcam, 0);

        webcam.setPipeline(pipeline = new PropDetectionRed(borderLeftX,borderRightX,borderTopY,borderBottomY));
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

        // VISION CODE START
        if (opModeIsActive()) {
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

                strafeLeft(0.5, 575);
                driveBack(0.5, 800);
                stopAllMotors();
                sleep(200);
                dropPixel();



            } else if (propLocation.equals("center")) {
                driveBack(0.5, 1500);
                driveForward(.25, 800);
                stopAllMotors();
                sleep(500);
                dropPixel();
                sleep(1000);
                driveForward(.5, 700);
//                driveForward(.5, 320);
//                turnLeft(0.5, 830);
//                driveBack(.5, 3800);
//                driveForward(.5, 300);
//                strafeLeft(.5, 1400);
//                driveBack(.5, 600);

            } else {
                driveBack(0.5, 1000);
                strafeRight(0.5, 550);
                driveForward(.5, 300);
                dropPixel();
//                driveForward(0.5, 3000);
//                turnRight(0.5, 1600);
//                driveBack(0.5, 1000);
//                strafeRight(0.5, 400);
//                strafeLeft(0.5, 1400);
//                driveBack(0.5, 600);
            }

            sleep(20000);

        }
    }

    private void dropPixel() {
        hook.setPosition(1);
    }


    public void driveBack(double power, long duration) {
        backRightMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(-power);
        sleep(duration);
    }

    public void driveForward(double power, long duration) {
        backRightMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        sleep(duration);
    }

    public void strafeLeft(double power, long duration) {
        backRightMotor.setPower(-power);
        backLeftMotor.setPower(power);
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        sleep(duration);
    }

    public void strafeRight(double power, long duration) {
        backRightMotor.setPower(power);
        backLeftMotor.setPower(-power);
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        sleep(duration);
    }

    public void turnRight(double power, long duration) {
        backRightMotor.setPower(-power);
        backLeftMotor.setPower(power);
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        sleep(duration);
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