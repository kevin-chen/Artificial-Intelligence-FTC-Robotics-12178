package org.firstinspires.ftc.teamcode.Helper;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

public class Pictograph {

    //private VuforiaLocalizer vuforia;
    private ClosableVuforiaLocalizer vuforia;
    private VuforiaTrackables relicTrackables;
    private VuforiaTrackable relicTemplate;

    private RelicRecoveryVuMark mark;

    private boolean isFound;

    public Pictograph(HardwareMap hardwareMap){
        int cameraId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId","id",hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(cameraId);

        params.vuforiaLicenseKey = Constant.key;
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        //this.vuforia = ClassFactory.createVuforiaLocalizer(params);
        this.vuforia = new ClosableVuforiaLocalizer(params);

        relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        isFound = false;

        mark = RelicRecoveryVuMark.UNKNOWN;
    }

    public void activate(){
        relicTrackables.activate();
    }

    public void deactivate(){
        relicTrackables.deactivate();
    }

    public boolean isFound(){
        return isFound;
    }

    public void scan(){
        mark = RelicRecoveryVuMark.from(relicTemplate);

        RelicRecoveryVuMark mark = RelicRecoveryVuMark.from(relicTemplate);
        if(mark != RelicRecoveryVuMark.UNKNOWN){
            this.mark = mark;
            isFound = true;
        }

    }


    public void detach(){
        vuforia.close();
    }


    public RelicRecoveryVuMark getMark(){
        return mark;
    }
}
