/*  Copyright 2017 by AlaskaLinuxUser (https://thealaskalinuxuser.wordpress.com)
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/
package com.alaskalinuxuser.readerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    // Declare my variables.
    ListView theList;
    ArrayList<String> titleList;
    ArrayList<String> urlList;
    SQLiteDatabase firstDatabaseEver;
    ArrayAdapter<String> addaptedAray;
    String eachURL;

    // Set up my background asyncronous task....
    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override // Do this in the background.
        protected String doInBackground(String... urls) {

            // Set my local variables.
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            // Try this because it may fail.
            try {

                // Use the first string passed as the URL.
                url = new URL(urls[0]);

                // Make a url connection.
                urlConnection = (HttpURLConnection)url.openConnection();

                // Start an input stream.
                InputStream in = urlConnection.getInputStream();

                // Start a reader.
                InputStreamReader reader = new InputStreamReader(in);

                // Set the data reader.
                int data = reader.read();

                // While there is still something to read, e.g., not the end.
                while (data != -1) {

                    // Set our current character.
                    char current = (char) data;

                    // Append our current char to the result.
                    result += current;

                    // Keep on reading.
                    data = reader.read();

                }

                // Return what the results were.
                return result;

            } // And have a catch clause.
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // First, make a database, open or create will open if exist, create if not.
        firstDatabaseEver = this.openOrCreateDatabase("readerapp", MODE_PRIVATE, null);

        // Now, let's ceate a table.
        firstDatabaseEver.execSQL("CREATE TABLE IF NOT EXISTS hackernews (id INTEGER PRIMARY KEY, title VARCHAR, url VARCHAR, artid INTEGER, body VARCHAR)");

        // Defining the list view that I want by id.
        theList = (ListView) findViewById(R.id.theList);

        // Defining an array of titles and urls.
        titleList = new ArrayList<String>();
        urlList = new ArrayList<String>();

        // Defining an adapter, to adapt my array list to the correct format.
        addaptedAray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList);

        // Using the adapter to adapt my array list to the defined list view that I declared already.
        theList.setAdapter(addaptedAray);

        // Load any saved info if they fail when hooking to the web.
        queryDB();

        // Start a new asyncronous download task....
        DownloadTask task = new DownloadTask();
        String result = null;

        // Try in case it fails.
        try {

            // Grab this result as a string.
            result = task.execute("https://hacker-news.firebaseio.com/v0/newstories.json?print=pretty").get();

            // Turn the result into a JSONArray.
            JSONArray articleArray = new JSONArray(result);

            // Clear out the database, since we got new results.
            firstDatabaseEver.execSQL("DELETE FROM hackernews");

            // Start our for each loop.
            for (int k = 1; k < 11; k++) {

                // Testing only. // Log.i("WJH", articleArray.getString(k));

                // Start a new download task for each article.
                DownloadTask eachArticleTask = new DownloadTask();

                String theNum = articleArray.getString(k);

                // Get each article in its own asyncronous task.
                String eachArticle = eachArticleTask.execute("https://hacker-news.firebaseio.com/v0/item/"
                        + theNum + ".json?print=pretty").get();

                // Testing only. // Log.i("WJH", eachArticle);

                // Turn that string into a JSONObject.
                JSONObject myObject = new JSONObject(eachArticle);

                // Pull the Title and URL from the objects.
                String eachTitle = myObject.getString("title");

                try {
                    // Try to grab each URL.
                    eachURL = myObject.getString("url");

                } catch (Exception e) {

                    e.printStackTrace();

                    // If there is not a url in the list, use this one, so that it has SOMETHING.
                    // I added this because sometimes Hacker News' API would return a comment instead
                    // of a story, and it would have no URL. I don't know why, probably because I
                    // messed something up.
                    eachURL = "https://thealaskalinuxuser.wordpress.com";

                }
                // Okay, put our sql input string together.
                String sqlInput = "INSERT INTO hackernews (title, url, artid) VALUES (?,?,?)";

                // Turn it into a statement for SQL.
                SQLiteStatement myStatement = firstDatabaseEver.compileStatement(sqlInput);

                // Bind the strings to keep malicious code out.
                myStatement.bindString(1, eachTitle);
                myStatement.bindString(2, eachURL);
                myStatement.bindString(3, theNum);

                // And execute that sql.
                myStatement.execute();

            }

            // And query the db and update the listview.
            queryDB();

        } catch (Exception e) {

            // Tell me why it failed, please!
            Log.i("WJH", "Exception with try task.execute." + e);

        }


        // Setting up a listener to "listen" for me to click on something in the list.
        theList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Overriding the generic code that Android uses for list views to do something specific.
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int z, long l) {

                // Convert the int z into the strings we need.
                String a = (String) titleList.get(z);
                String b =  (String) urlList.get(z);

                // Logging that I tapped said item in the list.
                // Logging only, not needed. // Log.i("tapped", a);

                // Send my intent with my variables.
                Intent myIntent = new Intent(getApplicationContext(), ReadArticle.class);
                myIntent.putExtra("titleIntent", a);
                myIntent.putExtra("urlIntent", b);
                startActivity(myIntent);


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void queryDB () {

        try {
            // Now we need a cursor to retreive the data with a query.
            Cursor myCursor = firstDatabaseEver.rawQuery("SELECT * FROM hackernews ORDER BY artid DESC", null);

            // Now that we have the data, we need a way to index them, or grab the parts we need.
            int titleIndex = myCursor.getColumnIndex("title");
            int artidIndex = myCursor.getColumnIndex("artid");
            int urlIndex = myCursor.getColumnIndex("url");

            // Let's start from the top of the table, by moving to the first position.
            myCursor.moveToFirst();

            // Clear the list before we repopulate it. This prevents duplicates....
            titleList.clear();
            urlList.clear();

            // And if it is not null, let's use it.
            while (myCursor != null) {

                titleList.add(myCursor.getString(titleIndex));
                urlList.add(myCursor.getString(urlIndex));

                /* Logging for posterity....
                Log.i("WJH", myCursor.getString(titleIndex));
                Log.i("WJH", myCursor.getString(urlIndex));
                Log.i("WJH", Integer.toString(myCursor.getInt(artidIndex)));
                */

                // And move on to the next result in the table.
                myCursor.moveToNext();
            }

            // Update the array.
            addaptedAray.notifyDataSetChanged();
        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
