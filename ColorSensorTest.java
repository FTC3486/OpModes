package com.FTC3486.OpModes;

import com.FTC3486.FTCRC_Extensions.Statistician;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import java.util.ArrayList;

/**
 * Created by Matthew on 11/25/2015.
 */
public class ColorSensorTest extends LinearOpMode {
    ColorSensor colorSensor;
    ArrayList<Double> dataList = new ArrayList(60);
    Statistician.ConfidenceInterval confidenceInterval;

    @Override
    public void runOpMode() throws InterruptedException {
        colorSensor = hardwareMap.colorSensor.get("cS");

        for(int i = 0; i < 60; i++) {
            waitOneFullHardwareCycle();
            sleep(100);
            dataList.add( (double) colorSensor.argb());
            telemetry.addData("DataPoint:", dataList.get(i));
        }

        telemetry.addData("Mean", Statistician.get_mean_value(dataList));
        telemetry.addData("Standard Dev", Statistician.get_standard_deviation(dataList));

        confidenceInterval = Statistician.construct_confidence_interval_from_data_list(dataList);
        telemetry.addData("Upper Bound", confidenceInterval.upperBound);
        telemetry.addData("Lower Bound", confidenceInterval.lowerBound);

    }
}