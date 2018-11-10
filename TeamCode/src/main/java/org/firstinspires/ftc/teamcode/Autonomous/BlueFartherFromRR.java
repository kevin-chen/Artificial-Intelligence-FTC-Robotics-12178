package org.firstinspires.ftc.teamcode.Autonomous;

import com.disnodeteam.dogecv.detectors.JewelDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Helper.Constant;
import org.firstinspires.ftc.teamcode.Helper.Jewel;
import org.firstinspires.ftc.teamcode.Helper.Pictograph;
import org.firstinspires.ftc.teamcode.Helper.Timer;
import org.firstinspires.ftc.teamcode.Robot.AutoRobot;

/**
 * Created by Artificial Intelligence 12178 on 1/16/2018.
 */
@Autonomous(name = "BLUE Team (Farther from RR)", group = "Autonomous")
public class BlueFartherFromRR extends LinearOpMode {

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
        //robot.initVertical();
        //robot.initJewelArm();

        jewel = new Jewel(hardwareMap);
        jewel.init();

        pictograph = new Pictograph(hardwareMap);

        time = new Timer();

        waitForStart();

        robot.initVertical();
        robot.initJewelArm();

        if(!isStopRequested()){

            scanPictograph(3500);

            robot.waitFor(100);

            scanJewel(3500);

            robot.waitFor(100);

            grabGlyph();

            robot.waitFor(100);

            knockJewel();

            robot.waitFor(100);

            placeGlyph();

        }
    }

    public void placeGlyph(){

        /**
         * Note: Values are NOT test yet***
         *
         * ***Please Test It
         */

        //Calibrating Movement

        //robot.driveForward(Constant.driveSpeed, 2500);

        //robot.driveBackward(Constant.driveSpeed, 500);

        //Figuring out which column to go to

        int distance;

        if (mark == RelicRecoveryVuMark.LEFT)
            distance = 550; //600 was not close enough - more 10:59 PM; 650 was too far 12:18 AM; 600 is too far; 550 is kinda perfect.
        else if (mark == RelicRecoveryVuMark.CENTER || mark == RelicRecoveryVuMark.UNKNOWN)
            distance = 1400; //1600 is too far 11:17 PM; PERFECT 1550; 1550 is too far 12:18; 1400 is PERFECT.
        else if (mark == RelicRecoveryVuMark.RIGHT)
            distance = 2000; //2300 is perfect 11:25 PM; 2300 is too far 12:18; 2100 is too far; 2000 is kinda fine
        else
            distance = 1400; //Middle Distance

        //Moving to Column

        robot.rotateRight(Constant.driveSpeed, (Constant.encoder180)); //At 11:00 PM -100 to -0

        robot.driveForward(Constant.driveSpeed, 2500); //Drive to safe zone

        robot.waitFor(100);

        robot.strafeRight(Constant.driveSpeed, distance); //strafe to desired locating

        robot.waitFor(100);

        //Placing into Column

        robot.driveForward(Constant.driveSpeed, 1000);

        robot.waitFor(100);

        robot.grabberB();

        robot.waitFor(100);

        robot.driveBackward(Constant.driveSpeed, 1000);

        robot.waitFor(100);

        //Making sure glyph is in crytobox

        robot.grabberY();

        robot.waitFor(100);

        robot.driveForward(Constant.driveSpeed, 750);

        robot.waitFor(100);

        robot.driveBackward(Constant.driveSpeed, 250);

        //Grabbing More glyphs

        //moreGlyphs();

    }

    public void moreGlyphs(){

        robot.liftElevator(0.25);

        robot.waitFor(600);

        robot.liftElevator(0);

        robot.grabberX();

        robot.rotateRight(Constant.fullSpeed, Constant.encoder90); //-0

        robot.driveForward(Constant.fullSpeed, 2000);

        robot.strafeRight(Constant.fullSpeed, 5000);

        robot.driveForward(Constant.fullSpeed, 200);

        robot.grabberA();

        robot.driveBackward(Constant.fullSpeed, 200);

        robot.strafeLeft(Constant.fullSpeed, 5000);

        robot.rotateLeft(Constant.fullSpeed, Constant.encoder90);

        robot.driveForward(Constant.fullSpeed, 300);

    }

    public void grabGlyph(){

        robot.grabberA();

        robot.liftElevator(0.25);

        robot.waitFor(500);

        robot.liftElevator(0);

    }

    public void knockJewel(){

        if (order != JewelDetector.JewelOrder.UNKNOWN) {
            robot.setJewelDown();
        }

        //Conditional Statement for Knocking Down Jewel

        if (order == JewelDetector.JewelOrder.RED_BLUE){

            robot.rotateLeft(Constant.knockSpeed, 500);

            robot.waitFor(100);

            robot.setJewelUp();

            robot.waitFor(100);

            robot.rotateRight(0.5, 500);


        }else if (order == JewelDetector.JewelOrder.BLUE_RED){

            robot.rotateRight(Constant.knockSpeed, 500);

            robot.waitFor(100);

            robot.setJewelUp();

            robot.waitFor(100);

            robot.rotateLeft(0.5, 500);


        }else{
            //Do Nothing
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

}