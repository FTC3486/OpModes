package org.firstinspires.ftc.teamcode.OpModes.Auto;

import org.firstinspires.ftc.teamcode.RobotCoreExtensions.Robot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Matthew on 7/1/2017.
 */

@Autonomous(name = "Test Auto", group = "TestAutonomous")
public class TestAuto extends LinearOpMode {
    Robot mammut = new Robot(this);

    @Override
    public void runOpMode() throws InterruptedException {
        mammut.init();
        waitForStart();

        while(opModeIsActive())
        {
            telemetry.addData("IMU Accelerometer:", mammut.hw.adafruitIMU.getAcceleration());
            telemetry.addData("IMU Gyro Reading:", mammut.hw.adafruitIMU.getAngularOrientation());
            telemetry.addData("IMU Gravity:", mammut.hw.adafruitIMU.getGravity());
            telemetry.addData("IMU Magnetic Field Strength:", mammut.hw.adafruitIMU.getMagneticFieldStrength());
            telemetry.update();
        }
    }
}