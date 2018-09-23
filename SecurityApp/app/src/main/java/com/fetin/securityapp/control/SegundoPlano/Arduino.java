package com.fetin.securityapp.control.SegundoPlano;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fetin.securityapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Arduino extends AppCompatActivity {

    private int ZERO = 48;
    private int UM = 49;
    private Button btnPaired;
    private ListView devicelist;
    public BluetoothAdapter btAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private BroadcastReceiver mReceiver;
    private ArrayList listDispPareados, listDispEncontrados;
    private ArrayAdapter adapter;
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    static ArrayList<BluetoothDevice> lista_de_disp;
    private int flag = 0;
    private BluetoothSocket bs = null;

    public static BluetoothSocket mmSocket;

    public void start() {
        //pede permissão para parear com o celular
     /*   ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.BLUETOOTH}, 1);*/

       // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 1);

        //verifica se o bluetooth do cel esta funcionando
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            mostrarMensagem("Bluetooth não está funcionando!");
        }
        // se estiver funcionando e desligado forca ligar o bluetooth
        if (!btAdapter.isEnabled()) {
            //Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // startActivityForResult(enableBtIntent, 5);
            btAdapter.enable();
        }

        // Thread para evitar alguns possíveis problemas
        try {
            Thread.sleep(1000);
        } catch (Exception E) {
            E.printStackTrace();
        }

    }

    public boolean connectToBluetooth(BluetoothDevice device) {

        boolean sucesso_leitura = false;
        boolean sucesso_conexão = false;
        int tentativa = 0;
        int resp = 0;

        // ----------------- CONEXÃO COM O BLUETOOTH -------------------//
        do {


            try {

                if (mmSocket != null)
                    mmSocket.close();

                //device esta com os dados do módulo e ele tenta criar uma conexão com o celular
                mmSocket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);//create a RFCOMM (SPP) connection

                //para de buscar os dispositivos com o bluetooth ligado
                btAdapter.cancelDiscovery();

                //pede a senha e tenta conectar
                mmSocket.connect();

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                sucesso_conexão = true;

            } catch (IOException e) {
                //msgLog("Erro = " + e.getMessage());

                try {
                    mmSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                sucesso_conexão = false;
            }

            tentativa++;

        } while (sucesso_conexão == false && tentativa < 5);


        if (sucesso_conexão == false || tentativa >= 20) {
            //T//oast.makeText(getApplicationContext(), "Não conseguiu conectar!", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }

    public BluetoothDevice buscarOBluetoothDoArduino() {
        for (int i = 0; i < lista_de_disp.size(); i++) {

            if (lista_de_disp.get(i).getName().contains("HC-05")) {
                return lista_de_disp.get(i);
            }

        }

        return null;
    }

    public void buscarDispositivos() {

        //btAdapter é o bluetooth do celular
        final String[] dadosBluetooth = new String[1];
        // Método para iniciar a busca pelos dispositivos
        boolean deuCerto = btAdapter.startDiscovery();

        if (listDispEncontrados == null) {
            listDispEncontrados = new ArrayList();
        }


        if (lista_de_disp == null)
            lista_de_disp = new ArrayList<>();


        // Create a BroadcastReceiver for ACTION_FOUND
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a devices
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    //add a var device na lista para pegar os atributos dela
                    lista_de_disp.add(device);

                    dadosBluetooth[0] = "Nome: " + device.getName() + "\nMAC: " + device.getAddress();

                    inserirDispositivo(dadosBluetooth[0]);

                    msgLog(dadosBluetooth[0]);

                }
            }
        };


        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

        // Delay para conseguir procurar os dispositivos
        try {
            if (listDispEncontrados.size() == 0) {
                Thread.sleep(6000);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void msgLog(String s) {
        Log.i("TESTE", s);
    }

    public void inserirDispositivo(String dadosBluetooth) {


        //se não contém o dado que foi passado, ela insere na lista
        if (!listDispEncontrados.contains(dadosBluetooth))
            listDispEncontrados.add(dadosBluetooth);
    }

    // funçao pega todos os dispositivos pareados
    public BluetoothDevice pairedDevicesList() {
        // pegando dispositivos que estão pareados na rede
        pairedDevices = btAdapter.getBondedDevices();

        BluetoothDevice bluetooth = null;

        if (pairedDevices.size() > 0) {

            for (BluetoothDevice bt : pairedDevices) {
                bluetooth = bt; //Get the device's name and the address
            }

            return bluetooth;

        } else {

            return bluetooth;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void mostrarMensagem(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    public void mostrarMensagemCurta(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
