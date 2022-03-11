package org.firstinspires.ftc.teamcode.therealslimshady.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.therealslimshady.SHardware;
import org.firstinspires.ftc.teamcode.therealslimshady.SMiscariRoti;
import org.firstinspires.ftc.teamcode.therealslimshady.autonomie.SCreier;

public class SGamepad {

    private static float PUTERE_ROTI = 0.8f;

    private static float LIMITARE_SUS_LIFT = -8000;
    private static float LIMITARE_JOS_LIFT = 0;

    private static float pozitie_team = 0;
    public static void init(){
        lift = null;
        SHardware.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private static double putereAnterioara = 0;

    private static DcMotor lift = null;

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
        SMiscariRoti.setVelXYR(gamepad1.left_stick_x * PUTERE_ROTI, gamepad1.left_stick_y * PUTERE_ROTI, gamepad1.right_stick_x * PUTERE_ROTI);

        //codul pentru maturici
        if (gamepad1.right_trigger > 0.5) {
            SHardware.matura_interior.setPower(1);
            SHardware.matura_exterior.setPower(-0.4);
        } else if (gamepad1.left_trigger > 0.5) {
            SHardware.matura_interior.setPower(-1);
            SHardware.matura_exterior.setPower(0.4);
        } else {
            SHardware.matura_interior.setPower(0);
            SHardware.matura_exterior.setPower(0);
        }


        Gamepad gamepad2 = opMode.gamepad2;
        //codul pentru lift

        int pozitie_lift = lift.getCurrentPosition();
        float left_stick_y = -gamepad2.left_stick_y;
//        lift.setPower(left_stick_y*0.1);
        double putere = 1;
//        if(pozitie_lift > LIMITARE_JOS_LIFT-1000 || pozitie_lift < LIMITARE_SUS_LIFT+5000)
//            putere = 0.1;

        if (left_stick_y > 0.5f && pozitie_lift > LIMITARE_SUS_LIFT){
            lift.setPower(putere);
        }else if(left_stick_y < -0.5f && pozitie_lift < LIMITARE_JOS_LIFT){
            lift.setPower(-putere);
        }else {
            lift.setPower(0);
        }

        if(putereAnterioara != putere) {


            putereAnterioara = putere;
        }

        opMode.telemetry.addData("putere brat",putere);
        opMode.telemetry.addData("lift",lift.getCurrentPosition());
        opMode.telemetry.addData("y",left_stick_y);

        //codul pentru cutie
        CRServo cutie = SHardware.cutie;
        cutie.setPower(gamepad2.right_stick_x);

        Servo team = SHardware.team;
        if(gamepad2.dpad_up) {
            pozitie_team += 0.005;
        }else if(gamepad2.dpad_down){
            pozitie_team -= 0.005;
        }

        if(pozitie_team > 1)
            pozitie_team = 1;
        if(pozitie_team < 0)
            pozitie_team = 0f;

        team.setPosition(pozitie_team);

        //codul pentru carusel
        if(gamepad2.right_trigger > 0.5){
            SHardware.carusel.setPower(0.6);
        }else if(gamepad2.left_trigger > 0.5){
            SHardware.carusel.setPower(-0.6);
        }else{
            SHardware.carusel.setPower(0);
        }



    }

}
