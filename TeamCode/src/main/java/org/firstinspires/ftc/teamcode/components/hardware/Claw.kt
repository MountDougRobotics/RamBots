@file:Config
package org.firstinspires.ftc.teamcode.components.hardware

/* ?
? * Claw Component
! * Don't call this directly if using BotComponents
? *
 * TODO: Testing this thing out + Tuning Values
? */

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

@JvmField var CLAW_INTAKE_NARROW = 0.578 // ! values for tuning claw pos
@JvmField var CLAW_DEPOSIT       = 0.686
@JvmField var CLAW_CLOSE         = 0.425

class Claw (hardwareMap: HardwareMap) {
    private val clawServo = hardwareMap.get(Servo::class.java, DeviceNames.CLAW_SERVO) // claw variable
    private val clawServo2 = hardwareMap.get(Servo::class.java, DeviceNames.CLAW_SERVO2)


    private var targetPos = CLAW_CLOSE // postion of claw

    // ? the following are individual helper functions for moving claw to certian postion
    fun openForIntakeNarrow() {
        targetPos = CLAW_INTAKE_NARROW
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
        clawServo2.position = 1-targetPos

    }

    fun clawControl(driver: Gamepad, codriver: Gamepad) { // ? Teleop claw control
        if (codriver.right_bumper || driver.right_bumper){
            if (targetPos == CLAW_CLOSE) targetPos = CLAW_INTAKE_NARROW
            else if (targetPos == CLAW_INTAKE_NARROW) targetPos = CLAW_DEPOSIT
        } else if (codriver.left_bumper || driver.left_bumper) {
            if (targetPos == CLAW_DEPOSIT) targetPos = CLAW_INTAKE_NARROW
            else if (targetPos == CLAW_INTAKE_NARROW) targetPos = CLAW_CLOSE
        }
        update()
    }
}