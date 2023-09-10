/**
 * drive.kt
 * Purpose: Used to control the movement of the main drive
 * TODO OMNIDRIVE (360° control)
 * TODO Routing
 **/


package org.firstinspires.ftc.teamcode.movement

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap

class drive {
    lateinit var frontLeft: DcMotor
    lateinit var frontRight: DcMotor
    lateinit var backLeft: DcMotor
    lateinit var backRight: DcMotor

    fun init() {
        frontLeft = hardwareMap.get(DcMotor::class.java, "a")
        frontRight = hardwareMap.get(DcMotor::class.java, "b")
        backLeft = hardwareMap.get(DcMotor::class.java, "c")
        backRight = hardwareMap.get(DcMotor::class.java, "d")
        // Run with encoder & use brake mode
        frontLeft.mode = DcMotor.RunMode.RUN_USING_ENCODER
        frontRight.mode = DcMotor.RunMode.RUN_USING_ENCODER
        backLeft.mode = DcMotor.RunMode.RUN_USING_ENCODER
        backRight.mode = DcMotor.RunMode.RUN_USING_ENCODER
        frontLeft.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        frontRight.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        backLeft.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        backRight.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }
}