package com.fetin.securityapp.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.fetin.securityapp.R;

public class Tutorial2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial2);
    }

    //anterior tutorial
    public void anteriorTutorial(View view)
    {
        Intent intent = new Intent(this,TutorialActivity.class);
        startActivity(intent);
    }

}
