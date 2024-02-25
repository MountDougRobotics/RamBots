package org.firstinspires.ftc.teamcode;

// FTC Imports
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp
// Tele-Op Class
public class ServoTest extends OpMode {

    // Motor variables
    private DcMotor backRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor armLiftMotor1;
    private DcMotor armLiftMotor2;
    private Servo clawServo1;
    private Servo clawServo2;
    private Servo clawWrist;
    private Servo planeLaunchServo;
    private Servo hookServo;
    private CRServo hangServo;
    private AnalogInput armPot;

    private ElapsedTime runtime = new ElapsedTime();

    // Initialize method
    @Override
    public void init() {
        // Initializes the motors with their wheels
        backRightMotor = hardwareMap.dcMotor.get("BR");
        backLeftMotor = hardwareMap.dcMotor.get("BL");
        frontRightMotor = hardwareMap.dcMotor.get("FR");
        frontLeftMotor = hardwareMap.dcMotor.get("FL");
        armLiftMotor1 = hardwareMap.dcMotor.get("AL1");
        armLiftMotor2 = hardwareMap.dcMotor.get("AL2");
        clawServo1 = hardwareMap.servo.get("CL1");
        clawServo2 = hardwareMap.servo.get("CL2");
        clawWrist = hardwareMap.servo.get("CW");
        planeLaunchServo = hardwareMap.servo.get("PL");
        hookServo = hardwareMap.servo.get("HK");
        hangServo = hardwareMap.crservo.get("HN");
        armPot = hardwareMap.get(AnalogInput.class, "AP");

        // Set motor and servo directions
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        armLiftMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
        armLiftMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
        clawServo1.setDirection(Servo.Direction.FORWARD);
        clawServo2.setDirection(Servo.Direction.FORWARD);
        clawWrist.setDirection(Servo.Direction.FORWARD);
        hookServo.setDirection(Servo.Direction.FORWARD);
        hangServo.setDirection(DcMotorSimple.Direction.FORWARD);
        planeLaunchServo.setDirection(Servo.Direction.FORWARD);

        // Unpowered all motors
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        armLiftMotor1.setPower(0);
        armLiftMotor2.setPower(0);
        clawServo1.setPosition(0.47);
        clawServo2.setPosition(0.5);
        clawWrist.setPosition(0.4);
        hookServo.setPosition(0.25);
        hangServo.setPower(0);
        planeLaunchServo.setPosition(0);

        // Runs with encoders
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armLiftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armLiftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Logs it in the driver hub
        telemetry.addData("Status", "Initialized");
    } // init

    // Checks for joystick input
    @Override
    public void loop() {
        double leftStickY = -gamepad1.left_stick_y; // Reverse Y-axis
        double leftStickX = gamepad1.left_stick_x;
        double rightStickY = -gamepad1.right_stick_y;

        hangServo.setPower(gamepad1.right_trigger);

        // Set Claw Position
        if (gamepad1.right_bumper) {

            if (gamepad1.a) {
                clawServo1.setPosition(clawServo1.getPosition() + 0.001);
            } else if (gamepad1.b) {
                clawServo1.setPosition(clawServo1.getPosition() - 0.001);
            } // else if

            armLiftMotor1.setPower(rightStickY * 0.2);

        } // if

        if (gamepad1.left_bumper) {

            if (gamepad1.a) {
                clawServo2.setPosition(clawServo2.getPosition() + 0.001);
            } else if (gamepad1.b) {
                clawServo2.setPosition(clawServo2.getPosition() - 0.001);
            } // else if

            armLiftMotor2.setPower(rightStickY * 0.2);

        } // if

        if (gamepad1.dpad_up) {

            backRightMotor.setPower(rightStickY);

        } // if

        if (gamepad1.dpad_down) {
            //clawWrist.setPosition(leftStickY);

            if (gamepad1.a) {
                clawWrist.setPosition(clawWrist.getPosition() + 0.001);
            } else if (gamepad1.b) {
                clawWrist.setPosition(clawWrist.getPosition() - 0.001);
            } // else if

            backLeftMotor.setPower(rightStickY);

        } // if

        if (gamepad1.dpad_left) {

            if (gamepad1.a) {
                planeLaunchServo.setPosition(planeLaunchServo.getPosition() + 0.001);
            } else if (gamepad1.b) {
                planeLaunchServo.setPosition(planeLaunchServo.getPosition() - 0.001);
            } // else if

            frontLeftMotor.setPower(rightStickY);

        } // if

        if (gamepad1.dpad_right) {

            if (gamepad1.a) {
                hookServo.setPosition(hookServo.getPosition() + 0.001);
            } else if (gamepad1.b) {
                hookServo.setPosition(hookServo.getPosition() - 0.001);
            } // else if

            frontRightMotor.setPower(rightStickY);

        } // if

        // Logs it in the driver hub
        //telemetry.addData("Status", "Running");
        telemetry.addData("BR Motor: ", (float)backRightMotor.getPower());
        telemetry.addData("BL Motor: ", (float)backLeftMotor.getPower());
        telemetry.addData("FR Motor: ", (float)frontRightMotor.getPower());
        telemetry.addData("FL Motor: ", (float)frontLeftMotor.getPower());
        telemetry.addData("Arm Lift 1 Power: ", (float)armLiftMotor1.getPower());
        telemetry.addData("Arm Lift 2 Power: ", (float)armLiftMotor2.getPower());
        telemetry.addData("Claw Servo 1 Pos: ", (float)clawServo1.getPosition());
        telemetry.addData("Claw Servo 2 Pos: ", (float)clawServo2.getPosition());
        telemetry.addData("Claw Wrist Pos: ", (float)clawWrist.getPosition());
        telemetry.addData("Plane Launch Pos: ", (float)planeLaunchServo.getPosition());
        telemetry.addData("Hook Servo Pos: ", (float)hookServo.getPosition());
        telemetry.addData("Hang Servo Power: ", (float)hangServo.getPower());
        telemetry.addData("Arm Pot Voltage: ", armPot.getVoltage());
        telemetry.update();

    } // loop

    // Stops the robot
    @Override
    public void stop() {
        // Stop the motors and perform any necessary cleanup
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        armLiftMotor1.setPower(0);
        armLiftMotor2.setPower(0);
        hangServo.setPower(0);

        // Logs it in the driver hub
        //telemetry.addData("Status", "Stopped");
        //telemetry.update();
    } // stop

} // ServoTest