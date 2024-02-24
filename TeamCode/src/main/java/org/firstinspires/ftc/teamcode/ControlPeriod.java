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
    private DcMotor armLiftMotor1;
    private DcMotor armLiftMotor2;
    private Servo clawServo1;
    private Servo clawServo2;
    private Servo clawWrist;
    private Servo planeLaunchServo;
    private Servo hookServo;
    private CRServo hangServo;
    private AnalogInput armPot;

    private boolean armUp = false;
    private boolean clawOpen = true;
    private double armUpVoltage = 0;
    private double armDownVoltage = 0;

    private int speedSetting = 0;

    private boolean a_toggle = false;
    private boolean b_toggle = false;
    private boolean x_toggle = false;
    private boolean y_toggle = false;
    private boolean rightBumper_toggle = false;

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime clawClosedTime = new ElapsedTime();
    private ElapsedTime planeLaunchTime = new ElapsedTime(1);

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
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        armLiftMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
        armLiftMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
        clawServo1.setDirection(Servo.Direction.FORWARD);
        clawServo2.setDirection(Servo.Direction.FORWARD);
        clawWrist.setDirection(Servo.Direction.FORWARD);
        planeLaunchServo.setDirection(Servo.Direction.FORWARD);
        hookServo.setDirection(Servo.Direction.FORWARD);
        hangServo.setDirection(DcMotorSimple.Direction.FORWARD);

        // Unpowered all motors
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        armLiftMotor1.setPower(0);
        armLiftMotor2.setPower(0);
        clawServo1.setPosition(0.88);
        clawServo2.setPosition(0.5);
        clawWrist.setPosition(0.4);
        planeLaunchServo.setPosition(0.5);
        hookServo.setPosition(1);

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
        double leftStickY = gamepad1.left_stick_y; // Reverse Y-axis
        double leftStickX = gamepad1.left_stick_x;
        double rightStickX = gamepad1.right_stick_x;

        // Calculates the angle and power to move the robot
        double magnitude = Math.hypot(leftStickX, leftStickY);
        double angle = Math.atan2(leftStickY, leftStickX) - Math.PI / 4;

        switch (speedSetting) {
            case 0:
                magnitude *= 0.9;
                break;
            case 1:
                magnitude *= 0.6;
                break;
            case 2:
                magnitude *= 0.3;
                break;
        } // switch

        // Calculate the speed and power
        double backRightPower = magnitude * Math.sin(angle) + (gamepad1.right_stick_x);
        double backLeftPower = magnitude * Math.cos(angle) + (-gamepad1.right_stick_x);
        double frontRightPower = magnitude * Math.cos(angle) + (gamepad1.right_stick_x);
        double frontLeftPower = magnitude * Math.sin(angle) + (-gamepad1.right_stick_x);

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
            if (!y_toggle) {
                armUp = !armUp;
                y_toggle = true;
            } // if
        } else {
            y_toggle = false;
        } // else

        if (gamepad2.a) {
            if (!a_toggle) {
                controlClaw();
                a_toggle = true;
            } // if
        } else {
            a_toggle = false;
        } // if

        if (gamepad1.x) {
            if (!x_toggle) {

                // checks if press is within the time frame
                if (planeLaunchTime.milliseconds() < 500) {
                    planeLaunchServo.setPosition(1);
                } else {
                    planeLaunchTime.reset();
                } // else
                x_toggle = true;
            } // if
        } else {
            x_toggle = false;
        } // else

        if (gamepad1.right_bumper) {
            if (!rightBumper_toggle) {

                switch (speedSetting) {
                    case 0:
                    case 1:
                        speedSetting ++;
                        break;
                    case 2:
                        speedSetting = 0;
                        break;
                } // switch

                rightBumper_toggle = true;
            } // if
        } else {
            rightBumper_toggle = false;
        } // else

        // Set the powers to the motors
        backRightMotor.setPower(backRightPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        frontLeftMotor.setPower(frontLeftPower);
        controlArm();

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
        // telemetry.addData("Arm Pot Voltage: ", (float)armPot.getVoltage());
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
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    } // stop

    public void controlArm() {

        double voltage = armPot.getVoltage();
        double targetUp = 3.2; // get correct value from testing
        double targetDown = clawClosedTime.milliseconds() > 300 && clawOpen == false ? 0.84 : 0.78; // get correct value from testing 0.78 is standard

        if (armUp) {
            //0.75

            if (voltage >= 1.5) {
                clawWrist.setPosition(0.88);
            } // if

            if (voltage <= targetUp) {
                armLiftMotor1.setPower(1);
                armLiftMotor2.setPower(1);
            } else {
                armLiftMotor1.setPower(0);
                armLiftMotor2.setPower(0);
            } // else
        } else {

            clawWrist.setPosition(0.4);

            if (voltage >= targetDown) {
                armLiftMotor1.setPower(-1);
                armLiftMotor2.setPower(-1);
            } else if (voltage < targetDown - 0.02) {
                armLiftMotor1.setPower(0.5);
                armLiftMotor2.setPower(0.5);
            } else {
                armLiftMotor1.setPower(0);
                armLiftMotor2.setPower(0);
            } // else

        } // else

    } // controlArmMotor

    public void controlClaw() {

        if (!clawOpen) {

            clawServo1.setPosition(1);
            clawServo2.setPosition(0.56); // delete later

            clawOpen = true;
        } else {

            clawServo1.setPosition(0.63);
            clawServo2.setPosition(0.56);

            clawClosedTime.reset();

            clawOpen = false;
        } // else

    } // controlClaw

} // RobotTeleOp