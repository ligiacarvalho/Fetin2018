package com.fetin.securityapp.model;

import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;

public class GpsManager extends AppCompatActivity{

    private transient LocationManager locationManager;
    private transient LocationListener locationListener;

    /*
    public void getLocalization() {

        Looper.prepare();
        locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        long minTime = 0;
        float minDistance = 0;
        String provider;
        if (Geolocation.LEVEL_EXACT.equals(level)) {
            provider = LocationManager.GPS_PROVIDER;
        } else {
            provider = LocationManager.NETWORK_PROVIDER;
        }
        locationManager.requestLocationUpdates(provider, minTime, minDistance, locationListener);
        Looper.loop();

    }

    */
}
