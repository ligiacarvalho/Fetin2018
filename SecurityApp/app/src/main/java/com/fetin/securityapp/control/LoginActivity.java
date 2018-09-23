package com.fetin.securityapp.control;

import android.Manifest;
import android.content.Intent;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.EditText;

import android.widget.Toast;


import com.fetin.securityapp.R;

import com.fetin.securityapp.control.Menu.MenuActivity;
import com.fetin.securityapp.control.Tutorial.Tutorial2Activity;
import com.fetin.securityapp.model.Dao.CelularDAO;


import com.fetin.securityapp.model.Dao.UsuarioDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class LoginActivity extends AppCompatActivity {


    private EditText editTextEmail;

    private EditText editTextSenha;


    // objeto para fazer manipulações no banco de dados do nó usuário

    private CelularDAO celularDAO;

    // Objeto para gerenciar o banco de dados

    private DatabaseReference referenciaDoBanco;

    // Objeto para gerenciar os usuários

    private FirebaseAuth usuario;
    private boolean sucessoAuth = false;

    private FirebaseAuth usuarioAuth;


    private String login, senha;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        CelularDAO daoC = new CelularDAO();
        daoC.buscarCelularesRoubado();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        referenciaComponentes();
        celularDAO = new CelularDAO();

        // verificando se a variável "dao" já foi criada
        UsuarioDAO.getInstance();

        // fazendo a busca dos usuários no FireBase, e armazenando em uma lista chamada: "lista_de_usuarios"
        UsuarioDAO.dao.buscarUsuarios();

        metodoParaTeste();

        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        LogarDireto();
    }


    public void referenciaComponentes()

    {

        editTextEmail = findViewById(R.id.EditTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);

    }


    public void metodoParaTeste()
    {
        editTextEmail.setText("@inatel.br");
        editTextSenha.setText("0123456789");
    }


    public boolean verificaEntradaDeDados()
    {
        if (editTextEmail.getText().toString().equals(""))

        {
            Toast.makeText(getApplicationContext(), "Campo sem dados!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (editTextSenha.getText().toString().equals(""))

        {
            Toast.makeText(getApplicationContext(), "Campo sem dados!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void LogarDireto(){

        FirebaseAuth fireAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fireAuth.getCurrentUser();


        if(user!=null){
            Intent intent = new Intent(this, MenuActivity.class);
            finish();
            startActivity(intent);
        }
    }

    //
    public void Autenticacao(String email, String senha) {

        final Intent intent = new Intent(this, MenuActivity.class);
        usuarioAuth = FirebaseAuth.getInstance();

        usuarioAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sucessoAuth = true;
                            finish();
                            startActivity(intent);
                            msg("Login efeutado com sucesso!!");

                        } else {
                            msg("Erro ao efetuar o login!");
                        }
                    }
                });
    }

    public void logar(View view)

    {
        boolean resp = verificaEntradaDeDados();

        if (resp) {

            login = editTextEmail.getText().toString();
            senha = editTextSenha.getText().toString();

            Autenticacao(login, senha);
        }
    }

    //fazer cadastro
    public void cadastrar(View view) {
        Intent intent = new Intent(this, Tutorial2Activity.class);
        finish();
        startActivity(intent);
    }


    public void msg(String s) {
        Toast.makeText(this, "" + s, Toast.LENGTH_LONG).show();
    }
}