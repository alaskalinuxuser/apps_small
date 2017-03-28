package com.alaskalinuxuser.googlemapsdemo;

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
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationManager localM;
    String myProvider;
    Location myLocation;
    String myNewLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        localM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        myProvider = localM.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        myLocation = localM.getLastKnownLocation(myProvider);

        if (myLocation != null) {

            Log.i("WJH", myLocation.toString());

        } else {

            Log.i("WJH", "Location is null.");

        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        localM.requestLocationUpdates(myProvider, 1000, 1, this);

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng fairbanks = new LatLng(64, -147);
        mMap.addMarker(new MarkerOptions().position(fairbanks).title("Marker in Alaska")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fairbanks));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(fairbanks, 5));

    }

    @Override
    public void onLocationChanged(Location location) {

        everyMove();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

        //everyMove();

    }

    @Override
    public void onProviderEnabled(String s) {

        //everyMove();

    }

    @Override
    public void onProviderDisabled(String s) {

        //everyMove();

    }

    public void everyMove() {

        Double lt = myLocation.getLatitude();
        Double lg = myLocation.getLongitude();

        myNewLocal = lt + "," + lg;

        Log.i("WJHlat", lt.toString());
        Log.i("WJHlon", lg.toString());
        Log.i("WJHloc", myNewLocal);

        LatLng currentPlace = new LatLng(lt,lg);

        // This map clear will make sure you only have 1 marker. But I left this blanked out to
        // make a trail of markers....
        //mMap.clear();

        mMap.addMarker(new MarkerOptions().position(currentPlace).title("Current Position.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPlace));

        Geocoder myGeo = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            List<Address> myAddresses = myGeo.getFromLocation(lt, lg, 1);

            if (myAddresses != null && myAddresses.size() > 0) {

                Log.i("WJH", myAddresses.get(0).toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
