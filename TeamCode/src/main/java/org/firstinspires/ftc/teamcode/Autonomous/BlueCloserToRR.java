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
@Autonomous(name = "BLUE Team (Closer to RR)", group = "Autonomous")
public class BlueCloserToRR extends LinearOpMode {

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

        //robot.driveBackward(Constant.driveSpeed, 2500);

        //robot.driveForward(Constant.driveSpeed, 500);

        //Figuring out which column to go to

        int distance;
        //All values are with jewel knocker ON White Line
        if (mark == RelicRecoveryVuMark.LEFT)
            distance = 2900; //2900 - Good at Kayla's house 2/24 5:20; PERFECT 1:17 AM.
        else if (mark == RelicRecoveryVuMark.CENTER || mark == RelicRecoveryVuMark.UNKNOWN)
            distance = 3700; //3700 - Perfect at Kayla's house 2/24 1:17 AM.
        else if (mark == RelicRecoveryVuMark.RIGHT)
            distance = 4500; //4500 - - Perfect at Kayla's house 2/24 1:22 AM.
        else
            distance = 3500; //3700

        //Moving to Column

        robot.driveBackward(Constant.driveSpeed,distance);

        robot.waitFor(100);

        robot.strafeLeft(Constant.driveSpeed, 200);

        robot.waitFor(100);

        robot.rotateRight(Constant.driveSpeed, Constant.encoder90);

        robot.waitFor(100);

        //Placing into Column

        robot.driveForward(Constant.driveSpeed, 1100);

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

        //Getting more glyphs

        moreGlyphs();

    }

    public void grabGlyph(){

        robot.grabberA();

        robot.liftElevator(0.25);

        robot.waitFor(800);

        robot.liftElevator(0);

    }

    public void moreGlyphs(){

        robot.liftElevator(0.25);

        robot.waitFor(600);

        robot.liftElevator(0);

        robot.grabberX();

        robot.rotateLeft(Constant.driveSpeed, Constant.encoder180); //-0

        robot.driveForward(Constant.driveSpeed, 2300);

        robot.grabberA();
        //grabMoreGlyph();

        robot.driveBackward(Constant.driveSpeed, 2000);

        robot.rotateLeft(Constant.driveSpeed, Constant.encoder180-100); //-100

        robot.driveForward(Constant.driveSpeed, 800);

        robot.grabberB();

        robot.driveBackward(Constant.driveSpeed, 500);

    }

    public void grabMoreGlyph(){

        robot.grabberA();

        robot.liftElevator(0.25);

        robot.waitFor(1500);

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