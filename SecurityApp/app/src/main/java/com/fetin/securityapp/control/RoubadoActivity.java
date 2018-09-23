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
import com.fetin.securityapp.model.Dao.CelularDAO;
import com.fetin.securityapp.model.Dao.UsuarioDAO;
import com.fetin.securityapp.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoubadoActivity extends AppCompatActivity {

    String senha;
    EditText campoSenha;
    //Desabilitar volume
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));
    public static DatabaseReference referenciaDoBanco, referenciaDoUsuario;


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


            RetornarListaNaoRoubados();

        }


    }

    public void RetornarListaNaoRoubados(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        referenciaDoBanco = FirebaseDatabase.getInstance().getReference();

        for (int i = 0; i < CelularDAO.lista_de_roubo.size();i++){

            if(user.getEmail().equals(CelularDAO.lista_de_roubo.get(i).getEmail())){


                String chaveDoUsuarioASerDeletado = UsuarioDAO.buscaUmUsuarioEspecificoERetornaASuaChaveDoFireBase(user.getEmail());

                referenciaDoUsuario = referenciaDoBanco.child("Celulares Roubados");
                referenciaDoUsuario.child(chaveDoUsuarioASerDeletado).removeValue();

            }

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
