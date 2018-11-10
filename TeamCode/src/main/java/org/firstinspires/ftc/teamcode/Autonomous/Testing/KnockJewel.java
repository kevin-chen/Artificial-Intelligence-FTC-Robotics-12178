package org.firstinspires.ftc.teamcode.Autonomous.Testing;

import com.disnodeteam.dogecv.detectors.JewelDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Helper.Jewel;
import org.firstinspires.ftc.teamcode.Helper.Pictograph;
import org.firstinspires.ftc.teamcode.Helper.Timer;
import org.firstinspires.ftc.teamcode.Robot.AutoRobot;

/**
 * Created by Artificial Intelligence 12178 on 1/16/2018.
 */
@Autonomous(name = "Blue Team - Knock Jewel", group = "Autonomous")
@Disabled
public class KnockJewel extends LinearOpMode {

    private AutoRobot robot;
    private Jewel jewel;
    private Pictograph pictograph;

    private Timer time;

    private JewelDetector.JewelOrder order = JewelDetector.JewelOrder.UNKNOWN;
    private RelicRecoveryVuMark mark = RelicRecoveryVuMark.UNKNOWN;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new AutoRobot(hardwareMap,telemetry, this);
        robot.initWheels();
        robot.initVertical();
        robot.initJewelArm();

        jewel = new Jewel(hardwareMap);
        jewel.init();

        //pictograph = new Pictograph(hardwareMap);

        time = new Timer();

        waitForStart();

        if(!isStopRequested()){

            scanJewel(10000);

            robot.print("Jewel Order: "+order.toString());
            robot.print("Jewel Order: "+order.toString());
            robot.print("Jewel Order: "+order.toString());
            telemetry.update();

            robot.waitFor(250);

            knockJewel();

        }
    }

    public void placeGlyph(){

        robot.rotateLeft(0.2, 3400);

        robot.driveForward(0.2, 1500);

        robot.driveBackward(.07, 250);

        int distance;

        if (mark == RelicRecoveryVuMark.LEFT)
            distance = 1000;
        else if (mark == RelicRecoveryVuMark.CENTER)
            distance = 1800;
        else if (mark == RelicRecoveryVuMark.RIGHT)
            distance = 2400;
        else
            distance = 1800;

        robot.waitFor(250);

        robot.driveForward(.5,distance);

        robot.waitFor(250);

        robot.rotateLeft(.35,2500);

        robot.waitFor(250);

        robot.driveForward(.25, 1000);

        robot.grabberB();

        robot.waitFor(500);

        robot.driveBackward(.35,300);
    }

    public void grabGlyph(){
        robot.grabberA();

        robot.waitFor(750);

        robot.liftElevator(0.25);

        robot.waitFor(500);

        robot.liftElevator(0);

        robot.waitFor(500);
    }

    public void knockJewel(){

        if (order != JewelDetector.JewelOrder.UNKNOWN) {
            robot.setJewelDown();
        }

        //Conditional Statement for Knocking Down Jewel

        if (order == JewelDetector.JewelOrder.RED_BLUE){

            robot.rotateLeft(1, 500);

            robot.waitFor(250);

            robot.setJewelUp();

            robot.waitFor(250);

            robot.rotateRight(1, 500);

        }else if (order == JewelDetector.JewelOrder.BLUE_RED){

            robot.rotateRight(1, 500);

            robot.waitFor(250);

            robot.setJewelUp();

            robot.waitFor(250);

            robot.rotateLeft(1, 500);


        }else{
            //robot.driveForward(.5,2000);
        }
    }

    public void scanJewel(long millis){

        time.reset();

        jewel.enable();

        while(!isStopRequested() && !time.hasReached(millis) && jewel.getJewel() == JewelDetector.JewelOrder.UNKNOWN){
            telemetry.addLine("Scanning for Jewels ("+time.formattedTime()+" seconds)");
            telemetry.update();
        }

        order = jewel.getJewel();

        jewel.disable();
    }

    public void scanPictograph(long millis){

        time.reset();

        pictograph.activate();

        while (!isStopRequested() && !time.hasReached(millis) && !pictograph.isFound()){

            telemetry.addLine("Scanning the Pictograph ("+time.formattedTime()+" seconds)");
            telemetry.addLine("Mark is: " + pictograph.getMark().toString());
            telemetry.update();

            pictograph.scan();
        }

        mark = pictograph.getMark();

        //pictograph.deactivate();
        pictograph.detach();
    }

    //Bad
    public void scanJewel(){

        time.reset();

        jewel.enable();

        while(!isStopRequested() && jewel.getJewel() == JewelDetector.JewelOrder.UNKNOWN){
            telemetry.addLine("Scanning for Jewels ("+time.formattedTime()+" seconds)");
            telemetry.update();
        }

        order = jewel.getJewel();

        jewel.disable();
    }
    //Bad
    public void scanPictograph(){

        time.reset();

        pictograph.activate();

        while (!isStopRequested() && pictograph.getMark().toString().equals("UNKNOWN")){

            telemetry.addLine("Scanning the Pictograph ("+time.formattedTime()+" seconds)");
            telemetry.addLine("Mark is: " + pictograph.getMark().toString());
            telemetry.update();

            pictograph.scan();
        }

        mark = pictograph.getMark();

        pictograph.deactivate();
        //pictograph.detach();
    }
}
