package org.firstinspires.ftc.teamcode;

// FTC Imports
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
    //private DcMotor armLiftMotor;

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
        //armLiftMotor = hardwareMap.dcMotor.get("AL");

        // Sets up the motors for a mecanum drive
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);


        // Unpowered all motors
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        intakeMotor.setPower(0);

        // Runs with encoders
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
        double backRightPower = magnitude * Math.cos(angle);
        double backLeftPower = magnitude * Math.sin(angle);
        double frontRightPower = magnitude * Math.sin(angle);
        double frontLeftPower = magnitude * Math.cos(angle);

        // Set the powers to the motors
        backRightMotor.setPower(backRightPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        frontLeftMotor.setPower(frontLeftPower);
        intakeMotor.setPower((double)gamepad1.right_trigger);

        // Logs it in the driver hub
        telemetry.addData("Status", "Running");
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
} // RobotTeleOp