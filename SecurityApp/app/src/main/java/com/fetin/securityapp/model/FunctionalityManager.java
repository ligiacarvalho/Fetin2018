package com.fetin.securityapp.model;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

public class FunctionalityManager extends AppCompatActivity {

    // método para enviar SMS
    public void sendSms(View view) {

        Intent intenet = new Intent();

        String number = "035998852374";
        String msg = "Teste";

        // requisitando ao usuário que permita o aplicativo enviar SMS
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "SMS enviado!.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Falha ao enviar! Erro: "+e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
