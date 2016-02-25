package com.FTC3486.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Matthew on 2/24/2016.
 */
public class NewGyroTest extends OpMode {
    GyroSensor gyroSensor;

    @Override
    public void init() {
        gyroSensor = hardwareMap.gyroSensor.get("gyroSensor");
        gyroSensor.calibrate();
    }

    @Override
    public void loop() {
        gyroSensor.resetZAxisIntegrator();
        telemetry.addData("GyroHeading", gyroSensor.getHeading());
    }
}

