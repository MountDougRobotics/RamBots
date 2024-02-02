package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.meta.Hardware;

public class ExampleComponent {
    HardwareMap hardwareMap;
    Telemetry telemetry;

    // * Declare Component Variables here: (eg. DcMotorEx, Servo)

    public ExampleComponent(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        // * Set variables declared above:

    }

    public void update(Gamepad driver, Gamepad codriver) {
        // ? Update function, runs every tick
    }
}