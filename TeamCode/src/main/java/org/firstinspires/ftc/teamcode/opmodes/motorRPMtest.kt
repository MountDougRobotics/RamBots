package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

class motorRPMtest : OpMode() {
    lateinit var motor : DcMotorEx
    private var ticks = 0
    private var lastPos = 0
    private val runtime = ElapsedTime()

    override fun init() {
        motor = hardwareMap.get(DcMotorEx::class.java, DeviceNames.DRIVE_FL)
        motor.mode = DcMotor.RunMode.RUN_USING_ENCODER

    }

    override fun start() {
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        runtime.reset()

    }

    override fun loop() {
        motor.power = 1.0
        ticks = motor.currentPosition - lastPos

        if (runtime.milliseconds() >= 100) {
            runtime.reset()
            lastPos = ticks
            ticks = 0

            telemetry.addData("RPM", (ticks/1440.0)/runtime.seconds()*60)
            telemetry.update()
        }


    }
}