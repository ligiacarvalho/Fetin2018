package com.fetin.securityapp.control.SegundoPlano;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.fetin.securityapp.control.RoubadoActivity;

import java.util.List;

public class BloqueioService extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private static boolean parou;
    private Context ctx;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {


            while (parou == false) {

                if (isInBackground()) {

                        restoreApp();
                }

            }


        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Log.i("Msg", "Celular Rooubado!");
        parou = false;
        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        ctx = this;

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {

        parou = true;

    }

    //chamar de novo
    private boolean isInBackground() {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return (!ctx.getApplicationContext().getPackageName().equals(componentInfo.getPackageName()));
    }

    private void restoreApp() {

        Intent i = new Intent(ctx, RoubadoActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.i("Travou","Chamou a função restore");
        ctx.startActivity(i);

    }
}