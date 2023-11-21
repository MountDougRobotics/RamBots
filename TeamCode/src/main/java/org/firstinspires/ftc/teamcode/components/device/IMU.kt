package org.firstinspires.ftc.teamcode.components.device

/* ?
! * Do not tamper!
? * These are some helper functions for IMU
? * Call this via import import  org.firstinspires.ftc.teamcode.components.device.IMU -> IMU
? *
 * TODO: N/A
? */

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.firstinspires.ftc.robotcore.external.navigation.Orientation

class IMU (hardwareMap: HardwareMap) {
    var imu: IMU = hardwareMap.get(IMU::class.java, "imu") // ? IMU, call this for functions not described below
    var robotOrientation: Orientation

    private val imuParams: IMU.Parameters = IMU.Parameters( // ! IMU parameters. Change these depending on orientation of Control Hub
        RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.UP,
            RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        )
    )

    init {
        imu.initialize(imuParams)
        robotOrientation = imu.getRobotOrientation(
            AxesReference.INTRINSIC,
            AxesOrder.ZYX,
            AngleUnit.DEGREES
        )
    }

    fun getOrientation() : Orientation { // ? get the orientation of IMU, should be self-explanatory
        robotOrientation = imu.getRobotOrientation(
            AxesReference.INTRINSIC,
            AxesOrder.ZYX,
            AngleUnit.DEGREES
        )
        return robotOrientation
    }

    fun getAngle() : Double { // ? get the X/Y angle of the robot
        return robotOrientation.firstAngle.toDouble()
    }


}