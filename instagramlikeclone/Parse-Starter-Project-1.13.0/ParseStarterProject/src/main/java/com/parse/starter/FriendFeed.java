package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

public class FriendFeed extends AppCompatActivity {

    // Declare my layout.
    LinearLayout theFeedLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the extra info we passed earlier.
        Intent i = getIntent();
        // Put it in a string.
        String userName = i.getStringExtra("theName");
        // Set the title bar with the string.
        setTitle(userName +"'s Pictures");
        // Define my layout.
        theFeedLayout = (LinearLayout)findViewById(R.id.friendFeedLayout);
        // Add the floating action button.
        FloatingActionButton fabFFBack = (FloatingActionButton) findViewById(R.id.fabFFBack);
        fabFFBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // So, find a particular object. We first need to query it.
        ParseQuery<ParseObject> anotherQuery = ParseQuery.getQuery("myImages");
        // Where it is for the selected user only.
        anotherQuery.whereEqualTo("username", userName);
        // Put them in order.
        anotherQuery.orderByDescending("picture");

        // Now let's find the pictures.
        anotherQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                // If statement.
                if (e == null) {
                    // If exception was empty, then nothing bad happened, it must have worked.
                    // Let's log how many objects there are.
                    Log.i("WJH", "found " + objects.size() + " objects.");
                    // Then let's log all those objects. Providing there are more than 0 objects.
                    if (objects.size() > 0) {

                        // For each objects, loop so we can parse the object.
                        for (ParseObject object : objects) {

                            // Get the parse file.
                            ParseFile downFile = (ParseFile) object.get("picture");

                            // Download it in the background.
                            downFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    // Turn that byte array into a bitmap.
                                    Bitmap downImage = BitmapFactory.decodeByteArray(data, 0, data.length);

                                    // Define a new imageview.
                                    ImageView iV = new ImageView(getApplicationContext());

                                    // Set our image view with our downloaded bitmap.
                                    iV.setImageBitmap(downImage);

                                    // And set some parameters.
                                    iV.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                    ));
                                    // And add the views to the layout.
                                    theFeedLayout.addView(iV);

                                }
                            });

                        }
                    }

                } else {
                    // If exception was not empty, something went wrong. Let's log it.
                    Log.i("WJH", "find everything Failed " + e);
                }

            }
        });
    }

}
