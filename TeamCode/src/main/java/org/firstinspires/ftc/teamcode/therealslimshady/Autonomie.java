package org.firstinspires.ftc.teamcode.therealslimshady;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.therealslimshady.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.therealslimshady.roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Autonomie")

public class Autonomie extends LinearOpMode {


    double poz1 = 0;
    double poz2 = 0.33;

    @Override
    public void runOpMode() throws InterruptedException {





        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//
//        TrajectorySequence myTrajectory = drive.trajectorySequenceBuilder(new Pose2d())
//                .strafeTo(new Vector2d(10, 5))
//                .addDisplacementMarker(() -> {
//                    SHardware.cutie.setPosition(1);
//                })
//                .waitSeconds(0.5)
//                .addDisplacementMarker(() -> {
//                    SHardware.cutie.setPosition(0);
//                })
//                .strafeTo(new Vector2d(0,0))
//                .build();

//        drive.followTrajectorySequence(myTrajectory);
        waitForStart();
        while (!isStopRequested()){
//            poz1=0.239;
//            poz2=0.775;


            if(-gamepad1.left_stick_y > 0.5)
                poz1 += 0.005;
            if(-gamepad1.left_stick_y < -0.5)
                poz1 -= 0.005;

            if(-gamepad1.right_stick_y > 0.5)
                poz2 += 0.005;
            if(-gamepad1.right_stick_y < -0.5)
                poz2 -= 0.005;

            drive.par.setPosition(poz1);
            drive.perp.setPosition(poz2);
            telemetry.addData("poz1",poz1);
            telemetry.addData("poz2",poz2);
            telemetry.update();
            drive.update();
        }

    }
}
