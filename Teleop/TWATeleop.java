package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotCoreExtensions.GamepadWrapper;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.TWA;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.TeleopDriver;

/**
 * Created by 3486 on 7/15/2017.
 */

@TeleOp(name = "TWA Teleop", group = "Teleop2017")

public class TWATeleop extends OpMode {
    //Declare parts of the robot that will be used by this Teleop
    private TWA twaRobot = new TWA(this);
    private GamepadWrapper joy1;
    private TeleopDriver teleopDriver;
    private int spinnerPos;

    @Override
    public void init() {
        twaRobot.init();
        teleopDriver = new TeleopDriver(this, twaRobot.hw.drivetrain);
        teleopDriver.setMaxSpeed(1f);
        joy1 = new GamepadWrapper();

        //Initial subsystem positions
        twaRobot.hw.glyphGrabber.collapsed();

        spinnerPos = 0;

        twaRobot.hw.jewelArm.up();
        twaRobot.hw.relicClaw.openClaw();
    }

    @Override
    public void init_loop() {
        twaRobot.hw.glyphSpinner.stop();
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        twaRobot.hw.jewelArm.up();
        joy1.update(gamepad1);

        //Toggle Half Speed on the drivetrain
        if (joy1.toggle.right_stick_button) {
            //Swap front and back of the robot, and control the drive train at half speed
            if (joy1.toggle.left_stick_button) {
                teleopDriver.half_speed_tank_drive(gamepad1, TeleopDriver.Direction.BACKWARD);
            } else {
                teleopDriver.half_speed_tank_drive(gamepad1, TeleopDriver.Direction.FORWARD);
            }
        } else {
            //Swap front and back of the robot, and control the drive train
            if (joy1.toggle.left_stick_button) {
                teleopDriver.tank_drive(gamepad1, TeleopDriver.Direction.BACKWARD);
            } else {
                teleopDriver.tank_drive(gamepad1, TeleopDriver.Direction.FORWARD);
            }
        }

        //Reset to position 1
        if (gamepad1.dpad_down) {
            //       twaRobot.hw.glyphSpinner.Reset();
        }
        //Spin Glyph grabber and Swap Glyph Grabber buttons so button orintation stays the same
        else if (joy1.toggle.y) {
            if (twaRobot.hw.glyphSpinner.spinnerTouch.getState() == false) {
                twaRobot.hw.glyphSpinner.stop();
            }
            if (twaRobot.hw.glyphLift.isFullyRetracted() && spinnerPos != 0) {
//                twaRobot.hw.glyphLift.shortlift();
                twaRobot.hw.glyphSpinner.Position2();
                spinnerPos = 0;
            } else {
                twaRobot.hw.glyphSpinner.Position2();
                spinnerPos = 0;
            }

            //Open bottom grabber while button is held
            if (joy1.toggle.right_bumper) {
                twaRobot.hw.glyphGrabber.openGrabber1();
            } else {
                twaRobot.hw.glyphGrabber.closeGrabber1();
            }
            //Open top grabber while button is held
            if (gamepad1.left_bumper) {
                twaRobot.hw.glyphGrabber.openGrabber2();
            } else {
                twaRobot.hw.glyphGrabber.closeGrabber2();
            }

        } else {
            if (twaRobot.hw.glyphSpinner.spinnerTouch.getState() == false) {
                twaRobot.hw.glyphSpinner.stop();
            }
            //Spin Glyph grabber and Swap Glyph Grabber buttons so button orintation stays the same
            if (twaRobot.hw.glyphLift.isFullyRetracted() && spinnerPos != 1) {
//                twaRobot.hw.glyphLift.shortlift();
                twaRobot.hw.glyphSpinner.Position1();
                spinnerPos = 1;
            } else {
                twaRobot.hw.glyphSpinner.Position1();
                spinnerPos = 1;
            }

            //Open top grabber while button is held
            if (gamepad1.left_bumper) {
                twaRobot.hw.glyphGrabber.openGrabber1();
            } else {
                twaRobot.hw.glyphGrabber.closeGrabber1();
            }

            //Open bottom grabber while button is held
            if (joy1.toggle.right_bumper) {
                twaRobot.hw.glyphGrabber.openGrabber2();
            } else {
                twaRobot.hw.glyphGrabber.closeGrabber2();
            }
        }

        //Lift Glyph Grabber while button is held
        if (gamepad1.left_trigger > 0.2) {
            twaRobot.hw.glyphLift.lift();
        }//Lower Glyph Grabber while button is held unless touch sensor detects that the lift is bottomed out
        else if (gamepad1.right_trigger > 0.2) {
            twaRobot.hw.glyphLift.retract();
        } else {
            //Stop all Glyph Lift motion while nothing is pressed
            twaRobot.hw.glyphLift.stop();
        }

        //Runs Relic Lift down while button is head
        if (gamepad2.dpad_down) {
            twaRobot.hw.relicLift.retract();
        }//Runs Relic Lift up while button is held
        else if (gamepad2.dpad_up) {
            twaRobot.hw.relicLift.lift();
        }//Stop all Relic Lift motion while nothing is pressed
        else {
            twaRobot.hw.relicLift.stop();
        }
        //Extends Relic Arm while button is held
        if (gamepad2.dpad_left) {
            twaRobot.hw.relicArm.extend();
        }//Retracts Relic Arm while button is held
        else if (gamepad2.dpad_right) {
            twaRobot.hw.relicArm.retract();
        }//Stop all Relic Arm motion while nothing is pressed
        else {
            twaRobot.hw.relicArm.stop();
        }
        //Open Relic Claw
        if (gamepad2.a) {
            twaRobot.hw.relicClaw.releaseRelic();
        }//Close Relic Claw
        else if (gamepad2.b) {
            twaRobot.hw.relicClaw.grabRelic();
            //twaRobot.hw.relicClaw.closeClaw();
        }
        //Set Relic CLaw Pivot to Position 1
        if (gamepad2.x) {
            twaRobot.hw.relicClaw.pivotPosition1();
        }//Set Relic CLaw Pivot to Position 2
        else if (gamepad2.y) {
            twaRobot.hw.relicClaw.pivotPosition2();
        }

        telemetry.addData("SpinnerPosition", twaRobot.hw.glyphSpinner.Spinner.getCurrentPosition());
        telemetry.addData("GlyphLift", twaRobot.hw.glyphLift);
    }

    @Override
    public void stop() {
    }
}
