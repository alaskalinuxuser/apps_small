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
package com.alaskalinuxuser.thefinalcountdown;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public int a = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Showing two different timer options.
         */

        // Up first, the Handler and runnable!

        // Create the handler, give it a name.
        final Handler firstHandler = new Handler();

        // Now create a runnable, give it a name.
        Runnable firstRun = new Runnable() {

            // Now, have that runnable override and run some code.
            @Override
            public void run() {

                // In this case, make a toast.
                Toast myToast = Toast.makeText(getApplicationContext(), "It's been 7 seconds.", Toast.LENGTH_SHORT);
                myToast.setGravity(Gravity.CENTER, 0, 0);
                myToast.show();

                a = a-1;

                if (a >= 0) {

                    // And call itself again in 7 seconds....
                    firstHandler.postDelayed(this, 7000);

                } else {
                    // do nothing.
                }

            }

        };

        // Be sure to initiate the handler the first time, or nothing will happen.
        // You could just use firstHandler.post(firstRun); but I wanted to wait seven seconds first.
        firstHandler.postDelayed(firstRun, 7000);

        // Up next, the countdown timer! Give it a lenght and an amount to count down in milliseconds.
        new CountDownTimer(121000, 10000) {

            // Implement code to happen every tick of the countdown.
            public void onTick (long myTimer) {

                // In this case a toast.
                Toast.makeText(
                        getApplicationContext(), "Ten seconds passed, " + String.valueOf(myTimer / 1000) + " seconds left!", Toast.LENGTH_SHORT).show();

            }

            // Implement code to happen when it is done counting down.
            public void onFinish() {

                // Again, I used a toast.
                Toast.makeText(getApplicationContext(), "Countdown is complete!", Toast.LENGTH_SHORT).show();

            }

            // Tell the countdown timer to start!
        }.start();

    }
}
