package com.fetin.securityapp.control.SegundoPlano;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
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
import com.fetin.securityapp.control.Menu.MenuActivity;
import com.fetin.securityapp.control.RoubadoActivity;
import com.fetin.securityapp.model.Dao.CelularDAO;
import com.fetin.securityapp.model.Dao.UsuarioDAO;
import com.fetin.securityapp.model.Usuario;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private String MAC_Bluetooth_Arduino = "98:D3:31:30:2C:20";
    private String NAME_Bluetooth = "HC-05";
    private String MAC_Bluetooth_PC = "B0:10:41:A2:AA:EE";
    private String NAME_Bluetooth_PC = "DESKTOP-R94QAVP";
    private int tentativas = 20;
    public static MenuActivity ma;
    private int tentativas_igual_a_zero;


    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {


            // Conectou
            do {
                //faz leitura do serial
                try {
                    if (Arduino.mmSocket != null) {
                        resp = Arduino.mmSocket.getInputStream().read();
                        Log.i("Teste", "Resp=" + resp);
                    } else {
                        stopSelf();
                        break;
                    }


                    Log.i("Teste", "Resp=" + resp);
                } catch (IOException e) {
                    e.printStackTrace();
                    if(resp == 0)
                    {
                        tentativas_igual_a_zero++;
                    }

                    if(tentativas_igual_a_zero > 2)
                    {
                        break;
                    }
                }


            } while (resp != 49);

            if(tentativas_igual_a_zero > 2)
            {
                return;
            }

            if(resp == 49)
            {
                ativarFuncionalidadesDeBloqueio();
            }

            //49 é 1 na tabela ASCII
            Log.i("Teste", "Teste Arduino");
            //ativarFuncionalidadesDeBloqueio();*/


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

        arduino.start();

        ma = new MenuActivity();
        tentativas_igual_a_zero = 0;
        iniciarFuncionalidadesDoArduino();

        parou = false;
        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

    }

    public void iniciarFuncionalidadesDoArduino() {


        BluetoothDevice bluetooth_pareado = arduino.pairedDevicesList();
        // tento armazenar as informações dele em uma variavel


        if (bluetooth_pareado == null) {
            BluetoothDevice bluetoothDoArudino = arduino.btAdapter.getRemoteDevice(MAC_Bluetooth_Arduino);
            arduino.connectToBluetooth(bluetoothDoArudino);
        } else
            arduino.connectToBluetooth(bluetooth_pareado);


    }

    public void ativarFuncionalidadesDeBloqueio() {
        playMusic();

        CelularDAO daoC = new CelularDAO();
        RoubadoActivity ra = new RoubadoActivity();

        // pega os serviços para uso da localização na API da Google
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

        UsuarioDAO.user_cadastrado = buscarUsuarioLogado();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        daoC.inserirRoubado(mLastLocation.getLatitude(), mLastLocation.getLongitude());

        sendSms(UsuarioDAO.user_cadastrado.getContatoProximo(), UsuarioDAO.user_cadastrado.getCelularP().getCodigo());

        ra.ExcluiDaListaCelRoubados();

        // ativa o bloqueio
        Intent intent = new Intent(this, BloqueioService.class);
        startService(intent);

      /*
        // ativa o bloqueio
        Intent intent_roubado = new Intent(this, RoubadoActivity.class);
        startActivity(intent);
*/


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ctx = this;


        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = 2;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    public void playMusic() {
        // Referenciando o "somAlarm" com a música que está na pasta RAW
        somAlarm = MediaPlayer.create(ctx, R.raw.alarme_roubo);
        somAlarm.start();
        somAlarm.setLooping(true);

        AudioManager audio = (AudioManager)
            getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, 15, 0);


    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(ma, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {

                            //obtém a última localização conhecida
                            mLastLocation = task.getResult();
                            // mostrando a latitude e longitude do celular atual na telinha do APP
                            //msg("Latitude: " + mLastLocation.getLatitude() + " Longitude: " + mLastLocation.getLongitude());

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
        String msg = "S.O.S!\n" + usuario + " foi roubado(a)!\n" + "Código para localização: \n" + senha;


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(contato, null, msg, null, null);
            //  Toast.makeText(getApplicationContext(), "SMS enviado!.", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Falha ao enviar! Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {


        try {
            if (Arduino.mmSocket != null)
                Arduino.mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Usuario buscarUsuarioLogado() {
        FirebaseUser usuario_logado = FirebaseAuth.getInstance().getCurrentUser();
        String email_user_logado = usuario_logado.getEmail();

        // uma busca na lista, querendo o usuario que está logado
        Usuario usuario_encontrado = null;

        for (int i = 0; i < UsuarioDAO.lista_de_usuarios.size(); i++) {

            if (UsuarioDAO.lista_de_usuarios.get(i).getEmail().equals(email_user_logado)) {

                usuario_encontrado = UsuarioDAO.lista_de_usuarios.get(i);

                usuario_encontrado.setCelularP(UsuarioDAO.lista_de_usuarios.get(i).getCelularP());

                return usuario_encontrado;

            }
        }

        return usuario_encontrado;
    }
}