/* Copyright (c) 2015 Qualcomm Technologies Inc
All rights reserved.
Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.
NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

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

        /*for(int i = 0; i < 60; i++) {
            waitOneFullHardwareCycle();
            sleep(100);
            dataList.add( (double) colorSensor.alpha());
            telemetry.addData("DataPoint:", dataList.get(i));
        }

        telemetry.addData("Mean", Statistician.get_mean_value(dataList));
        telemetry.addData("Standard Dev", Statistician.get_standard_deviation(dataList));

        confidenceInterval = Statistician.construct_confidence_interval_from_data_list(dataList);
        telemetry.addData("Upper Bound", confidenceInterval.upperBound);
        telemetry.addData("Lower Bound", confidenceInterval.lowerBound);*/

    }
}