package com.alaskalinuxuser.crossfade;

import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void crossFade (View view){

        /* First things first, transition between all of the
        * images in the tansition.xml file. In this case, there
        * are only two.
        */
        // Define the image view that is used
        ImageView picture = (ImageView) findViewById(R.id.imageView);
        // Call the transition
        TransitionDrawable drawable = (TransitionDrawable) picture.getDrawable();
        // Give it a time period
        drawable.startTransition(1500);


    }

    public void doubleTrouble (View myView){

        /* Now we will have one image slide off the screen
        * and another image slide into it's place. Note that one
        * of the images is offscreen in the on create methode below.
        */
        // Define the image views to use.
        ImageView firstPic = (ImageView) findViewById(R.id.imageView2);
        ImageView secondPic = (ImageView) findViewById(R.id.imageView3);
        // Set the alpha (not really needed here, was used in other exercises.
        // Used here to make them "dimmer" at half brightness.
        firstPic.setAlpha(0.5f);
        secondPic.setAlpha(0.5f);
        // Call the translation (movement) and set the duration.
        firstPic.animate().translationXBy(-1000f).setDuration(1500);
        secondPic.animate().translationXBy(-1000f).setDuration(1500);
    }

    public void spinner (View aView){
        // A simple trick to spin the image.
        // Define the image view to use.
        ImageView firstPic = (ImageView) findViewById(R.id.imageView2);
        // Rotate the image this many degrees in this much time.
        firstPic.animate().rotation(360f).setDuration(1500);

    }

    public void sizeMe (View badView){
        // A simple trick to "grow" an image.
        // Define the image view to use.
        ImageView borris = (ImageView) findViewById(R.id.imageView4);
        // Tell that image to animate to be twice it's size.
        borris.animate().scaleX(2.0f).scaleY(2.0f).setDuration(1500);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Note that this image view is set off screen from the app start.
        // Define the image view to use.
        ImageView firstPic = (ImageView) findViewById(R.id.imageView2);
        // Set that view off screen.
        firstPic.setTranslationX(1000f);
    }
}
