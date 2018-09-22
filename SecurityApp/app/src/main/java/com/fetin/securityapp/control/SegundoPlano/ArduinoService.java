package com.fetin.securityapp.control.SegundoPlano;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.fetin.securityapp.R;
import com.fetin.securityapp.model.Dao.CelularDAO;
import com.fetin.securityapp.model.Dao.UsuarioDAO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;

public class ArduinoService extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private static boolean parou;
    private Context ctx;
    private int resp;
    private Arduino arduino;
    public static MediaPlayer somAlarm;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;


    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler  {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {


            // Conectou
            do {
                //faz leitura do serial
                try {
                    resp = Arduino.mmSocket.getInputStream().read();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Log.i("Teste", "Teste Arduino");

            } while (resp != 49);
            //49 é 1 na tabela ASCII

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playMusic();

            CelularDAO daoC = new CelularDAO();

            getLastLocation();

            daoC.inserirRoubado(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            sendSms(UsuarioDAO.user_cadastrado.getContatoProximo(), UsuarioDAO.user_cadastrado.getCelularP().getCodigo());
            //sendSms(UsuarioDAO.user_cadastrado.getContatoProximo(),123);


        }




    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        arduino = new Arduino();

        iniciarFuncionalidadesDoArduino();

        parou = false;
        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    public void iniciarFuncionalidadesDoArduino() {

        // busca o bluetooth do arudino
        arduino.buscarDispositivos();


        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // tento armazenar as informações dele em uma variavel
        BluetoothDevice bluetoothDoArudino = arduino.buscarOBluetoothDoArduino();

        if(bluetoothDoArudino != null)
        {
            arduino.connectToBluetooth(bluetoothDoArudino);
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service arduino starting", Toast.LENGTH_SHORT).show();

        ctx = this;

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    public void playMusic() {
        // Referenciando o "somAlarm" com a música que está na pasta RAW
        somAlarm = MediaPlayer.create(ctx, R.raw.alarme_roubo);
        somAlarm.start();

    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener((Activity) ctx, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {

                            //obtém a última localização conhecida
                            mLastLocation = task.getResult();
                            // mostrando a latitude e longitude do celular atual na telinha do APP
                            //msg("Latitude: " + mLastLocation.getLatitude() + " Longitude: " + mLastLocation.getLongitude());
                            // setando a localização atual do celular no MAPA, com o marcador
                            //setMyLocation(mLastLocation);

                        } else {

                            //Não há localização conhecida ou houve uma excepção
                            //A excepção pode ser obtida com task.getException()

                            //msg("Erro");
                        }
                    }
                });
    }

    public void sendSms(String contato, int cod) {
        String usuario = UsuarioDAO.user_cadastrado.getNome();
        String senha = Integer.toString(cod);
        Intent intenet = new Intent();
        String msg = "S.O.S!\n" + usuario + " foi roubado(a)!\n" + "Código para localização: \n"+ senha;


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(contato, null, msg, null, null);
            //  Toast.makeText(getApplicationContext(), "SMS enviado!.", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Falha ao enviar! Erro: "+e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }


}