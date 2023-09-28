package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.opmodes.baseOpMode


@TeleOp(name = "Test Op Mode w/ BlackSmith")
class testOp : baseOpMode() {
    private fun describeDriverControls() = with(bot) {
    }


    private fun describeCodriverControls() = with(bot) {
    }



    override fun describeControls() {
        describeCodriverControls()
        describeDriverControls()
    }
}