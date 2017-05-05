package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class FriendsList extends AppCompatActivity {

    // Declare views.
    ListView friendsList;
    ArrayList<String> myFriendsList;
    ArrayAdapter myFriendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // The floating action button.
        FloatingActionButton fabFLBack = (FloatingActionButton) findViewById(R.id.fabFLBack);
        fabFLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Define my friends list.
        friendsList = (ListView)findViewById(R.id.friendsList);

        // First, let's create an array list.
        myFriendsList = new ArrayList<String>();

        // Then adapt our list.
        myFriendsAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                myFriendsList);

        // Listen to our friends list for clicks.
        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int here, long id) {

                // When clicked, go to the friends feed page.
                Intent myFriendIntent = new Intent(getApplicationContext(), FriendFeed.class);
                // Also send the username info.
                myFriendIntent.putExtra("theName", myFriendsList.get(here));
                // And start that new activity.
                startActivity(myFriendIntent);

            }
        });

        // Let's make a query.
        ParseQuery<ParseUser> myQuery = ParseUser.getQuery();
        // And not call our own self.
        myQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        // And sort alphabetically.
        myQuery.addAscendingOrder("username");
        // Let's get our query in the background.
        myQuery.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                // As long as there are no errors.
                if (e == null) {

                    // We should see if there are names on the list.
                    if (objects.size() > 0) {

                        // Since there is no error, and there are friends on the list, let's populate it.
                        // For each username....
                        for (ParseUser user : objects) {

                            // Add them to the list.
                            myFriendsList.add(user.getUsername());

                        }

                        // Apply that adapter to our list.
                        friendsList.setAdapter(myFriendsAdapter);

                    } else {

                        // Tell the user they have no friends :(
                        Toast.makeText(getApplicationContext(), "You have no friends :(", Toast.LENGTH_LONG).show();

                    }

                } else { // Since e is not null, there is an error.

                    // Split the error into parts of an array.
                    String[] toastedString = String.valueOf(e).split(":");
                    // Get the length of the array.
                    int i = toastedString.length;
                    // Tell the user what is wrong.
                    Toast.makeText(getApplicationContext(), toastedString[i-1], Toast.LENGTH_LONG).show();

                }

            }

        });

    }

}
