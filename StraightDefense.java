package com.FTC3486.OpModes;

import com.FTC3486.FTCRC_Extensions.AutoDriver;
import com.FTC3486.FTCRC_Extensions.EncoderAutoDriver;
import com.FTC3486.FTCRC_Extensions.GyroscopeAutoDriver;
import com.FTC3486.FTCRC_Extensions.DriveTrain;
import com.FTC3486.FTCRC_Extensions.ExtendedDcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Matthew on 11/25/2015.
 */
public class StraightDefense extends LinearOpMode {
    GyroSensor gyroSensor;
    DriveTrain driveTrain;
    AutoDriver linearDriver;
    AutoDriver angularDriver;

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
        linearDriver = new GyroscopeAutoDriver(this, driveTrain, "gyroSensor", hardwareMap);
        angularDriver = new EncoderAutoDriver(this, driveTrain);
        gyroSensor = hardwareMap.gyroSensor.get("gyroSensor");

        // wait for the start button to be pressed
        gyroSensor.calibrate();
        waitOneFullHardwareCycle();

        waitForStart();

        while(gyroSensor.isCalibrating() && this.opModeIsActive()) {
            sleep(1);
        }

        sleep(10000);
        linearDriver.drive_forward(9000);
    }
}
