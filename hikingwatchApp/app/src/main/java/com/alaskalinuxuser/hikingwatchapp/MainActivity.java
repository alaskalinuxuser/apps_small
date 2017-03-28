package com.alaskalinuxuser.hikingwatchapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    // Let's declare out location managers.
    LocationManager localM;
    String myProvider;
    Location myLocation;
    String myNewLocal;

    // We need to declare out text Views.
    TextView titleText;
    TextView altText;
    TextView bearingText;
    TextView speedText;
    TextView latText;
    TextView longText;
    TextView addressText;

    // And our strings.
    String altString;
    String bearingString;
    String speedString;
    String latString;
    String longString;
    String addressString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Now we define our text views.
        titleText = (TextView)findViewById(R.id.titleView);
        altText = (TextView)findViewById(R.id.altView);
        bearingText = (TextView)findViewById(R.id.bearingView);
        speedText = (TextView)findViewById(R.id.speedView);
        latText = (TextView)findViewById(R.id.latView);
        longText = (TextView)findViewById(R.id.longView);
        addressText = (TextView)findViewById(R.id.addressView);

        changeLocal();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        localM.requestLocationUpdates(myProvider, 5000, 1, this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;

        }

        localM.removeUpdates(this);

    }

    @Override
    public void onLocationChanged(Location location) {

        changeLocal();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void changeLocal() {

        localM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        myProvider = localM.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        myLocation = localM.getLastKnownLocation(myProvider);

        if (myLocation != null) {

            Log.i("location", myLocation.toString());
            // Get the information.
            Double lt = myLocation.getLatitude();
            Double lg = myLocation.getLongitude();
            Double alt = myLocation.getAltitude();
            Float brg = myLocation.getBearing();
            Float spd = myLocation.getSpeed();

            Geocoder myGeo = new Geocoder(getApplicationContext(), Locale.getDefault());

            try {
                List<Address> myAddresses = myGeo.getFromLocation(lt, lg, 1);

                if (myAddresses != null && myAddresses.size() > 0) {

                    Log.i("WJH", myAddresses.get(0).toString());

                    myNewLocal = myAddresses.get(0).getAddressLine(0) + ", "
                            + myAddresses.get(0).getAddressLine(1);

                } else {

                    myNewLocal = "";

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Update our strings.
            longString ="Longitude: " + lg.toString();
            latString = "Lattitude: " + lt.toString();
            altString = "Altitude: " + alt.toString();
            bearingString = "Bearing: " + brg.toString();
            addressString = "Address: " + myNewLocal;
            speedString = "Speed: " + spd.toString();

            // Update the text views with update text.
            latText.setText(latString);
            longText.setText(longString);
            speedText.setText(speedString);
            altText.setText(altString);
            bearingText.setText(bearingString);
            addressText.setText(addressString);

        } else {

            Log.i("location", "Location is null.");

        }




    }

    public void manualUpdate (View view) {

        changeLocal();

    }

}
