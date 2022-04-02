package org.firstinspires.ftc.teamcode.therealslimshady.autonomie;

public class Configuratie {

    public static double ASTEAPTA_RECUNOASTERE = 5;

    //puterea cu care sa isi mentina directia
    public static double PUTERE_REDRESARE = 0.3;

    //cele 3 pozitii ale liftului in functie de ce nivel este selectat
    public static int LIFT_1 = 480,
                      LIFT_2 = 2700,
                      LIFT_3 = 7650;

    //pozitiile in functie de partea pe care este robotul
    public static int POZ_STANGA, POZ_DREAPTA, POZ_NEVAZUTA;

    //puterea si directia in care sa se invarta caruselul
    public static double PUTERE_CARUSEL;

    //unghiul la care sa se invarta robotul cand este la carusel
    public static int UNGHI_CARUSEL;

    //puterea cu care robotul sa mearga spre carusel
    public static double PUTERE_SPRE_CARUSEL;

    //timpul rotirii caruselului
    public static double TIMP_ROTIRE_CARUSEL = 3;

    //directia in care sa mearga robotul
    public static double DIRECTIE;

    //AUTONOMIE FULL - mers inainte
    public static double MERGI_INAINTE = 50,
                         MERGI = 32,
                         MERGI_ADD_2 = 2.3,
                         MERGI_ADD_3 = 2.5;

    public static double DISTANTA_PERETE_FULL;

    public static double D1_FULL = 90, D2_FULL = 140;

    public static double MERGI_INAPOI;

    public static double UNGHI_ANTI_TEVI;

    public static double MERGI_WH_PARTIAL;

    public static double MERGI_BACKUP;

    public static double NU_LOVI_TSE;

    public static void init(int PARTE){
        if(PARTE==0){
            //ROSU

            POZ_STANGA = 1;
            POZ_DREAPTA = 2;
            POZ_NEVAZUTA = 3;

            MERGI = 34;

            PUTERE_CARUSEL = -0.5;

            UNGHI_CARUSEL = 55;

            PUTERE_SPRE_CARUSEL = 0.1;

            DIRECTIE = 1;

            DISTANTA_PERETE_FULL = 1.8;

            MERGI_INAPOI = 26;

            MERGI_ADD_3 = 2.5;

            UNGHI_ANTI_TEVI = 5;

            MERGI_WH_PARTIAL = 60;

            MERGI_BACKUP = 80;

            NU_LOVI_TSE = 80;
        }else{
            //ALBASTRU

            POZ_STANGA = 2;
            POZ_DREAPTA = 3;
            POZ_NEVAZUTA = 1;

            PUTERE_CARUSEL = -0.5;

            UNGHI_CARUSEL = 55+90;

            PUTERE_SPRE_CARUSEL = 0.1;

            DIRECTIE = -1;

            DISTANTA_PERETE_FULL = 1.8;

            MERGI_INAPOI = 21;

            MERGI_ADD_3 = 3;

            UNGHI_ANTI_TEVI = 3;

            MERGI_WH_PARTIAL = 40;

            MERGI_BACKUP = 80;

            MERGI = 33;

            NU_LOVI_TSE = 80;
        }
    }

}
