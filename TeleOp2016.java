package com.FTC3486.OpModes;
import com.FTC3486.FTCRC_Extensions.DriveTrain;
import com.FTC3486.FTCRC_Extensions.TeleopDriver;
import com.FTC3486.FTCRC_Extensions.ExtendedDcMotor;
import com.FTC3486.FTCRC_Extensions.GamepadWrapper;
import com.FTC3486.Subsystems.ClimberDump;
import com.FTC3486.Subsystems.ParkingBrake;
import com.FTC3486.Subsystems.Pickup;
import com.FTC3486.Subsystems.Plow;
import com.FTC3486.Subsystems.TapeMeasure;
import com.FTC3486.Subsystems.Turret;
import com.FTC3486.Subsystems.Winch;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Matthew on 8/11/2015.
 */
public class TeleOp2016 extends OpMode{
    GamepadWrapper joy1;
    GamepadWrapper joy2;
    DriveTrain driveTrain;
    TeleopDriver teleopDriver;
    TapeMeasure tapeMeasure;
    Winch winch;
    ParkingBrake parkingBrake;
    Turret turret;
    Plow plow;
    Pickup pickup;
    ClimberDump climberDump;

    @Override
    public void init() {
        joy1 = new GamepadWrapper();
        joy2 = new GamepadWrapper();

        driveTrain = new DriveTrain.Builder()
                .addLeftMotor(hardwareMap.dcMotor.get("leftback"))
                .addLeftMotorWithEncoder(hardwareMap.dcMotor.get("leftfront"))
                .addRightMotor(hardwareMap.dcMotor.get("rightback"))
                .addRightMotorWithEncoder(hardwareMap.dcMotor.get("rightfront"))
                .build();
        teleopDriver = new TeleopDriver(this, driveTrain);

        tapeMeasure = new TapeMeasure("tapeMotor", "tapeTilt", hardwareMap);
        winch = new Winch("winchMotor", hardwareMap);
        parkingBrake = new ParkingBrake("parkingBrake", hardwareMap);
        turret = new Turret("swivel", "extender", "dumper", "dumperSwivel", hardwareMap);
        plow = new Plow("leftPlow", "rightPlow", hardwareMap);
        pickup = new Pickup("pickup", hardwareMap);
        climberDump = new ClimberDump("climberDump", hardwareMap);
    }


    @Override
    public void loop() {
        joy1.update(gamepad1);
        joy2.update(gamepad2);

        // Gamepad 1
        // TODO:Remove reverse button; Wesley only wanted to test;
        if(joy1.toggle.x) {
            teleopDriver.tank_drive(gamepad1, TeleopDriver.Direction.BACKWARD);
        } else {
            teleopDriver.tank_drive(gamepad1, TeleopDriver.Direction.FORWARD);
        }

        if(gamepad1.right_trigger > 0.7){
            winch.in();
        } else if(gamepad1.right_bumper){
            winch.out();
        } else winch.stop();

        if(joy1.toggle.left_bumper){
            plow.goUp();
        } else {
            plow.goDown();
        }

        if(gamepad1.a){
            parkingBrake.brake();
        } else {
            parkingBrake.release();
        }

        if(gamepad1.dpad_down) {
            climberDump.dumpClimbers();
            climberDump.hasDumped = true;
        } else if(climberDump.hasDumped){
            climberDump.stayVertical();
        }

        // Gamepad 2
        if(gamepad2.dpad_right) {
            tapeMeasure.extendTapeMeasure();
        } else if(gamepad2.dpad_left) {
            tapeMeasure.retractTapeMeasure();
        } else {
            tapeMeasure.stopTapeMeasure();
        }

        if(gamepad2.dpad_up) {
            tapeMeasure.tiltUp();
        } else if(gamepad2.dpad_down) {
            tapeMeasure.tiltDown();
        } else {
            tapeMeasure.stopTilt();
        }

        if(gamepad2.y) {
            tapeMeasure.tiltBack();
        }

        if(gamepad2.left_stick_x > 0.8) {
            turret.swivelRight();
        } else if(gamepad2.left_stick_x < -0.8) {
            turret.swivelLeft();
        } else {
            turret.swivelStop();
        }

        if(joy2.toggle.b) {
            turret.dumperSwivelRight();
        } else if(joy2.toggle.x) {
            turret.dumperSwivelLeft();
        } else {
            turret.dumperSwivelCenter();
        }

        if(gamepad2.left_stick_y > 0.8) {
            turret.extend();
        } else if(gamepad2.left_stick_y < -0.8) {
            turret.retract();
        } else {
            turret.extenderStop();
        }

        if(gamepad2.right_bumper) {
            turret.wholeDumpDebris();
        } else if(gamepad2.a) {
            turret.halfDumpDebris();
        } else {
            turret.holdDebris();
        }

        if(joy2.toggle.left_bumper) {
           pickup.collect();
        } else if(joy2.toggle.start) {
            pickup.reverse();
        } else {
            pickup.stop();
        }

        telemetry.addData("Turret ", turret);
        telemetry.addData("Parking Brake ", parkingBrake);
        telemetry.addData("Winch ", winch);
        telemetry.addData("Plow ", plow);
        telemetry.addData("Pickup ", pickup);
        telemetry.addData("tapeMotor ", tapeMeasure);
    }
}
