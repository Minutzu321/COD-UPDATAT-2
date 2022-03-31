package org.firstinspires.ftc.teamcode.therealslimshady.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.therealslimshady.SHardware;
import org.firstinspires.ftc.teamcode.therealslimshady.SMiscariRoti;
import org.firstinspires.ftc.teamcode.therealslimshady.autonomie.SCreier;

public class SGamepad {

    private static float PUTERE_ROTI = 1;

    private static float LIMITARE_SUS_LIFT = -8000;
    private static float LIMITARE_JOS_LIFT = 0;

    private static Gamepad.RumbleEffect ref = new Gamepad.RumbleEffect.Builder()
            .addStep(0.8, 0.8, 120)
            .addStep(0, 0, 50)
            .addStep(0.8, 0.8, 120)
            .build(),
                                        ref2 = new Gamepad.RumbleEffect.Builder()
            .addStep(1, 0.2, 450)
            .addStep(0, 0, 100)
            .addStep(0.2, 1, 450)
            .addStep(0,0,600)
            .build();



    private static float pozitie_team = 0, pozitie_cutie = 0;
    public static void init(){
        lift = null;
        SHardware.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private static DcMotor lift = null;

    private static double unghi = 0;
    private static boolean statusApasat = false, modAnyas = false;

    public static void loop(OpMode opMode) {
        if (!SHardware.initializat) return;
        if(lift == null) {
            lift = SHardware.lift;

            SHardware.par.setPosition(SHardware.parSus);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SHardware.perp.setPosition(SHardware.perpSus);
        }




        Gamepad gamepad1 = opMode.gamepad1;
        //codul pentru miscare
        if(!modAnyas) {
            SMiscariRoti.setVelXYR(-gamepad1.left_stick_x * PUTERE_ROTI, gamepad1.left_stick_y * PUTERE_ROTI, -gamepad1.right_stick_x * PUTERE_ROTI);
        }else{
            if(gamepad1.right_stick_x > 0.5){
                unghi = 0;
            }else if(gamepad1.right_stick_x < -0.5){
                unghi = 180;
            }else if(-gamepad1.right_stick_y < -0.5){
                unghi = 90;
            }else if(-gamepad1.right_stick_y > 0.5){
                unghi = 270;
            }
            SMiscariRoti.setVelXY(-gamepad1.left_stick_x * PUTERE_ROTI, gamepad1.left_stick_y * PUTERE_ROTI);
            SMiscariRoti.indreaptaSpre(0.6,unghi, AngleUnit.DEGREES);
        }

        //codul pentru maturici
        if (gamepad1.right_trigger > 0.5) {
            SHardware.matura_interior.setPower(1);
            SHardware.matura_exterior.setPower(1);
        } else if (gamepad1.left_trigger > 0.5) {
            SHardware.matura_interior.setPower(-1);
            SHardware.matura_exterior.setPower(-1);
        } else {
            SHardware.matura_interior.setPower(0);
            SHardware.matura_exterior.setPower(0);
        }



        //modu anyas
        if(gamepad1.x != statusApasat){
            if(gamepad1.x){
                gamepad1.runRumbleEffect(ref);
                modAnyas = !modAnyas;
            }
            statusApasat = gamepad1.x;
        }



        Gamepad gamepad2 = opMode.gamepad2;
        //codul pentru lift

        int pozitie_lift = lift.getCurrentPosition();
        float left_stick_y = -gamepad2.left_stick_y;
//        lift.setPower(left_stick_y*0.1);
        double putere = 1;
//        if(pozitie_lift > LIMITARE_JOS_LIFT-1000 || pozitie_lift < LIMITARE_SUS_LIFT+5000)
//            putere = 0.1;

        if(!gamepad2.isRumbling()){
            if (left_stick_y > 0.5f && pozitie_lift <= LIMITARE_SUS_LIFT+500){
                gamepad2.runRumbleEffect(ref2);
            }
            if (left_stick_y < -0.5f && pozitie_lift >= LIMITARE_JOS_LIFT-500){
                gamepad2.runRumbleEffect(ref2);
            }
        }
        if (left_stick_y > 0.5f && pozitie_lift > LIMITARE_SUS_LIFT){
            lift.setPower(putere);
        }else if(left_stick_y < -0.5f && pozitie_lift < LIMITARE_JOS_LIFT){
            lift.setPower(-putere);
        }else {
            lift.setPower(0);
        }


        //codul pentru cutie
        Servo cutie = SHardware.cutie;

            pozitie_cutie -= gamepad2.right_stick_y * 0.005;

        if(pozitie_cutie > 1)
            pozitie_cutie = 1f;
        if(pozitie_cutie < 0.23)
            pozitie_cutie = 0.23f;
        opMode.telemetry.addData("cutie",pozitie_cutie);
        cutie.setPosition(pozitie_cutie);

        Servo team = SHardware.team;
        if(gamepad2.right_bumper) {
            pozitie_team += 0.005;
        }else if(gamepad2.left_bumper){
            pozitie_team -= 0.005;
        }

        if(pozitie_team > 1)
            pozitie_team = 1f;
        if(pozitie_team < 0.1)
            pozitie_team = 0.1f;
        opMode.telemetry.addData("team",pozitie_team);


        team.setPosition(pozitie_team);

        //codul pentru carusel
        if(gamepad2.right_trigger > 0.5){
            SHardware.carusel.setPower(-0.6);
        }else if(gamepad2.left_trigger > 0.5){
            SHardware.carusel.setPower(0.6);
        }else{
            SHardware.carusel.setPower(0);
        }



    }

}
