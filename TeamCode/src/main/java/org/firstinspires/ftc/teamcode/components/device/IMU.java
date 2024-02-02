package org.firstinspires.ftc.teamcode.components.device;


/* ?
! * Do not tamper!
? * These are some helper functions for IMU
? * Call this via import import  org.firstinspires.ftc.teamcode.components.device.IMU -> IMU
? *
 * TODO: N/A
? */

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

class imuModule {
    HardwareMap hardwareMap;
    Telemetry telemetry;

    IMU imu = hardwareMap.get(IMU.class, "imu"); // ? IMU, call this for functions not described below
    Orientation robotOrientation;
    private final IMU.Parameters imuParams = new IMU.Parameters( // ! IMU parameters. Change these depending on orientation of Control Hub
            new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
            )
    );

    public imuModule(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        imu.initialize(imuParams);
        robotOrientation = imu.getRobotOrientation(
                AxesReference.INTRINSIC,
                AxesOrder.ZYX,
                AngleUnit.DEGREES
        );
    }

    Orientation getOrientation() { // ? get the orientation of IMU, should be self-explanatory
        robotOrientation = imu.getRobotOrientation(
                AxesReference.INTRINSIC,
                AxesOrder.ZYX,
                AngleUnit.DEGREES
        );
        return robotOrientation;
    }

    double getAngle() { // ? get the X/Y angle of the robot
        return robotOrientation.firstAngle;
    }


}