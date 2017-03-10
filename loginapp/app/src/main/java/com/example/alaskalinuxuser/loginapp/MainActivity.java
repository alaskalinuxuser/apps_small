package com.example.alaskalinuxuser.loginapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void login (View view) {
        EditText myUsername = (EditText) findViewById(R.id.username);
        EditText myPassword = (EditText) findViewById(R.id.password);

        Log.i("username", myUsername.getText().toString());
        Log.i("password", myPassword.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
