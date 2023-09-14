/**
 * drive.kt
 * Purpose: Used to control the movement of the main drive
 * TODO OMNIDRIVE (360° control)
 * TODO Routing
 **/


package org.firstinspires.ftc.teamcode.movement

import com.arcrobotics.ftclib.drivebase.HDrive
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.arcrobotics.ftclib.hardware.motors.MotorEx
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap


class drive {
    lateinit var frontLeft: MotorEx
    lateinit var frontRight: MotorEx
    lateinit var backLeft: MotorEx
    lateinit var backRight: MotorEx
    lateinit var drive: HDrive


    fun init() {
        frontLeft = MotorEx(hardwareMap, "fL")
        frontRight = MotorEx(hardwareMap, "fR")
        backLeft = MotorEx(hardwareMap, "bL")
        backRight = MotorEx(hardwareMap, "bR")

        // Motor settings & use brake mode
        frontLeft.setRunMode(Motor.RunMode.RawPower)
        frontRight.setRunMode(Motor.RunMode.RawPower)
        backLeft.setRunMode(Motor.RunMode.RawPower)
        backRight.setRunMode(Motor.RunMode.RawPower)
        frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE)
        frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE)
        backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE)
        backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE)

        // setup drive
        drive = HDrive(
            frontLeft, frontRight,
            backLeft, backRight
        )
    }
}