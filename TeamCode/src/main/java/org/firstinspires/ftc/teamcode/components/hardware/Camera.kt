package org.firstinspires.ftc.teamcode.components.hardware

/* Sample for Helping
*
* https://github.com/FIRST-Tech-Challenge/FtcRobotController/blob/f3a5a54f67688eb778a8f9a62d9ce1b6e728b836/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/ConceptAprilTag.java#L126
* */


import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.components.device.ColourMassDetectionProcessor
import org.firstinspires.ftc.teamcode.components.device.ColourMassDetectionProcessor.PropPositions
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor
import org.opencv.core.Scalar


class Camera (hardwareMap: HardwareMap, telemetry: Telemetry) {

    // the current range set by lower and upper is the full range
    // HSV takes the form: (HUE, SATURATION, VALUE)
    // which means to select our colour, only need to change HUE
    // the domains are: ([0, 180], [0, 255], [0, 255])
    // this is tuned to detect red, so you will need to experiment to fine tune it for your robot
    // and experiment to fine tune it for blue
    var lowerRed = Scalar(150.0, 100.0, 100.0) // the lower hsv threshold for your detection
    var upperRed = Scalar(180.0, 255.0, 255.0) // the upper hsv threshold for your detection
    var lowerBlue = Scalar(0.0, 255.0, 255.0) // the lower hsv threshold for your detection
    var upperBlue = Scalar(30.0, 255.0, 255.0) // the upper hsv threshold for your detection

    var minArea = 100.0 // the minimum area for the detection to consider for your prop


    // global vars
    private var aprilTag : AprilTagProcessor = AprilTagProcessor.Builder()
        .build()

    private var colourMassDetectionProcessor: ColourMassDetectionProcessor = ColourMassDetectionProcessor(
        lowerRed,
        upperRed,
        {minArea},
        {213.0},
        {426.0}
    )


    var visionPortal : VisionPortal = VisionPortal.Builder()
        .setCamera(hardwareMap.get(WebcamName::class.java, DeviceNames.WEBCAM1))
        .addProcessor(aprilTag)
        .addProcessor(colourMassDetectionProcessor)
        .build()

    private var telemetry = telemetry



    fun detectLocation():PropPositions {
        // gets the recorded prop position

        // gets the recorded prop position
        var recordedPropPosition = colourMassDetectionProcessor.recordedPropPosition

        // now we can use recordedPropPosition to determine where the prop is! if we never saw a prop, your recorded position will be UNFOUND.
        // if it is UNFOUND, you can manually set it to any of the other positions to guess

        // now we can use recordedPropPosition to determine where the prop is! if we never saw a prop, your recorded position will be UNFOUND.
        // if it is UNFOUND, you can manually set it to any of the other positions to guess
        if (recordedPropPosition == PropPositions.UNFOUND) {
            recordedPropPosition = PropPositions.MIDDLE
        }

        return recordedPropPosition
    }

//    fun update(): AprilTagDetection {
//        val currentDetections: List<AprilTagDetection> = aprilTag.detections
//        telemetry.addData("# AprilTags Detected", currentDetections.size)
//        return currentDetections[0]
//
//    }


//    fun aprilTagTelemetry(telemetry: Telemetry) {
//        val currentDetections: List<AprilTagDetection> = aprilTag.detections
//        telemetry.addData("# AprilTags Detected", currentDetections.size)
//
//        // Step through the list of detections and display info for each one.
//        for (detection in currentDetections) {
//            if (detection.metadata != null) {
//                telemetry.addLine(
//                    String.format(
//                        Locale("en"),
//                        "\n==== (ID %d) %s",
//                        detection.id, // TODO: pretty sure one of these 2 what we'll be using to detect
//                        detection.metadata.name
//                    )
//                )
//                telemetry.addLine(
//                    String.format(
//                        Locale("en"),
//                        "XYZ %6.1f %6.1f %6.1f  (inch)",
//                        detection.ftcPose.x,
//                        detection.ftcPose.y,
//                        detection.ftcPose.z
//                    )
//                )
//                telemetry.addLine(
//                    String.format(
//                        Locale("en"),
//                        "PRY %6.1f %6.1f %6.1f  (deg)",
//                        detection.ftcPose.pitch,
//                        detection.ftcPose.roll,
//                        detection.ftcPose.yaw
//                    )
//                )
//                telemetry.addLine(
//                    String.format(
//                        Locale("en"),
//                        "RBE %6.1f %6.1f %6.1f  (inch, deg, deg)",
//                        detection.ftcPose.range,
//                        detection.ftcPose.bearing,
//                        detection.ftcPose.elevation
//                    )
//                )
//            } else {
//                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id))
//                telemetry.addLine(
//                    String.format(
//                        Locale("en"),
//                        "Center %6.0f %6.0f   (pixels)",
//                        detection.center.x,
//                        detection.center.y
//                    )
//                )
//            }
//        } // end for() loop
//
//        // Add "key" information to telemetry
//        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.")
//        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)")
//        telemetry.addLine("RBE = Range, Bearing & Elevation")
//    } // end method aprilTagTelemetry()


}