package com.example.myfirstapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * PasswordRecoveryCompletedActivity is an activity that lets the user change their
 * password to a new one in the event that they have forgotten their previous password.
 * @author Elijah Elefson (elefse)
 *
 */
public class PasswordResetActivity extends Activity {
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String PASSWORD_CONFIRMATION = "passwordConfirmation";
	private static final String IS_VALID_PASSWORD = "isValidPassword";
	private static final String PASSWORDS_MATCH = "passwordsMatch";
	
	/**
	 * Stores the username the user gave in their signup form.
	 */
	private String username;
	
	/**
	 * Displays the password reset page.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent receivedIntent = getIntent();
	    boolean isValidPassword = receivedIntent.getBooleanExtra(IS_VALID_PASSWORD, true);
	    boolean passwordsMatch = receivedIntent.getBooleanExtra(PASSWORDS_MATCH, true);
	    username = receivedIntent.getStringExtra(USERNAME);
	    setContentView(R.layout.activity_password_recovery_completed);
		// Displays corresponding errors if password is invalid
		if (!isValidPassword) {
	    	TextView error = (TextView) findViewById(R.id.invalidPasswordError);
	    	error.setVisibility(View.VISIBLE);
		// Displays error message if only the password and confirmation don't match
		} else if (!passwordsMatch) {
	    	TextView error = (TextView) findViewById(R.id.nonMatchingPasswordError);
	    	error.setVisibility(View.VISIBLE);
		}
		EditText passwordEditText = (EditText)findViewById(R.id.passwordInput);
		passwordEditText.requestFocus();
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
	 * Responds to the change password button click and sends the user input passwords to
	 * be validated.
	 * @param view The current view
	 */
	public void gotoLogin(View view) {
		Intent intent = new Intent(this, NewPasswordValidatorActivity.class);
		intent.putExtra(USERNAME, username);
		EditText passwordEditText = (EditText) findViewById(R.id.passwordInput);
		String password = passwordEditText.getText().toString().trim();
		intent.putExtra(PASSWORD, password);
		
		EditText passwordConfirmationEditText = (EditText) findViewById(R.id.confirmPasswordInput);
		String passwordConfirmation = passwordConfirmationEditText.getText().toString().trim();
		intent.putExtra(PASSWORD_CONFIRMATION, passwordConfirmation);
		startActivity(intent);
	}
}
