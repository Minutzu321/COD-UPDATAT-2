package org.firstinspires.ftc.teamcode.therealslimshady;

import android.util.Log;

import com.qualcomm.robotcore.util.ThreadPool;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SConnection {

    private static ServerSocket server;
    private static boolean serverDeschis = false, conexiune = false;
    private static BufferedWriter os;
    private static BufferedReader is;

    public static void init(){
        int cores = Runtime.getRuntime().availableProcessors();
        Log.e("CORES-CONN", cores + "");
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        ExecutorService executor3 = Executors.newSingleThreadExecutor();
        executor1.execute(() -> {
            int port = 8091;
            try {
                server = new ServerSocket(port);
                serverDeschis = true;
                Log.e("Server status", "Serverul a fost deschis");
            } catch (IOException e) {
                serverDeschis = false;
                Log.e("Server status", "Serverul NU a fost deschis " + e.getLocalizedMessage());
            }
        });
        executor2.execute(() -> {
            while(serverDeschis){
                if(conexiune) continue;
                try {
                    Socket s = server.accept();
                    os = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                    is = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    conexiune = true;
                    Log.e("111333","KONN");
                }catch (Exception e){
                    Log.e("Conn status", Objects.requireNonNull(e.getLocalizedMessage()));
                    conexiune = false;
                }
            }
            Log.e("EXEC2", "exited");
        });
        executor3.execute(() -> {
            while (serverDeschis) {
                if (!conexiune) continue;
                try{
                    String rasp = is.readLine();
//                    Log.e("MESAJ SOCKET", rasp);

//                    if(SVuforia.targetVisible){
//                        VectorF translation = SVuforia.targetLocation.getTranslation();
//                        os.write(translation.get(0)+" - "+translation.get(1)+" - "+translation.get(2)+"\n");
//                    }else{
//                        os.write("Tinta nu este vizibila\n");
//                    }
                    os.flush();

                }catch (IOException e){
                    conexiune = false;
                }
            }
            Log.e("EXEC3", "exited");
        });
    }

    public static void stop(){
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverDeschis = false;
        server = null;
        is = null;
        os = null;
    }

}
