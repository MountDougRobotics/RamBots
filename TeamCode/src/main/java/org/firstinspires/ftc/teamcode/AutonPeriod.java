
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


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

@Autonomous
public class AutonPeriod extends LinearOpMode {
    static final int STREAM_WIDTH = 1280; // modify for your camera
    static final int STREAM_HEIGHT = 720; // modify for your camera
    OpenCvWebcam webcam;
    ThePipeline2 pipeline;

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = null;
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        pipeline = new ThePipeline2();
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(STREAM_WIDTH, STREAM_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Failed", "");
                telemetry.update();
            }
        });

        waitForStart(); // Waiting for start button

        // VISION CODE START
        int analysis = pipeline.getAnalysis();

        telemetry.addData("analysis", "start_" + analysis);

    }
}

class ThePipeline2 extends OpenCvPipeline {
    Mat HSV = new Mat();
    Mat hsv1 = new Mat();
    Mat hsv2 = new Mat();
    int avgr;
    int avgm;
    int avgl;
    Scalar lowerBound;
    Scalar upperBound;
    Mat RectR = new Mat();
    Mat RectM = new Mat();
    Mat RectL = new Mat();
    Rect lrect = new Rect(220, 620, 500, 460);
    Rect mrect = new Rect(800, 600, 500, 360);
    Rect rrect = new Rect(1500, 620, 420, 460);
    Rect lrect2 = new Rect(0, 620, 500, 460);
    Rect mrect2 = new Rect(550, 600, 500, 360);
    Rect rrect2 = new Rect(1200, 620, 420, 460);
    boolean rightsideofbarrier = false;
    boolean debugview = true;
    boolean doBlue = false;
    int returnlocation;
    /*
     * This function takes the RGB frame, converts to HSV,
     * and extracts the red color to the '' variable
     */
    void extractColor(Mat input) {
        Imgproc.cvtColor(input, HSV, Imgproc.COLOR_RGB2HSV);  // convert the RGB to HSV
        if (!doBlue){
            // get red pixels (hue > 0)
            lowerBound = new Scalar(0, 200, 30);
            upperBound = new Scalar(5, 255, 255);
            Core.inRange(HSV, lowerBound, upperBound, hsv1);
            // get red pixels (hue < 180)
            lowerBound = new Scalar(170, 200, 30);
            upperBound = new Scalar(180, 255, 255);
            Core.inRange(HSV, lowerBound, upperBound, hsv2);

            Core.add(hsv1, hsv2, HSV);
        }                                     // get red pixels
        else {
            // get blue pixels
            lowerBound = new Scalar(100, 150, 0);
            upperBound = new Scalar(140, 255, 255);
            Core.inRange(HSV, lowerBound, upperBound, hsv1);
            Core.add(hsv1, hsv1, HSV);
        }                                            // get blue pixels
    }
    void drawRects(Mat input) {
        Point ptl = new Point(290, 300);
        Point ptm = new Point(970, 300);
        Point ptr = new Point(1580, 300);
        Scalar col = new Scalar(255);
        Imgproc.putText(HSV, avgl + "", ptl, 1, 10, col, 5);
        Imgproc.putText(HSV, avgm + "", ptm, 1, 10, col, 5);
        Imgproc.putText(HSV, avgr + "", ptr, 1, 10, col, 5);
        if(rightsideofbarrier){
            Imgproc.rectangle(HSV, lrect2, col, 5);
            Imgproc.rectangle(HSV, mrect2, col, 5);
            Imgproc.rectangle(HSV, rrect2, col, 5);
            if (avgl > avgm && avgl > avgr) {
                Imgproc.rectangle(HSV, lrect2, col, 40);
            } else if (avgm > avgr) {
                Imgproc.rectangle(HSV, mrect2, col, 40);
            } else {
                Imgproc.rectangle(HSV, rrect2, col, 40);
            }
        }else {
            Imgproc.rectangle(HSV, lrect, col, 5);
            Imgproc.rectangle(HSV, mrect, col, 5);
            Imgproc.rectangle(HSV, rrect, col, 5);
            if (avgl > avgm && avgl > avgr) {
                Imgproc.rectangle(HSV, lrect, col, 40);
            } else if (avgm > avgr) {
                Imgproc.rectangle(HSV, mrect, col, 40);
            } else {
                Imgproc.rectangle(HSV, rrect, col, 40);
            }
        }
    }

    @Override
    public void init(Mat firstFrame) {
        extractColor(firstFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        extractColor(input);
        if (rightsideofbarrier){
            RectL = HSV.submat(lrect2);
            RectM = HSV.submat(mrect2);
            RectR = HSV.submat(rrect2);
        }else {
            RectL = HSV.submat(lrect);
            RectM = HSV.submat(mrect);
            RectR = HSV.submat(rrect);
        }
        System.out.println("processing requested");
        avgl = (int) Core.mean(RectL).val[0];
        avgm = (int) Core.mean(RectM).val[0];
        avgr = (int) Core.mean(RectR).val[0];
        if(debugview) {
            drawRects(HSV);
        }
        if (avgl >= (avgm + 5) && avgl >= (avgr + 5)) {
            returnlocation = 1;
        } else if (avgm >= (avgl + 5) && avgm >= (avgr + 5)) {
            returnlocation = 2;
        } else if (avgr >= (avgl + 5) && avgr >= (avgm + 5)){
            returnlocation = 3;
        }else{
            returnlocation = 4;
        }

        hsv1.release();   // don't leak memory
        hsv2.release();   //        |
        RectL.release();  //        |
        RectM.release();  //        |
        RectR.release();  //________V__________
        if (debugview)
            return HSV;
        else {
            HSV.release(); // don't leak memory
            return input;
        }
    }
    public int getAnalysis() {
        return returnlocation;
    }
}