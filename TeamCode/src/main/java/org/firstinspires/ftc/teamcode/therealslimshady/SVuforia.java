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

    public static float mmPerInch         = 25.4f;
    private static float mmTargetHeight   = 6 * mmPerInch;          // the height of the center of the target image above the floor
    private static float halfField        = 72 * mmPerInch;
    private static float halfTile         = 12 * mmPerInch;
    private static float oneAndHalfTile   = 36 * mmPerInch;

    // Class Members
    public static OpenGLMatrix targetLocation   = null;
    public static  VuforiaLocalizer vuforia    = null;
    public static  VuforiaTrackables targets   = null ;

    public static WebcamName webcam1, webcam2;
    public static SwitchableCamera switchableCamera;

    public static boolean targetVisible       = false;

    public static TFObjectDetector tfod;


    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    public static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };


    public static void init(OpMode var){
        int cameraMonitorViewId = var.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", var.hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        webcam1 = var.hardwareMap.get(WebcamName.class, "Webcam 1");
        webcam2 = var.hardwareMap.get(WebcamName.class, "Webcam 2");
        parameters.cameraName = ClassFactory.getInstance().getCameraManager().nameForSwitchableCamera(webcam1, webcam2);
        parameters.useExtendedTracking = true;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        switchableCamera = (SwitchableCamera) vuforia.getCamera();
        assert switchableCamera != null;
        switchableCamera.setActiveCamera(webcam1);

        targets = vuforia.loadTrackablesFromAsset("FreightFrenzy");

        targets.get(0).setName("Blue Storage");
        targets.get(1).setName("Blue Alliance Wall");
        targets.get(2).setName("Red Storage");
        targets.get(3).setName("Red Alliance Wall");

        targets.activate();
        initTfod(var);
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.5, 20.0/9.0);
        }
    }

    public static void loop(OpMode var){
        targetVisible = false;
        for (VuforiaTrackable trackable : targets) {
            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible())
            {
                targetLocation = ((VuforiaTrackableDefaultListener)trackable.getListener()).getVuforiaCameraFromTarget();
                if (targetLocation != null) {
                    targetVisible = true;
                    break;
                }
            }
        }

        if (targetVisible) {
            VectorF translation = targetLocation.getTranslation();
            var.telemetry.addData("Pos (mm)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0), translation.get(1), translation.get(2));
            Orientation rotation = Orientation.getOrientation(targetLocation, EXTRINSIC, XYZ, DEGREES);
            var.telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
        }
        else {
            var.telemetry.addData("Visible Target", "none");
        }
    }

    public static void stop(){
        targets.deactivate();
        if(tfod != null){
            tfod.shutdown();
        }
    }



    private static void identifyTarget(int targetIndex, String targetName, float dx, float dy, float dz, float rx, float ry, float rz) {
        VuforiaTrackable aTarget = targets.get(targetIndex);
        aTarget.setName(targetName);
        aTarget.setLocation(OpenGLMatrix.translation(dx, dy, dz)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, rx, ry, rz)));
    }




    private static void initTfod(OpMode opMode) {
        int tfodMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.5f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

}
