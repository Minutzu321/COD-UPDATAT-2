package org.firstinspires.ftc.teamcode.therealslimshady.autonomie;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.therealslimshady.SHardware;
import org.firstinspires.ftc.teamcode.therealslimshady.SMiscariRoti;
import org.firstinspires.ftc.teamcode.therealslimshady.SVuforia;
import org.firstinspires.ftc.teamcode.therealslimshady.roadrunner.drive.SampleMecanumDrive;

import java.util.List;

public class SCUrgenta {

    private static int FAZA = 0;
    private static ElapsedTime et, ett;
    private static int poz = 0;
    private static SampleMecanumDrive drive;
    private static double unghi = 0;

    private static double MERGI, MERGI_INAINTE;

    private static Telemetry telemetry;

    public static void init(OpMode opMode){
        MERGI_INAINTE = Configuratie.MERGI_INAINTE;
        MERGI = Configuratie.MERGI;
        unghi=0;
        FAZA=0;
        poz = 0;
        et = null;
        ett = null;
        SMiscariRoti.setVelXYR(0,0,0);
        drive = null;
        SHardware.cutie.setPosition(0);

        telemetry = new MultipleTelemetry(opMode.telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    public static void loop(OpMode opMode){
        telemetry.addData("POZITIE",poz);
        telemetry.addData("FAZA",FAZA);
        telemetry.addData("lift",SHardware.lift.getCurrentPosition());

        if(drive != null){
            drive.update();
            telemetry.addData("XU",drive.getPoseEstimate().getX());
            telemetry.addData("YU",drive.getPoseEstimate().getY());
//            ((DcMotorEx)SHardware.matura_exterior).getVelocity()

        }else{
            drive = new SampleMecanumDrive(opMode.hardwareMap);
        }
        if(et == null) {
            et = new ElapsedTime();
            ett = new ElapsedTime();
            et.reset();
        }
        SMiscariRoti.indreaptaSpre(Configuratie.PUTERE_REDRESARE,unghi,AngleUnit.DEGREES);

        if(FAZA==0) {
            double x = -drive.getPoseEstimate().getX();

            if (x < 20) {
                if(x<10)
                    SMiscariRoti.setVelXY(0, -0.4);
                else
                    SMiscariRoti.setVelXY(0,-0.2);
            }
            if (x >= 20) {
                SMiscariRoti.setVelXY(0, 0);
                FAZA = 1;
            }
        }

        if(FAZA==1){

        }
    }


    public static void stop(){
        et = null;
        FAZA = 0;
        poz = 0;
        SMiscariRoti.setVelXYR(0,0,0);
    }

}
