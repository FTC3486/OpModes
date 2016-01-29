package com.FTC3486.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Matthew on 1/28/2016.
 */
public class ColorSensorTest extends OpMode {
    ColorSensor colorSensor;

    @Override
    public void init() {
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
    }

    @Override
    public void loop() {
        colorSensor.enableLed(false);
        telemetry.addData("Red", colorSensor.red());
        telemetry.addData("Blue", colorSensor.blue());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Alpha", colorSensor.alpha());
        telemetry.addData("ARGB", colorSensor.argb());
    }
}
