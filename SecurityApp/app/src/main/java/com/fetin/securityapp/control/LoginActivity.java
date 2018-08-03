package com.fetin.securityapp.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fetin.securityapp.R;
import com.fetin.securityapp.model.TestDAO;
import com.fetin.securityapp.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextSenha;

    // objeto para fazer manipulações no banco de dados do nó usuário
    private TestDAO userDao;
    // Objeto para gerenciar o banco de dados
    private DatabaseReference referenciaDoBanco;
    // Objeto para gerenciar os usuários
    private FirebaseAuth usuario;

    private String login, senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        referenciaComponentes();

        login = editTextEmail.getText().toString();
        senha = editTextSenha.getText().toString();
    }

    public void referenciaComponentes()
    {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
    }
    public boolean verificaEntradaDeDados()
    {
        if(editTextEmail.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Campo sem dados!",Toast.LENGTH_LONG).show();
            return false;
        }

        if (editTextSenha.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Campo sem dados!",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void logar(View view)
    {
        Intent intent = new Intent(this, MenuActivity.class);
        boolean resp = verificaEntradaDeDados();

        if(resp){
            startActivity(intent);
        }
    }

    //fazer cadastro
    public void cadastrar(View view)
    {
        Intent intent = new Intent(this, Cadastro1Activity.class);
        startActivity(intent);
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
