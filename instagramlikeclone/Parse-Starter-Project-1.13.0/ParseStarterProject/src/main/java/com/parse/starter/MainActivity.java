/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // To capture analytics, if used.
    // ParseAnalytics.trackAppOpenedInBackground(getIntent());

    // Define my views.
    ImageView circleWait = (ImageView)findViewById(R.id.waitView);
    // Fancy animation while checking for login status.
    circleWait.animate().rotation(36000f).setDuration(30000);

      /* How to check if a user is loged in... */
      if (ParseUser.getCurrentUser() != null) {

          // Someone is logged in already. Let's log it.
          Log.i("WJH", ParseUser.getCurrentUser().getUsername() + " is already logged in.");

          // Call an intent to go to the home screen, since they are logged in.
          // First you define it.
          Intent myintent = new Intent(MainActivity.this, homeActivity.class);
          // Now you call it.
          startActivity(myintent);

      } else {

          // No one is logged in, let's log someone in.... For now, we just log it.
          Log.i("WJH", "No one loged in.");

          // Call an intent to go to the log in screen, since we are not logged in...
          // First you define it.
          Intent myintent = new Intent(MainActivity.this, LoginActivity.class);
          // Now you call it.
          startActivity(myintent);

      }

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

}
