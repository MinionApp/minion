package com.example.myfirstapp;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
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
 *
 */
public class PasswordRecoveryQuestionActivity extends Activity {
	private static final String USERNAME = "username";
	
	/**
	 * Stores the username the user gave in their signup form.
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
		Intent receivedIntent = getIntent();
		username = receivedIntent.getStringExtra(USERNAME);
		setContentView(R.layout.activity_password_recovery_question);
		// Gets security question for user from remote database
		question = RemoteDbAccess.getSecurityQuestion(username);
		TextView securityQuestionTextView = (TextView)findViewById(R.id.securityQuestion);
		securityQuestionTextView.setText(question);
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
		Intent intent;
		EditText answerEditText = (EditText) findViewById(R.id.questionInput);
		String answer = answerEditText.getText().toString().trim();
		
    	// Checks for internet connectivity
    	if (ConnectionChecker.hasConnection(this)) {
    		// Tests security question for user on remote database
    		if(RemoteDbAccess.securityQuestionTest(username, question, answer)) {
    			intent = new Intent(this, PasswordResetActivity.class);
    		} else {
    			intent = new Intent(this, PasswordRecoveryQuestionActivity.class);
    		}
    		intent.putExtra(USERNAME, username);
    	} else {
    	   Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
    	   intent = new Intent(this, PasswordRecoveryQuestionActivity.class);
    	}
		startActivity(intent);
	}

}
