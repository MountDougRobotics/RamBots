@file:Config


package org.firstinspires.ftc.teamcode.components.hardware


import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

@JvmField var CLAW_INTAKE_WIDE   = 0.6245
@JvmField var CLAW_INTAKE_NARROW = 0.578
@JvmField var CLAW_DEPOSIT       = 0.686
@JvmField var CLAW_CLOSE         = 0.425

class Claw (hardwareMap: HardwareMap) {
    private val clawServo = hardwareMap.get(Servo::class.java, DeviceNames.CLAW_SERVO)
    private val clawServo2 = hardwareMap.get(Servo::class.java, DeviceNames.CLAW_SERVO2)


    private var targetPos = CLAW_CLOSE

    fun openForIntakeNarrow() {
        targetPos = CLAW_INTAKE_NARROW
        update()
    }

    fun openForIntakeWide() {
        targetPos = CLAW_INTAKE_WIDE
        update()
    }

    fun openForDeposit() {
        targetPos = CLAW_DEPOSIT
        update()
    }

    fun close() {
        targetPos = CLAW_CLOSE
        update()
    }

    fun update() {
        clawServo.position = targetPos
        clawServo2.position = targetPos

    }

    fun clawControl(gamepad: Gamepad) {
        if (gamepad.x){
            if (targetPos == CLAW_CLOSE) targetPos = CLAW_INTAKE_NARROW
            else if (targetPos == CLAW_INTAKE_NARROW) targetPos = CLAW_DEPOSIT
        } else if (gamepad.b) {
            if (targetPos == CLAW_DEPOSIT) targetPos = CLAW_INTAKE_NARROW
            else if (targetPos == CLAW_INTAKE_NARROW) targetPos = CLAW_CLOSE
        }
    }
}