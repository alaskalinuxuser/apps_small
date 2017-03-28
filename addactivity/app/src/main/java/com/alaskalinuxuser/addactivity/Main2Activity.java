package com.alaskalinuxuser.addactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent i = getIntent();
        String a = i.getStringExtra("fromfirst");

        // A simple toast to inform the user of what they clicked on in the list.
        Toast.makeText(getApplicationContext(), "Hi " + a , Toast.LENGTH_LONG).show();

    }

    public void backMain (View view) {

        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);

    }
}
