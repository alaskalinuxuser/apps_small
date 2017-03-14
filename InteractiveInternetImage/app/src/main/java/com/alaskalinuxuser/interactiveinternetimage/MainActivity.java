package com.alaskalinuxuser.interactiveinternetimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static android.R.attr.bitmap;

public class MainActivity extends AppCompatActivity {

    ImageView myView;
    Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = (ImageView)findViewById(R.id.imageView);
        myButton = (Button)findViewById(R.id.myButton);
    }

    public class DownloadTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection)url.openConnection();

                // To download in one "go" as Rob says.
                urlConnection.connect();

                // To grab the whole thing at once.
                InputStream inStream = urlConnection.getInputStream();

                Bitmap myBit = BitmapFactory.decodeStream(inStream);

                return myBit;

            }
            catch(Exception e) {

                e.printStackTrace();

                Log.i("WJH", "Failed");

                return null;

            }


        }

    }

    public void clickedButton (View thisButton) {

        DownloadTask task = new DownloadTask();

        try {

            Bitmap myImage = task.execute("https://upload.wikimedia.org/wikipedia/commons/thumb/d/db/Android_robot_2014.svg/75px-Android_robot_2014.svg.png").get();

            myView.setImageBitmap(myImage);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        Log.i("WJH", "logging click....");

    }
}
