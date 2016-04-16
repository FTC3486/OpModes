package com.FTC3486.OpModes;

import com.FTC3486.FTCRC_Extensions.AutoDriver;
import com.FTC3486.FTCRC_Extensions.ColorAutoDriver;
import com.FTC3486.FTCRC_Extensions.EncoderAutoDriver;
import com.FTC3486.FTCRC_Extensions.DriveTrain;
import com.FTC3486.FTCRC_Extensions.ExtendedDcMotor;
import com.FTC3486.FTCRC_Extensions.GyroscopeAutoDriver;
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

/**
 * Created by Matthew on 11/25/2015.
 */
public class RedAutoPark extends LinearOpMode {
    TapeMeasure tapeMeasure;
    Winch winch;
    ParkingBrake parkingBrake;
    Turret turret;
    Plow plow;
    Pickup pickup;
    GyroSensor gyroSensor;
    ClimberDump climberDump;
    DriveTrain driveTrain;
    AutoDriver gyroDriver;
    AutoDriver encoderDriver;
    AutoDriver colorDriver;

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
        gyroDriver = new GyroscopeAutoDriver(this, driveTrain, "gyroSensor", hardwareMap);
        encoderDriver = new EncoderAutoDriver(this, driveTrain);
        colorDriver = new ColorAutoDriver(this, driveTrain, "cS", hardwareMap);
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

        gyroDriver.set_wait_time_between_movements(250);
        encoderDriver.set_wait_time_between_movements(250);
        colorDriver.set_wait_time_between_movements(250);

        gyroDriver.set_power(0.75);
        gyroDriver.drive_forward(11000);
        encoderDriver.turn_counterclockwise(1300);
        colorDriver.set_power(0.25);
        colorDriver.drive_forward(0);
        colorDriver.drive_forward(0);

        gyroDriver.set_power(0.5);
        gyroDriver.drive_backward(-900);
        encoderDriver.turn_counterclockwise(890);
        encoderDriver.set_power(0.5);
        encoderDriver.drive_backward(-725);
        climberDump.dumpClimbers();
        sleep(2000);
        climberDump.holdClimbers();
        sleep(1000);

        gyroDriver.drive_forward(900);
        encoderDriver.turn_clockwise(890);
        gyroDriver.drive_forward(1900);
    }
}
