package org.firstinspires.ftc.teamcode.components.hardware

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.components.meta.DeviceNames

class Arm (hardwareMap: HardwareMap, telemetry: Telemetry) {
    private val arm = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR)
    private val arm2 = hardwareMap.get(DcMotorEx::class.java, DeviceNames.ARM_MOTOR2)

    val telemetry = telemetry

    fun update(gamepad: Gamepad) {
        var up = if (gamepad.y) 0.3 else 0.0
        var down = if (gamepad.a) 0.3 else 0.0

        arm.power = up-down
        arm2.power = up-down

    }
}