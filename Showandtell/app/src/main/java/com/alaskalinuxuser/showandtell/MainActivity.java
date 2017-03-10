package com.alaskalinuxuser.showandtell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    TextView hereText;
    TextView goneText;
    Button theButton;
    boolean isHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isHidden = false;

        theButton = (Button)findViewById(R.id.onlyButton);

        hereText = (TextView)findViewById(R.id.showTextA);
        goneText = (TextView)findViewById(R.id.showTextB);

        hereText.setVisibility(View.VISIBLE);
        goneText.setVisibility(View.INVISIBLE);
    }

    public void showAndTell(View view) {

        if (isHidden){

            hereText.setVisibility(View.VISIBLE);
            goneText.setVisibility(View.INVISIBLE);
            theButton.setText("Hide!");
            isHidden = false;

        } else {

            hereText.setVisibility(View.INVISIBLE);
            goneText.setVisibility(View.VISIBLE);
            theButton.setText("Show!");
            isHidden = true;
        }

    }

}
