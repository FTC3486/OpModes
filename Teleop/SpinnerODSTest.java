package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotCoreExtensions.GamepadWrapper;
import org.firstinspires.ftc.teamcode.Subsystems.RelicClaw;
import org.firstinspires.ftc.teamcode.Subsystems.Spinner;

/**
 * Created by 3486 on 7/15/2017.
 */

@TeleOp(name="SpinnerODSTest", group="Teleop2016")

public class SpinnerODSTest extends OpMode
{
    GamepadWrapper joy1;
    Spinner spinner;

    @Override
    public void init() {
        joy1 = new GamepadWrapper();
        spinner = new Spinner("Spinner","SpinnerODS",hardwareMap);

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
        /*if (gamepad1.left_bumper){
            relicClaw.closeClaw();
        } else if(gamepad1.right_bumper){
            relicClaw.openClaw();
        } else if(gamepad1.a && gamepad1.b){

        }*/
        telemetry.addData("Greem Value",spinner.Ods.green());
        telemetry.addData("Blue Value", spinner.Ods.blue());
        telemetry.addData("Red Value",spinner.Ods.red());
        telemetry.addData("Alpha Value", spinner.Ods.alpha());
        telemetry.update();

    }
    @Override
    public void stop(){
    }
}