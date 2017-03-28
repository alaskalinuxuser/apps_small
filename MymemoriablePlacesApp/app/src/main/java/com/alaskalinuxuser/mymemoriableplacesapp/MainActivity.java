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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    ListView theList;
    List latList;
    List longList;
    List myList;
    String anamePlace;
    String alatPlace;
    String alongPlace;
    ArrayAdapter<String> addaptedAray;

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

            // Adding names to the array list.
            myList.add("Alaska");
            latList.add("64");
            longList.add("-147");

            Log.i("WJH", "Adding Alaska to blank list.");

        }

        // Defining an adapter, to adapt my array list to the correct format.
        addaptedAray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);

        // Using the adapter to adapt my array list to the defined list view that I declared already.
        theList.setAdapter(addaptedAray);

        // Setting up a listener to "listen" for me to click on something in the list.
        theList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Overriding the generic code that Android uses for list views to do something specific.
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int z, long l) {

                // Converting the integer of "z" to a string with the name of the item clicked in the list.
                String a = (String) myList.get(z);
                String b = (String) latList.get(z);
                String c = (String) longList.get(z);

                // Logging that I tapped said item in the list.
                Log.i("WJH", a + b + c);


                Intent myIntent = new Intent(getApplicationContext(), MapsActivity.class);
                myIntent.putExtra("namePlace", a);
                myIntent.putExtra("latPlace", b);
                myIntent.putExtra("longPlace", c);
                startActivityForResult(myIntent, 1);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                anamePlace = data.getStringExtra("anamePlace");
                alatPlace = data.getStringExtra("alatPlace");
                alongPlace = data.getStringExtra("alongPlace");
                Log.i("WJH", anamePlace + alatPlace + alongPlace);

                if (anamePlace != null) {

                    myList.add(anamePlace);
                    latList.add(alatPlace);
                    longList.add(alongPlace);

                    addaptedAray.notifyDataSetChanged();

                } else {

                    Log.i("WJH", "anameplace is null.");

                }

            }

            if (resultCode == Activity.RESULT_CANCELED) {

                Log.i("WJH", "There was no result.");

            }
        }
    }//onActivityResult

}
