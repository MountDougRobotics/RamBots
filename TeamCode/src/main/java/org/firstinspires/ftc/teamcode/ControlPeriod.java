package org.firstinspires.ftc.teamcode;

// FTC Imports
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp
// Tele-Op Class
public class ControlPeriod extends OpMode {

    // Motor variables
    private DcMotor backRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor intakeMotor;
    private DcMotor armLiftMotor;
    private DcMotor armExtendMotor;
    private Servo intakeServo;

    private ElapsedTime runtime = new ElapsedTime();

    // Initialize method
    @Override
    public void init() {
        // Initializes the motors with their wheels
        backRightMotor = hardwareMap.dcMotor.get("BR");
        backLeftMotor = hardwareMap.dcMotor.get("BL");
        frontRightMotor = hardwareMap.dcMotor.get("FR");
        frontLeftMotor = hardwareMap.dcMotor.get("FL");
        intakeMotor = hardwareMap.dcMotor.get("IN");
        armLiftMotor = hardwareMap.dcMotor.get("AL");
        armExtendMotor = hardwareMap.dcMotor.get("AE");
        intakeServo = hardwareMap.servo.get("INS");

        // Set motor and servo directions
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armLiftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        armExtendMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeServo.setDirection(Servo.Direction.FORWARD);

        // Unpowered all motors
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        intakeMotor.setPower(0);
        armLiftMotor.setPower(0);
        armExtendMotor.setPower(0);

        // Runs with encoders
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armExtendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Logs it in the driver hub
        telemetry.addData("Status", "Initialized");
    } // init

    // Checks for joystick input
    @Override
    public void loop() {
        double leftStickY = -gamepad1.left_stick_y; // Reverse Y-axis
        double leftStickX = gamepad1.left_stick_x;

        // Calculates the angle and power to move the robot
        double magnitude = Math.hypot(leftStickX, leftStickY);
        double angle = Math.atan2(leftStickY, leftStickX) - Math.PI / 4;

        // Calculate the speed and power
        double backRightPower = magnitude * Math.cos(angle) + (gamepad1.right_stick_x * -1);
        double backLeftPower = magnitude * Math.sin(angle) + gamepad1.right_stick_x;
        double frontRightPower = magnitude * Math.sin(angle) + (gamepad1.right_stick_x * -1);
        double frontLeftPower = magnitude * Math.cos(angle) + gamepad1.right_stick_x;

        // compensates for trying to rotate when the motors are already at max power
        // when motor power is > 1 or < -1, motor will default to 1 or -1, so
        // to compensate, power is subtracted from other motors

        if (frontRightPower > 1) {
            frontLeftPower -= frontRightPower - 1;
            backLeftPower -= frontRightPower - 1;
        } else if (frontRightPower < -1) {
            frontLeftPower -= frontRightPower + 1;
            backLeftPower -= frontRightPower + 1;
        } // if

        if (frontLeftPower > 1) {
            frontRightPower -= frontLeftPower - 1;
            backRightPower -= frontLeftPower - 1;
        } else if (frontLeftPower < -1) {
            frontRightPower -= frontLeftPower + 1;
            backRightPower -= frontLeftPower + 1;
        } // if

        //Arm Lift Idea to Stop Sliding unintentionally
        //Use the encoder to track when the motor rotates and apply power to compensate
        //Begin Encoder Tracking when set distance is reached or
        //manual button/trigger is released

        // Set the powers to the motors
        backRightMotor.setPower(backRightPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        frontLeftMotor.setPower(frontLeftPower);
        intakeMotor.setPower(gamepad1.right_stick_y); // Intake Test
        armLiftMotor.setPower((double)gamepad1.left_trigger); // Arm Lift Test
        armExtendMotor.setPower(gamepad1.right_stick_y); // Arm Extend Test

        // Logs it in the driver hub
        //telemetry.addData("Status", "Running");
        telemetry.addData("BR Motor: ", backRightPower);
        telemetry.addData("BL Motor: ", backLeftPower);
        telemetry.addData("FR Motor: ", frontRightPower);
        telemetry.addData("FL Motor: ", frontLeftPower);
        telemetry.addData("Intake Power: ", (float)intakeMotor.getPower());
        telemetry.addData("Arm Lift Power: ", (float)armLiftMotor.getPower());
        telemetry.addData("Arm Extend Power: ", (float)armExtendMotor.getPower());
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

        // Logs it in the driver hub
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    } // stop

    public void IntakeServo() {



    } // spinIntakeServo



} // RobotTeleOp