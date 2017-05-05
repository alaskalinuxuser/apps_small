/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Application;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("instagram19850703")
            .clientKey("74chevy1985")
            .server("https://floating-dawn-66878.herokuapp.com/parse/")
    .build()
    );

      /* Don't need this every time....
      // Add a new class of object to save, called GameScore
      ParseObject gameScore = new ParseObject("GameScore");
      // And put the "score" in it.
      gameScore.put("score", 1187);
      // And put the "playername"
      gameScore.put("playerName", "Other Guy");
      // And put the "cheatmode" boolean.
      gameScore.put("cheatMode", true);
      // Go ahead and save it in the background, and check if it passed or not.
      gameScore.saveInBackground(new SaveCallback() {
          public void done(ParseException e) {

              // If statement.
              if (e == null) {
                  // If exception was empty, then nothing bad happened, it must have worked.
                  Log.i("WJH", "first save Succeeded");
              } else {
                  // If exception was not empty, something went wrong. Let's log it.
                  Log.i("WJH", "first save Failed " + e);
              }
          }
      }); */

      /* How to update objects...
      // So, how to update an object? We first need to querry it.
      ParseQuery<ParseObject> myQuerry = ParseQuery.getQuery("GameScore");

      // Once we defined what to querry, we need it for this user id:
      myQuerry.getInBackground("lhlw1DVr3T", new GetCallback<ParseObject>() {
          @Override
          public void done(ParseObject object, ParseException e) {

              // If statement.
              if (e == null) {

                  // If the exception is null, the we have that id's score.
                  // So we should "put" a new score in.
                  object.put("score", 150);
                  // And let's save that in the background.
                  object.saveInBackground(new SaveCallback() {
                      @Override
                      public void done(ParseException e) {

                          // If statement.
                          if (e == null) {
                              // If exception was empty, then nothing bad happened, it must have worked.
                              Log.i("WJH", "update score Succeeded");
                          } else {
                              // If exception was not empty, something went wrong. Let's log it.
                              Log.i("WJH", "update score Failed " + e);
                          }

                      }

                  });

              } else {
                  // If exception was not empty, something went wrong. Let's log it.
                  Log.i("WJH", "Get score Failed " + e);
              }

          }
      }); */

      /* Query stuff....
      // So, how to find an object? We first need to query it.
      ParseQuery<ParseObject> nextQuery = ParseQuery.getQuery("GameScore");

      // Now let's find stuff.
      nextQuery.findInBackground(new FindCallback<ParseObject>() {
          @Override
          public void done(List<ParseObject> objects, ParseException e) {

              // If statement.
              if (e == null) {
                  // If exception was empty, then nothing bad happened, it must have worked.
                  // Let's log how many objects there are.
                  Log.i("WJH", "found " + objects.size() + " objects.");
                  // Then let's log all those objects. Providing there are more than 0 objects.
                  if (objects.size() > 0) {

                      // For each objects, we can parse the object.
                      for (ParseObject object : objects) {

                          // Go ahead and log just the playerNames.
                          Log.i("WJH", "object: " + object.get("playerName"));

                      }
                  }

              } else {
                  // If exception was not empty, something went wrong. Let's log it.
                  Log.i("WJH", "find everything Failed " + e);
              }

          }
      });

      // So, how to find a particular object? We first need to query it.
      ParseQuery<ParseObject> anotherQuery = ParseQuery.getQuery("GameScore");

      anotherQuery.whereEqualTo("playerName", "Sean Plott");

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

                      // For each objects, we can parse the object.
                      for (ParseObject object : objects) {

                          // Go ahead and log just the score of the person.
                          Log.i("WJH", "object: " + object.get("score"));

                      }
                  }

              } else {
                  // If exception was not empty, something went wrong. Let's log it.
                  Log.i("WJH", "find everything Failed " + e);
              }

          }
      }); */

      /* How to sign up a new user.
      // How to create a log in with username and password:
      ParseUser myUser = new ParseUser();
      myUser.setUsername("AlaskaLinuxUser");
      myUser.setPassword("Apassw0rd");
      myUser.setEmail("someemail@place");
      myUser.put("phoneNumber", "9079079079");

      myUser.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {

              // If statement.
              if (e == null) {
                  // If exception was empty, then nothing bad happened, it must have worked.
                  Log.i("WJH", "signup Succeeded");
              } else {
                  // If exception was not empty, something went wrong. Let's log it.
                  Log.i("WJH", "signup Failed " + e);
              }

          }
      });
      */

      /* How to log in a user...
      ParseUser.logInInBackground("AlaskaLinuxUser", "Apassw0rd", new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {

              // If statement.
              if (e == null) {

                  // If exception was empty, then nothing bad happened, it must have worked.
                  Log.i("WJH", user + " sign in Succeeded");

              } else {

                  // If exception was not empty, something went wrong. Let's log it.
                  Log.i("WJH", "sign in Failed " + e);
              }

          }
      }); */

      /* Log user out.
      ParseUser.logOut(); */

      /* How to check if a user is loged in... */
      if (ParseUser.getCurrentUser() != null) {

          // Someone is logged in already. Let's log it.
          Log.i("WJH", ParseUser.getCurrentUser() + " is already logged in.");

      } else {

          // No one is logged in, let's log someone in.... For now, we just log it.
          Log.i("WJH", "No one loged in.");

      }



      // Go ahead and automatically give it a number.
      // ParseUser.enableAutomaticUser(); // Disable by removing it.
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
