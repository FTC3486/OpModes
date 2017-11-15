package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotCoreExtensions.GamepadWrapper;
import org.firstinspires.ftc.teamcode.Subsystems.RelicClaw;

/**
 * Created by 3486 on 7/15/2017.
 */

@TeleOp(name="RelicGrabberTest", group="Teleop2016")

public class RelicClawTest extends OpMode
{
    GamepadWrapper joy1;
    RelicClaw relicClaw;

    @Override
    public void init() {
        joy1 = new GamepadWrapper();
        relicClaw = new RelicClaw("clawservo1","clawservo2",hardwareMap);

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
        if (gamepad1.left_bumper){
            relicClaw.closeClaw();
        } else if(gamepad1.right_bumper){
            relicClaw.openClaw();
        } else if(gamepad1.a && gamepad1.b){

        }
        if(gamepad1.x){
            relicClaw.grabRelic();
        }else if (gamepad1.y){
            relicClaw.releaseRelic();
        }


    }
    @Override
    public void stop(){
    }
}