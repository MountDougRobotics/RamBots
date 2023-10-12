package org.firstinspires.ftc.teamcode.components.device

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.firstinspires.ftc.robotcore.external.navigation.Orientation

class IMU (hardwareMap: HardwareMap) {
    var imu: IMU = hardwareMap.get(IMU::class.java, "imu")
    var robotOrientation: Orientation

    private val imuParams: IMU.Parameters = IMU.Parameters(
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

    fun getOrientation() : Orientation {
        robotOrientation = imu.getRobotOrientation(
            AxesReference.INTRINSIC,
            AxesOrder.ZYX,
            AngleUnit.DEGREES
        )
        return robotOrientation
    }

    fun getAngle() : Double {
        return robotOrientation.firstAngle.toDouble()
    }


}