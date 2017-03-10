package com.alaskalinuxuser.downloadingwebstuff;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

        public class DownloadTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... urls) {

                String result = "";
                URL url;
                HttpURLConnection urlConnection = null;

                try {

                    url = new URL(urls[0]);

                    urlConnection = (HttpURLConnection)url.openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader reader = new InputStreamReader(in);

                    int data = reader.read();

                    while (data != -1) {

                        char current = (char) data;

                        result += current;

                        data = reader.read();

                    }

                    return result;

                }
                catch(Exception e) {

                    e.printStackTrace();

                    return "Failed";

                }


            }

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            DownloadTask task = new DownloadTask();
            String result = null;

            try {

                result = task.execute("https://thealaskalinuxuser.wordpress.com/").get();

            } catch (InterruptedException e) {

                e.printStackTrace();

            } catch (ExecutionException e) {

                e.printStackTrace();

            }

            Log.i("Contents Of URL", result);

        }

    }

    /* public class DownloadingStuff extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            // Log what was the parameter.
            Log.i("url", params[0]);

            // define some variables.
            String results = "";
            URL url;
            HttpURLConnection urlConnection = null;

            // Try this.
            try {

                // Get our URL from our parameters.
                url = new URL(params[0]);
                // Make our connection.
                urlConnection = (HttpURLConnection) url.openConnection();
                // Stream our url into the connection.
                InputStream inPut = urlConnection.getInputStream();
                // Now we need a way to read it, enter the reader.
                InputStreamReader readMe = new InputStreamReader(inPut);
                // But it is read one bit at a time, so we need a place holder.
                int inputData = readMe.read();
                // So we can have a while loop to read it.
                while (inputData != -1) {

                    // We need the charactors.
                    char theData = (char) inputData;

                    // Now we append that charactor to the results.
                    results += theData;

                    // And we tell it to move to the next charactor.
                    inputData = readMe.read();

                }

                // And we can finally return the results.
                return results;

                // What to do if something goes wrong.
            } catch (Exception e) {

                e.printStackTrace();

                // return failure.
                return "no worky.";

            }

        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadingStuff myWebsite = new DownloadingStuff();

        String result = null;

        try { result = myWebsite.execute("https://thealaskalinuxuser.wordpress.com/").get();
        } catch (Exception e) {

            e.printStackTrace();

        }

        Log.i("Results", result);
    }
} */
