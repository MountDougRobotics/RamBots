@file:Config


package org.firstinspires.ftc.teamcode.components.hardware


import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

@JvmField var CLAW_INTAKE_WIDE   = 0.6245
@JvmField var CLAW_INTAKE_NARROW = 0.578
@JvmField var CLAW_DEPOSIT       = 0.686
@JvmField var CLAW_CLOSE         = 0.425

class Claw (hardwareMap: HardwareMap) {
//    private val clawServo = hardwareMap.get(Servo::class.java, DeviceNames.CLAW_SERVO)
//
//    private var targetPos = CLAW_CLOSE
//
//    fun openForIntakeNarrow() {
//        targetPos = CLAW_INTAKE_NARROW
//    }
//
//    fun openForIntakeWide() {
//        targetPos = CLAW_INTAKE_WIDE
//    }
//
//    fun openForDeposit() {
//        targetPos = CLAW_DEPOSIT
//    }
//
//    fun close() {
//        targetPos = CLAW_CLOSE
//    }

    fun update() {
//        clawServo.position = targetPos
    }
}