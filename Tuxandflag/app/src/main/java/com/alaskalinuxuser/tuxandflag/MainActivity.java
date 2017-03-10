package com.alaskalinuxuser.tuxandflag;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Boolean to determine if the game ended.
    boolean gameInPlay = true;

    // Rob showed us a better way, so using that.
    // So, whose turn is it?
    int turn = 0;

    // And, what is the starting condition of the board?
    int[] conditions = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // Oh, and what are the winning moves?
    int[][] winningMoves = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // My nifty animation!
        // Declare the image view to use.
        ImageView aklu = (ImageView)findViewById(R.id.aklu);
        // Set it to be visable.
        aklu.setVisibility(View.VISIBLE);
        // Initially set that image off screen.
        aklu.setTranslationX(-1000f);
        // Bring image on screen and then off the screen again!
        aklu.animate().translationXBy(2000f).setDuration(3000);
        // And just to be sure, let's make it invisable again.
        aklu.setVisibility(View.INVISIBLE);

    }

    public void exitApp (View exitView) {
        // Exit the app when clicked.
        finish();

    }

    public void startOver (View startView) {

        // Do this when you want to start over
        Toast newGame = Toast.makeText(getApplicationContext(),"Okay, here's a new game!", Toast.LENGTH_SHORT);
                    newGame.setGravity(Gravity.CENTER, 0, 0);
                    newGame.show();

        // Reset all the pieces.
        ImageView p0 = (ImageView)findViewById(R.id.p0);
        ImageView p1 = (ImageView)findViewById(R.id.p1);
        ImageView p2 = (ImageView)findViewById(R.id.p2);
        ImageView p3 = (ImageView)findViewById(R.id.p3);
        ImageView p4 = (ImageView)findViewById(R.id.p4);
        ImageView p5 = (ImageView)findViewById(R.id.p5);
        ImageView p6 = (ImageView)findViewById(R.id.p6);
        ImageView p7 = (ImageView)findViewById(R.id.p7);
        ImageView p8 = (ImageView)findViewById(R.id.p8);
        p0.setImageResource(R.drawable.coin);
        p1.setImageResource(R.drawable.coin);
        p2.setImageResource(R.drawable.coin);
        p3.setImageResource(R.drawable.coin);
        p4.setImageResource(R.drawable.coin);
        p5.setImageResource(R.drawable.coin);
        p6.setImageResource(R.drawable.coin);
        p7.setImageResource(R.drawable.coin);
        p8.setImageResource(R.drawable.coin);

        // Reset all the variables.
        turn = 0;
        gameInPlay = true;

        // Reset the array.
        for (int p = 0; p < conditions.length; p++) {
            conditions[p] = 2;
        }


    }

    public void putPiece (View pieceView) {

        if (gameInPlay) {

            // Do this when a piece is clicked....

            ImageView myClick = (ImageView) pieceView;

            // Get the clicked squares tag to see if it has been played before.
            int played = Integer.parseInt(pieceView.getTag().toString());

            if (conditions[played] == 2) {

                conditions[played] = turn;

                myClick.setTranslationY(-1000f);

                if (turn == 0) {

                    myClick.setImageResource(R.drawable.flagcoin);
                    turn = 1;

                } else {

                    myClick.setImageResource(R.drawable.tuxcoin);
                    turn = 0;

                }

                myClick.animate().translationYBy(1000f).setDuration(500);

                for (int[] won : winningMoves) {

                    if (conditions[won[0]] == conditions[won[1]] &&
                            conditions[won[1]] == conditions[won[2]] &&
                            conditions[won[0]] != 2) {

                        if (turn == 0) {

                            Toast wins = Toast.makeText(getApplicationContext(), "Penguin won!", Toast.LENGTH_SHORT);
                            wins.setGravity(Gravity.CENTER, 0, 0);
                            wins.show();
                        } else {

                            Toast wins = Toast.makeText(getApplicationContext(), "Flag won!", Toast.LENGTH_SHORT);
                            wins.setGravity(Gravity.CENTER, 0, 0);
                            wins.show();

                        }

                        gameInPlay = false;

                    }

                }

            } else {

                Toast cant = Toast.makeText(getApplicationContext(), "You can't do that!", Toast.LENGTH_SHORT);
                cant.setGravity(Gravity.CENTER, 0, 0);
                cant.show();

            }
        } else {

                Toast over = Toast.makeText(getApplicationContext(), "The game has ended. Press start over to play again.", Toast.LENGTH_SHORT);
                over.setGravity(Gravity.CENTER, 0, 0);
                over.show();

        }
    }
}
