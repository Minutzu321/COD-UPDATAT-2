package org.firstinspires.ftc.teamcode.therealslimshady.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.therealslimshady.SMiscariRoti;
import org.firstinspires.ftc.teamcode.therealslimshady.autonomie.SCreier;
import org.firstinspires.ftc.teamcode.therealslimshady.teleop.SGamepad;
import org.firstinspires.ftc.teamcode.therealslimshady.SHardware;
import org.firstinspires.ftc.teamcode.therealslimshady.SVuforia;

@TeleOp(name = "STELEOP")
public class STELEOP extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {


        //OPTIONAL Initializam inainte serverul pentru conexiunea externa cu laptopul
        //daca aplicatia se inchide in timpul testului neasteptat, stergeti linia SConnection.init();

        //SConnection.init(); //STERGE LINIA ASTA DACA APLICATIA SE INCHIDE RANDOM

        //In primul rand trebuie sa initializam parte de hardware
        //deci chemam fucntia init din clasa SHardware si pasam argumentul OpMode
        //care este "this", adica clasa asta, intrucat extinde OpMode!!
        SHardware.init(this);
        SGamepad.init();

        //asteptam pana se apasa butonul de start!
        waitForStart();

        //In loc de functia loop vom avea un while care se opreste cand apasam pe butonul de stop
        while(!isStopRequested()){
            SGamepad.loop(this);

            SMiscariRoti.loop(this);

            telemetry.update();
        }

        SVuforia.stop();
        SHardware.initializat = false;
    }
}
