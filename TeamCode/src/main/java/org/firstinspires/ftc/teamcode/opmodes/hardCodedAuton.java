package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.components.device.Camera;
import org.firstinspires.ftc.teamcode.components.device.ColourMassDetectionProcessor;
import org.firstinspires.ftc.teamcode.components.meta.Hardware;
import org.firstinspires.ftc.teamcode.components.meta.MotorGroup;

public class hardCodedAuton extends LinearOpMode {
    public Camera camera;
    DcMotorEx frontLeft, frontRight, backLeft, backRight;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_FL);// Motor vars
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_FR);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE); // ! FIX ANDERSON POWERPOLE

        backLeft = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_BL);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = hardwareMap.get(DcMotorEx.class, Hardware.DRIVE_BR);

        waitForStart();

        ColourMassDetectionProcessor.PropPositions position = camera.detectLocation();

        switch (position) {
            case LEFT:
                rotate(-1, 100);
                move(1, 200);
                break;
            case MIDDLE:
                move(1, 400);
                break;
            case RIGHT:
                rotate(1, 100);
                move(1, 200);
        }

    }

    public void move(double pow, long dur){
        frontLeft.setPower(pow);
        backLeft.setPower(pow);
        frontRight.setPower(pow);
        backRight.setPower(pow);
        sleep(dur);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    public void rotate(double pow, long dur){
        frontLeft.setPower(pow);
        backLeft.setPower(pow);
        frontRight.setPower(-pow);
        backRight.setPower(-pow);
        sleep(dur);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }
}
