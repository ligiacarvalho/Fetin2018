package com.fetin.securityapp.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fetin.securityapp.R;
import com.fetin.securityapp.model.TestDAO;


public class LoginActivity extends AppCompatActivity {

    // objeto para fazer manipulações no banco de dados do nó usuário
    private TestDAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        start();
    }

    //fazer cadastro
    public void cadastrar(View view)
    {
        Intent intent = new Intent(this, Cadastro1Activity.class);
        startActivity(intent);
    }

    public void start()
    {
        // inserir aqui tudo que deve ser feito, ao iniciar a tela de login
    }








    // Autenticação do usuário por e-mail e senha, usando o FireBase(OBS: deixei comentado, pois ainda não é o obj)
    /*
    public void autenticarUsuarioPorEmailESenha(String email, String senha)
    {
        // Autenticando um usuário com email e senha
        userDao.getUsuario().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // a autenticação deu certo
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this,"Usuário autenticado com sucesso!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Erro na autenticação!",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    */


}
