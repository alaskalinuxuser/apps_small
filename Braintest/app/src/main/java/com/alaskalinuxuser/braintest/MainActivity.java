package com.alaskalinuxuser.braintest;

	import android.os.CountDownTimer;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.Button;
	import android.widget.ImageView;
	import android.widget.TextView;
	import java.util.Random;
    import android.widget.Toast;
    import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

		// Let's start by declaring all of the fields and views....
		Button goButton;
		TextView finalScore;
		TextView timerView;
		TextView scoreView;
		TextView questionView;
		TextView p0;
		TextView p1;
		TextView p2;
		TextView p3;
		ImageView litmusView;
		CountDownTimer gameClock;
		String minutes;
		String seconds;
		String theFinalScore;
		int firstNum;
		int secondNum;
		int answerNum;
		int randNumZero;
		int randNumOne;
		int randNumTwo;
		int randNumThree;
		int scoreRight;
		int questionsAsked;

		// And a boolean for if the game is in play or not.
		boolean isRunning;


		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);

			// On create, we want to define all of those views and button.
			goButton = (Button)findViewById(R.id.goButton);
			finalScore = (TextView)findViewById(R.id.finalScore);
			timerView = (TextView)findViewById(R.id.timerView);
			scoreView = (TextView)findViewById(R.id.scoreView);
			questionView = (TextView)findViewById(R.id.questionView);
			litmusView = (ImageView)findViewById(R.id.litmusView);
			p0 = (TextView)findViewById(R.id.p0);
			p1 = (TextView)findViewById(R.id.p1);
			p2 = (TextView)findViewById(R.id.p2);
			p3 = (TextView)findViewById(R.id.p3);

			// And our boolean.
			isRunning = false;

			// And make sure that the game is reset. In the event it was opened before.
			this.resetGame();

		}

		// We need a way to "reset" the game, so let's make a method to do that.
		public void resetGame() {

			// So, when the game is reset, these things need to be properly set:

			/*
			 * The button needs to say go! and these textviews need reset.
			 * This may seem redundant, since they start this way, but they
			 * will not stay that way as the game is played.
			 */

			goButton.setText("Go!");
			finalScore.setText("0");
			timerView.setText("00:30");
			scoreView.setText("0/0");
			questionView.setText("0+0");
			p0.setText("0");
			p1.setText("1");
			p2.setText("2");
			p3.setText("3");

			// And we need to define what is seen (Visible) and what is not (invisible)....
			goButton.setVisibility(View.VISIBLE);
			finalScore.setVisibility(View.INVISIBLE);
			timerView.setVisibility(View.INVISIBLE);
			scoreView.setVisibility(View.INVISIBLE);
			questionView.setVisibility(View.INVISIBLE);
			p0.setVisibility(View.INVISIBLE);
			p1.setVisibility(View.INVISIBLE);
			p2.setVisibility(View.INVISIBLE);
			p3.setVisibility(View.INVISIBLE);

			// And set our image view back to blank.
			litmusView.setImageResource(R.drawable.blank);
			
			// And reset the score.
			scoreRight = 0;
			questionsAsked = 0;

		}

		// We need a time calculator method.
		public void theTime (int timeStillLef) {

			// To get the rounded down number of "whole" minutes.
			int m = timeStillLef/60000;
			// To get the remainder in seconds.
			int s = (timeStillLef - (m*60000))/1000;

			String mi = String.valueOf(m);
			String se = String.valueOf(s);

			if (m <=9) {

				minutes = "0" + mi;

			} else {

				minutes = mi;

			}

			if (s <= 9) {

				seconds = "0" + se;

			} else {

				seconds = se;

			}

			// Set the time.
			timerView.setText(minutes + ":" + seconds);

		}
		
		// We will also need a score keeping method.
		public void scoreKeeper() {
			
			String sR = String.valueOf(scoreRight);
			String qA = String.valueOf(questionsAsked);
			
			theFinalScore = sR + "/" + qA;
			
			scoreView.setText(theFinalScore);
			
		}

		// And we need a gameClock method.
		public void gameTimer() {

			gameClock = new CountDownTimer(30200, 1000) {



				@Override
				public void onTick(long timeLeft) {

					// Update the time every tick with the method "theTime" based on the long "timeLeft".
					theTime((int) timeLeft);

				}

				@Override
				public void onFinish() {

					// We should set the text to 00:00, since the counter stopped updating after 00:01.
					timerView.setText("00:00");
					
					// And we need to hide the choices and qiestion.
					questionView.setVisibility(View.INVISIBLE);
					p0.setVisibility(View.INVISIBLE);
					p1.setVisibility(View.INVISIBLE);
					p2.setVisibility(View.INVISIBLE);
					p3.setVisibility(View.INVISIBLE);
					
					// Set the final score value.
					finalScore.setText(theFinalScore);
					
					// And make the final score viewable.
					finalScore.setVisibility(View.VISIBLE);
					
					// And blank the check or x.
					litmusView.setImageResource(R.drawable.blank);


				}
			}.start(); // To start the timer.

		}

		// And we need a random number generator.
		public void randomNumber() {

			// The actual number generator.
			Random smallGen = new Random();
			
			// To pick the tile threat is the answer.
			// Remember: 0 is a number.
			answerNum = smallGen.nextInt(4);
			
			// Now to pick a number between 1 and 20.
			// And assign that number to each tile.
			randNumZero = smallGen.nextInt(19) + 1;
			p0.setText(String.valueOf(randNumZero));
			
			randNumOne = randNumZero + 6;
			p1.setText(String.valueOf(randNumOne));
			
			randNumTwo = randNumZero - 1;
			p2.setText(String.valueOf(randNumTwo));
			
			randNumThree = randNumZero + 3;
			p3.setText(String.valueOf(randNumThree));
			
			// And our random first number of our question.
			// A number between 1 and 20. We will minus this from
			// the answer to get the second number.
			firstNum = smallGen.nextInt(19) + 1;

			// Now to get the question.
			if (answerNum == 0) {
				
				secondNum = randNumZero - firstNum;
				
			} else if (answerNum == 1) {
				
				secondNum = randNumOne - firstNum;
				
			} else if (answerNum == 2) {
				
				secondNum = randNumTwo - firstNum;
				
			} else {
				
				secondNum = randNumThree - firstNum;
				
			}
			
			String fN = String.valueOf(firstNum);
			String sN = String.valueOf(secondNum);
			
			questionView.setText(fN + " + " + sN);
			
		}

		// Now that we have everything, we can play a game!
		public void gameGo (View view) {

			// We should check if a game is running or not.
			if (isRunning) {

				// What to do if the game is in run? We must want to reset it.
				// First, we set the boolean, since the game is now stopped.
				isRunning = false;

				// And we reset all of the pieces.
				// We want to call this = this app and class, and the method resetGame().
				this.resetGame();

				// And stop the game clock.
				gameClock.cancel();
				
				// With a toast to two the user.
				Toast.makeText(getApplicationContext(),
				"The game is reset, and ready when you are!",
				Toast.LENGTH_SHORT).show();

			} else {

				// What to do if the game is not in run. We should start the game!
				// First, we set the boolean, because the game is now in run.
				isRunning = true;

				// Next, we want to change the go button to reset.
				goButton.setText("Reset");

				// Now, we need to show some hidden things.
				timerView.setVisibility(View.VISIBLE);
				scoreView.setVisibility(View.VISIBLE);
				questionView.setVisibility(View.VISIBLE);
				p0.setVisibility(View.VISIBLE);
				p1.setVisibility(View.VISIBLE);
				p2.setVisibility(View.VISIBLE);
				p3.setVisibility(View.VISIBLE);

				// We also need to start our gameTimer.
				this.gameTimer();
				
				// And get our first set of numbers!
				randomNumber();

			}


		}
		
		public void testClick (View ve) {
			
			// Here we define what to do when a choice is clicked.
			String tagNum = (String) ve.getTag();
			int taggedNum = Integer.parseInt(tagNum);
			
			if (taggedNum == answerNum) {
				
				/* 
				 * Since the answernum variable randomly chose
				 * the square or view button to be the correct
				 * one, we will use that and compare to our tag
				 * to distinguish a correct click.
				 */
				 
				 // set the good check mark so the user knows they chose correctly.
				 litmusView.setImageResource(R.drawable.right);
				 
				 // Also add one to the score.
				 scoreRight ++;
				 
				 // And adjust questions asked variable.
				 questionsAsked ++;
				 
				// And display the new score.
				scoreKeeper();
				
				 // And we need the next set of random numbers.
				 randomNumber();
				
			} else {
				
				// set the bad mark so the user knows they chose poorly.
				litmusView.setImageResource(R.drawable.wrong);
				
				// And adjust questions asked variable.
				questionsAsked ++;
				
				// And display the new score.
				scoreKeeper();
				
				// And we need the next set of random numbers.
				randomNumber();
			}
			
		}
	}

