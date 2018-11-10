package org.firstinspires.ftc.teamcode.Helper;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.JewelDetector;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Jewel {

    private final HardwareMap hardwareMap;
    private JewelDetector jewelDetector;

    public Jewel(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
    }

    public void init(){
        jewelDetector = new JewelDetector();
        jewelDetector.init(hardwareMap.appContext, CameraViewDisplay.getInstance(), 0);
        jewelDetector.areaWeight = 0.02;
        jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.MAX_AREA; // PERFECT_AREA
        jewelDetector.debugContours = true;
        jewelDetector.maxDiffrence = 15;
        jewelDetector.ratioWeight = 15;
        jewelDetector.minArea = 700;

    }

    public void enable(){
        this.jewelDetector.enable();
    }

    public void disable(){
        this.jewelDetector.disable();
    }

    public JewelDetector.JewelOrder getJewel(){
        return jewelDetector.getCurrentOrder();
    }


}
