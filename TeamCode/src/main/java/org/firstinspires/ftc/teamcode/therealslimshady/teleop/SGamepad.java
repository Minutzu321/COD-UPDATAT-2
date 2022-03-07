package org.firstinspires.ftc.teamcode.therealslimshady.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.therealslimshady.SHardware;
import org.firstinspires.ftc.teamcode.therealslimshady.SMiscariRoti;
import org.firstinspires.ftc.teamcode.therealslimshady.autonomie.SCreier;

public class SGamepad {

    private static float PUTERE_ROTI = 0.8f;

    private static float LIMITARE_SUS_LIFT = -7200;
    private static float LIMITARE_JOS_LIFT = 0;

    private static float pozitie_cutie = 1;

    public static void init(){
        SHardware.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public static void loop(OpMode opMode) {
        if (!SHardware.initializat) return;
        SHardware.par.setPosition(0.6);
        SHardware.perp.setPosition(0);

        Gamepad gamepad1 = opMode.gamepad1;
        //codul pentru miscare
        SMiscariRoti.setVelXYR(gamepad1.left_stick_x * PUTERE_ROTI, -gamepad1.left_stick_y * PUTERE_ROTI, -gamepad1.right_stick_x * PUTERE_ROTI);

        //codul pentru maturici
        if (gamepad1.right_trigger > 0.5) {
            SHardware.matura_interior.setPower(1);
            SHardware.matura_exterior.setPower(-0.6);
        } else if (gamepad1.left_trigger > 0.5) {
            SHardware.matura_interior.setPower(-1);
            SHardware.matura_exterior.setPower(0.6);
        } else {
            SHardware.matura_interior.setPower(0);
            SHardware.matura_exterior.setPower(0);
        }


        Gamepad gamepad2 = opMode.gamepad2;
        //codul pentru lift
        DcMotor lift = SHardware.lift;
        float left_stick_y = -gamepad2.left_stick_y;
        double putere = 1;
        if(lift.getCurrentPosition() < LIMITARE_JOS_LIFT+1000 || lift.getCurrentPosition() > LIMITARE_SUS_LIFT-500)
            putere = 0.3;

        if (left_stick_y > 0.5 && lift.getCurrentPosition() > LIMITARE_SUS_LIFT){
            lift.setPower(-putere);
        }else if(left_stick_y < -0.5 && lift.getCurrentPosition() < LIMITARE_JOS_LIFT){
            lift.setPower(putere);
        }else {
            lift.setPower(0);
        }

        opMode.telemetry.addData("lift",lift.getCurrentPosition());

        //codul pentru cutie
        Servo cutie = SHardware.cutie;
        pozitie_cutie += gamepad2.right_stick_y*0.005;

        if(pozitie_cutie > 1)
            pozitie_cutie = 1;
        if(pozitie_cutie < 0.2)
            pozitie_cutie = 0.2f;

        cutie.setPosition(pozitie_cutie);

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
