package org.firstinspires.ftc.teamcode.components.hardware

/* Sample for Helping
*
* https://github.com/FIRST-Tech-Challenge/FtcRobotController/blob/f3a5a54f67688eb778a8f9a62d9ce1b6e728b836/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/ConceptAprilTag.java#L126
* */


import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcontroller.external.samples.ConceptTelemetry
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.robotcore.external.tfod.Recognition
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor
import org.firstinspires.ftc.vision.tfod.TfodProcessor
import java.util.Locale


class Camera (hardwareMap: HardwareMap, telemetry: Telemetry) {
    // global vars
    private var aprilTag : AprilTagProcessor = AprilTagProcessor.Builder()
        .build()
    private var TFODProcessor : TfodProcessor = TfodProcessor.Builder()
        .build()

    var visionPortal : VisionPortal = VisionPortal.Builder()
        .setCamera(hardwareMap.get(WebcamName::class.java, DeviceNames.WEBCAM1))
        .addProcessor(aprilTag)
        .addProcessor(TFODProcessor)
        .build()

    private var telemetry = telemetry

    fun update(): AprilTagDetection {
        val currentDetections: List<AprilTagDetection> = aprilTag.detections
        telemetry.addData("# AprilTags Detected", currentDetections.size)
        return currentDetections[0]

    }


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

//    fun telemetryTfod(telemetry: Telemetry) {
//        val currentRecognitions: List<Recognition> = TFODProcessor.getRecognitions()
//        telemetry.addData("# Objects Detected", currentRecognitions.size)
//
//        // Step through the list of recognitions and display info for each one.
//        for (recognition in currentRecognitions) {
//            val x = ((recognition.left + recognition.right) / 2).toDouble()
//            val y = ((recognition.top + recognition.bottom) / 2).toDouble()
//            telemetry.addData("", " ")
//            telemetry.addData(
//                "Image",
//                "%s (%.0f %% Conf.)",
//                recognition.label,
//                recognition.confidence * 100
//            )
//            telemetry.addData("- Position", "%.0f / %.0f", x, y)
//            telemetry.addData("- Size", "%.0f x %.0f", recognition.width, recognition.height)
//        } // end for() loop
//    } // end method telemetryTfod()


}