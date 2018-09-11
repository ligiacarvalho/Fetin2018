package com.fetin.securityapp.control.SegundoPlano;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fetin.securityapp.control.RoubadoActivity;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, RoubadoActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}