package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
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

@Autonomous(name = "Auto Blue Close")
public class AutonPeriodBlueClose extends LinearOpMode {
    static final int STREAM_WIDTH = 1920; // modify for your camera
    static final int STREAM_HEIGHT = 1080; // modify for your camera
    OpenCvWebcam webcam;
    PropDetectionBlue pipeline;
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

        // VISION CODE START
        while (opModeIsActive()) {
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
            sleep(200000);
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



// Credits to team 7303 RoboAvatars,
// Edited By Rambots for their prop
class PropDetectionBlue extends OpenCvPipeline {
    Scalar Red = new Scalar(252, 71, 89); //it isnt hotpink its red BOUNDING BOX

    // Pink, the default color                         Y      Cr     Cb    (Do not change Y)
    public static Scalar scalarLowerYCrCb = new Scalar(0.0, 5.0, 130.0);
    public static Scalar scalarUpperYCrCb = new Scalar(255.0, 100.0, 255.0);

    // Yellow, freight or ducks!
    //public static Scalar scalarLowerYCrCb = new Scalar(0.0, 100.0, 0.0);
    //public static Scalar scalarUpperYCrCb = new Scalar(255.0, 170.0, 120.0);

    // Green                                             Y      Cr     Cb
    // public static Scalar scalarLowerYCrCb = new Scalar(  0.0, 0.0, 0.0);
    // public static Scalar scalarUpperYCrCb = new Scalar(255.0, 120.0, 120.0);

    // Use this picture for you own color https://github.com/PinkToTheFuture/OpenCV_FreightFrenzy_2021-2022/blob/main/YCbCr.jpeg
    // Note that the Cr and Cb values range between 0-255. this means that the origin of the coordinate system is (128,128)

    // Volatile because accessed by OpMode without sync
    public volatile boolean error = false;
    public volatile Exception debug;

    private double borderLeftX;     //fraction of pixels from the left side of the cam to skip
    private double borderRightX;    //fraction of pixels from the right of the cam to skip
    private double borderTopY;      //fraction of pixels from the top of the cam to skip
    private double borderBottomY;   //fraction of pixels from the bottom of the cam to skip

    private int CAMERA_WIDTH;
    private int CAMERA_HEIGHT;

    private int loopCounter = 0;
    private int pLoopCounter = 0;

    private final Mat mat = new Mat();
    private final Mat processed = new Mat();

    private Rect maxRect = new Rect(600,1,1,1);

    private double maxArea = 0;
    private boolean first = false;

    private final Object sync = new Object();

    public PropDetectionBlue(double borderLeftX, double borderRightX, double borderTopY, double borderBottomY) {
        this.borderLeftX = borderLeftX;
        this.borderRightX = borderRightX;
        this.borderTopY = borderTopY;
        this.borderBottomY = borderBottomY;
    }
    public void configureScalarLower(double y, double cr, double cb) {
        scalarLowerYCrCb = new Scalar(y, cr, cb);
    }
    public void configureScalarUpper(double y, double cr, double cb) {
        scalarUpperYCrCb = new Scalar(y, cr, cb);
    }
    public void configureScalarLower(int y, int cr, int cb) {
        scalarLowerYCrCb = new Scalar(y, cr, cb);
    }
    public void configureScalarUpper(int y, int cr, int cb) {
        scalarUpperYCrCb = new Scalar(y, cr, cb);
    }
    public void configureBorders(double borderLeftX, double borderRightX, double borderTopY, double borderBottomY) {
        this.borderLeftX = borderLeftX;
        this.borderRightX = borderRightX;
        this.borderTopY = borderTopY;
        this.borderBottomY = borderBottomY;
    }

    @Override
    public Mat processFrame(Mat input) {
        CAMERA_WIDTH = input.width();
        CAMERA_HEIGHT = input.height();
        try {
            // Process Image
            Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2YCrCb);
            Core.inRange(mat, scalarLowerYCrCb, scalarUpperYCrCb, processed);
            // Remove Noise
            Imgproc.morphologyEx(processed, processed, Imgproc.MORPH_OPEN, new Mat());
            Imgproc.morphologyEx(processed, processed, Imgproc.MORPH_CLOSE, new Mat());
            // GaussianBlur
            Imgproc.GaussianBlur(processed, processed, new Size(5.0, 15.0), 0.00);
            // Find Contours
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(processed, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

            // Draw Contours
            Imgproc.drawContours(input, contours, -1, new Scalar(255, 0, 0));

            // Lock this up to prevent errors when outside threads access the max rect property.
            synchronized (sync) {
                // Loop Through Contours
                for (MatOfPoint contour : contours) {
                    Point[] contourArray = contour.toArray();

                    // Bound Rectangle if Contour is Large Enough
                    if (contourArray.length >= 15) {
                        MatOfPoint2f areaPoints = new MatOfPoint2f(contourArray);
                        Rect rect = Imgproc.boundingRect(areaPoints);

                        if (                        rect.area() > maxArea
                                && rect.x + (rect.width / 2.0)  > (borderLeftX * CAMERA_WIDTH)
                                && rect.x + (rect.width / 2.0)  < CAMERA_WIDTH - (borderRightX * CAMERA_WIDTH)
                                && rect.y + (rect.height / 2.0) > (borderTopY * CAMERA_HEIGHT)
                                && rect.y + (rect.height / 2.0) < CAMERA_HEIGHT - (borderBottomY * CAMERA_HEIGHT)

                                || loopCounter - pLoopCounter   > 6
                                && rect.x + (rect.width / 2.0)  > (borderLeftX * CAMERA_WIDTH)
                                && rect.x + (rect.width / 2.0)  < CAMERA_WIDTH - (borderRightX * CAMERA_WIDTH)
                                && rect.y + (rect.height / 2.0) > (borderTopY * CAMERA_HEIGHT)
                                && rect.y + (rect.height / 2.0) < CAMERA_HEIGHT - (borderBottomY * CAMERA_HEIGHT)
                        ){
                            maxArea = rect.area();
                            maxRect = rect;
                            pLoopCounter++;
                            loopCounter = pLoopCounter;
                            first = true;
                        }
                        else if(loopCounter - pLoopCounter > 10){
                            maxArea = new Rect().area();
                            maxRect = new Rect();
                        }

                        areaPoints.release();
                    }
                    contour.release();
                }
                if (contours.isEmpty()) {
                    maxRect = new Rect(600,1,1,1);
                }
            }
            // Draw Rectangles If Area Is At Least 500
            if (first && maxRect.area() > 500) {
                Imgproc.rectangle(input, maxRect, new Scalar(0, 255, 0), 2);
            }
            // Draw Borders
            Imgproc.rectangle(input, new Rect(
                    (int) (borderLeftX * CAMERA_WIDTH),
                    (int) (borderTopY * CAMERA_HEIGHT),
                    (int) (CAMERA_WIDTH - (borderRightX * CAMERA_WIDTH) - (borderLeftX * CAMERA_WIDTH)),
                    (int) (CAMERA_HEIGHT - (borderBottomY * CAMERA_HEIGHT) - (borderTopY * CAMERA_HEIGHT))
            ), Red, 2);

            // Display Data
            Imgproc.putText(input, "Area: " + getRectArea() + " Midpoint: " + getRectMidpointXY().x + " , " + getRectMidpointXY().y, new Point(5, CAMERA_HEIGHT - 5), 0, 0.6, new Scalar(255, 255, 255), 2);

            loopCounter++;
        } catch (Exception e) {
            debug = e;
            error = true;
        }
        return input;
    }
    /*
    Synchronize these operations as the user code could be incorrect otherwise, i.e a property is read
    while the same rectangle is being processed in the pipeline, leading to some values being not
    synced.
     */

    public int getRectHeight() {
        synchronized (sync) {
            return maxRect.height;
        }
    }
    public int getRectWidth() {
        synchronized (sync) {
            return maxRect.width;
        }
    }
    public int getRectX() {
        synchronized (sync) {
            return maxRect.x;
        }
    }
    public int getRectY() {
        synchronized (sync) {
            return maxRect.y;
        }
    }
    public double getRectMidpointX() {
        synchronized (sync) {
            return getRectX() + (getRectWidth() / 2.0);
        }
    }
    public double getRectMidpointY() {
        synchronized (sync) {
            return getRectY() + (getRectHeight() / 2.0);
        }
    }
    public Point getRectMidpointXY() {
        synchronized (sync) {
            return new Point(getRectMidpointX(), getRectMidpointY());
        }
    }
    public double getAspectRatio() {
        synchronized (sync) {
            return getRectArea() / (CAMERA_HEIGHT * CAMERA_WIDTH);
        }
    }
    public double getRectArea() {
        synchronized (sync) {
            return maxRect.area();
        }
    }
}