package com.alaskalinuxuser.addactivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Defining the list view that I want by id.
        final ListView theList = (ListView) findViewById(R.id.theList);

        // Defining an array of names.
        final List myList = new ArrayList();

        // Adding names to the array list.
        myList.add("Dad");
        myList.add("Mom");
        myList.add("Brother");
        myList.add("Sister");
        myList.add("Uncle");
        myList.add("Aunt");
        myList.add("Grandma");
        myList.add("Grandpa");
        myList.add("First Cousin");
        myList.add("Second Cousin");
        myList.add("Double Cousin");
        myList.add("Friend");

        // Defining an adapter, to adapt my array list to the correct format.
        ArrayAdapter<String> addaptedAray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);

        // Using the adapter to adapt my array list to the defined list view that I declared already.
        theList.setAdapter(addaptedAray);

        // Setting up a listener to "listen" for me to click on something in the list.
        theList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Overriding the generic code that Android uses for list views to do something specific.
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int z, long l) {

                /*
                 * My method, that I came up with during the class....
                 */
                // Converting the integer of "z" to a string with the name of the item clicked in the list.
                String a = (String) myList.get(z);

                // Logging that I tapped said item in the list.
                Log.i("tapped", a);

                Intent myIntent = new Intent(getApplicationContext(), Main2Activity.class);
                myIntent.putExtra("fromfirst", a);
                startActivity(myIntent);


            }
        });
    }
}
