package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

class clawTuner: OpMode() {
    var servoPos: Double = 0.0
    var servoA: Servo = hardwareMap.get(Servo::class.java, DeviceNames.CLAW_SERVO)
    var servoB: Servo = hardwareMap.get(Servo::class.java, DeviceNames.CLAW_SERVO2)

    override fun init() {
        servoA.position = servoPos
        servoB.position = 1-servoPos
    }

    override fun loop() {
        servoA.position = servoPos
        servoB.position = 1-servoPos
        telemetry.addData("Servo Position: ", servoPos)
        telemetry.update()
    }

}