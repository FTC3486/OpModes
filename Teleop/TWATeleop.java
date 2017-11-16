package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.GamepadWrapper;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.TWA;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.TWARobot;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.TeleopDriver;

/**
 * Created by 3486 on 7/15/2017.
 */

@TeleOp(name="TWA Teleop", group="Teleop2016")

public class TWATeleop extends OpMode
{
    //Declare parts of the robot that will be used by this Teleop
    TWA twaRobot = new TWA(this);
    GamepadWrapper joy1;
    TeleopDriver teleopDriver;
    int spinnerPos;

    @Override
    public void init() {
        twaRobot.init();
        teleopDriver = new TeleopDriver(this, twaRobot.hw.drivetrain);
        teleopDriver.setMaxSpeed(1f);
        joy1 = new GamepadWrapper();

        //Initial subsystem positions
        twaRobot.hw.glyphGrabber.collapsed();

        spinnerPos = 0;
    }

    @Override
    public void init_loop() {
        twaRobot.hw.spinner.stop();
    }

    @Override
    public void start()
    {
    }

    @Override
    public void loop()
    {
        joy1.update(gamepad1);

        //Swap front and back of the robot, and control the drive train
        if (joy1.toggle.right_stick_button){
            if(joy1.toggle.left_stick_button)
            {
                teleopDriver.half_speed_tank_drive(gamepad1, TeleopDriver.Direction.BACKWARD);
            }
            else
            {
                teleopDriver.half_speed_tank_drive(gamepad1, TeleopDriver.Direction.FORWARD);
            }
        }else {
            if (joy1.toggle.left_stick_button) {
                teleopDriver.tank_drive(gamepad1, TeleopDriver.Direction.BACKWARD);
            } else {
                teleopDriver.tank_drive(gamepad1, TeleopDriver.Direction.FORWARD);
            }
        }
        //Reset to position 1
        if(gamepad1.dpad_down){
            // twaRobot.hw.spinner.Reset();
        }
        //Spin Glyph grabber and Swap Glyph Grabber buttons so button orintation stays the same
        else if(joy1.toggle.y){
            if(twaRobot.hw.glyphLift.liftTouch.getState() == false && spinnerPos != 0) {
                twaRobot.hw.glyphLift.shortlift();
                twaRobot.hw.spinner.Position2();
                spinnerPos = 0;
            }else {
                twaRobot.hw.spinner.Position2();
                spinnerPos = 0;
            }
            //Open top grabber while button is held
            if (gamepad1.left_bumper){
                twaRobot.hw.glyphGrabber.openGrabber1();
            } else {
                twaRobot.hw.glyphGrabber.closeGrabber1();
            }
            //Open bottom grabber while button is held
            if (joy1.toggle.right_bumper){
                twaRobot.hw.glyphGrabber.openGrabber2();
            } else {
                twaRobot.hw.glyphGrabber.closeGrabber2();
            }

        }else{
            //Spin Glyph grabber and Swap Glyph Grabber buttons so button orintation stays the same
            if(twaRobot.hw.glyphLift.liftTouch.getState() == false && spinnerPos != 1) {
                twaRobot.hw.glyphLift.shortlift();
                twaRobot.hw.spinner.Position1();
                spinnerPos = 1;
            }else {
                twaRobot.hw.spinner.Position1();
                spinnerPos = 1;
            }
            //Open bottom grabber while button is held
            if (joy1.toggle.right_bumper){
                twaRobot.hw.glyphGrabber.openGrabber1();
            } else {
                twaRobot.hw.glyphGrabber.closeGrabber1();
            }
            //Open top grabber while button is held
            if (gamepad1.left_bumper){
                twaRobot.hw.glyphGrabber.openGrabber2();
            } else {
                twaRobot.hw.glyphGrabber.closeGrabber2();
            }
        }

        //Lift Glyph Grabber while button is held
        if (gamepad1.left_trigger >0.2){
            twaRobot.hw.glyphLift.lift();
        }//Lower Glyph Grabber while button is held unless touch sensor detects that the lift is bottomed out
        else if (gamepad1.right_trigger >0.2){
            twaRobot.hw.glyphLift.retract()
            ;
        } else {
            //Stop all lift motion while nothing is pressed
            twaRobot.hw.glyphLift.stop();
        }


        if (gamepad1.dpad_down){
            twaRobot.hw.relicLift.retract();
        } else if(gamepad1.dpad_up){
            twaRobot.hw.relicLift.lift();
        } else {
            twaRobot.hw.relicLift.stop();
        }

        if(gamepad1.dpad_left){
            twaRobot.hw.relicArm.retract();
        } else if(gamepad1.dpad_right){
            twaRobot.hw.relicArm.extend();
        } else {
            twaRobot.hw.relicArm.stop();
        }

        if (gamepad1.a){
            twaRobot.hw.relicClaw.releaseRelic();
        } else if(gamepad1.b){
            twaRobot.hw.relicClaw.grabRelic();
        }

        if (gamepad1.x){
            twaRobot.hw.relicClaw.pivotPosition1();
        } else if(gamepad1.back){
            twaRobot.hw.relicClaw.pivotPosition2();
        } else{

        }

    telemetry.addData("SpinnerPosition", twaRobot.hw.spinner.Spinner.getCurrentPosition());
    telemetry.addData("LiftTouch", twaRobot.hw.glyphLift.liftTouch.getState());
    telemetry.addData("GlyphLift", twaRobot.hw.glyphLift.Lift.getCurrentPosition());

        twaRobot.hw.glyphLift.reset();
    }
    @Override
    public void stop(){
    }
}
