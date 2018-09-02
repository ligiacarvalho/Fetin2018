package com.fetin.securityapp.model;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.fetin.securityapp.model.Dao.UsuarioDAO;

public class FunctionalityManager extends AppCompatActivity {

    // método para enviar SMS
    public void sendSms(String contato, int cod) {
        String usuario = UsuarioDAO.user_cadastrado.getNome();
        String senha = Integer.toString(cod);
        Intent intenet = new Intent();
        //String msg = usuario;
        String msg = "S.O.S!\n" + usuario + " foi roubado(a)!\n" + "Código para localização: \n"+ senha  ;


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
