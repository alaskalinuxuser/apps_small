package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener{

    // Declare my views and booleans, etc.
    Boolean logInBool;
    Button logInButton;
    TextView registerText;
    EditText passText, nameText;
    ImageView camLogo;
    LinearLayout linLogLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // The floating action button.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Please enter your username and password.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // They should log in.
        logInBool = true;

        // Define our views.
        registerText = (TextView)findViewById(R.id.registerText);
        logInButton = (Button)findViewById(R.id.loginButton);
        passText = (EditText) findViewById(R.id.passwordEditText);
        nameText = (EditText)findViewById(R.id.nameEditText);
        linLogLay = (LinearLayout)findViewById(R.id.linLogLay);
        camLogo = (ImageView)findViewById(R.id.camLogo);

        // Define our listeners.
        camLogo.setOnClickListener(this);
        linLogLay.setOnClickListener(this);
        passText.setOnKeyListener(this);
        

        // Set the default values
        logInButton.setText("Log in!");
        registerText.setText("Register");

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

        // If you click settings....
        if (id == R.id.action_settings) {
            return true; // This does nothing right now.
        }

        return super.onOptionsItemSelected(item);
    }

    // If they click on the log in button.
    public void logMeIn (View view) {

        Log.i("WJH", "clicked login");

        // If they have not chosen to register, then they will log in.


        if (logInBool) {

                // Log them in.
                ParseUser.logInInBackground(
                        String.valueOf(nameText.getText()),
                        String.valueOf(passText.getText()),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {

                                // If statement.
                                if (e == null) {

                                    // If exception was empty, then nothing bad happened, it must have worked.
                                    // Testing only // Log.i("WJH", user + " sign in Succeeded");

                                    // Tell them with a toast.
                                    Toast.makeText(getApplicationContext(), "Sign in successful!", Toast.LENGTH_SHORT).show();

                                    // Call an intent to go to the home screen, since they are logged in.
                                    // First you define it.
                                    Intent myintent = new Intent(LoginActivity.this, homeActivity.class);
                                    // Now you call it.
                                    startActivity(myintent);

                                } else {

                                    // If exception was not empty, something went wrong. Let's log it.
                                    Log.i("WJH", "sign in Failed " + e);

                                    // Split the error into parts of an array.
                                    String[] toastedString = String.valueOf(e).split(":");
                                    // Get the length of the array.
                                    int i = toastedString.length;

                                    // Tell the user what is wrong.
                                    Toast.makeText(getApplicationContext(), toastedString[i-1], Toast.LENGTH_LONG).show();

                                }

                            }
                        });

        } else {

                // Register them.
                // How to create a log in with new username and password:
                ParseUser myUser = new ParseUser();
                myUser.setUsername(String.valueOf(nameText.getText()));
                myUser.setPassword(String.valueOf(passText.getText()));
                //myUser.setEmail("someemail@place"); // if needed later.
                //myUser.put("phoneNumber", "9079079079"); // if needed later.

                myUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        // If statement.
                        if (e == null) {
                            // If exception was empty, then nothing bad happened, it must have worked.
                            // Testing only // Log.i("WJH", "register Succeeded");

                            Toast.makeText(getApplicationContext(), "Registration was successful!", Toast.LENGTH_SHORT).show();

                            // Call an intent to go to the home screen, since they are logged in.
                            // First you define it.
                            Intent myintent = new Intent(LoginActivity.this, homeActivity.class);
                            // Now you call it.
                            startActivity(myintent);

                        } else {
                            // If exception was not empty, something went wrong. Let's log it.
                            Log.i("WJH", "register Failed " + e);

                            // Split the error into an array.
                            String[] toastedString = String.valueOf(e).split(":");
                            //Get the length of the array.
                            int i = toastedString.length;

                            // Tell the user what is wrong.
                            Toast.makeText(getApplicationContext(), toastedString[i-1], Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }


    }





    // If they click on the registerUser text.
    public void registerUser (View view) {

        Log.i("WJH", "clicked register");
        // If they have clicked to register or log in.

        if (logInBool) {

            // They now don't want to log in, they want to register.
            // Set the default values
            logInButton.setText("Register");
            registerText.setText("Log in instead");
            logInBool = false;

        } else {

            // They now don't want to register, they want to log in.
            // Set the default values
            logInButton.setText("Log in!");
            registerText.setText("Register");
            logInBool = true;

        }

    }

    @Override // Our on key event listener....
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        // if they press the enter key, when they push it down, then....
        if ( i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == keyEvent.ACTION_DOWN ) {

            // Call the log in.
            logMeIn(view);

        }

            return false;
    }

    @Override // Our on click listener....
    public void onClick(View view) {

        // If they click on the logo or the layout, then....
        if (view.getId() == R.id.camLogo || view.getId() == R.id.linLogLay) {

            // Log it.
            Log.i("WJH", "tapped onclick");

            // Let's hide the keyboard.
            try {

                InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            } catch (Exception e) {
                // Log it if we can't hide it.
                Log.e("WJH", "We can't hide the keyboard.");
            }

        }
    }

    @Override // To prevent going back to home screen if you logged out.
    public void onBackPressed() {
        moveTaskToBack(true); // So, instead of going back, go to home screen.
    }

}
