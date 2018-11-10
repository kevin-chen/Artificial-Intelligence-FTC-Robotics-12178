package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.Constant;
import org.firstinspires.ftc.teamcode.Helper.Timer;

/**
 * Created by Artificial Intelligence 12178 on 1/16/2018.
 */

public class AutoRobot {

    private ElapsedTime runtime;
    private Timer time;

    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private LinearOpMode opMode;

    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    private DcMotor elevator = null;

    private Servo leftClaw = null;
    private Servo rightClaw = null;

    private Servo relicClaw = null;
    private Servo relicWrist = null;

    private Servo jewelArm = null;

    public AutoRobot(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode opMode){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.opMode = opMode;

        runtime = new ElapsedTime();
        time = new Timer();
    }

    public void initWheels() {

        print("> Begin Initializing Wheels");
        try {
            frontLeft = hardwareMap.dcMotor.get("frontLeft");
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } catch (Exception e) {
            print("Error: frontLeft");
        }
        try {
            frontRight = hardwareMap.dcMotor.get("frontRight");
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } catch (Exception e) {
            print("Error: frontRight");
        }
        try {
            backLeft = hardwareMap.dcMotor.get("backLeft");
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } catch (Exception e) {
            print("Error: backLeft");
        }
        try {
            backRight = hardwareMap.dcMotor.get("backRight");
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } catch (Exception e) {
            print("Error: backRight");
        }
        print("> End Initializing Wheels");
        telemetry.update();
    }

    public void initVertical() {

        Constant.grabberFuture = 0;

        print("> Begin Initializing Vertical Components");
        try {
            elevator = hardwareMap.dcMotor.get("elevator");
            elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } catch (Exception e) {
            print("Error: elevator");
        }

        try {
            leftClaw = hardwareMap.servo.get("leftClaw");
            leftClaw.setPosition(0);
        } catch (Exception e) {
            print("Error: leftClaw");
        }
        try {
            rightClaw = hardwareMap.servo.get("rightClaw");
            rightClaw.setPosition(1);
        } catch (Exception e) {
            print("Error: rightClaw");
        }
        print("> End Initializing Vertical Components");
        telemetry.update();
    }

    public void initJewelArm(){
        print("> Begin Initializing Jewel Arm");

        try{
            jewelArm = hardwareMap.servo.get("jewel");
            jewelArm.setPosition(Constant.jewelUpPos);
        }
        catch (Exception e){
            print("Error: jewel");
        }

        try {
            relicWrist = hardwareMap.servo.get("relicWrist");
            relicWrist.setPosition(Constant.relicWristDown);
        } catch (Exception e) {
            print("Error: relicWrist");
        }
        try {
            relicClaw = hardwareMap.servo.get("relicClaw");
            relicClaw.setPosition(Constant.relicClawClose);
        } catch (Exception e) {
            print("Error: relicClaw");
        }

        print("> End Initializing Jewel Arm");
        telemetry.update();
    }

    public void waitFor(long millis){
        time.reset();
        while(!opMode.isStopRequested() && !time.hasReached(millis)){}
    }

    //Encoder Driving Methods - Begin

    public void driveForward(double power, int distance){

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - distance);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + distance);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - distance);
        backRight.setTargetPosition(backRight.getCurrentPosition() + distance);

        print("FL Target: " + frontLeft.getTargetPosition());
        print("FR Target: " + frontRight.getTargetPosition());
        print("BL Target: " + backLeft.getTargetPosition());
        print("BR Target: " + backRight.getTargetPosition());

        waitFor(100);

        telemetry.update();

        while(!opMode.isStopRequested() && frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){

            print("Before");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            print("");

            /*
            frontLeft.setPower(frontLeft.getPower() + power);
            frontRight.setPower(frontRight.getPower() + power);
            backLeft.setPower(backLeft.getPower() + power);
            backRight.setPower(backRight.getPower() + power);
            */

            frontLeft.setPower(power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(power);

            print("After");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            telemetry.update();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /*
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        */
    }

    public void driveBackward(double power, int distance){

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + distance);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - distance);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + distance);
        backRight.setTargetPosition(backRight.getCurrentPosition() - distance);

        print("FL Target: " + frontLeft.getTargetPosition());
        print("FR Target: " + frontRight.getTargetPosition());
        print("BL Target: " + backLeft.getTargetPosition());
        print("BR Target: " + backRight.getTargetPosition());

        waitFor(100);

        telemetry.update();

        while(!opMode.isStopRequested() && frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){

            print("Before");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            print("");

            /*
            frontLeft.setPower(frontLeft.getPower() + power);
            frontRight.setPower(frontRight.getPower() + power);
            backLeft.setPower(backLeft.getPower() + power);
            backRight.setPower(backRight.getPower() + power);
            */

            frontLeft.setPower(power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(power);

            print("After");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            telemetry.update();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /*
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        */
    }

    public void strafeLeft(double power, int distance){

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + distance);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + distance);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - distance);
        backRight.setTargetPosition(backRight.getCurrentPosition() - distance);

        print("FL Target: " + frontLeft.getTargetPosition());
        print("FR Target: " + frontRight.getTargetPosition());
        print("BL Target: " + backLeft.getTargetPosition());
        print("BR Target: " + backRight.getTargetPosition());

        waitFor(100);

        telemetry.update();

        while(!opMode.isStopRequested() && frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){

            print("Before");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            print("");

            /*
            frontLeft.setPower(frontLeft.getPower() + power);
            frontRight.setPower(frontRight.getPower() + power);
            backLeft.setPower(backLeft.getPower() + power);
            backRight.setPower(backRight.getPower() + power);
            */

            frontLeft.setPower(power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(power);

            print("After");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            telemetry.update();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /*
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        */
    }

    public void strafeRight(double power, int distance){

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - distance);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - distance);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + distance);
        backRight.setTargetPosition(backRight.getCurrentPosition() + distance);

        print("FL Target: " + frontLeft.getTargetPosition());
        print("FR Target: " + frontRight.getTargetPosition());
        print("BL Target: " + backLeft.getTargetPosition());
        print("BR Target: " + backRight.getTargetPosition());

        waitFor(100);

        telemetry.update();

        while(!opMode.isStopRequested() && frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){

            print("Before");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            print("");

            /*
            frontLeft.setPower(frontLeft.getPower() + power);
            frontRight.setPower(frontRight.getPower() + power);
            backLeft.setPower(backLeft.getPower() + power);
            backRight.setPower(backRight.getPower() + power);
            */

            frontLeft.setPower(power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(power);

            print("After");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            telemetry.update();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /*
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        */
    }

    public void rotateLeft(double power, int distance){

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + distance);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + distance);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + distance);
        backRight.setTargetPosition(backRight.getCurrentPosition() + distance);

        print("FL Target: " + frontLeft.getTargetPosition());
        print("FR Target: " + frontRight.getTargetPosition());
        print("BL Target: " + backLeft.getTargetPosition());
        print("BR Target: " + backRight.getTargetPosition());

        waitFor(100);

        telemetry.update();

        while(!opMode.isStopRequested() && frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){

            print("Before");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            print("");

            /*
            frontLeft.setPower(frontLeft.getPower() + power);
            frontRight.setPower(frontRight.getPower() + power);
            backLeft.setPower(backLeft.getPower() + power);
            backRight.setPower(backRight.getPower() + power);
            */

            frontLeft.setPower(power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(power);

            print("After");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            telemetry.update();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /*
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        */
    }

    public void rotateRight(double power, int distance){

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - distance);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - distance);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - distance);
        backRight.setTargetPosition(backRight.getCurrentPosition() - distance);

        print("FL Target: " + frontLeft.getTargetPosition());
        print("FR Target: " + frontRight.getTargetPosition());
        print("BL Target: " + backLeft.getTargetPosition());
        print("BR Target: " + backRight.getTargetPosition());

        waitFor(100);

        telemetry.update();

        while(!opMode.isStopRequested() && frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){

            print("Before");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            print("");

            /*
            frontLeft.setPower(frontLeft.getPower() + power);
            frontRight.setPower(frontRight.getPower() + power);
            backLeft.setPower(backLeft.getPower() + power);
            backRight.setPower(backRight.getPower() + power);
            */

            frontLeft.setPower(power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(power);

            print("After");
            print("FL Position: "+frontLeft.getCurrentPosition());
            print("FR Position: "+frontRight.getCurrentPosition());
            print("BL Position: "+backLeft.getCurrentPosition());
            print("BR Position: "+backRight.getCurrentPosition());
            print("");
            print("FL Power: "+frontLeft.getPower());
            print("FR Power: "+frontRight.getPower());
            print("BL Power: "+backLeft.getPower());
            print("BR Power: "+backRight.getPower());
            telemetry.update();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /*
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        */
    }

    //Encoder Driving Methods - End

    public void setJewelUp(){
        jewelArm.setPosition(Constant.jewelUpPos);
    }

    public void setJewelDown(){
        jewelArm.setPosition(Constant.jewelDownPos);
    }

    public void grabberA(){
        //Normal Grab
        try{
            leftClaw.setPosition(Constant.leftNormalGrab);
            rightClaw.setPosition(Constant.rightNormalGrab);
        }
        catch (Exception e){
            print("Error: Grabbing, Mode A");
        }
    }

    public void grabberB(){
        //Soft Release
        try{
            leftClaw.setPosition(Constant.leftSoftRelease);
            rightClaw.setPosition(Constant.rightSoftRelease);
        }
        catch (Exception e){
            print("Error: Grabbing, Mode B");
        }
    }

    public void grabberX(){
        //Big Release/Initial Position
        try{
            leftClaw.setPosition(Constant.leftInitialPosition);
            rightClaw.setPosition(Constant.rightInitialPosition);
        }
        catch (Exception e){
            print("Error: Grabbing, Mode X");
        }
    }

    public void grabberY(){
        //Hard Grab
        try{
            leftClaw.setPosition(Constant.leftHardGrab);
            rightClaw.setPosition(Constant.rightHardGrab);
        }
        catch (Exception e){
            print("Error: Grabbing, Mode Y");
        }
    }

    public void liftElevator(double power){
        elevator.setPower(power);
    }

    public void print(String words){
        telemetry.addLine(words);
    }

    public int inchesToEncoder(int inches){
        return (3500/36)*inches;
    }
}
