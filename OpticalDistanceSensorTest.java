package com.FTC3486.OpModes;

import com.FTC3486.FTCRC_Extensions.Statistician;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import java.util.ArrayList;

/**
 * Created by Matthew on 11/25/2015.
 */
public class OpticalDistanceSensorTest extends LinearOpMode {
    OpticalDistanceSensor opticalDistanceSensor;

    ArrayList<Double> dataList = new ArrayList(60);

    Statistician.ConfidenceInterval confidenceInterval;

    @Override
    public void runOpMode() throws InterruptedException {
        opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get("opticalDistanceSensor");

        for(int i = 0; i < 60; i++) {
            sleep(100);
            dataList.add(opticalDistanceSensor.getLightDetected() * 1000);
            telemetry.addData("DataPoint:", dataList.get(i));
        }

        telemetry.addData("Mean", Statistician.get_mean_value(dataList));
        telemetry.addData("Standard Dev", Statistician.get_standard_deviation(dataList));

        confidenceInterval = Statistician.construct_confidence_interval_from_data_list(dataList);
        telemetry.addData("Upper Bound", confidenceInterval.upperBound);
        telemetry.addData("Lower Bound", confidenceInterval.lowerBound);
    }
}