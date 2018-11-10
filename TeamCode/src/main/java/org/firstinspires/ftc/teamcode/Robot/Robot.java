package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.Constant;

/**
 * Created by Robotics on 11/12/2017.
 */

public class Robot {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    public ElapsedTime time = new ElapsedTime();
    public double now = time.milliseconds();

    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    private DcMotor elevator = null;

    private Servo leftClaw = null;
    private Servo rightClaw = null;

    private Servo relicClaw = null;
    private Servo relicWrist = null;
    private DcMotor extender = null;

    private Servo jewel = null;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    public void initWheels() {

        print("> Begin Initializing Wheels");
        try {
            frontLeft = hardwareMap.dcMotor.get("frontLeft");
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } catch (Exception e) {
            print("Error: frontLeft");
        }
        try {
            frontRight = hardwareMap.dcMotor.get("frontRight");
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } catch (Exception e) {
            print("Error: frontRight");
        }
        try {
            backLeft = hardwareMap.dcMotor.get("backLeft");
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } catch (Exception e) {
            print("Error: backLeft");
        }
        try {
            backRight = hardwareMap.dcMotor.get("backRight");
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } catch (Exception e) {
            print("Error: backRight");
        }
        print("> End Initializing Wheels");

    }

    public void initVertical() {

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
            leftClaw.setPosition(Constant.leftInitialPosition);
        } catch (Exception e) {
            print("Error: leftClaw");
        }
        try {
            rightClaw = hardwareMap.servo.get("rightClaw");
            rightClaw.setPosition(Constant.rightInitialPosition);
        } catch (Exception e) {
            print("Error: rightClaw");
        }
        print("> End Initializing Vertical Components");
    }

    public void initHorizontal() {

        Constant.relicWristFuture = 0;
        Constant.relicClawFuture = 0;

        print("> Begin Initializing Horizontal Components");
        try {
            relicWrist = hardwareMap.servo.get("relicWrist");
            //relicWrist.setPosition(Constant.relicWristDown);
        } catch (Exception e) {
            print("Error: relicWrist");
        }
        try {
            relicClaw = hardwareMap.servo.get("relicClaw");
            relicClaw.setPosition(Constant.relicClawClose);
        } catch (Exception e) {
            print("Error: relicClaw");
        }
        try {
            extender = hardwareMap.dcMotor.get("extender");
            extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } catch (Exception e) {
            print("Error: extender");
        }
        print("> End Initializing Horizontal Components");

        try{
            jewel = hardwareMap.servo.get("jewel");
            jewel.setPosition(Constant.jewelUpPos);
        }
        catch (Exception e){
            print("Error: jewel");
        }

    }

    public void allMovement(Gamepad gamepad1){

        now = time.milliseconds();

        float gamepad1LeftY = -gamepad1.left_stick_y;
        float gamepad1LeftX = gamepad1.left_stick_x;
        float gamepad1RightX = gamepad1.right_stick_x;

        // holonomic formulas

        float FrontLeft = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float FrontRight = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float BackRight = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
        float BackLeft = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = (float)scaleInput(FrontRight);
        FrontLeft = (float)scaleInput(FrontLeft);
        BackLeft = (float)scaleInput(BackLeft);
        BackRight = (float)scaleInput(BackRight);

        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);

        // write the values to the motors
        frontRight.setPower(FrontRight);
        frontLeft.setPower(FrontLeft);
        backLeft.setPower(BackLeft);
        backRight.setPower(BackRight);
    }

    public void elevator(Gamepad gamepad1){

        now = time.milliseconds();

        float verticalUp = -gamepad1.left_trigger;
        float verticalDown = gamepad1.right_trigger;
        float elevatorPower = 0;
        if (gamepad1.left_trigger > 0) {
            print("Elevator Down");
            elevatorPower = verticalUp;
        }
        else if (gamepad1.right_trigger > 0) {
            print("Elevator Up");
            elevatorPower = verticalDown;
        }
        else{
            elevatorPower = 0;
        }
        elevatorPower = Range.clip(elevatorPower, -1, 1);
        elevator.setPower(elevatorPower);
    }

    public void grabber(Gamepad gamepad1){

        now = time.milliseconds();

        // && now >= Constant.grabberFuture

        if(gamepad1.a){
            grabberA();
        }
        else if(gamepad1.b){
            grabberB();
        }
        else if(gamepad1.x){
            grabberX();
        }
        else if(gamepad1.y){
            grabberY();
        }
        else {
            //leftClaw.setPosition(leftClaw.getPosition());
            //rightClaw.setPosition(rightClaw.getPosition());
        }

    }

    public void grabberA(){
        //Normal Grab
        try{
            Constant.grabberFuture = now + 50;
            print("Normal Grab");
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
            Constant.grabberFuture = now + 50;
            print("Soft Release");
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
            Constant.grabberFuture = now + 50;
            print("Big/Initial Position");
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
            Constant.grabberFuture = now + 50;
            print("Hard Grab/To Push Glyph In");
            leftClaw.setPosition(Constant.leftHardGrab);
            rightClaw.setPosition(Constant.rightHardGrab);
        }
        catch (Exception e){
            print("Error: Grabbing, Mode Y");
        }
    }

    public void horizontal(Gamepad gamepad2){

        now = time.milliseconds();

        if (gamepad2.x && now >= Constant.relicClawFuture) {
            print("Close Relic Claw");
            Constant.relicClawFuture = now + 100;
            relicClaw.setPosition(Constant.relicClawClose);
        }
        else if (gamepad2.y && now >= Constant.relicClawFuture) {
            print("Open Relic Claw");
            Constant.relicClawFuture = now + 100;
            relicClaw.setPosition(Constant.relicClawOpen);
        }
        else{
            //relicClaw.setPosition(relicClaw.getPosition());
        }

        if (gamepad2.dpad_up) {
            print("Relic Wrist Up");
            //Constant.relicWristFuture = now + 100;
            relicWrist.setPosition(Constant.relicWristUp);
        }
        else if (gamepad2.dpad_down) {
            print("Relic Wrist Down");
            //Constant.relicWristFuture = now + 100;
            relicWrist.setPosition(Constant.relicWristDown);
        }
        else {
            //relicWrist.setPosition(relicWrist.getPosition());
        }

        if (gamepad2.dpad_right) { //Extend
            print("Extending Horizontal");
            extender.setPower(Constant.extendPower);
        }
        else if (gamepad2.dpad_left) { //Retract
            print("Retracting Horizontal");
            extender.setPower(Constant.retractPower);
        }
        else{
            extender.setPower(0);
        }
    }

    public void print(String words){
        telemetry.addLine(words);
    }

    public void printController(Gamepad gamepad1, Gamepad gamepad2){

        print("***Controller 1 Info***");
        print("Up: " + gamepad1.dpad_up);
        print("Down: " + gamepad1.dpad_down);
        print("Left: " + gamepad1.dpad_left);
        print("Right: " + gamepad1.dpad_right);
        print("X: "+ gamepad1.x);
        print("Y:"+ gamepad1.y);
        print("B: "+ gamepad1.b);
        print("A: "+ gamepad1.a);
        print("Start: "+ gamepad1.start);
        print("Back: "+ gamepad1.back);
        print("LBumper: "+ gamepad1.left_bumper);
        print("RBumper: "+ gamepad1.right_bumper);
        print("LJoyStickButton: "+ gamepad1.left_stick_button);
        print("RJoyStickButton: "+ gamepad1.right_stick_button);
        print("RightTrigger: "+ String.format("%.2f", gamepad1.right_trigger));
        print("LeftTrigger: "+ String.format("%.2f", gamepad1.left_trigger));
        print("LeftJoyStickX"+ String.format("%.2f", gamepad1.left_stick_x));
        print("LeftJoyStickY"+ String.format("%.2f", -gamepad1.left_stick_y));
        print("RightJoyStickX"+ String.format("%.2f", gamepad1.right_stick_x));
        print("RightJoyStickY"+ String.format("%.2f", -gamepad1.right_stick_y));

        print("");

        print("***Controller 2 Info***");
        print("Up: "+ gamepad2.dpad_up);
        print("Down: "+ gamepad2.dpad_down);
        print("Left: "+ gamepad2.dpad_left);
        print("Right: "+ gamepad2.dpad_right);
        print("X: "+ gamepad2.x);
        print("Y:"+ gamepad2.y);
        print("B: "+ gamepad2.b);
        print("A: "+ gamepad2.a);
        print("Start: "+ gamepad2.start);
        print("Back: "+ gamepad2.back);
        print("LBumper: "+ gamepad2.left_bumper);
        print("RBumper: "+ gamepad2.right_bumper);
        print("LJoyStickButton: "+ gamepad2.left_stick_button);
        print("RJoyStickButton: "+ gamepad2.right_stick_button);
        print("RightTrigger: "+ String.format("%.2f", gamepad2.right_trigger));
        print("LeftTrigger: "+ String.format("%.2f", gamepad2.left_trigger));
        print("LeftJoyStickX"+ String.format("%.2f", gamepad2.left_stick_x));
        print("LeftJoyStickY"+ String.format("%.2f", -gamepad2.left_stick_y));
        print("RightJoyStickX"+ String.format("%.2f", gamepad2.right_stick_x));
        print("RightJoyStickY"+ String.format("%.2f", -gamepad2.right_stick_y));

    }

    public void printRobot(){

        now = time.milliseconds();
        print("Time: "+now);
        print("***Robot Data***");
        print("Front Left Wheel Power: " + frontLeft.getPower());
        print("Front Right Wheel Power: " + frontRight.getPower());
        print("Back Left Wheel Power: " + backLeft.getPower());
        print("Back Right Wheel Power: " + backRight.getPower());
        print("");
        print("Elevator Power: " + elevator.getPower());
        print("");
        print("Grabber");
        print("Grabber Future: " + Constant.grabberFuture);
        print("Left Claw Position: " + leftClaw.getPosition());
        print("Right Claw Position: " + rightClaw.getPosition());
        print("");
        print("Relic");
        print("Claw Future: " + Constant.relicClawFuture);
        print("Relic Wrist Position: " + relicWrist.getPosition());
        print("Relic Claw Position: " + relicClaw.getPosition());
        print("Extender Power: "+extender.getPower());
        print("");
    }

    public double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

    public double scaleInputSmaller(double dVal)  {
        double[] scaleArray = { 0.0, 0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.07,
                0.08, 0.09, 0.1, 0.11, 0.12, 0.13, 0.14, 0.15, 0.2 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

    public double scaleInputBigger(double dVal)  {
        double[] scaleArray = { 0, 0.04, 0.08, 0.12, 0.16, 0.2, 0.24, 0.28,
                0.32, 0.36, 0.4, 0.44, 0.48, 0.52, 0.56, 0.60, 1}; //17 Indexes

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}