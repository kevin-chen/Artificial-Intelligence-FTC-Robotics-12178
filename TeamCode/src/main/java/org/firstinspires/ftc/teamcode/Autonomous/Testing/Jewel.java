package org.firstinspires.ftc.teamcode.Autonomous.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Grabber", group="Testing")
@Disabled

public class Jewel extends OpMode
{
    private Servo jewel;
    private double jewelFuture = 0;
    private double jewelPosition = 0.6;
    //private Servo jewel2;
    //private double jewelFuture2 = 0;
    //private double jewelPosition2 = 0.6;

    private ElapsedTime time = new ElapsedTime();

    public Jewel(){
    }

    @Override
    public void init() {
        print("Status: Initialized Begin");

        try {
            jewel = hardwareMap.servo.get("grabber");

        }
        catch (Exception e) {
            jewel=null;
        }
        /*
        try {
            jewel2 = hardwareMap.servo.get("jewel2");

        }
        catch (Exception e) {
            jewel2=null;
        }
        */

        print("Status: Initialized End");
    }

    @Override
    public void loop() {
        grabber();
    }

    public void grabber() {
        double now = time.milliseconds();
        if(jewel!=null) {
            if (gamepad1.a && now >= jewelFuture)
            {
                jewelFuture = now + 100;
                jewelPosition -= 0.05;
            }
            else if (gamepad1.b && now >= jewelFuture)
            {
                jewelFuture = now + 100;
                jewelPosition += 0.05;
            }
            jewelPosition = Range.clip(jewelPosition, 0, 1);
            jewel.setPosition(jewelPosition);

            print("Jewel: " + jewelPosition);
            print("Now: " + now);
            print("Future: " + jewelFuture);
        }
        /*
        if(jewel2!=null) {
            if (gamepad1.x && now >= jewelFuture2)
            {
                jewelFuture2 = now + 100;
                jewelPosition2 -= 0.05;
            }
            else if (gamepad1.y && now >= jewelFuture2)
            {
                jewelFuture2 = now + 100;
                jewelPosition2 += 0.05;
            }
            jewelPosition2 = Range.clip(jewelPosition2, 0, 1);
            jewel2.setPosition(jewelPosition2);

            print("Jewel2: " + jewelPosition2);
            print("Now: " + now);
            print("Future2: " + jewelFuture2);
        }
        */
    }

    public void print(String str){
        telemetry.addLine(str);
    }
}
