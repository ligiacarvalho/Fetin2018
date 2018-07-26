package com.fetin.securityapp.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.fetin.securityapp.R;

import com.fetin.securityapp.model.Usuario;


public class Cadastro1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro1);
    }

    //finalizar cadastro pessoal
    public void finalizarCadastroPessoal(View view)
    {
        Intent intent = new Intent(this, Cadastro2Activity.class);
        startActivity(intent);
        getDadosDosCampos();
    }

    public void getDadosDosCampos()
    {

        EditText campoNomeCompleto = findViewById(R.id.CampoNomeCompleto);
        EditText campoEmail = findViewById(R.id.campoEmail);
        EditText campoTelefone = findViewById(R.id.campoTelefone);
        EditText campoRG = findViewById(R.id.campoRG);
        EditText campoContatoProximo = findViewById(R.id.campoContatoProximo);
        EditText campoCidade = findViewById(R.id.campoCidade);

        //instanciando o usuario
        Usuario novoUsuario = new Usuario();

        novoUsuario.setNome(campoNomeCompleto.getText().toString());
        novoUsuario.setEmail(campoEmail.getText().toString());
        novoUsuario.setTelefone(campoTelefone.getText().toString());
        novoUsuario.setRg(campoRG.getText().toString());
        novoUsuario.setContatoProximo(campoContatoProximo.getText().toString());
        novoUsuario.setCidade(campoCidade.getText().toString());

        /*
        Toast.makeText(this,"Nome do Usuario = "+novoUsuario.getNome(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"E-MAIL = "+novoUsuario.getEmail(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Telefone = "+novoUsuario.getTelefone(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"RG = "+novoUsuario.getRg(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Contato Proximo = "+novoUsuario.getContatoProximo(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Cidade = "+novoUsuario.getCidade(),Toast.LENGTH_LONG).show();
        */



    }


}
