package com.FTC3486.OpModes;

import com.FTC3486.FTCRC_Extensions.AutoDriver;
import com.FTC3486.FTCRC_Extensions.ColorAutoDriver;
import com.FTC3486.FTCRC_Extensions.EncoderAutoDriver;
import com.FTC3486.FTCRC_Extensions.GyroscopeAutoDriver;
import com.FTC3486.FTCRC_Extensions.DriveTrain;
import com.FTC3486.FTCRC_Extensions.ExtendedDcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Matthew on 11/25/2015.
 */
public class ColorSensorDriveTest extends LinearOpMode {
    DriveTrain driveTrain;
    AutoDriver sensorDriver;
    AutoDriver linearDriver;
    AutoDriver angularDriver;
    ColorSensor colorSensor;

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
        sensorDriver = new ColorAutoDriver(this, driveTrain, "cS", hardwareMap);
        angularDriver = new EncoderAutoDriver(this, driveTrain);
        colorSensor = hardwareMap.colorSensor.get("cS");

        // wait for the start button to be pressed
        waitOneFullHardwareCycle();

        waitForStart();
        int threshold = colorSensor.argb();
        waitOneFullHardwareCycle();

        sensorDriver.set_power(0.25);
        sensorDriver.drive_forward(threshold);

        sensorDriver.drive_forward(threshold);
    }
}