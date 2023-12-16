package org.firstinspires.ftc.teamcode.components.device;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.components.meta.Hardware;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Scalar;

public class Camera {
    HardwareMap hardwareMap;
    Telemetry telemetry;

    // the current range set by lower and upper is the full range
    // HSV takes the form: (HUE, SATURATION, VALUE)
    // which means to select our colour, only need to change HUE
    // the domains are: ([0, 180], [0, 255], [0, 255])
    // this is tuned to detect red, so you will need to experiment to fine tune it for your robot
    // and experiment to fine tune it for blue
    // ! THESE VALUES ARE TUNED BUT NOT TESTED!
    Scalar lowerRed = new Scalar(150.0, 100.0, 100.0); // the lower hsv threshold for your detection
    Scalar upperRed = new Scalar(180.0, 255.0, 255.0); // the upper hsv threshold for your detection
    // TODO: Allow Blue and Red Switching with Hardcoded Opmodes
    Scalar lowerBlue = new Scalar(90.0, 100.0, 100.0); // the lower hsv threshold for your detection
    Scalar upperBlue = new Scalar(120.0, 255.0, 255.0); // the upper hsv threshold for your detection

    double minArea = 100.0; // the minimum area for the detection to consider for your prop

    ColourMassDetectionProcessor colourMassDetectionProcessor;
    AprilTagProcessor aprilTagProcessor;
    VisionPortal visionPortal;

    public Camera(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

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
    }


}
