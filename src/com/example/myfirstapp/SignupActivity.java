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

/**
 * SignupActivity is an activity that provides a signup form for the user. It will respond to user
 * input and display relevant error messages on invalid input and preserve valid input between
 * attempts.
 * @author Elijah Elefson (elefse)
 *
 */
public class SignupActivity extends Activity{
	
	/**
	 * Displays the signup form in various states depending on the validity of the input and 
	 * whether the user has viewed the form before.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent receivedIntent = getIntent();
		// If the page is being viewed for the first time isFirstView will be true, otherwise false
		boolean isFirstView = receivedIntent.getBooleanExtra("isFirstView", true);
	    boolean isValidEmail = receivedIntent.getBooleanExtra("isValidEmail", false);
	    boolean isValidPassword = receivedIntent.getBooleanExtra("isValidPassword", false);
	    boolean passwordsMatch = receivedIntent.getBooleanExtra("passwordsMatch", false);
	    // Displays blank signup page if it is the user's first viewing of the page
	    if (isFirstView) {
	    	setContentView(R.layout.activity_signup);
	    // Displays an alternate signup page with data between submissions preserved and relevant
	    // error messages shown
	    } else {
	    	// Displays corresponding errors if neither email nor password are valid
			if (!isValidEmail && !isValidPassword) {
				setContentView(R.layout.activity_signup_invalid_email_and_password);
				EditText emailEditText = (EditText)findViewById(R.id.emailInput);
				emailEditText.requestFocus();
			// Displays corresponding error if only email is invalid and password and confirmation
			// password may not match
			} else if (!isValidEmail) {
				// Displays error for if password and confirmation don't match
				if (!passwordsMatch) {
					setContentView(R.layout.activity_signup_invalid_email_and_non_matching_passwords);
				// Displays no error if password and confirmation do match
				} else {
					setContentView(R.layout.activity_signup_invalid_email_and_matching_passwords);
					EditText passwordEditText = (EditText)findViewById(R.id.passwordInput);
					passwordEditText.setText(receivedIntent.getStringExtra("password"), EditText.BufferType.EDITABLE);
					EditText passwordConfirmationEditText = (EditText)findViewById(R.id.confirmPasswordInput);
					passwordConfirmationEditText.setText(receivedIntent.getStringExtra("passwordConfirmation"), EditText.BufferType.EDITABLE);
				}
				EditText emailEditText = (EditText)findViewById(R.id.emailInput);
				emailEditText.requestFocus();
			// Displays error message if only the password is invalid
			} else if (!isValidPassword) {
				setContentView(R.layout.activity_signup_invalid_password);
				EditText emailEditText = (EditText)findViewById(R.id.emailInput);
				emailEditText.setText(receivedIntent.getStringExtra("email"), EditText.BufferType.EDITABLE);
				EditText passwordEditText = (EditText)findViewById(R.id.passwordInput);
				passwordEditText.requestFocus();
			// Displays error message if only the password and confirmation don't match
			} else if (!passwordsMatch) {
				setContentView(R.layout.activity_signup_non_matching_passwords);
				EditText emailEditText = (EditText)findViewById(R.id.emailInput);
				emailEditText.setText(receivedIntent.getStringExtra("email"), EditText.BufferType.EDITABLE);
				EditText passwordEditText = (EditText)findViewById(R.id.passwordInput);
				passwordEditText.requestFocus();
			}
			EditText usernameEditText = (EditText)findViewById(R.id.usernameInput);
			usernameEditText.setText(receivedIntent.getStringExtra("username"), EditText.BufferType.EDITABLE);
	    }
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
	 * Responds to the signup button click and sends the information that has been input
	 * to be validated.
	 * @param view The current view
	 */
	public void gotoValidator(View view) {
		Intent intent = new Intent(this, EmptyValidatorActivity.class);
		
		EditText usernameEditText = (EditText) findViewById(R.id.usernameInput);
		String username = usernameEditText.getText().toString().trim();
		intent.putExtra("username", username);
		
		EditText emailEditText = (EditText) findViewById(R.id.emailInput);
		String email = emailEditText.getText().toString().trim();
		intent.putExtra("email", email);
		
		EditText passwordEditText = (EditText) findViewById(R.id.passwordInput);
		String password = passwordEditText.getText().toString().trim();
		intent.putExtra("password", password);
		
		EditText passwordConfirmationEditText = (EditText) findViewById(R.id.confirmPasswordInput);
		String passwordConfirmation = passwordConfirmationEditText.getText().toString().trim();
		intent.putExtra("passwordConfirmation", passwordConfirmation);
		startActivity(intent);
	}

}
