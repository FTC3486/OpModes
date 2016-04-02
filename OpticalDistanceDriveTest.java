package com.FTC3486.OpModes;

import com.FTC3486.FTCRC_Extensions.AutoDriver;
import com.FTC3486.FTCRC_Extensions.EncoderAutoDriver;
import com.FTC3486.FTCRC_Extensions.DriveTrain;
import com.FTC3486.FTCRC_Extensions.ExtendedDcMotor;
import com.FTC3486.FTCRC_Extensions.OpticalAutoDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by Matthew on 11/25/2015.
 */
public class OpticalDistanceDriveTest extends LinearOpMode {
    DriveTrain driveTrain;
    AutoDriver linearDriver;
    AutoDriver angularDriver;
    OpticalDistanceSensor opticalDistanceSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor rightback = hardwareMap.dcMotor.get("rightback");
        rightback.setDirection(DcMotor.Direction.REVERSE);
        DcMotor rightfront = new ExtendedDcMotor(hardwareMap.dcMotor.get("rightfront"), this);
        rightfront.setDirection(DcMotor.Direction.REVERSE);

        driveTrain = new DriveTrain.Builder()
                .addLeftMotor(hardwareMap.dcMotor.get("leftback"))
                .addLeftMotorWithEncoder(new ExtendedDcMotor(hardwareMap.dcMotor.get("leftfront"), this))
                .addRightMotor(rightback)
                .addRightMotorWithEncoder(rightfront)
                .build();
        linearDriver = new OpticalAutoDriver(this, driveTrain, "opticalDistanceSensor", hardwareMap);
        angularDriver = new EncoderAutoDriver(this, driveTrain);
        opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get("opticalDistanceSensor");

        // wait for the start button to be pressed
        waitOneFullHardwareCycle();

        waitForStart();

        linearDriver.set_power(0.25);
        linearDriver.drive_backward(0);
    }
}