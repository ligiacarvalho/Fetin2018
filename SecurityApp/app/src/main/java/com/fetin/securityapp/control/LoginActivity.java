package com.fetin.securityapp.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.fetin.securityapp.R;
import com.fetin.securityapp.control.Cadastro1Activity;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //fazer cadastro
    public void cadastrar(View view)
    {
        Intent intent = new Intent(this, Cadastro1Activity.class);
        startActivity(intent);
    }

}
