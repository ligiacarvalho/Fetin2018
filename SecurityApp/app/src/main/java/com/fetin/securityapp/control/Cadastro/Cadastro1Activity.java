package com.fetin.securityapp.control.Cadastro;

import android.content.Intent;
import android.os.Bundle;
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


public class Cadastro1Activity extends AppCompatActivity {


    private EditText campoNomeCompleto;
    private EditText campoEmail;
    private EditText campoTelefone;
    private EditText campoCPF;
    private EditText campoContatoProximo;
    private EditText campoCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro1);
        referenciaComponentes();

        MascaraTelefone();
        MascaraCPF();
        MascaraContatoProximo();
    }


    public void referenciaComponentes() {

        campoNomeCompleto = findViewById(R.id.CampoNomeCompleto);
        campoEmail = findViewById(R.id.campoEmail);
        campoTelefone = findViewById(R.id.campoTelefone);
        campoCPF = findViewById(R.id.campoCPF);
        campoContatoProximo = findViewById(R.id.campoContatoProximo);
        campoCidade = findViewById(R.id.campoCidade);
    }

    public boolean verificaEntradaDeDados()

    {
        if(campoNomeCompleto.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Campo sem dados!",Toast.LENGTH_LONG).show();
            return false;
        }

        if (campoEmail.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Campo sem dados!",Toast.LENGTH_LONG).show();
            return false;
        }
        if (campoTelefone.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Campo sem dados!",Toast.LENGTH_LONG).show();
            return false;
        }
        if (campoCPF.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Campo sem dados!",Toast.LENGTH_LONG).show();
            return false;
        }
        if (campoContatoProximo.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Campo sem dados!",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    //finalizar cadastro pessoal
    public void finalizarCadastroPessoal(View view)
    {
        Intent intent = new Intent(this, Cadastro2Activity.class);

        boolean resp = verificaEntradaDeDados();

        if(resp){
            getDadosDosCampos();
            startActivity(intent);
        }
    }

    //voltar ao login
    public void voltarLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void getDadosDosCampos()
    {
        //instanciando o usuario
        Usuario novoUsuario = new Usuario();

        novoUsuario.setNome(campoNomeCompleto.getText().toString());
        novoUsuario.setEmail(campoEmail.getText().toString());
        novoUsuario.setTelefone(campoTelefone.getText().toString());
        novoUsuario.setRg(campoCPF.getText().toString());
        novoUsuario.setContatoProximo(campoContatoProximo.getText().toString());
        novoUsuario.setCidade(campoCidade.getText().toString());

        UsuarioDAO dao = new UsuarioDAO();
        dao.inserir(novoUsuario);

        /*
        Toast.makeText(this,"Nome do Usuario = "+novoUsuario.getNome(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"E-MAIL = "+novoUsuario.getEmail(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Telefone = "+novoUsuario.getTelefone(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"RG = "+novoUsuario.getRg(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Contato Proximo = "+novoUsuario.getContatoProximo(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Cidade = "+novoUsuario.getCidade(),Toast.LENGTH_LONG).show();
        */

    }

    //criando mascaras
    public void MascaraTelefone()
    {
        // Referenciando o número o campo do número do Telefone, para pode manipular os dados de lá
        EditText numeroTelefone = findViewById(R.id.campoTelefone);

        // Passando qual é o padrão de telefone que eu vou querer que o usuário digite
        SimpleMaskFormatter mascaraSimplesDeTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");

        // Passando para o objeto que vai gerenciar o formato do telefone, qual é o editText e qual é o formato que queremos
        MaskTextWatcher objetoQueVaiGerenciarOFormatoDoTelefone = new MaskTextWatcher(numeroTelefone,mascaraSimplesDeTelefone);

        // Qualquer alteração de texto no EditText do número do Chip1, o método abaixo vai usar botar
        // o "objetoQueVaiGerenciarOFormatoDoTelefone" para trabalhar.
        numeroTelefone.addTextChangedListener( objetoQueVaiGerenciarOFormatoDoTelefone );


    }

    public void MascaraCPF()
    {
        // Referenciando o número o campo do número do CHIP 1, para pode manipular os dados de lá
        EditText numeroCPF = findViewById(R.id.campoCPF);

        // Passando qual é o padrão de telefone que eu vou querer que o usuário digite
        SimpleMaskFormatter mascaraSimplesDeCPF = new SimpleMaskFormatter("NNN.NNN.NNN-NN");

        // Passando para o objeto que vai gerenciar o formato do telefone, qual é o editText e qual é o formato que queremos
        MaskTextWatcher objetoQueVaiGerenciarOFormatoDoCPF = new MaskTextWatcher(numeroCPF,mascaraSimplesDeCPF);

        // Qualquer alteração de texto no EditText do número do Chip1, o método abaixo vai usar botar
        // o "objetoQueVaiGerenciarOFormatoDoTelefone" para trabalhar.
        numeroCPF.addTextChangedListener( objetoQueVaiGerenciarOFormatoDoCPF );
    }

    public void MascaraContatoProximo()
    {
        // Referenciando o número o campo do número do Telefone, para pode manipular os dados de lá
        EditText contatoProximo = findViewById(R.id.campoContatoProximo);

        // Passando qual é o padrão de telefone que eu vou querer que o usuário digite
        SimpleMaskFormatter mascaraSimplesDeTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");

        // Passando para o objeto que vai gerenciar o formato do telefone, qual é o editText e qual é o formato que queremos
        MaskTextWatcher objetoQueVaiGerenciarOFormatoDoTelefone = new MaskTextWatcher(contatoProximo,mascaraSimplesDeTelefone);

        // Qualquer alteração de texto no EditText do número do Chip1, o método abaixo vai usar botar
        // o "objetoQueVaiGerenciarOFormatoDoTelefone" para trabalhar.
        contatoProximo.addTextChangedListener( objetoQueVaiGerenciarOFormatoDoTelefone );


    }


}
