package com.fetin.securityapp.control.Cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fetin.securityapp.R;

import com.fetin.securityapp.control.LoginActivity;
import com.fetin.securityapp.model.Usuario;
import com.fetin.securityapp.model.Dao.UsuarioDAO;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Cadastro1Activity extends AppCompatActivity {


    private EditText campoNomeCompleto;
    private EditText campoEmail;
    private EditText campoTelefone;
    private EditText campoCPF;
    private EditText campoContatoProximo;
    private EditText campoCidade;
    private EditText campoSenha;
    private boolean sucessoAuth = false;

    private FirebaseAuth usuarioAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro1);
        referenciaComponentes();

        MascaraTelefone();
        MascaraCPF();
        MascaraContatoProximo();

        metodoParaTeste();

    }


    public void metodoParaTeste() {
        campoNomeCompleto.setText("Nome Teste");
        //campoEmail.setText("email@teste");
        campoTelefone.setText("35999999999");
        campoCPF.setText("11111111125");
        campoContatoProximo.setText("123");
        campoCidade.setText("123");
        campoSenha.setText("0123456789");
    }

    public void referenciaComponentes() {

        campoNomeCompleto = findViewById(R.id.CampoNomeCompleto);
        campoEmail = findViewById(R.id.campoEmail);
        campoTelefone = findViewById(R.id.campoTelefone);
        campoCPF = findViewById(R.id.campoCPF);
        campoContatoProximo = findViewById(R.id.campoContatoProximo);
        campoCidade = findViewById(R.id.campoCidade);
        campoSenha = findViewById(R.id.campoSenha);
    }

    public boolean verificaEntradaDeDados()

    {
        if (campoNomeCompleto.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Campo do nome está vazio ou inválido!", Toast.LENGTH_LONG).show();
            campoNomeCompleto.requestFocus();
            return false;
        }

        if (campoEmail.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Campo do e-mail está vazio  ou inválido!", Toast.LENGTH_LONG).show();
            campoEmail.requestFocus();
            return false;
        }
        if (campoTelefone.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Campo do telefone está vazio ou inválido!", Toast.LENGTH_LONG).show();
            campoTelefone.requestFocus();
            return false;
        }
        if (campoCPF.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Campo do cpf está vazio ou inválido!", Toast.LENGTH_LONG).show();
            campoCPF.requestFocus();
            return false;
        }
        if (campoContatoProximo.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Campo do contato próximo, vazio ou inválido!", Toast.LENGTH_LONG).show();
            campoContatoProximo.requestFocus();
            return false;
        }
        if (campoSenha.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Campo da senha está vazia ou inválido!", Toast.LENGTH_LONG).show();
            campoSenha.requestFocus();
            return false;
        }

        return true;
    }

    //finalizar cadastro pessoal
    public void finalizarCadastroPessoal(View view) {
        boolean resp = verificaEntradaDeDados();

        if (resp == true) {

            Usuario novoUsuario = getDadosDosCampos();

            Autenticacao(novoUsuario.getEmail(), novoUsuario.getSenha());

        }
    }

    //voltar ao login
    public void voltarLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public Usuario getDadosDosCampos() {
        //instanciando o usuario
        Usuario novoUsuario = new Usuario();

        novoUsuario.setNome(campoNomeCompleto.getText().toString());
        novoUsuario.setEmail(campoEmail.getText().toString());
        novoUsuario.setTelefone(campoTelefone.getText().toString());
        novoUsuario.setCPF(campoCPF.getText().toString());
        novoUsuario.setContatoProximo(campoContatoProximo.getText().toString());
        novoUsuario.setCidade(campoCidade.getText().toString());
        novoUsuario.setSenha(campoSenha.getText().toString());

        UsuarioDAO.getInstance();
        UsuarioDAO.dao.inserir(novoUsuario);

        return novoUsuario;

    }

    //
    public void Autenticacao(String email, String senha) {

        final Intent intent = new Intent(this, Cadastro2Activity.class);
        usuarioAuth = FirebaseAuth.getInstance();

        usuarioAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(Cadastro1Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            sucessoAuth = true;
                            startActivity(intent);

                        } else {
                            sucessoAuth = false;
                            msg("E-mail inválido! Digite novamente.");
                        }

                    }
                });

    }

    //criando mascaras
    public void MascaraTelefone() {
        // Referenciando o número o campo do número do Telefone, para pode manipular os dados de lá
        EditText numeroTelefone = findViewById(R.id.campoTelefone);

        // Passando qual é o padrão de telefone que eu vou querer que o usuário digite
        SimpleMaskFormatter mascaraSimplesDeTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");

        // Passando para o objeto que vai gerenciar o formato do telefone, qual é o editText e qual é o formato que queremos
        MaskTextWatcher objetoQueVaiGerenciarOFormatoDoTelefone = new MaskTextWatcher(numeroTelefone, mascaraSimplesDeTelefone);

        // Qualquer alteração de texto no EditText do número do Chip1, o método abaixo vai usar botar
        // o "objetoQueVaiGerenciarOFormatoDoTelefone" para trabalhar.
        numeroTelefone.addTextChangedListener(objetoQueVaiGerenciarOFormatoDoTelefone);


    }

    public void MascaraCPF() {
        // Referenciando o número o campo do número do CHIP 1, para pode manipular os dados de lá
        EditText numeroCPF = findViewById(R.id.campoCPF);

        // Passando qual é o padrão de telefone que eu vou querer que o usuário digite
        SimpleMaskFormatter mascaraSimplesDeCPF = new SimpleMaskFormatter("NNN.NNN.NNN-NN");

        // Passando para o objeto que vai gerenciar o formato do telefone, qual é o editText e qual é o formato que queremos
        MaskTextWatcher objetoQueVaiGerenciarOFormatoDoCPF = new MaskTextWatcher(numeroCPF, mascaraSimplesDeCPF);

        // Qualquer alteração de texto no EditText do número do Chip1, o método abaixo vai usar botar
        // o "objetoQueVaiGerenciarOFormatoDoTelefone" para trabalhar.
        numeroCPF.addTextChangedListener(objetoQueVaiGerenciarOFormatoDoCPF);
    }

    public void MascaraContatoProximo() {
        // Referenciando o número o campo do número do Telefone, para pode manipular os dados de lá
        EditText contatoProximo = findViewById(R.id.campoContatoProximo);

        // Passando qual é o padrão de telefone que eu vou querer que o usuário digite
        SimpleMaskFormatter mascaraSimplesDeTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");

        // Passando para o objeto que vai gerenciar o formato do telefone, qual é o editText e qual é o formato que queremos
        MaskTextWatcher objetoQueVaiGerenciarOFormatoDoTelefone = new MaskTextWatcher(contatoProximo, mascaraSimplesDeTelefone);

        // Qualquer alteração de texto no EditText do número do Chip1, o método abaixo vai usar botar
        // o "objetoQueVaiGerenciarOFormatoDoTelefone" para trabalhar.
        contatoProximo.addTextChangedListener(objetoQueVaiGerenciarOFormatoDoTelefone);

    }


    public void msg(String s) {
        Toast.makeText(this, "Senha = " + s, Toast.LENGTH_LONG).show();
    }

}
