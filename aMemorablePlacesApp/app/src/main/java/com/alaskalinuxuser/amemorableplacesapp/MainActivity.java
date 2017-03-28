package com.alaskalinuxuser.amemorableplacesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView theList;
    List latList;
    List longList;
    List myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Defining the list view that I want by id.
        theList = (ListView) findViewById(R.id.theList);

        // Defining an array of names.
        myList = new ArrayList();

        // Defining the array of lattitude.
        latList = new ArrayList();

        // Defining the array of longitude.
        longList = new ArrayList();

        // Only want to do this once....
        if (myList.size() < 1) {

            Log.i("WJH", "Less than one, adding.");

            // Adding names to the array list.
            myList.add("Alaska");
            latList.add("64");
            longList.add("-147");

        }

        Intent i = getIntent();
        String anamePlace = i.getStringExtra("anamePlace");
        String alatPlace = i.getStringExtra("alatPlace");
        String alongPlace = i.getStringExtra("alongPlace");

        if (anamePlace != null) {

            myList.add(anamePlace);
            latList.add(alatPlace);
            longList.add(alongPlace);

        } else {

            Log.i("WJH", "Null anameplace.");

        }

        // Defining an adapter, to adapt my array list to the correct format.
        ArrayAdapter<String> addaptedAray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);

        // Using the adapter to adapt my array list to the defined list view that I declared already.
        theList.setAdapter(addaptedAray);

        // Setting up a listener to "listen" for me to click on something in the list.
        theList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Overriding the generic code that Android uses for list views to do something specific.
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int z, long l) {

                // Converting the integer of "z" to a string with the name of the item clicked in the list.
                String a = (String) myList.get(z);

                // Logging that I tapped said item in the list.
                Log.i("WJH", a);

               // Intent myIntent = new Intent(getApplicationContext(), MapsActivity.class);
                //myIntent.putExtra("namePlace", a);
                //startActivity(myIntent);


            }
        });
    }
}
