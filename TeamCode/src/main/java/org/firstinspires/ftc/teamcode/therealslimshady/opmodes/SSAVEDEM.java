package org.firstinspires.ftc.teamcode.therealslimshady.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.therealslimshady.SConnection;
import org.firstinspires.ftc.teamcode.therealslimshady.SHardware;
import org.firstinspires.ftc.teamcode.therealslimshady.SMiscariRoti;
import org.firstinspires.ftc.teamcode.therealslimshady.SVuforia;
import org.firstinspires.ftc.teamcode.therealslimshady.autonomie.IASAVEDEM;
import org.firstinspires.ftc.teamcode.therealslimshady.autonomie.SCreier;

@Autonomous(name = "SSAVEDEM")
public class SSAVEDEM extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //OPTIONAL Initializam inainte serverul pentru conexiunea externa cu laptopul
        //daca aplicatia se inchide in timpul testului neasteptat, stergeti linia SConnection.init();

//        SConnection.init(); //STERGE LINIA ASTA DACA APLICATIA SE INCHIDE RANDOM

        //In primul rand trebuie sa initializam parte de hardware
        //deci chemam fucntia init din clasa SHardware si pasam argumentul OpMode
        //care este "this", adica clasa asta, intrucat extinde OpMode!!
        SHardware.init(this);

        //Initializam si vuforia pentru ca suntem la autonomie si ne ajuta
//        SVuforia.init(this);

        //asteptam pana se apasa butonul de start!
        waitForStart();


        SHardware.cutie.setPower(0.4);
        ElapsedTime et = new ElapsedTime();
        while (et.seconds() < 0.4) {};
        SHardware.cutie.setPower(0);
        while (et.seconds() < 0.2) {};
        float r=0,g=0,b=0;
        while (r <= (r+g+b)/3){
            SHardware.cutie.setPower(-0.15);

            r = SHardware.colorSensor.getNormalizedColors().red;
            g = SHardware.colorSensor.getNormalizedColors().green;
            b = SHardware.colorSensor.getNormalizedColors().blue;
        }
        SHardware.cutie.setPower(0);

        //In loc de functia loop vom avea un while care se opreste cand apasam pe butonul de stop
        while(!isStopRequested()){
//            SVuforia.loop(this);
//            telemetry.addData("dist",SHardware.distanta.getDistance(DistanceUnit.MM));

//            IASAVEDEM.loop(this);
//            SCreier.mergi(7650);
            SMiscariRoti.loop(this);


            telemetry.update();
        }


        IASAVEDEM.stop();
        SVuforia.stop();
        SConnection.stop();
        SHardware.initializat = false;
    }
}
