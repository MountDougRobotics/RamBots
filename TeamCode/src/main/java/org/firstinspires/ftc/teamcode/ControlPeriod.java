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
public class ControlPeriod extends OpMode {

    // Motor variables
    private DcMotor backRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor intakeMotor;
    private DcMotor armLiftMotor;
    private DcMotor armExtendMotor;
    private CRServo intakeServo;
    private Servo clawServo1;
    private Servo clawServo2;
    private Servo clawWrist;
    private Servo planeLaunchServo;
    private AnalogInput armPot;

    private boolean armUp = false;
    private boolean clawOpen = true;
    private double armUpVoltage = 0;
    private double armDownVoltage = 0;

    private boolean a_toggle = false;
    private boolean b_toggle = false;
    private boolean x_toggle = false;
    private boolean y_toggle = false;

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime clawClosedTime = new ElapsedTime();

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
        intakeServo = hardwareMap.crservo.get("INS");
        clawServo1 = hardwareMap.servo.get("CL1");
        clawServo2 = hardwareMap.servo.get("CL2");
        clawWrist = hardwareMap.servo.get("CW");
        planeLaunchServo = hardwareMap.servo.get("PL");
        armPot = hardwareMap.get(AnalogInput.class, "AP");

        // Set motor and servo directions
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armLiftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        armExtendMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeServo.setDirection(DcMotorSimple.Direction.FORWARD);
        clawServo1.setDirection(Servo.Direction.FORWARD);
        clawServo2.setDirection(Servo.Direction.FORWARD);
        planeLaunchServo.setDirection(Servo.Direction.FORWARD);
        clawWrist.setDirection(Servo.Direction.FORWARD);

        // Unpowered all motors
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        intakeMotor.setPower(0);
        armLiftMotor.setPower(0);
        armExtendMotor.setPower(0);
        intakeServo.setPower(0);
        clawServo1.setPosition(0.88);
        clawServo2.setPosition(0.5);
        clawWrist.setPosition(0.4);

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
        double leftStickY = gamepad1.left_stick_y; // Reverse Y-axis
        double leftStickX = gamepad1.left_stick_x;
        double rightStickX = gamepad1.right_stick_x;

        // Calculates the angle and power to move the robot
        double magnitude = Math.hypot(leftStickX, leftStickY);
        double angle = Math.atan2(leftStickY, leftStickX) - Math.PI / 4;

        // Calculate the speed and power
         double backRightPower = magnitude * Math.sin(angle) + (-gamepad1.right_stick_x);
         double backLeftPower = magnitude * Math.cos(angle) + (gamepad1.right_stick_x);
         double frontRightPower = magnitude * Math.cos(angle) + (-gamepad1.right_stick_x);
         double frontLeftPower = magnitude * Math.sin(angle) + (gamepad1.right_stick_x);

//        double backRightPower = (leftStickY + (-leftStickX)) * 0.5;
//        double backLeftPower = (leftStickY + (leftStickX)) * 0.5;
//        double frontRightPower = (leftStickY + (-leftStickX)) * 0.5;
//        double frontLeftPower = (leftStickY + (leftStickX)) * 0.5;

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

        if (gamepad2.y) {
            if (!a_toggle) {
                armUp = !armUp;
                y_toggle = true;
            } // if
        } else {
            y_toggle = false;
        } // else

        if (gamepad2.a) {
            if (!b_toggle) {
                controlClaw();
                b_toggle = true;
            } // if
        } else {
            b_toggle = false;
        } // if

        if (gamepad1.x) {
            planeLaunchServo.setPosition(1);
        } // if

        // Set the powers to the motors
        backRightMotor.setPower(backRightPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        frontLeftMotor.setPower(frontLeftPower);
//        intakeMotor.setPower(-gamepad1.right_stick_y); // Intake Test
//        armLiftMotor.setPower((double)gamepad1.left_trigger); // Arm Lift Test
//        armExtendMotor.setPower(-gamepad1.right_stick_y); // Arm Extend Test

        controlArm();

        // Spin Intake Servo
//        if (gamepad1.a) {
//            intakeServo.setPower(1);
//        } else if (gamepad1.b) {
//            intakeServo.setPower(0);
//        } // else if

        String arm = (armUp) ? "Up" : "Down";
        String claw = (clawOpen) ? "Open" : "Closed";

        // Logs it in the driver hub
        telemetry.addData("Status", "Running");
        telemetry.addData("BR Motor: ", backRightPower);
        telemetry.addData("BL Motor: ", backLeftPower);
        telemetry.addData("FR Motor: ", frontRightPower);
        telemetry.addData("FL Motor: ", frontLeftPower);
        // telemetry.addData("Magnitude: ", magnitude);
        // telemetry.addData("Angle: ", angle);
//        telemetry.addData("Intake Motor Power: ", (float)intakeMotor.getPower());
//        telemetry.addData("Intake Servo Power: ", (float)intakeServo.getPower());
        //telemetry.addData("Arm Lift Power: ", (float)armLiftMotor.getPower());
//        telemetry.addData("Arm Extend Power: ", (float)armExtendMotor.getPower());
        //telemetry.addData("Claw Servo 1 Pos: ", (float)clawServo1.getPosition());
        //telemetry.addData("Claw Servo 2 Pos: ", (float)clawServo2.getPosition());
        //telemetry.addData("Claw Wrist Pos: ", (float)clawWrist.getPosition());
        telemetry.addData("Arm: ", arm);
        telemetry.addData("Claw: ", claw);
        telemetry.addData("Arm Pot Voltage: ", (float)armPot.getVoltage());
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

        // Logs it in the driver hubd
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    } // stop

    public void controlArm() {

        double voltage = armPot.getVoltage();
        double targetUp = 2.55; // get correct value from testing
        double targetDown = 0.55; // get correct value from testing

        if (armUp) {
        //0.75

            if (voltage >= 0.75) {
                clawWrist.setPosition(0.91);
            } // if

            if (voltage <= targetUp) {
                armLiftMotor.setPower(1);
            } else {
                armLiftMotor.setPower(0);
            } // else
        } else {

            if (clawClosedTime.milliseconds() > 300 && clawOpen == false) {
                clawWrist.setPosition(0.43);
            } else {
                clawWrist.setPosition(0.4); // standard position
            } // else

            if (voltage >= targetDown) {
                armLiftMotor.setPower(-1);
            } else {
                armLiftMotor.setPower(0);
            } // else

        } // else

    } // controlArmMotor

    public void controlClaw() {

        if (!clawOpen) {

            clawServo1.setPosition(0.88);
            clawServo2.setPosition(0.5);

            clawOpen = true;
        } else {

            clawServo1.setPosition(0.57);
            clawServo2.setPosition(0.5);

            clawClosedTime.reset();

            clawOpen = false;
        } // else

    } // controlClaw

} // RobotTeleOp