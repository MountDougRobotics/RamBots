@file:Config

package org.firstinspires.ftc.teamcode.components.hardware

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.hardware.SimpleServo
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

// ! Do not change these constants except when claw tuning
@JvmField var CLAW_INTAKE_WIDE   = 0.6245
@JvmField var CLAW_INTAKE_NARROW = 0.578
@JvmField var CLAW_DEPOSIT       = 0.686
@JvmField var CLAW_CLOSE         = 0.425

class Claw (hardwareMap: HardwareMap) {
    private val clawServo = SimpleServo(hardwareMap, DeviceNames.CLAW_SERVO, 0.0, 180.0)

    private var targetPos = CLAW_CLOSE

    fun openForIntakeNarrow() {
        targetPos = CLAW_INTAKE_NARROW
    }

    fun openForIntakeWide() {
        targetPos = CLAW_INTAKE_WIDE
    }

    fun openForDeposit() {
        targetPos = CLAW_DEPOSIT
    }

    fun close() {
        targetPos = CLAW_CLOSE
    }

    fun update() {
        clawServo.position = targetPos
    }
}