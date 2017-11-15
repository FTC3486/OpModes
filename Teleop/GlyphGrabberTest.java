package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotCoreExtensions.GamepadWrapper;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.TWARobot;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.TeleopDriver;
import org.firstinspires.ftc.teamcode.Subsystems.GlyphGrabber;

/**
 * Created by 3486 on 7/15/2017.
 */

@TeleOp(name="GlyphGrabberTest", group="Teleop2016")

public class GlyphGrabberTest extends OpMode
{
    GamepadWrapper joy1;
    GlyphGrabber glyphGrabber;

    @Override
    public void init() {
        joy1 = new GamepadWrapper();
        glyphGrabber = new GlyphGrabber("leftservo1","leftservo2","rightservo1","rightservo2", hardwareMap);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start()
    {
    }

    @Override
    public void loop()
    {
        joy1.update(gamepad1);
        if (gamepad1.a){
            glyphGrabber.adjustCloseGrabber1();
        } else if(gamepad1.b){
            glyphGrabber.adjustOpenGrabber1();
        } else if(gamepad1.x){
            glyphGrabber.adjustCloseGrabber2();
        }else if (gamepad1.y){
            glyphGrabber.adjustOpenGrabber2();
        }else if (gamepad1.left_bumper){
            glyphGrabber.closeGrabber1();
        } else if(gamepad1.left_trigger >0.5){
            glyphGrabber.openGrabber1();
        }else if (gamepad1.right_bumper){
            glyphGrabber.closeGrabber2();
        }else if (gamepad1.right_trigger >0.5){
            glyphGrabber.openGrabber2();
        }else if(gamepad1.dpad_down){
            glyphGrabber.collapsed();
        }
        telemetry.addData("Left 1", glyphGrabber.LeftServo1.getPosition());
        telemetry.addData("Right 1", glyphGrabber.RightServo1.getPosition());
        telemetry.addData("Left 2", glyphGrabber.LeftServo2.getPosition());
        telemetry.addData("Right 2", glyphGrabber.RightServo2.getPosition());
        telemetry.update();

    }
    @Override
    public void stop(){
    }
}
