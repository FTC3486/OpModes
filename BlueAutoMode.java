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

import com.FTC3486.FTCRC_Extensions.AutoDriver;
import com.FTC3486.FTCRC_Extensions.DriveTrain;
import com.FTC3486.FTCRC_Extensions.ExtendedDcMotor;
import com.FTC3486.Subsystems.ClimberDump;
import com.FTC3486.Subsystems.ParkingBrake;
import com.FTC3486.Subsystems.Pickup;
import com.FTC3486.Subsystems.Plow;
import com.FTC3486.Subsystems.TapeMeasure;
import com.FTC3486.Subsystems.Turret;
import com.FTC3486.Subsystems.Winch;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Matthew on 11/25/2015.
 */
public class BlueAutoMode extends LinearOpMode {
    TapeMeasure tapeMeasure;
    Winch winch;
    ParkingBrake parkingBrake;
    Turret turret;
    Plow plow;
    Pickup pickup;
    GyroSensor gyroSensor;
    ClimberDump climberDump;
    DriveTrain driveTrain;
    AutoDriver autoDriver;

    @Override
    public void runOpMode() throws InterruptedException {

        driveTrain = new DriveTrain.Builder()
                .addLeftMotor(hardwareMap.dcMotor.get("leftback"))
                .addLeftMotorWithEncoder(new ExtendedDcMotor(hardwareMap.dcMotor.get("leftfront")))
                .addRightMotor(hardwareMap.dcMotor.get("rightback"))
                .addRightMotorWithEncoder(new ExtendedDcMotor(hardwareMap.dcMotor.get("rightfront")))
                .build();
        autoDriver = new AutoDriver(this, driveTrain, "gyroSensor", hardwareMap);
        tapeMeasure = new TapeMeasure("tapeMotor", "tapeTilt", hardwareMap);
        winch = new Winch("winchMotor", hardwareMap);
        parkingBrake = new ParkingBrake("parkingBrake", hardwareMap);
        turret = new Turret("swivel", "extender", "dumper", "dumperSwivel", hardwareMap);
        plow = new Plow("leftPlow", "rightPlow", hardwareMap);
        pickup = new Pickup("pickup", hardwareMap);
        gyroSensor = hardwareMap.gyroSensor.get("gyroSensor");
        climberDump = new ClimberDump("climberDump", hardwareMap);

        // wait for the start button to be pressed
        gyroSensor.calibrate();
        waitOneFullHardwareCycle();

        waitForStart();

        while(gyroSensor.isCalibrating() && this.opModeIsActive()) {
            sleep(1);
        }

        autoDriver.waitMilliseconds(500);

        autoDriver.driveForwardtoEncoderCountWithCorrection(1600, 1.0, 0);

        autoDriver.waitMilliseconds(500);

        //The double turn increases accuracy. 
        autoDriver.gyroTurn("CLOCKWISE", 15, 0.25);
        autoDriver.waitMilliseconds(500);
        autoDriver.gyroTurn("CLOCKWISE", 45, 0.15);

        autoDriver.waitMilliseconds(500);

        autoDriver.driveForwardtoEncoderCountWithCorrection(7000, 1.0, 315);

        autoDriver.waitMilliseconds(500);

        autoDriver.gyroTurn("COUNTER_CLOCKWISE", 220, 0.25);
        autoDriver.waitMilliseconds(500);
        autoDriver.gyroTurn("COUNTER_CLOCKWISE", 264, 0.15);

        autoDriver.waitMilliseconds(500);

        autoDriver.driveBackwardtoEncoderCount(-250, -0.25);

        climberDump.dumpClimbers();

        autoDriver.waitMilliseconds(2000);

        climberDump.holdClimbers();

    }
}
