package org.firstinspires.ftc.teamcode.therealslimshady;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public class SMiscariRoti {

    public static float[] puteri = {0,0,0};//respectiv x, y ,r(rotatie)

    public static RiverGauss rg = new RiverGauss(-5,0);

    public static void loop(OpMode opMode){

        //luam elementele si le punem in variabile
        float x = puteri[0];
        float y = puteri[1];
        float r = puteri[2];

        opMode.telemetry.addData("puteri",x+" - "+y+" - "+r);

        //facem un normalizator ca sa nu treaca suma puterilor de 1
        double normalizator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(r), 1);

        //aplicam formula mecanum cu numalizatorul
        double sf = (y + x + r) / normalizator;
        double ss = (y - x + r) / normalizator;
        double df = (y - x - r) / normalizator;
        double ds = (y + x - r) / normalizator;

        //daca hardware-ul este initializat, setam puterile
        //in caz contrar, e posibil sa dea erori!
        if(SHardware.initializat){
            SHardware.ss.setPower(ss);
            SHardware.sf.setPower(sf);
            SHardware.ds.setPower(ds);
            SHardware.df.setPower(df);
        }
    }

    public static void setVelXYR(float x, float y, float r){
        puteri[0] = x;
        puteri[1] = y;
        puteri[2] = r;
    }

    public static void setVelXY(float x, float y){
        puteri[0] = x;
        puteri[1] = y;
    }

    public static void setVelX(float x){
        puteri[0] = x;
    }

    public static void setVelY(float y){
        puteri[1] = y;
    }

    public static void setVelR(float r){
        puteri[2] = r;
    }

    public static void setVelPolar(float putere, float unghi, AngleUnit angleUnit){
        if(angleUnit == AngleUnit.DEGREES)
            unghi = angleUnit.toRadians(unghi);
        puteri[0] = (float) Math.cos(unghi) * putere;
        puteri[1] = (float) Math.sin(unghi) * putere;
    }

//    public static float mergiSpreTarget(float DESIRED_DISTANCE, float DESIRED_STANGA){
//        float targetRange, targetBearing;
//
//        VectorF trans = SVuforia.targetLocation.getTranslation();
//
//        // Extract the X & Y components of the offset of the target relative to the robot
//        float targetX = trans.get(0) / SVuforia.mmPerInch; // Image X axis
//        float targetY = trans.get(2) / SVuforia.mmPerInch; // Image Z axis
//
//        // target range is based on distance from robot position to origin (right triangle).
//        targetRange = (float) Math.hypot(targetX, targetY);
//
//        // target bearing is based on angle formed between the X axis and the target range line
//        targetBearing = (float) Math.toDegrees(Math.asin(targetX / targetRange));
//
//        float  rangeError   = (targetRange - DESIRED_DISTANCE);
//        float  headingError = targetBearing - DESIRED_STANGA;
//
//        // Use the speed and turn "gains" to calculate how we want the robot to move.
//        float drive = rangeError * 0.02f;
//        float turn  = headingError * 0.02f;
//
//        setVelXY(turn,drive);
////        setVelY(drive);
////        setVelR(turn);
//
//        return rangeError;
//    }

    public static void mergiLa(double ax, double ay, double x, double y, double cx, double cy){
        double py;
        if(ax>cx){
            py = rg.toMotor(x,cx,ax);
        }else{
            py = rg.toMotor(x,ax,cx);
        }
        if(x<cx-2){
            py = -py;
        }else if(x>cx+2){
            py = py;
        }else{
            py=0;
        }

        double px;
        if(ay>cy){
            px = rg.toMotor(y,cy,ay);
        }else{
            px = rg.toMotor(y,ay,cy);
        }
        if(x<cx-2){
            px = px;
        }else if(x>cx+2){
            px = -px;
        }else{
            px=0;
        }
        setVelXY((float)px,(float)py);

    }


    static final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
    static final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable


    public static boolean eSpre(double angle){
        double error = getError(angle, AngleUnit.DEGREES);
        if (Math.abs(error) <= HEADING_THRESHOLD) {
            return true;
        }
        return false;
    }

    public static boolean indreaptaSpre(double speed, double angle, AngleUnit degrees) {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double rotire;

        error = getError(angle, degrees);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            rotire  = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, P_TURN_COEFF);
            rotire  = speed * steer;
        }

        setVelR((float) rotire);

        SHardware.telemetry.addData("Target", "%5.2f", angle);
        SHardware.telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        SHardware.telemetry.addData("Speed.", "%5.2f", rotire);

        return onTarget;
    }

    public static double getError(double targetAngle, AngleUnit unit) {
        double robotError;
        double la = SHardware.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        robotError = targetAngle - la;
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    public static double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }


}
