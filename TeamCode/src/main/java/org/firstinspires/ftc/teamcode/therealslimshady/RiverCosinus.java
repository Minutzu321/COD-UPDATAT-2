package org.firstinspires.ftc.teamcode.therealslimshady;

public class RiverCosinus {

    public static double toMotor(double valoare, double a1, double a2){
        double limitaCosinus = Math.PI/2;
        double translatatInCosinus = translateaza(a1,a2,-limitaCosinus,limitaCosinus, valoare);
        return translateaza(0, 1, 0.1 ,0.4, Math.cos(translatatInCosinus));
    }

    private static double translateaza(double a1, double a2, double b1, double b2, double s){
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

}
