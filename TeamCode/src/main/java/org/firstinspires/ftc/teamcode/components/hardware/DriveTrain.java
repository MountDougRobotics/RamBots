package org.firstinspires.ftc.teamcode.components.hardware;

import static java.lang.Math.sqrt;

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

    MotorGroup motorGroup;
    public DriveTrain(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        frontLeft = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_FL);// Motor vars
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_FR);
        backLeft = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_BL);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_BR);
        motorGroup = new MotorGroup(frontLeft, frontRight, backLeft, backRight);

        motorGroup.applyToMotors(motor -> motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER));
        motorGroup.applyToMotors(motor -> motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE));
    }

    private double largest(double[] arr) {
        double max = Math.abs(arr[0]);
        int i = 1;
        while (i < arr.length) {
            if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
            }
            i++;
        }
        return max;
    }

    private double[] reduce(double[] x) {
        double max = largest(x);
        if (max > 1) {
            for (int i = 0; i < x.length; i++) {
                x[i] = x[i] / max;
            }
        }
        return x;
    }


    public void drive(Gamepad gamepad, double powerMulti) {
        double[] driveSticks = GamepadKt.getDriveSticks(gamepad);
        double x = driveSticks[0];
        double y = driveSticks[1];
        double r = driveSticks[2];

        double theta = Math.atan2(y, x);
        double power = Math.min(Math.hypot(x, y), 1.0);

        double xComponent = power * Math.cos(theta - Math.PI / 4);
        double yComponent = power * Math.sin(theta - Math.PI / 4);

        double[] powers = {
                xComponent + r,
                yComponent - r,
                yComponent + r,
                xComponent - r
        };


//        if (power + Math.abs(r) > 1) {
//            for (int i = 0; i < powers.length; i++) {
//                powers[i] /= (power + Math.abs(r));
//            }
//        }

        double _powerMulti = !GamepadKt.isAnyJoystickTriggered(gamepad) ? 0.0 : powerMulti;

        for (int i = 0; i < powers.length; i++) {
            powers[i] = Math.pow(powers[i], 3.0) * _powerMulti;
        }

        powers = reduce(powers);

        double[] finalPowers = powers;
        motorGroup.applyToMotors(motor -> motor.setPower(finalPowers[motorGroup.motorIndex]));
        telemetry.addData("FL", frontLeft.getPower());
        telemetry.addData("FR", frontRight.getPower());
        telemetry.addData("BL", backLeft.getPower());
        telemetry.addData("BR", backRight.getPower());
        telemetry.update();

    }


}
