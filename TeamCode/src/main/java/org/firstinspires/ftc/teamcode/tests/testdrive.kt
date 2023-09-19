package org.firstinspires.ftc.teamcode.opmodes

import com.arcrobotics.ftclib.drivebase.HDrive
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.arcrobotics.ftclib.hardware.motors.MotorEx
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion
import kotlin.math.pow


class testdrive : OpMode() {
    lateinit var frontLeft: MotorEx
    lateinit var frontRight: MotorEx
    lateinit var backLeft: MotorEx
    lateinit var backRight: MotorEx
    lateinit var drive: HDrive

    lateinit var driverOp: GamepadEx
    override fun init() {
        frontLeft = MotorEx(BlocksOpModeCompanion.hardwareMap, "fL")
        frontRight = MotorEx(BlocksOpModeCompanion.hardwareMap, "fR")
        backLeft = MotorEx(BlocksOpModeCompanion.hardwareMap, "bL")
        backRight = MotorEx(BlocksOpModeCompanion.hardwareMap, "bR")

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
        driverOp = GamepadEx(gamepad1);
    }

    override fun init_loop() {

    }

    override fun start() {

    }
    private fun cube(x : Float) : Double { return x.toDouble().pow(3.0)
    }

    override fun loop() {
        drive.driveRobotCentric(
                driverOp.getLeftX(),
                driverOp.getLeftY(),
                driverOp.getRightY()
        );
    }

    override fun stop() {

    }

}