package com.alaskalinuxuser.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        SharedPreferences myPrefs = this.getSharedPreferences("com.alaskalinuxuser.sharedpref", Context.MODE_PRIVATE);
        myPrefs.edit().putString("myName", "AKLU").apply();

        String userName = myPrefs.getString("myName", "");

        Log.i("WJH", userName);

    }
}
