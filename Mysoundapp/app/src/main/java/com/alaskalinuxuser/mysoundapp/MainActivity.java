package com.alaskalinuxuser.mysoundapp;

import android.content.Context;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.view.View;
import android.widget.SeekBar;
import android.util.Log;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    // To allow us to play media.
    MediaPlayer myPlayer;
    // To allow us to play with Audio.
    AudioManager myAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Defining the song that we want the media player to interact with.
        myPlayer = MediaPlayer.create(this, R.raw.thunder);

        // Define where to get the audio volumes.
        myAudio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Define the maximum volume
        int maxVol = myAudio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        // Define the current volume
        int curVol = myAudio.getStreamVolume(AudioManager.STREAM_MUSIC);

        // Define the maximum length.
        int maxLength = myPlayer.getDuration();

        // Defining the seekbar wich will act as a volume control
        SeekBar volumeBar = (SeekBar) findViewById(R.id.seekBar);

        // Define the maximum value and current value for seekbar and volume.
        volumeBar.setMax(maxVol);
        volumeBar.setProgress(curVol);

        // Define the seekbar which will act as a progress of the file length.
        final SeekBar lengthBar = (SeekBar) findViewById(R.id.seekBar2);

        // Define the maximum value for seekbar and length.
        lengthBar.setMax(maxLength);

        /* TAKE 1:
        * Define the current length using a timer, per the course.
        * This method was discussed and presented in the course, but it
        * causes a terrible stutter in the audio. */
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                lengthBar.setProgress(myPlayer.getCurrentPosition());
            }
        }, 0, 1000);
        /* End TAKE 1
        */

        /* TAKE 2: This was another idea, from scrounging stack exchange, however,
        * it also stutters just as bad as the first one.

        //make it run with it's own call to run itself again (this).
        Runnable runBar = new Runnable() {
            @Override
            public void run(){

                // Set the slider position as time progresses.
                lengthBar.setProgress(myPlayer.getCurrentPosition());

                // Start a new handler to call itself over and over again....
                Handler inLoop = new Handler();

                inLoop.postDelayed(this, 1000); // <-- delay time in miliseconds.
            }
        };

        // To make it run the first time.
        Handler h = new Handler();
        h.postDelayed(runBar, 1000); // <-- delay time in miliseconds.
        * End TAKE 2
        */


        // Now we will code our volume bar.
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // Overides for what to do when the user starts touching the bar. Do this, which is nothing.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // Overides for what to do when the user stops touching the bar. Do this, which is nothing.
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            // Overides for what to do when the user changes the bar. Do this, which is where the value comes from.
            @Override
            public void onProgressChanged(SeekBar seekBar, int percentVolume, boolean userInteraction) {

                // Currently we are just logging the change in percentage to the logs.
                Log.i("valueseekbar", Integer.toString(percentVolume));

                // Now we are changing the value of the volume. Remember that this needs all three
                // variables, the who, the int, the boolean....
                myAudio.setStreamVolume(AudioManager.STREAM_MUSIC, percentVolume, 0);
            }
        });

        // Now we will code our progress bar.
        lengthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // Overides for what to do when the user starts touching the bar. Do this, which is nothing.

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                myPlayer.pause();

            }

            // Overides for what to do when the user stops touching the bar. Do this, which is nothing.
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                myPlayer.start();

            }

            // Overides for what to do when the user changes the bar. Do this, which is where the value comes from.
            @Override
            public void onProgressChanged(SeekBar seekBar, int percentLength, boolean userInteraction) {

                // Now we are changing the value of the length. Remember that this needs just the
                // integer for what it should be now.
                myPlayer.seekTo(percentLength);
            }
        });

    }

    // Control the media player to play the sound.
    public void play(View playView) {

        myPlayer.start();

    }

    // Control the media player to stop the sound.
    public void pause(View pauseView) {

        myPlayer.pause();

    }
}