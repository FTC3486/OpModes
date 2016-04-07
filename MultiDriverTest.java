package com.FTC3486.OpModes;

import com.FTC3486.FTCRC_Extensions.AutoDriver;
import com.FTC3486.FTCRC_Extensions.ColorAutoDriver;
import com.FTC3486.FTCRC_Extensions.DriveTrain;
import com.FTC3486.FTCRC_Extensions.EncoderAutoDriver;
import com.FTC3486.FTCRC_Extensions.ExtendedDcMotor;
import com.FTC3486.FTCRC_Extensions.ProtectedMotorAutoDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Matthew on 11/25/2015.
 */
public class MultiDriverTest extends LinearOpMode {
    DriveTrain driveTrain;
    AutoDriver linearDriver;
    AutoDriver angularDriver;
    AutoDriver sensorDriver;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor rightback = hardwareMap.dcMotor.get("rightback");
        rightback.setDirection(DcMotor.Direction.REVERSE);
        DcMotor rightfront = new ExtendedDcMotor(hardwareMap.dcMotor.get("rightfront"), this);
        rightfront.setDirection(DcMotor.Direction.REVERSE);

        driveTrain = new DriveTrain.Builder()
                .addLeftMotor(hardwareMap.dcMotor.get("leftback"))
                .addLeftMotorWithEncoder(new ExtendedDcMotor(hardwareMap.dcMotor.get("leftfront")
                        , this))
                .addRightMotor(rightback)
                .addRightMotorWithEncoder(rightfront)
                .build();
        linearDriver = new ProtectedMotorAutoDriver(this, driveTrain);
        angularDriver = new EncoderAutoDriver(this, driveTrain);
        sensorDriver = new ColorAutoDriver(this, driveTrain, "cS", hardwareMap);

        waitForStart();

        linearDriver.set_power(1.0);
        linearDriver.drive_forward(2000);
        sleep(1000);

        angularDriver.turn_clockwise(1000);
        sleep(1000);

        sensorDriver.drive_backward(1000);


    }
}