package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.parse.ParseUser;

public class homeActivity extends AppCompatActivity implements View.OnClickListener{

    // Declare some views.
    ImageView myFriendsView, myPicsView;
    TextView myFriendsText, myPicsText;

    @Override // What to do when creating the app.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Define our views.
        myFriendsText = (TextView)findViewById(R.id.myFriendsText);
        myFriendsView = (ImageView)findViewById(R.id.myFriendsView);
        myPicsText = (TextView)findViewById(R.id.myPicsText);
        myPicsView = (ImageView)findViewById(R.id.myPicsView);

        // Set the on click listening.
        myFriendsView.setOnClickListener(this);
        myFriendsText.setOnClickListener(this);
        myPicsView.setOnClickListener(this);
        myPicsText.setOnClickListener(this);

    }

    @Override // Create the menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override // What to do when you select something in the menu.
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // if you clicked on settings....
        if (id == R.id.action_settings) {
            return true; // essentially does nothing right now.
        }
        // if you clicked on logout....
        if (id == R.id.action_logout) {

            // Log user out.
            ParseUser.logOut();

            // Call an intent to go to the log in screen, since we are not logged in...
            // First you define it.
            Intent myintent = new Intent(homeActivity.this, LoginActivity.class);
            // Now you call it.
            startActivity(myintent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override // What to do if user clicked something.
    public void onClick(View view) {

        // Our if then statement. In this case, if they clicked on my friends.
        if (view.getId() == R.id.myFriendsText || view.getId() == R.id.myFriendsView) {

            // Since they clicked on "my friends" let's go to the my friends page.
            // First you define it.
            Intent myintent = new Intent(homeActivity.this, FriendsList.class);
            // Now you call it.
            startActivity(myintent);
            // Or if you clicked on my pictures.
        } else if (view.getId() == R.id.myPicsText || view.getId() == R.id.myPicsView) {

            // Since they clicked on "my pictures" let's go to the my pictures page.
            // First you define it.
            Intent myintent = new Intent(homeActivity.this, MyPics.class);
            // Now you call it.
            startActivity(myintent);

        }

    }

    @Override // To prevent going back to login screen if you logged in already.
    public void onBackPressed() {
        moveTaskToBack(true); // So, instead of going back, go to home screen.
    }
}

