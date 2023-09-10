/* Sample for Helping
*
* https://github.com/FIRST-Tech-Challenge/FtcRobotController/blob/f3a5a54f67688eb778a8f9a62d9ce1b6e728b836/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/ConceptAprilTag.java#L126
* */


package org.firstinspires.ftc.teamcode.vision

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor
import kotlin.String
import java.util.Locale


class visionportal {

    private lateinit var aprilTag : AprilTagProcessor
    private lateinit var visionPortal : VisionPortal

    private fun initVisionPortal() {
        // Create the AprilTag processor and assign it to a variable.
        aprilTag  = AprilTagProcessor.Builder()
            .build()

        // create vision portal
        visionPortal = VisionPortal.Builder()
            .setCamera(hardwareMap.get(WebcamName::class.java, "Webcam 1"))
            .addProcessor(aprilTag)
            .build()
    }


    private fun telemetryAprilTag() {
        val currentDetections: List<AprilTagDetection> = aprilTag.detections
        telemetry.addData("# AprilTags Detected", currentDetections.size)

        // Step through the list of detections and display info for each one.
        for (detection in currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(
                    String.format(
                        Locale("en"),
                        "\n==== (ID %d) %s",
                        detection.id,
                        detection.metadata.name
                    )
                )
                telemetry.addLine(
                    String.format(
                        Locale("en"),
                        "XYZ %6.1f %6.1f %6.1f  (inch)",
                        detection.ftcPose.x,
                        detection.ftcPose.y,
                        detection.ftcPose.z
                    )
                )
                telemetry.addLine(
                    String.format(
                        Locale("en"),
                        "PRY %6.1f %6.1f %6.1f  (deg)",
                        detection.ftcPose.pitch,
                        detection.ftcPose.roll,
                        detection.ftcPose.yaw
                    )
                )
                telemetry.addLine(
                    String.format(
                        Locale("en"),

                        "RBE %6.1f %6.1f %6.1f  (inch, deg, deg)",
                        detection.ftcPose.range,
                        detection.ftcPose.bearing,
                        detection.ftcPose.elevation
                    )
                )
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id))
                telemetry.addLine(
                    String.format(
                        Locale("en"),

                        "Center %6.0f %6.0f   (pixels)",
                        detection.center.x,
                        detection.center.y
                    )
                )
            }
        } // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.")
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)")
        telemetry.addLine("RBE = Range, Bearing & Elevation")
    } // end method telemetryAprilTag()


}