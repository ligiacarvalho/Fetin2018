package com.fetin.securityapp.control.Tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.fetin.securityapp.R;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
    }

    //proximo tutorial
    public void proximoTutorial(View view)
    {
        Intent intent = new Intent(this,Tutorial2Activity.class);
        startActivity(intent);
    }
    //pular tutorial
    public void pularTutorial(View view)
    {
        Intent intent = new Intent(this,Tutorial2Activity.class);
        startActivity(intent);
    }

}
