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
package com.alaskalinuxuser.guessthatceleb;

// Import some libraries....
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // Declare our strings, integers, imageviews, bitmaps, and textviews.
    String result;
    int celebNum;
    int aCeleb;
    int rightAnswer;
    String foundString;
    String whosWho;
    String whosName;
    String whoWins;
    ImageView picOne;
    ImageView picTwo;
    ImageView picThree;
    ImageView picFour;
    TextView nameText;
    Bitmap winningPic;
    Bitmap myBit;

    // Our new class to download the page html data to parse for info.
    public class DownloadPage extends AsyncTask<String, Void, String> {

        // Do this in the background.
        @Override
        protected String doInBackground(String... urls) {

            // A few declared items.
            result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            //Log.i("WJH", urls[0]); // To log that the URL arrived in the class when called.

            // Try this.
            try {

                // Make our URL based on our given url to lookup.
                url = new URL(urls[0]);

                // Make a connection for that URL and open it.
                urlConnection = (HttpURLConnection) url.openConnection();

                // Start an input stream to get the bits.
                InputStream in = urlConnection.getInputStream();

                // Buffer it to reader.
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "iso-8859-1"), 8);

                // Build it string by string.
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) // Read line by line
                    sb.append(line + "\n");

                // Here is the result.
                String resString = sb.toString();

                // Close the stream.
                in.close();

                // Tie the result to our variable that is known outside of this class.
                result = resString;

                // return that variable.
                return result;

                // Have an exception clause so you don't crash.
            } catch (Exception e) {

                e.printStackTrace();

                return "Failed";

            }


        }

    }

    // Our new class to download the picture in the background.
    public class DownloadPic extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // To download in one "go" as Rob says.
                urlConnection.connect();

                // To grab the whole thing at once.
                InputStream inStream = urlConnection.getInputStream();

                // Turn that data into a bitmap.
                myBit = BitmapFactory.decodeStream(inStream);

                // Return that bitmap.
                return myBit;

                // Have an exception if there is a failure.
            } catch (Exception e) {

                e.printStackTrace();

                //Log.i("WJH", "Failed"); // You can log this to see the failure if needed.

                // Since it fails, return nothing.
                return null;

            }


        }
    }

    // Our random number generator.
    public void randomIzer() {

        // The random number generator itself.
        Random ranGen = new Random();

        // The random number of richest celebrities.
        celebNum = ranGen.nextInt(20);

        // The random number of the right answer.
        rightAnswer = ranGen.nextInt(4);

        //Log.i("WJH", Integer.toString(rightAnswer)); // Log this to test the random number answer.

    }

    // Our method to get the name of the celebrity.
    public void getCelebname(){

        // Call the class to download the page.
        DownloadPage task = new DownloadPage();
        String result = null;

        // my trick number we will use later.
        aCeleb=0;

        try {

            // execute, or go on and do that task.
            result = task.execute("http://www.therichest.com/top-lists/top-100-richest-celebrities").get();

            // A fail clause.
        } catch (Exception e) {

            e.printStackTrace();

        }

        // Now regex the data with this pattern.
        Pattern pat = Pattern.compile("<span>(.*?)</span>");

        // And search for matches in the results.
        Matcher mat = pat.matcher(result);

        // For every one you find, do this.
        while (mat.find() && aCeleb <= celebNum) {

            // Add to my magic number.
            aCeleb++;

            // The search results.
            String foundNameString = (mat.group(1));

            // Tie the search results to a public variable we can use outside of this method.
            whosName = foundNameString;

            //Log.i("WJH", whosName); // Log to see if this is working.

        }

    }

    // And a method to figure out which picture we need. Same as above method, just a different
    // search pattern.
    public void getCelebpic(){

        DownloadPage task = new DownloadPage();
        String result = null;
        aCeleb=0;

        try {

            result = task.execute("http://www.therichest.com/top-lists/top-100-richest-celebrities").get();

        } catch (Exception e) {

            e.printStackTrace();

        }

        Pattern pat = Pattern.compile("0px\\)\" sizes=\"70px\" srcset=\"(.*?)\"");

        Matcher mat = pat.matcher(result);

        while (mat.find() && aCeleb <= celebNum - 1) {

            aCeleb++;

            foundString = (mat.group(1));

            whosWho = foundString;

            //Log.i("WJH", foundString); // Log to see if this is working.

        }

    }

    // Ok, now we need to download that picture.
    public void downloadPics(){

        // Call the class to download the picture.
        DownloadPic task = new DownloadPic();

        try {

            // Let's get the picture.
            Bitmap myImage = task.execute(whosWho).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            // Define a few objects.
        picOne = (ImageView)findViewById(R.id.imageViewZero);
        picTwo = (ImageView)findViewById(R.id.imageViewOne);
        picThree = (ImageView)findViewById(R.id.imageViewTwo);
        picFour = (ImageView)findViewById(R.id.imageViewThree);
        nameText = (TextView)findViewById(R.id.nameView);

            // Set up our first round.
        setupGame();

    }

    // The method to set up each round of choices.
    public void setupGame() {

        // Call the randomizer method.
        randomIzer();

        // Okay, so the real winner is:
        // Get the winner name.
        getCelebname();
        // Set the winner name to the text field.
        nameText.setText(whosName);
        // Get the URL for the celeb pic.
        getCelebpic();
        // Tie in our winning variable, since we will overwrite it later.
        whoWins = whosWho;
        // Call the method to download the pic.
        downloadPics();
        // Tie in our winning pic to our downloaded pic, since we will overwrite it later.
        winningPic = myBit;

        // if/then. if you are the right answer, set your pic to the winning pic.
        if (rightAnswer == 0) {

            picOne.setImageBitmap(winningPic);

        } else {

            // If not, then plus one on the celebNum and call for a random picture.
            celebNum++;
            getCelebpic();
            downloadPics();
            picOne.setImageBitmap(myBit);

        }

        if (rightAnswer == 1) {

            picTwo.setImageBitmap(winningPic);

        } else {

            celebNum++;
            getCelebpic();
            downloadPics();
            picTwo.setImageBitmap(myBit);

        }

        if (rightAnswer == 2) {

            picThree.setImageBitmap(winningPic);

        } else {

            celebNum++;
            getCelebpic();
            downloadPics();
            picThree.setImageBitmap(myBit);

        }

        if (rightAnswer == 3) {

            picFour.setImageBitmap(winningPic);

        } else {

            celebNum++;
            getCelebpic();
            downloadPics();
            picFour.setImageBitmap(myBit);

        }
    }

    // Well, am I right? This is called through onClick of each imageview.
    public void amIRight(View view){

        // Make a tag number string with get tag for the clicked object.
        String tagNum = (String) view.getTag();
        //Log.i("WJH", Integer.toString(rightAnswer)); // Log to see if this is working.
        //Log.i("WJH", tagNum); // Log to see if this is working.
        // Parse the integer from the string.
        int taggedNum = Integer.parseInt(tagNum);
        // Define a toast text for later.
        String toastText;

        // If the tagged number of the clicked square is the right answer....
        if (taggedNum == rightAnswer) {

            // Set the text to correct.
            toastText = "Correct!";

            // If not....
        } else {

            // Set the text to incorrect.
            toastText = "Incorrect.";

        }

        // Set up the next round.
        setupGame();

        // And tell the user if they were right or not.
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();

        /* This may seem a bit odd. Logic would dictate we toast first, then set up the
         * next round. But, if I toast first, the toast does not display because the phone
         * is working too hard to get the new pictures. This way, we wait until the new pictures
         * are in, then display the toast, which works, but seems sloppy.
         */
    }

}
