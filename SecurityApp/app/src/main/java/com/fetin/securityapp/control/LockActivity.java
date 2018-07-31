package com.fetin.securityapp.control;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;

import com.fetin.securityapp.R;

public class LockActivity extends AppCompatActivity{


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);


        super.onCreate(savedInstanceState);

        final KeyguardManager.KeyguardLock unLock = km.newKeyguardLock("unLock");
        unLock.disableKeyguard();

        setContentView(R.layout.activity_lock);


    }


}
