package org.firstinspires.ftc.teamcode.Autonomous.Testing;

import com.disnodeteam.dogecv.detectors.JewelDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
@Autonomous(name = "Grab More Glyphs", group = "Autonomous")
@Disabled
//Only for Closer Sides
public class GrabbingMoreGlyphs extends LinearOpMode {

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

        pictograph = new Pictograph(hardwareMap);

        time = new Timer();

        waitForStart();

        if(!isStopRequested()){ //12 Seconds Total for Going back and getting more glyphs

            /*
            //Testing Encoder counts per inch
            robot.print("Circumference Going 10 Inches");
            telemetry.update();
            robot.driveForward(Constant.fullSpeed, (int)(Constant.COUNTS_PER_INCH*10));
            robot.print("Circumference Going 10 Inches");
            telemetry.update();

            robot.waitFor(2000);


            robot.print("Average 30,000 / Distance Covered"); //30000 encoder counts = 286 inches
            telemetry.update();
            robot.driveForward(Constant.fullSpeed, 30000); //Total Encoder (30000) / Total Distance (Inches) = Encoder Counts Per Inch
            robot.print("Average 30,000 / Distance Covered"); //105 encoder counts per inch
            telemetry.update();

            robot.waitFor(2000);


            robot.print("Going 10 inches using encoder counts instead of countsPerInch"); //30000 encoder counts = 286 inches
            telemetry.update();
            robot.driveForward(Constant.fullSpeed, Constant.encoderCountsPerInch*10); //Total Encoder (30000) / Total Distance (Inches) = Encoder Counts Per Inch
            robot.print("Going 10 inches using encoder counts instead of countsPerInch"); //105 encoder counts per inch
            telemetry.update();



            robot.print("Counts Per Inch: "+Constant.COUNTS_PER_INCH);
            robot.print("Encoder 90 THEN Encoder 180");
            telemetry.update();
            robot.rotateLeft(Constant.fullSpeed, Constant.encoder90);
            robot.waitFor(1000);
            robot.rotateRight(Constant.fullSpeed, Constant.encoder180);
            robot.print("Encoder 90 THEN  Encoder 180");
            telemetry.update();
*/

            /*

            robot.rotateLeft(Constant.fullSpeed, Constant.encoder180);

            robot.driveForward(Constant.fullSpeed, 2000);

            grabMoreGlyph();

            robot.rotateLeft(Constant.fullSpeed, Constant.encoder180-100);

            robot.driveForward(Constant.fullSpeed, 2200);

            robot.grabberB();

            robot.driveBackward(Constant.driveSpeed, 1000);

            */

        }
    }

    public void grabGlyph(){

        robot.grabberA();

        robot.liftElevator(0.25);

        robot.waitFor(500);

        robot.liftElevator(0);

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

            robot.rotateRight(Constant.knockSpeed, 500);

            robot.waitFor(100);

            robot.setJewelUp();

            robot.waitFor(100);

            robot.rotateLeft(Constant.knockSpeed, 500);


        }else if (order == JewelDetector.JewelOrder.BLUE_RED){

            robot.rotateLeft(Constant.knockSpeed, 500);

            robot.waitFor(100);

            robot.setJewelUp();

            robot.waitFor(100);

            robot.rotateRight(Constant.knockSpeed, 500);


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