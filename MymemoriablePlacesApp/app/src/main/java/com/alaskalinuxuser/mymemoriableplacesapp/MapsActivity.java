/*  Copyright 2017 by AlaskaLinuxUser (https://thealaskalinuxuser.wordpress.com)
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/
package com.alaskalinuxuser.mymemoriableplacesapp;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String namePlace;
    String latPlace;
    String longPlace;
    String myNewLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        namePlace = "Fairbanks";
        latPlace = "64";
        longPlace = "-147";

        Intent i = getIntent();
        namePlace = i.getStringExtra("namePlace");
        latPlace = i.getStringExtra("latPlace");
        longPlace = i.getStringExtra("longPlace");

        // A simple toast to inform the user of what they clicked on in the list.
        Toast.makeText(getApplicationContext(), "Showing " + namePlace, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        Double lat = Double.parseDouble(latPlace);
        Double lon = Double.parseDouble(longPlace);

        LatLng currentPlace = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions()
                .position(currentPlace)
                .title(namePlace)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory
                                .HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPlace));


        mMap.setOnMapLongClickListener(new OnMapLongClickListener() {


            @Override
            public void onMapLongClick(LatLng arg0) {

                mMap.addMarker(new MarkerOptions()
                        .position(arg0)
                        .title("new location")
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                Double lati = (arg0.latitude);
                Double loni = (arg0.longitude);
                String aLatPlace = lati.toString();
                String aLongPlace = loni.toString();

                Geocoder myGeo = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    List<Address> myAddresses = myGeo.getFromLocation(lati, loni, 1);

                    if (myAddresses != null && myAddresses.size() > 0) {

                        // FOR TESTING //Log.i("WJH", myAddresses.get(0).toString());

                        myNewLocal = myAddresses.get(0).getAddressLine(0) + ", "
                                + myAddresses.get(0).getAddressLine(1);

                    } else {

                        myNewLocal = "";

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent returnIntent = getIntent();
                returnIntent.putExtra("anamePlace",myNewLocal);
                returnIntent.putExtra("alatPlace",aLatPlace);
                returnIntent.putExtra("alongPlace",aLongPlace);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });

    }
}
