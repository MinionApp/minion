package uw.cse403.minion;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * PasswordRecoveryQuestionActivity is an activity that gives the user their security question
 * and then allows them to answer it in order to determine if they can reset their password.
 * @author Elijah Elefson (elefse)
 */
public class PasswordRecoveryQuestionActivity extends Activity {
	
	private static final String USERNAME = "username";
	private static final String QUESTION = "question";
	
	private AccountUtils account;
	
	/**
	 * Stores the user's username.
	 */
	private String username;
	
	/**
	 * Stores the relevant security question for the user.
	 */
	private String question;
	
	/**
	 * Displays the password recovery security question page.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_recovery_question);
		account = new AccountUtils();
		Intent receivedIntent = getIntent();
		username = receivedIntent.getStringExtra(USERNAME);
		question = receivedIntent.getStringExtra(QUESTION);
		TextView security_questionTextView = (TextView) findViewById(R.id.security_question);
		security_questionTextView.setText(question);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	/**
	 * Creates Options Menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sqlite_test, menu);
		return true;
	}
	
	/**
	 * Sets up the Up button
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Responds to the answer question button click and sends user to password reset page.
	 * @param view The current view
	 */
	public void gotoLogin(View view) {
		EditText answerEditText = (EditText) findViewById(R.id.question_input);
		String answer = answerEditText.getText().toString().trim();
		
    	// Checks for internet connectivity
    	if (ConnectionChecker.hasConnection(this)) {
    		// Tests security question for user on remote database
    		boolean result = account.checkAnswer(username, question, answer);
    		TextView error = (TextView) findViewById(R.id.incorrect_answer_error);
        	if (result) {
        		Intent intent = new Intent(this, PasswordResetActivity.class);
        		intent.putExtra(USERNAME, username);
        		startActivity(intent);
        	} else {
        		error.setVisibility(View.VISIBLE);  
        	}
    	} else {
    	   Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
    	}
	}

	
}
