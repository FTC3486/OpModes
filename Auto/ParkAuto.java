package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotCoreExtensions.Robot;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.TWA;

/**
 * Created by Matthew on 7/1/2017.
 */

@Autonomous(name = "Park Auto", group = "TestAutonomous")
public class ParkAuto extends LinearOpMode {
    TWA twaRobot = new TWA(this);

    @Override
    public void runOpMode() throws InterruptedException {
        twaRobot.init();
        waitForStart();

        twaRobot.hw.drivetrain.setPowers(0.3, 0.3);
        sleep(800);
        twaRobot.hw.drivetrain.haltDrive();




    }
}