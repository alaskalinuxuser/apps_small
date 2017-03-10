package com.example.alaskalinuxuser.toastname;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public void myName (View view) {
        EditText myUsername = (EditText) findViewById(R.id.editText3);

        Toast.makeText(getApplicationContext(), "Hi " + myUsername.getText().toString() , Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
