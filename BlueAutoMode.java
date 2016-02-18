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

import com.FTC3486.FTCRC_Extensions.Driver;
import com.FTC3486.Subsystems.ClimberDump;
import com.FTC3486.Subsystems.ParkingBrake;
import com.FTC3486.Subsystems.Pickup;
import com.FTC3486.Subsystems.Plow;
import com.FTC3486.Subsystems.TapeMeasure;
import com.FTC3486.Subsystems.Turret;
import com.FTC3486.Subsystems.Winch;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Matthew on 11/25/2015.
 */
public class BlueAutoMode extends LinearOpMode {
    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    Driver driver;
    DcMotor leftfront, leftback, rightfront, rightback;
    TapeMeasure tapeMeasure;
    Winch winch;
    ParkingBrake parkingBrake;
    Turret turret;
    Plow plow;
    Pickup pickup;
    GyroSensor gyroSensor;
    ClimberDump climberDump;

    //TODO: Reorganize/Move these methods
    public void resetDriveMotorEncoders(DcMotor leftMotorWithEncoder, DcMotor rightMotorWithEncoder)
            throws InterruptedException {
        leftMotorWithEncoder.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotorWithEncoder.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();

        leftMotorWithEncoder.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotorWithEncoder.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        waitOneFullHardwareCycle();
    }

    public void driveForwardtoEncoderCount(int encoderCount) {
        while(leftfront.getCurrentPosition() < encoderCount && rightfront.getCurrentPosition() < encoderCount
                && this.opModeIsActive()) {

            leftfront.setPower(.6f);
            leftback.setPower(.6f);
            rightfront.setPower(.6f);
            rightback.setPower(.6f);
        }

        leftfront.setPower(0.0f);
        leftback.setPower(0.0f);
        rightfront.setPower(0.0f);
        rightback.setPower(0.0f);
    }

    public void counterClockwiseGyroTurn(int gyroHeading) {
        while(gyroSensor.getHeading() < gyroHeading && this.opModeIsActive()) {
            leftfront.setPower(-0.5f);
            leftback.setPower(-0.5f);
            rightfront.setPower(0.5f);
            rightback.setPower(0.5f);
        }

        while(gyroSensor.getHeading() > gyroHeading && this.opModeIsActive()) {
            leftfront.setPower(-0.5f);
            leftback.setPower(-0.5f);
            rightfront.setPower(0.5f);
            rightback.setPower(0.5f);
        }
    }

    public void clockwiseGyroTurn(int gyroHeading) {
        while(gyroSensor.getHeading() > gyroHeading && this.opModeIsActive()) {
            leftfront.setPower(1.0f);
            leftback.setPower(1.0f);
            rightfront.setPower(-1.0f);
            rightback.setPower(-1.0f);
        }

        while(gyroSensor.getHeading() < gyroHeading && this.opModeIsActive()) {
            leftfront.setPower(1.0f);
            leftback.setPower(1.0f);
            rightfront.setPower(-1.0f);
            rightback.setPower(-1.0f);
        }

        leftfront.setPower(0.0f);
        leftback.setPower(0.0f);
        rightfront.setPower(0.0f);
        rightback.setPower(0.0f);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        leftfront = hardwareMap.dcMotor.get("leftfront");
        leftback = hardwareMap.dcMotor.get("leftback");
        rightfront = hardwareMap.dcMotor.get("rightfront");
        rightback = hardwareMap.dcMotor.get("rightback");

        tapeMeasure = new TapeMeasure("tapeMotor", "tapeTilt", hardwareMap);
        winch = new Winch("winchMotor", hardwareMap);
        parkingBrake = new ParkingBrake("parkingBrake", hardwareMap);
        turret = new Turret("swivel", "extender", "dumper", hardwareMap);
        plow = new Plow("leftPlow", "rightPlow", hardwareMap);
        pickup = new Pickup("pickup", hardwareMap);
        gyroSensor = hardwareMap.gyroSensor.get("gyroSensor");
        climberDump = new ClimberDump("climberDump", hardwareMap);

        // wait for the start button to be pressed

        waitForStart();

        while(leftfront.getCurrentPosition() != 0 && rightfront.getCurrentPosition() != 0 && this.opModeIsActive()) {
            resetDriveMotorEncoders(leftfront, rightfront);
            waitOneFullHardwareCycle();
            leftfront.setPower(0.0f);
            leftback.setPower(0.0f);
            rightfront.setPower(0.0f);
            rightback.setPower(0.0f);
        }

        driveForwardtoEncoderCount(1500);

        timer.reset();
        while(timer.time() < 500 && this.opModeIsActive()) { }

        while(leftfront.getCurrentPosition() != 0 && rightfront.getCurrentPosition() != 0 && this.opModeIsActive()) {
            resetDriveMotorEncoders(leftfront, rightfront);
            waitOneFullHardwareCycle();
            leftfront.setPower(0.0f);
            leftback.setPower(0.0f);
            rightfront.setPower(0.0f);
            rightback.setPower(0.0f);
        }

        while(leftfront.getCurrentPosition() < 475 && rightfront.getCurrentPosition() > -475 && this.opModeIsActive()) {
            leftfront.setPower(0.5f);
            leftback.setPower(0.5f);
            rightfront.setPower(-0.5f);
            rightback.setPower(-0.5f);
        }
        leftfront.setPower(0.0f);
        leftback.setPower(0.0f);
        rightfront.setPower(0.0f);
        rightback.setPower(0.0f);

        timer.reset();
        while(timer.time() < 500 && this.opModeIsActive()) { }

        while(leftfront.getCurrentPosition() != 0 && rightfront.getCurrentPosition() != 0 && this.opModeIsActive()) {
            resetDriveMotorEncoders(leftfront, rightfront);
            waitOneFullHardwareCycle();
        }

        driveForwardtoEncoderCount(5250);

        timer.reset();
        while(timer.time() < 500 && this.opModeIsActive()) { }

        while(leftfront.getCurrentPosition() != 0 && rightfront.getCurrentPosition() != 0 && this.opModeIsActive()) {
            resetDriveMotorEncoders(leftfront, rightfront);
            waitOneFullHardwareCycle();
            leftfront.setPower(0.0f);
            leftback.setPower(0.0f);
            rightfront.setPower(0.0f);
            rightback.setPower(0.0f);
        }
    }
}