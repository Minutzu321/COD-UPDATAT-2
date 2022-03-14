package org.firstinspires.ftc.teamcode.therealslimshady;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XZY;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.SwitchableCamera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

public class SVuforia {

    private static final String VUFORIA_KEY ="AfNM7lD/////AAABmWC9oSAkbk+RiPHZnJIoSCEnkeEQcO95qSHOsX7sNV52AfvXc4J1dqXGF+QYKG52nTQNx3w3lwZ1ywLLdVxTr6oaRKh9Yo795IWDbgDrOOrtjKkPflJZWRWwrt97cvsZVDSSG3P+Vx3Pa3YqsE73CveiZHTR2Gp0tzzhXZnlGGEpUfPlkFg0VjW16H/aRJ5lyvskFIiosXWPSHJevHf7GIdXQSwB5ekSM9uYfE1BNDrMnKeNRbgKJO9r0FUbgRlu0emAVmNsYI5icXiDm+fLNyYl7EMz7uQBEmkzsy6O3wFx3NuG3mRP/pRZLIVNbGP1+SwNJIHt4vehFsDsut7+vgDgiaOacqFct4Bi9q2zfeoR ";

    public static  VuforiaLocalizer vuforia    = null;

    public static WebcamName webcam1;

    public static TFObjectDetector tfod;


    private static final String TFOD_MODEL_ASSET = "RiverWolves.tflite";
    public static final String[] LABELS = {
            "Pahar",
    };


    public static void init(OpMode var){
        int cameraMonitorViewId = var.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", var.hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        webcam1 = var.hardwareMap.get(WebcamName.class, "Webcam 1");
        parameters.cameraName = webcam1;
        parameters.useExtendedTracking = false;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        initTfod(var);
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.5, 20.0/9.0);
        }
    }

    public static void loop(OpMode var){

    }

    public static void stop(){
        if(tfod != null){
            tfod.shutdown();
        }
    }

    private static void initTfod(OpMode opMode) {
        int tfodMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

}
