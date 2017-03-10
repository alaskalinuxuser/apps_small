package com.alaskalinuxuser.videoview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
// Import the widget for video views.
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the video view by id so we can use it.
        VideoView myVideo = (VideoView) findViewById(R.id.videoView);

        // To set the path to the video. Sample.mp4 is in the "raw" folder.
        // You can use http://online.path.to.your.video also.
        myVideo.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sample);

        // To create the media controller (play, pause, etc.)
        MediaController myController = new MediaController(this);

        // To link the media controller to the video view.
        myController.setAnchorView(myVideo);

        // To link the video view to the media controller.
        myVideo.setMediaController(myController);

        // To autoplay on opening.
        myVideo.start();

    }
}
