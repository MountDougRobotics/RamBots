package org.firstinspires.ftc.teamcode.components.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.device.GamepadKt;
import org.firstinspires.ftc.teamcode.components.meta.Hardware;
import org.firstinspires.ftc.teamcode.components.meta.MotorGroup;

public class DriveTrain {
    HardwareMap hardwareMap;
    Telemetry telemetry;

    DcMotorEx frontLeft, frontRight, backLeft, backRight;
    MotorGroup motorGroup = new MotorGroup(frontLeft, frontRight, backLeft, backRight);

    public DriveTrain(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        frontLeft = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_FL);// Motor vars
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_FR);
        backLeft = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_BL);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_BR);

        motorGroup.applyToMotors(motor -> motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER));
        motorGroup.applyToMotors(motor -> motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE));
    }


    public void drive(Gamepad gamepad, double powerMulti) {
        Float[] driveSticks = GamepadKt.getDriveSticks(gamepad);
        double x = driveSticks[0];
        double y = driveSticks[1];
        double r = driveSticks[2];

        double theta = Math.atan2(y, x);
        double power = Math.hypot(x, y);

        double xComponent = power * Math.cos(theta - Math.PI / 4);
        double yComponent = power * Math.sin(theta - Math.PI / 4);

        double max = 1.0;

        double[] powers = {
                power * (xComponent / max) + r,
                power * (yComponent / max) - r,
                power * (yComponent / max) + r,
                power * (xComponent / max) - r
        };

        if (power + Math.abs(r) > 1) {
            for (int i = 0; i < powers.length; i++) {
                powers[i] /= (power + Math.abs(r));
            }
        }

        double _powerMulti = !GamepadKt.isAnyJoystickTriggered(gamepad) ? 0.0 : powerMulti;

        for (int i = 0; i < powers.length; i++) {
            powers[i] = Math.pow(powers[i], 3.0) * _powerMulti;
        }
        motorGroup.applyToMotors(motor -> motor.setPower(powers[motorGroup.index]));
    }


}
