package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


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

@Autonomous(name = "Encoder Test")
public class EncoderTest extends LinearOpMode {

    private DcMotor backRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor intakeMotor;
    private DcMotor armLiftMotor1;
    private DcMotor armLiftMotor2;
    private CRServo intakeServo;
    private Servo clawServo1;
    private Servo clawServo2;
    private Servo clawWrist;
    private Servo planeLaunchServo;
    private AnalogInput armPot;

    @Override
    public void runOpMode() {

        // Initializes the motors with their wheels
        backRightMotor = hardwareMap.dcMotor.get("BR");
        backLeftMotor = hardwareMap.dcMotor.get("BL");
        frontRightMotor = hardwareMap.dcMotor.get("FR");
        frontLeftMotor = hardwareMap.dcMotor.get("FL");
        intakeMotor = hardwareMap.dcMotor.get("IN");
        armLiftMotor1 = hardwareMap.dcMotor.get("AL1");
        armLiftMotor2 = hardwareMap.dcMotor.get("AL2");
        intakeServo = hardwareMap.crservo.get("INS");
        clawServo1 = hardwareMap.servo.get("CL1");
        clawServo2 = hardwareMap.servo.get("CL2");
        clawWrist = hardwareMap.servo.get("CW");
        planeLaunchServo = hardwareMap.servo.get("PL");
        armPot = hardwareMap.analogInput.get("AP");

        // Set motor and servo directions
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armLiftMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
        armLiftMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeServo.setDirection(DcMotorSimple.Direction.FORWARD);
        clawServo1.setDirection(Servo.Direction.FORWARD);
        clawServo2.setDirection(Servo.Direction.FORWARD);
        clawWrist.setDirection(Servo.Direction.FORWARD);
        planeLaunchServo.setDirection(Servo.Direction.FORWARD);

        // Runs with encoders
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLiftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armLiftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    } // runOpMode

} // EncoderTest