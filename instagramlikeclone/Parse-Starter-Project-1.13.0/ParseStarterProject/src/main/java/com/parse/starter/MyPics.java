package com.parse.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MyPics extends AppCompatActivity {

    // Declare bitmaps and etc.
    AlertDialog.Builder aD;
    Bitmap myBitmap;
    Boolean bitmapReady;
    LinearLayout myPicLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Define layouts and builders, etc.
        aD = new AlertDialog.Builder(this);
        myPicLayout = (LinearLayout)findViewById(R.id.myPicLayout);
        setTitle(ParseUser.getCurrentUser().getUsername() + "'s Pictures");
        // Call method to get pictures.
        getMyPics();
        // Floating action button to add pictures.
        FloatingActionButton fabAddPic = (FloatingActionButton) findViewById(R.id.fabAddPic);
        fabAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Here is what the popup will do.
                aD
                        .setIcon(android.R.drawable.ic_menu_camera)
                        .setTitle("Add a picture!")
                        .setMessage("Do you want to take a new picture, or get one from the gallery?")
                        .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // Call the intent for the gallery.
                                Intent fromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                // And start that intent for the result number 1.
                                startActivityForResult(fromGallery, 1);

                            }
                        })
                        .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // Call the intent for the camera.
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // And start that intent for the result number 2.
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, 2);
                                }

                            }
                        })
                        .show(); // Make sure you show your popup or it wont work very well!
            }
        });
    }


    @Override // Listen for the results from intents.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Set our boolean as false, as the bitmap is not ready yet.
        bitmapReady = false;

        // If it is result number 1, and it was ok, and they chose something, then...
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            // Get the uri.
            Uri myChosenImage = data.getData();

            // Try in case it fails.
            try {

                // Make a bitmap from the uri.
                myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myChosenImage);

                // Set our boolean to true, because we have a bitmap ready.
                bitmapReady = true;


            } catch (IOException e) {
                e.printStackTrace();
            }

            // But if it is result number 2, and it is okay, and there is data, then....
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

             try { // In case it fails.

                // Get the extras (a small thumbnail in this case).
                Bundle extras = data.getExtras();
                // Set our bitmap to that extra.
                myBitmap = (Bitmap) extras.get("data");

                 // Set our boolean to true, because we have a bitmap ready.
                 bitmapReady = true;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // If that bitmap is ready, then....
        if (bitmapReady) {

            // First, let's get the date and time by setting the format and then pulling it.
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());

            //// Get a stream of bytes ready for transmitting.
            ByteArrayOutputStream chosenStream = new ByteArrayOutputStream();

            // compress my bitmap into that stream.
            myBitmap.compress(Bitmap.CompressFormat.PNG, 50, chosenStream);

            // send our stream to a byte array.
            // Remember, any file can be turned into a byte array! Very useful!
            byte[] byteArray = chosenStream.toByteArray();

            // And we convert that array into a parse file.
            ParseFile pFile = new ParseFile(currentDateandTime + ".png", byteArray);

            // Select which parse object to save it to.
            ParseObject object = new ParseObject("myImages");

            // Make sure we save it for our current user!
            object.put("username", ParseUser.getCurrentUser().getUsername());

            // And save our object.
            object.put("picture", pFile);

            // Add access control lists.
            ParseACL myACL = new ParseACL();
            // Set it so the public can view the picture.
            myACL.setPublicReadAccess(true);
            // Set it so the user can read/write/edit picture.
            myACL.setWriteAccess(ParseUser.getCurrentUser(), true);
            // Apply this acl to your picture.
            object.setACL(myACL);

            // And let's save that in the background.
            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    // If statement.
                    if (e == null) {
                        // If exception was empty, then nothing bad happened, it must have worked.
                        Log.i("WJH", "upload image Succeeded");
                    } else {
                        // If exception was not empty, something went wrong. Let's log it.
                        Log.i("WJH", "upload image Failed " + e);
                    }

                }

            });
        } else { // If the bitmap is not ready, then....

            // no picture.
            Log.i("WJH", "No bitmap was chosen or taken or ready....");

        }

            // Need some way to refresh the pictures....
            // This doesn't work with slow connections....
            myPicLayout.removeAllViews();
            getMyPics();

    }

    public void getMyPics () {
        // So, how to find a particular object? We first need to query it.
        ParseQuery<ParseObject> anotherQuery = ParseQuery.getQuery("myImages");
        // Where it is your pictures, and in order.
        anotherQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        anotherQuery.orderByDescending("picture");

        // Now let's find stuff.
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
                                    // Add the views to your layout.
                                    myPicLayout.addView(iV);

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
