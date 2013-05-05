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
import android.widget.Spinner;

/**
 * SecurityQuestionActivity is an activity that provides a security question and answer
 * form for the user.
 * @author Elijah Elefson (elefse)
 *
 */
public class SecurityQuestionActivity extends Activity {
	private static final String USERNAME = "username";
	
	/**
	 * Stores the username the user gave in their signup form.
	 */
	private String username;
	
	/**
	 * Displays the security question selection page.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent receivedIntent = getIntent();
		username = receivedIntent.getStringExtra(USERNAME);
		setContentView(R.layout.activity_security_question);
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
	 * Responds to the signup button click and returns user to login page.
	 * @param view The current view
	 */
	public void gotoLogin(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		Spinner securityQuestions = (Spinner) findViewById(R.id.securityQuestionSpinner);
		// Gives a string representation of whatever item is selected in the spinner
		String selectedSecurityQuestion = securityQuestions.getSelectedItem().toString();
		
		EditText answerEditText = (EditText) findViewById(R.id.securityAnswerInput);
		String answer = answerEditText.getText().toString().trim();
		// Updates security question on remote database
		RemoteDbAccess.updateSecurityQuestion(username, selectedSecurityQuestion, answer);
		startActivity(intent);
	}

}
