package com.fetin.securityapp.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fetin.securityapp.R;
import com.fetin.securityapp.control.Menu.MenuActivity;
import com.fetin.securityapp.control.SegundoPlano.Arduino;
import com.fetin.securityapp.control.SegundoPlano.ArduinoService;
import com.fetin.securityapp.control.SegundoPlano.BloqueioService;
import com.fetin.securityapp.model.Dao.UsuarioDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoubadoActivity extends AppCompatActivity {

    String senha;
    EditText campoSenha;
    //Desabilitar volume
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));
    private int Abriu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roubado);
        campoSenha  = findViewById(R.id.editTextSenha);

        campoSenha.setText("0123456789");

    }


    public void desbloquear(View view)
    {
         senha = UsuarioDAO.user_cadastrado.getSenha();
        if (campoSenha.getText().toString().equals(senha)) {
            Intent intent_bloqueio = new Intent(this, BloqueioService.class);
            stopService(intent_bloqueio);
            // ativa o bloqueio
            Intent intent_arudino = new Intent(this, ArduinoService.class);
            stopService(intent_arudino);
            ArduinoService.somAlarm.stop();
            Intent intent1 = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent1);

            Toast.makeText(getApplicationContext(),"Modo bloqueio desativado!",Toast.LENGTH_LONG).show();

        }
    }

    //Desabilitar botão de voltar
    @Override
    public void onBackPressed() {
        // nothing to do here
        // … really
    }

    //Desabilitar aperto longo no power
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    //Desabilitar volume
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }


}
