package org.firstinspires.ftc.teamcode.therealslimshady.autonomie;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.therealslimshady.SHardware;
import org.firstinspires.ftc.teamcode.therealslimshady.SMiscariRoti;
import org.firstinspires.ftc.teamcode.therealslimshady.SVuforia;

import java.util.List;

public class SCreier {

    private static int FAZA = 0;
    private static ElapsedTime et;
    private static int poz = 0;

    public static void init(){
        SMiscariRoti.setVelXYR(0,0,0);
    }

    public static void loop(OpMode opMode){
        opMode.telemetry.addData("POZITIE",poz);
        opMode.telemetry.addData("FAZA",FAZA);
        opMode.telemetry.addData("lift",SHardware.lift.getCurrentPosition());
        if(et == null) {
            et = new ElapsedTime();
            et.reset();
        }
        if(poz != 0){
            int target = 480;
            if(poz == 2)
                target = 2500;
            if(poz == 3)
                target = 7650;

            mergi(target);
        }else{
            mergi(poz);
        }

        if(FAZA == 0){
            if(et.seconds() < 5) {
                List<Recognition> recognitions = SVuforia.tfod.getRecognitions();
                for (Recognition recognition : recognitions) {
                    if (recognition.getLabel().equals(SVuforia.LABELS[2])) {
                        if(getMijloc(recognition)<recognition.getImageWidth()/2f){
                            poz = 1;
                        }else{
                            poz = 2;
                        }
                        et.reset();
                        FAZA = 1;
                    }
                }
            }else{
                poz = 3;
                et.reset();
                FAZA = 1;
            }
        }
        if(FAZA==1){
//            SVuforia.switchableCamera.setActiveCamera(SVuforia.webcam2);
            SVuforia.tfod.deactivate();
            SVuforia.tfod.shutdown();

            et.reset();
            FAZA=2;
        }
        if(FAZA==2){
//            SMiscariRoti.indreaptSpre(0.5, 0, AngleUnit.DEGREES);
            if(et.seconds() < 1.3) {
                SMiscariRoti.setVelPolar(0.5f, 0, AngleUnit.DEGREES);
            }else {
                SMiscariRoti.setVelPolar(0.2f, 0, AngleUnit.DEGREES);
            }

//            if(SVuforia.targetVisible) {
//                SMiscariRoti.setVelXYR(0, 0, 0);
//                FAZA = 3;
//            }
        }
        if(FAZA==3){
            float dist = 0;
            opMode.telemetry.addData("distanta",dist);
            if(dist>-5) {
                SMiscariRoti.setVelXYR(0, 0, 0);
                FAZA = 4;
            }
        }
        if(FAZA==4){
            if(poz==3){
//                if (SMiscariRoti.indreaptSpre(0.5, 42, AngleUnit.DEGREES)) {
//                    FAZA = 5;
//                    et.reset();
//                }
            }else if(poz==2){
//                if (SMiscariRoti.indreaptSpre(0.5, 40, AngleUnit.DEGREES)) {
//                    FAZA = 5;
//                    et.reset();
//                }
            }else {
//                if (SMiscariRoti.indreaptSpre(0.5, 40, AngleUnit.DEGREES)) {
//                    FAZA = 5;
//                    et.reset();
//                }
            }
        }
        if(FAZA==5) {
            if(poz == 3){
//                if (SHardware.distanta.getDistance(DistanceUnit.MM) > 99) {
//                    SMiscariRoti.setVelY(-0.12f);
//                } else if (SHardware.distanta.getDistance(DistanceUnit.MM) < 85) {
//                    SMiscariRoti.setVelY(0.1f);
//                } else {
//                    if (et.seconds() > 5) {
//                        SMiscariRoti.setVelY(0);
//                        et.reset();
//                        FAZA = 6;
//                    }
//                }
            }else if(poz == 2){
//                if (SHardware.distanta.getDistance(DistanceUnit.MM) > 100) {
//                    SMiscariRoti.setVelY(-0.12f);
//                } else if (SHardware.distanta.getDistance(DistanceUnit.MM) < 90) {
//                    SMiscariRoti.setVelY(0.1f);
//                } else {
//                    if (et.seconds() > 3) {
//                        SMiscariRoti.setVelY(0);
//                        et.reset();
//                        FAZA = 6;
//                    }
                }
            }else{
//                if (SHardware.distanta.getDistance(DistanceUnit.MM) > 100) {
//                    SMiscariRoti.setVelY(-0.1f);
//                } else if (SHardware.distanta.getDistance(DistanceUnit.MM) < 98) {
//                    SMiscariRoti.setVelY(0.1f);
//                } else {
//                    if (et.seconds() > 5) {
//                        SMiscariRoti.setVelY(0);
//                        et.reset();
//                        FAZA = 6;
//                    }
//                }
//            }
        }
        if(FAZA==6){
//            SHardware.cutie.setPosition(0.27);
            if(et.seconds()>1) {
//                SHardware.cutie.setPosition(0.45);
                poz=0;
                FAZA = 7;
                et.reset();
            }
        }

        if(FAZA==7){
//            if(SHardware.distanta.getDistance(DistanceUnit.MM) <= 100){
//                SMiscariRoti.setVelY(0.4f);
//            }else{
//                SMiscariRoti.setVelXYR(0,0,0);
//                FAZA=8;
//            }

        }

        if(FAZA==8){
//            if(SMiscariRoti.indreaptSpre(0.4, 0, AngleUnit.DEGREES)){
//                SMiscariRoti.setVelXYR(0,0,0);
////                SHardware.cutie.setPosition(1);
//                FAZA=9;
//                et.reset();
//            }

        }

        if(FAZA==9){
            if(et.seconds()<1) {
            }else if(et.seconds()<3) {
//                SMiscariRoti.mergiSpreTarget(6.5f, 0);
            }else{
//                SMiscariRoti.indreaptSpre(0.5, 0, AngleUnit.DEGREES);
//                SMiscariRoti.mergiSpreTarget(6.5f, -2);
            }
        }


    }

    private static float getMijloc(Recognition recognition){
        return recognition.getLeft()+(recognition.getWidth()/2);
    }

    public static int mergi(int pozitie){
        DcMotor motor = SHardware.lift;

        int eroare = pozitie - motor.getCurrentPosition();
        double putere = 0.5;
        if(Math.abs(eroare) < 1000)
            putere = 0.2;
        if(motor.getCurrentPosition() < pozitie-20)
            motor.setPower(-putere);
        else if(motor.getCurrentPosition() > pozitie+20)
            motor.setPower(putere);
        else
            motor.setPower(0);

        return eroare;
    }

    public static void stop(){
        et = null;
        FAZA = 0;
        poz = 0;
        SMiscariRoti.setVelXYR(0,0,0);
    }

}
