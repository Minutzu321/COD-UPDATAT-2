package org.firstinspires.ftc.teamcode.therealslimshady.autonomie;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.therealslimshady.SHardware;
import org.firstinspires.ftc.teamcode.therealslimshady.SMiscariRoti;
import org.firstinspires.ftc.teamcode.therealslimshady.SVuforia;
import org.firstinspires.ftc.teamcode.therealslimshady.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.therealslimshady.roadrunner.trajectorysequence.TrajectorySequence;

import java.util.List;

public class SC2 {

    private static int FAZA = 0;
    private static ElapsedTime et;
    private static int poz = 0;
    private static SampleMecanumDrive drive;
    private static double unghi = 0;

    public static void init(OpMode opMode){
        SMiscariRoti.setVelXYR(0,0,0);
        drive = new SampleMecanumDrive(opMode.hardwareMap);
        SHardware.cutie.setPosition(0);
    }

    public static void loop(OpMode opMode){
        opMode.telemetry.addData("POZITIE",poz);
        opMode.telemetry.addData("FAZA",FAZA);
        opMode.telemetry.addData("lift",SHardware.lift.getCurrentPosition());
        if(drive != null){
            opMode.telemetry.addData("XU",drive.getPoseEstimate().getX());
            opMode.telemetry.addData("YU",drive.getPoseEstimate().getY());
        }
        if(et == null) {
            et = new ElapsedTime();
            et.reset();
        }
        SMiscariRoti.indreaptaSpre(0.3,unghi,AngleUnit.DEGREES);

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
                    if (recognition.getLabel().equals(SVuforia.LABELS[0])) {
                        if(getMijloc(recognition)<(recognition.getImageWidth()/2f+50)){
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
            if(et.seconds() > 0.1) {
                unghi = 63;
                if (SMiscariRoti.eSpre(unghi) || et.seconds() > 2) {
                    SMiscariRoti.setVelXYR(0,0,0);
                    et.reset();
                    FAZA = 2;
                }
            }else{
                SMiscariRoti.setVelXYR(0,0.1f,0);
            }
        }

        if(FAZA==2){
            SMiscariRoti.setVelXYR(0,0,0);
            SHardware.carusel.setPower(-0.5);

            if(et.seconds() > 3){
                unghi = 0;
                if(SMiscariRoti.eSpre(unghi) || et.seconds() > 5) {
                    FAZA = 3;
                    et.reset();
                    SHardware.carusel.setPower(0);
                }
            }
        }

        if(FAZA==3){
            SMiscariRoti.setVelXYR(0,0,0);
            et.reset();
            FAZA = 4;
        }
        if(FAZA==4) {
            double x = -drive.getPoseEstimate().getX();
            double y = -drive.getPoseEstimate().getY();
            SMiscariRoti.mergiLa(0,0,x,y,52,30);
            //TODO AICI M-AM OPRIT
//            if (x < 25) {
//                SMiscariRoti.setVelY(-0.6f);
//            } else {
//                SMiscariRoti.setVelY(-0.2f);
//            }
//            if (x > 52) {
//                SMiscariRoti.setVelXYR(0, 0, 0);
//                FAZA = 5;
//            }
        }
        if(FAZA==5) {
            double y = -drive.getPoseEstimate().getY();
            double target = 29;
            if(poz == 1){
                target = 24;
            }
            if(poz == 3){
                target = 30;
            }
            if(y<target) {
                SMiscariRoti.setVelXYR(0.2f, 0, 0);
            }else{
                unghi=10;
                if(SMiscariRoti.eSpre(unghi) || et.seconds() > 2) {
                    SMiscariRoti.setVelXYR(0, 0, 0);
                    SHardware.cutie.setPosition(0.9);
                    et.reset();
                    FAZA = 6;
                }
            }

        }

        if(FAZA==6) {
            if(et.seconds() > 2) {
                et.reset();
                SHardware.cutie.setPosition(0);
                FAZA = 7;
            }
        }
        if(FAZA==7) {
            poz = 0;
            double y = -drive.getPoseEstimate().getY();
            unghi = 0;
            if(y > 0){
                if (SMiscariRoti.eSpre(unghi) || et.seconds() > 2) {
                    SMiscariRoti.setVelXYR(-0.4f, 0, 0);
                }
            }else {
                SMiscariRoti.setVelXYR(0, 0, 0);
                et.reset();
                FAZA = 8;
            }
        }
        if(FAZA==8) {
            poz = 0;
            double x = -drive.getPoseEstimate().getX();
            if(x > 75){
                FAZA = 9;
                et.reset();
                SMiscariRoti.setVelXYR(0, 0, 0);
            }else{
                SMiscariRoti.setVelXYR(0, -0.4f, 0);
            }
        }

        if(FAZA==9){
            double x = -drive.getPoseEstimate().getX();
            if(x < 130){
                SMiscariRoti.setVelXYR(0, -0.4f, 0);
            }else{
                SMiscariRoti.setVelXYR(0, 0, 0);
            }
        }
//        if(FAZA == 14){
//            if(et.seconds() < 2)
//                SMiscariRoti.setVelXYR(0.2f, 0, 0);
//            else
//                SMiscariRoti.setVelXY(0,0);
//        }


        if(drive != null) {
            drive.update();
        }
    }

    private static float getMijloc(Recognition recognition){
        return recognition.getLeft()+(recognition.getWidth()/2);
    }

    public static int mergi(int pozitie){
        DcMotor motor = SHardware.lift;
        int pozitie_motor = -motor.getCurrentPosition();

        int eroare = pozitie - pozitie_motor;
        double putere = 0.5;
        if(Math.abs(eroare) < 1000)
            putere = 0.2;
        if(pozitie_motor < pozitie-40)
            motor.setPower(putere);
        else if(pozitie_motor > pozitie+40)
            motor.setPower(-putere);
        else
            motor.setPower(0);

        return 0;
    }

    public static void stop(){
        et = null;
        FAZA = 0;
        poz = 0;
        SMiscariRoti.setVelXYR(0,0,0);
    }

}
