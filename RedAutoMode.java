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
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Matthew on 11/25/2015.
 */
public class RedAutoMode extends LinearOpMode {
    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    DcMotor leftback, rightback;
    ExtendedDcMotor leftfront, rightfront;
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
        leftfront.setPower(-0.5f);
        leftback.setPower(-0.5f);
        rightfront.setPower(0.5f);
        rightback.setPower(0.5f);

        while(gyroSensor.getHeading() < gyroHeading && this.opModeIsActive()) {
            telemetry.addData("Gyro Heading", gyroSensor.getHeading());
        }

        while(gyroSensor.getHeading() > gyroHeading && this.opModeIsActive()) {
            telemetry.addData("Gyro Heading", gyroSensor.getHeading());
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
        autoDriver.gyroTurn("COUNTER_CLOCKWISE", 345, 0.25);
        autoDriver.waitMilliseconds(500);
        autoDriver.gyroTurn("COUNTER_CLOCKWISE", 315, 0.15);

        autoDriver.waitMilliseconds(500);

        autoDriver.driveForwardtoEncoderCountWithCorrection(7000, 1.0, 315);

        autoDriver.waitMilliseconds(500);

        autoDriver.gyroTurn("CLOCKWISE", 45, 0.25);
        autoDriver.waitMilliseconds(500);
        autoDriver.gyroTurn("CLOCKWISE", 87, 0.15);

        autoDriver.waitMilliseconds(500);

        autoDriver.driveBackwardtoEncoderCount(-250, -0.25);

        climberDump.dumpClimbers();

        autoDriver.waitMilliseconds(2000);

        climberDump.holdClimbers();


        /*leftfront.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightfront.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        while( (leftfront.getMode() != DcMotorController.RunMode.RESET_ENCODERS) || (rightfront.getMode() != DcMotorController.RunMode.RESET_ENCODERS) && (this.opModeIsActive()) ) {
            sleep(1);
        }

        while( (leftfront.getCurrentPosition() != 0) || (rightfront.getCurrentPosition() != 0) && (this.opModeIsActive()) ) {
            sleep(1);
        }

        if(leftfront.getCurrentPosition() != 0 && rightfront.getCurrentPosition() != 0) {
            telemetry.addData("LeftMotorMode", leftfront.getMode());
            telemetry.addData("RightMotorMode", rightfront.getMode());
            telemetry.addData("Error, Left Encoders.", leftfront.getCurrentPosition());
            telemetry.addData("Error, Right Encoders.", rightfront.getCurrentPosition());
            leftfront.setPower(0.0f);
            leftback.setPower(0.0f);
            rightfront.setPower(0.0f);
            rightback.setPower(0.0f);
            sleep(2000);
        }*/

        /*while(leftfront.getCurrentPosition() != 0 && rightfront.getCurrentPosition() != 0 && this.opModeIsActive()) {
            sleep(1);
        }*/

        /*leftfront.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightfront.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        while( (leftfront.getMode() != DcMotorController.RunMode.RUN_USING_ENCODERS) || (rightfront.getMode() != DcMotorController.RunMode.RUN_USING_ENCODERS) && (this.opModeIsActive())) {
            sleep(1);
        }

        telemetry.addData("LeftMotorMode", leftfront.getMode());
        telemetry.addData("RightMotorMode", rightfront.getMode());

        driveForwardtoEncoderCountWithCorrection(1500);

        timer.reset();
        while(timer.time() < 500 && this.opModeIsActive()) {
        }

        leftfront.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        rightfront.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        while( (leftfront.getMode() != DcMotorController.RunMode.RUN_WITHOUT_ENCODERS) || (rightfront.getMode() != DcMotorController.RunMode.RUN_WITHOUT_ENCODERS) && (this.opModeIsActive())) {
            sleep(1);
        }

        telemetry.addData("LeftMotorMode", leftfront.getMode());
        telemetry.addData("RightMotorMode", rightfront.getMode());

        counterClockwiseGyroTurn(342);
        leftfront.setPower(0.0f);
        leftback.setPower(0.0f);
        rightfront.setPower(0.0f);
        rightback.setPower(0.0f);

        /*while(leftfront.getCurrentPosition() > -475 && rightfront.getCurrentPosition() < 475 && this.opModeIsActive()) {
            leftfront.setPower(-0.5f);
            leftback.setPower(-0.5f);
            rightfront.setPower(0.5f);
            rightback.setPower(0.5f);
        }
        leftfront.setPower(0.0f);
        leftback.setPower(0.0f);
        rightfront.setPower(0.0f);
        rightback.setPower(0.0f);*/

        /*timer.reset();
        while(timer.time() < 500 && this.opModeIsActive()) { }

        leftfront.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightfront.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        while( (leftfront.getMode() != DcMotorController.RunMode.RESET_ENCODERS) || (rightfront.getMode() != DcMotorController.RunMode.RESET_ENCODERS) && (this.opModeIsActive()) ) {
            sleep(1);
        }

        while( (leftfront.getCurrentPosition() != 0) || (rightfront.getCurrentPosition() != 0) && (this.opModeIsActive()) ) {
            sleep(1);
        }

        if(leftfront.getCurrentPosition() != 0 && rightfront.getCurrentPosition() != 0) {
            telemetry.addData("LeftMotorMode", leftfront.getMode());
            telemetry.addData("RightMotorMode", rightfront.getMode());
            telemetry.addData("Error, Left Encoders.", leftfront.getCurrentPosition());
            telemetry.addData("Error, Right Encoders.", rightfront.getCurrentPosition());
            leftfront.setPower(0.0f);
            leftback.setPower(0.0f);
            rightfront.setPower(0.0f);
            rightback.setPower(0.0f);
            sleep(2000);
        }

        leftfront.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightfront.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        while( (leftfront.getMode() != DcMotorController.RunMode.RUN_USING_ENCODERS) || (rightfront.getMode() != DcMotorController.RunMode.RUN_USING_ENCODERS) && (this.opModeIsActive()) ) {
            sleep(1);
        }

        driveForwardtoEncoderCountWithCorrection(5000);

        timer.reset();
        while (timer.time() < 500 && this.opModeIsActive()) { }

        //counterClockwiseGyroTurn(270);
        leftfront.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        rightfront.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        while( (leftfront.getMode() != DcMotorController.RunMode.RUN_WITHOUT_ENCODERS) || (rightfront.getMode() != DcMotorController.RunMode.RUN_WITHOUT_ENCODERS) && (this.opModeIsActive())) {
            sleep(1);
        }

        telemetry.addData("GyroReading:", gyroSensor.getHeading());
        telemetry.addData("LeftMotorMode", leftfront.getMode());
        telemetry.addData("RightMotorMode", rightfront.getMode());

        leftfront.setPower(-0.5f);
        leftback.setPower(-0.5f);
        rightfront.setPower(0.5f);
        rightback.setPower(0.5f);

        while(gyroSensor.getHeading() > 270 && this.opModeIsActive()) {
            telemetry.addData("Gyro Heading", gyroSensor.getHeading());
        }

        telemetry.addData("Out of loop", gyroSensor.getHeading());
        telemetry.addData("LeftMotorMode", leftfront.getMode());
        telemetry.addData("RightMotorMode", rightfront.getMode());
        leftfront.setPower(0.0f);
        leftback.setPower(0.0f);
        rightfront.setPower(0.0f);
        rightback.setPower(0.0f);
        telemetry.addData("Stopped Motors", gyroSensor.getHeading());
        telemetry.addData("LeftMotorMode", leftfront.getMode());
        telemetry.addData("RightMotorMode", rightfront.getMode());*/

        /*leftfront.setPower(-1.0f);
        leftback.setPower(-1.0f);
        rightfront.setPower(-1.0f);
        rightback.setPower(-1.0f);*/


    }
}
