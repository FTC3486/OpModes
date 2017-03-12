package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Robot;

/**
 * Created by Owner_2 on 12/31/2016.
 */
@Autonomous(name="SensorTest", group="Autonomus")
public class SensorTest extends LinearOpMode {
    Robot mammut = new Robot(this);

    @Override
    public void runOpMode() {
        mammut.init();
        waitForStart();
        mammut.hw.baconActivator.sensorScanning();

        while (opModeIsActive()) {
            telemetry.addData("Blue ", mammut.hw.colorSensor.blue());
            telemetry.addData("Raw Left", mammut.hw.leftOpticalDistanceSensor.getRawLightDetected());
            telemetry.addData("Left ODS reading", mammut.hw.leftOpticalDistanceSensor.getLightDetected());
            telemetry.addData("Raw Right", mammut.hw.rightOpticalDistanceSensor.getRawLightDetected());
            telemetry.addData("Right ODS reading", mammut.hw.rightOpticalDistanceSensor.getLightDetected());
            telemetry.addData("Side Ultrasonic", mammut.hw.sideRangeSensor.getUltrasonicRange());
            telemetry.addData("Front Ultrasonic", mammut.hw.frontRangeSensor.getUltrasonicRange());
            telemetry.update();

        }
    }
}