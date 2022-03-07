package org.firstinspires.ftc.teamcode.therealslimshady.teleop;

import com.google.ftcresearch.tfod.util.ImageUtils;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name="echipa")
public class Echipa extends LinearOpMode {

    DcMotor ss, sf, ds, df;
    ElapsedTime el;
    BNO055IMU imu;
    double target = 45;
    @Override
    public void runOpMode() throws InterruptedException {
        ss = (DcMotor) hardwareMap.get("ss");
        sf = (DcMotor) hardwareMap.get("sf");
        ds = (DcMotor) hardwareMap.get("ds");
        df = (DcMotor) hardwareMap.get("df");

        ds.setDirection(DcMotorSimple.Direction.REVERSE);
        df.setDirection(DcMotorSimple.Direction.REVERSE);


        el=new ElapsedTime();
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        waitForStart();
        while(!isStopRequested()) {
            double angle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
            double error = target - angle;

            power(0,0, error*0.02);
            telemetry.addData("err", error);
            telemetry.update();


        }
    }


    public void power(double x, double y, double r) {
        double normalizator = Math.abs(x) + Math.abs(y) + Math.abs(r);
        normalizator = Math.max(normalizator, 1);
        double psf = (y + x + r) / normalizator;
        double pss = (y - x + r) / normalizator;
        double pdf = (y - x - r) / normalizator;
        double pds = (y + x - r) / normalizator;



        ds.setPower(pds);
        ss.setPower(pss);
        sf.setPower(psf);
        df.setPower(pdf);


    }
}
