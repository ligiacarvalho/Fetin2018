package com.fetin.securityapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;


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
    }

}
