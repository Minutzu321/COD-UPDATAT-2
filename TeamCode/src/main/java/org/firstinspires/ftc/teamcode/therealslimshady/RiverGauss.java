package org.firstinspires.ftc.teamcode.therealslimshady;

public class RiverGauss {

    public double a,c;

    public RiverGauss(double a, double c){
        this.a = a;
        this.c = c;
    }
    
    public double toMotor(double i, double a1, double a2) {
    	double limita = getPunctMaxim()-getLimitaPozitiva();
    	return translateaza(limita, getPunctMaxim(), 0.1, 0.6, getPunctMaxim()-Math.abs(map(i, a1, a2)));
    }

    public double map(double nr, double a1, double a2){
        return translateaza(a1, a2, -getLimitaPozitiva(), getLimitaPozitiva(), nr);
    }

    public double f(double x){
        return Math.exp((Math.pow(x,2)*a)+c);
    }

    public double inversa(double y){
        double ec = Math.log(y);
        double delta = -4*a*(c-ec);
        return Math.sqrt(delta)/(2*a);
    }

    public double getPunctMaxim(){
        return f(0);
    }

    public static double translateaza(double a1, double a2, double b1, double b2, double s){
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

    public double getLimitaPozitiva(){
        return Math.abs(inversa(0.03));
    }

}