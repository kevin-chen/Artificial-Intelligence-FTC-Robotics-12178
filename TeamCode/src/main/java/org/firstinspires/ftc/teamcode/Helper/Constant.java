package org.firstinspires.ftc.teamcode.Helper;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Artificial Intelligence 12178 on 1/16/2018.
 */

public class Constant {

    public static final String frontLeft = "frontLeft";
    public static final String frontRight = "frontRight";
    public static final String backLeft = "backLeft";
    public static final String backRight = "backRight";

    public static final String elevator = "elevator";

    public static final String leftClaw = "leftClaw";
    public static final String rightClaw = "rightClaw";
    public static double grabberFuture = 0;

    //Left: Add to Grab More, Subtract to Grab Less
    //Right: Subtract to Grab More, Add to Grab Less
    public static final double leftNormalGrab = 0.52;
    public static final double rightNormalGrab = 0.4;
    public static final double leftHardGrab = 0.7;
    public static final double rightHardGrab = 0.3;
    public static final double leftSoftRelease = 0.3;
    public static final double rightSoftRelease = 0.6;
    public static final double leftInitialPosition = 0;
    public static final double rightInitialPosition = 1;

    public static final String extender = "extender";

    public static final double extendPower = -1;
    public static final double retractPower = 0.3;

    public static final String relicWrist = "relicWrist";
    public static final String relicClaw = "relicClaw";
    public static double relicWristFuture = 0;
    public static double relicClawFuture = 0;

    public static final double relicClawOpen = 1;
    public static final double relicClawClose = 0.3;
    public static final double relicWristUp = 1;
    public static final double relicWristDown = 0.1;
    public static final double relicWristStill = 0.45;

    public static final String key = "AWEwafL/////AAAAGQpM0TNc/0Z7vo0sYXZSAmdcz/6n1n4WGTL7qiDLeGs1qM2S2H2g44i1SUhnuqvE/1Srs1KcC4eUmaW/9giP4NT/C0Pw7stbcNh4n23bOq7HmSasm61MXyuGg0U5yNHAbaRoLlcz3R5bGIFAg/tgfj3KmBdPNvzrv4NdEvngjQdkl1w9/io2iGzXQz4SzFIoVQDtem8fO2kaOOx1+yzptv/FzHR89DeNelDSZRMOcVXtUctzIepf30puP+FAgxIURJmeYtL5R+UcPKYGmecg5nba63o/HGBoWY6zCwS3LRuXxLDBiHqtb9CPopX/OOlpkgQtjcwjqUrtwXEqMr6Ay713Dlc5lS/sNb7hXfgySO/v";

    public static final double COUNTS_PER_MOTOR_REV = 1680; //Andymark Neverest60 = 1680 // eg: TETRIX Motor Encoder = 1220
    public static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    public static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415); //The value is: 133.694

    public static final String jewel = "jewel";
    public static final double jewelUpPos = 0.25; //Add to get more down //Subtract to get more up
    public static final double jewelDownPos = 0.95;

    public static final int encoder90 = 2100; //2200
    public static final int encoder180 = 4200; //4400
    //36 Inches with encoder count of 3500
    public static final int encoderCountsPerInch = 105; //Use ruler to measure how much we are missing
    //Each square tile is 24 inches by 24 inches
    public static final double driveSpeed = 0.7;
    public static final double fullSpeed = 1;
    public static final double knockSpeed = 1;
}
