package com.fetin.securityapp.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        EditText campoNomeComleto = findViewById(R.id.CampoNomeCompleto);

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(campoNomeComleto.getText().toString());

        Toast.makeText(this,"Nome do Usuario = "+novoUsuario.getNome(),Toast.LENGTH_LONG).show();

    }


}
