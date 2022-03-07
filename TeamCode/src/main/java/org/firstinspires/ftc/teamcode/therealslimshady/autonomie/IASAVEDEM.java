package org.firstinspires.ftc.teamcode.therealslimshady.autonomie;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.therealslimshady.SMiscariRoti;
import org.firstinspires.ftc.teamcode.therealslimshady.SVuforia;

public class IASAVEDEM {

    private static int FAZA = 0;
    private static ElapsedTime et;
    private static int poz = 0;

    public static void init(){

    }

    public static void loop(OpMode opMode) {
        opMode.telemetry.addData("FAZA", FAZA);

        if(FAZA==0)
        {
                if(et.seconds()<1.3){
                    SMiscariRoti.setVelXYR(0.5F, 0,0);
                    SMiscariRoti.indreaptSpre(0.2, 0, AngleUnit.DEGREES);
                }else{
                    SMiscariRoti.setVelXYR(0.2F, 0,0);
                }

            if(SVuforia.targetVisible) {
                SMiscariRoti.setVelXYR(0, 0, 0);
                FAZA = 1;
            }
            et.reset();
        }
        if(FAZA==1){

        }
    }

    public static void stop(){
        et = null;
        FAZA = 0;
        SMiscariRoti.setVelXYR(0,0,0);
    }

}
