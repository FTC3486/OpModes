package org.firstinspires.ftc.teamcode.OpModes.Auto;

import org.firstinspires.ftc.teamcode.RobotCoreExtensions.Robot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Matthew on 7/1/2017.
 */

@Autonomous(name = "Range Sensor Test", group = "TestAutonomous")
public class RangeSensorTest extends LinearOpMode {
    Robot mammut = new Robot(this);

    @Override
    public void runOpMode() throws InterruptedException {
        mammut.init();
        waitForStart();

        while(opModeIsActive())
        {
            telemetry.addData("Left Range:", mammut.hw.leftRangeSensor.getUltrasonicRange());
            telemetry.addData("Right Range:", mammut.hw.rightRangeSensor.getUltrasonicRange());
            telemetry.update();
        }
    }
}