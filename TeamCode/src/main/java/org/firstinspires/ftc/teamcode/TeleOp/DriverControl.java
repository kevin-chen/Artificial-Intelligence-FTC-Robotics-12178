package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Helper.Constant;
import org.firstinspires.ftc.teamcode.Robot.Robot;

/**
 * Created by Artificial Intelligence 12178 on 1/16/2018.
 */
@TeleOp(name = "Driver Control", group = "TeleOp")
public class DriverControl extends OpMode {

    private Robot robot;

    @Override
    public void init() {

        robot = new Robot(hardwareMap, telemetry);
        robot.initWheels();
        //robot.initVertical();
        //robot.initHorizontal();

    }

    @Override
    public void start() {
        robot.initVertical();
        robot.initHorizontal();
    }

    @Override
    public void loop() {

        robot.now = robot.time.milliseconds();

        robot.allMovement(gamepad1);

        robot.elevator(gamepad1);

        robot.grabber(gamepad1);

        robot.horizontal(gamepad2);

        //robot.print("Grabber Future: "+Constant.grabberFuture);

        robot.printRobot();

        robot.printController(gamepad1, gamepad2);

    }
}
