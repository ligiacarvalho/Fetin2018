package com.fetin.securityapp.control.Tutorial;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.fetin.securityapp.R;
import com.fetin.securityapp.control.Cadastro.Cadastro1Activity;
import com.fetin.securityapp.control.LoginActivity;
import com.fetin.securityapp.control.Menu.MenuActivity;

public class Tutorial2Activity extends AppCompatActivity {

    private CheckBox termoUso1;
    private CheckBox termoUso2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial2);
        referenciaCheckBox();
    }

    //anterior tutorial
    public void anteriorTutorial(View view)
    {
        Intent intent = new Intent(this,LoginActivity.class);
        finish();
        startActivity(intent);
    }
    public void referenciaCheckBox()
    {
        termoUso1 = findViewById(R.id.checkBoxTermos1);
        termoUso2 = findViewById(R.id.checkBoxTermos2);
    }
    //depois tutorial
    public void depoisTutorial(View view)
    {
        if(termoUso1.isChecked())
        {
            if(termoUso2.isChecked())
            {
                solicitandoPermissoes();
                Intent intent = new Intent(this, Cadastro1Activity.class);
                finish();
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Cadastro feito com sucesso!",Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(getApplicationContext(),"Aceitar Termos de Uso",Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Aceitar Termos de Uso",Toast.LENGTH_LONG).show();

        }
    }

    public void solicitandoPermissoes()
    {
        // requisitando ao usuário que permita o aplicativo enviar SMS
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

}
