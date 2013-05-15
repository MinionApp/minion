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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * SignupActivity is an activity that provides a signup form for the user. It will respond to user
 * input and display relevant error messages on invalid input and preserve valid input between
 * attempts.
 * @author Elijah Elefson (elefse)
 */
public class SignupActivity extends Activity{
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String PASSWORD_CONFIRMATION = "passwordConfirmation";
	private static final String QUESTION = "question";
	private static final String ANSWER = "answer";
	private static final String IS_VALID_PASSWORD = "isValidPassword";
	private static final String PASSWORDS_MATCH = "passwordsMatch";
	private static final String USERNAME_IN_USE = "usernameInUse";
	
	/**
	 * Displays the signup form in various states depending on the validity of the input and 
	 * whether the user has viewed the form before.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent receivedIntent = getIntent();
	    boolean isValidPassword = receivedIntent.getBooleanExtra(IS_VALID_PASSWORD, true);
	    boolean passwordsMatch = receivedIntent.getBooleanExtra(PASSWORDS_MATCH, true);
	    boolean usernameInUse = receivedIntent.getBooleanExtra(USERNAME_IN_USE, false);
	    
		setContentView(R.layout.activity_signup);
	    if (usernameInUse) {
			EditText usernameEditText = (EditText)findViewById(R.id.username_input);
			usernameEditText.requestFocus();
	    	TextView error = (TextView) findViewById(R.id.username_error);
	    	error.setVisibility(View.VISIBLE);
			EditText passwordEditText = (EditText)findViewById(R.id.password_input);
			passwordEditText.setText(receivedIntent.getStringExtra(PASSWORD), EditText.BufferType.EDITABLE);
			EditText passwordConfirmationEditText = (EditText)findViewById(R.id.confirm_password_input);
			passwordConfirmationEditText.setText(receivedIntent.getStringExtra("passwordConfirmation"), EditText.BufferType.EDITABLE);
	    // Displays an alternate signup page with data between submissions preserved and relevant
	    // error messages shown
	    } else {
			// Displays error message if only the password is invalid
			if (!isValidPassword) {
		    	TextView error = (TextView) findViewById(R.id.invalid_password_error);
		    	error.setVisibility(View.VISIBLE);
				EditText passwordEditText = (EditText)findViewById(R.id.password_input);
				passwordEditText.requestFocus();
			// Displays error message if only the password and confirmation don't match
			} else if (!passwordsMatch) {
		    	TextView error = (TextView) findViewById(R.id.nonmatching_password_error);
		    	error.setVisibility(View.VISIBLE);
				EditText passwordEditText = (EditText)findViewById(R.id.password_input);
				passwordEditText.requestFocus();
			}
			EditText usernameEditText = (EditText)findViewById(R.id.username_input);
			usernameEditText.setText(receivedIntent.getStringExtra(USERNAME), EditText.BufferType.EDITABLE);
	    }
		Spinner securityQuestions = (Spinner) findViewById(R.id.security_question_spinner);
		ArrayAdapter<String> myAdap = (ArrayAdapter<String>) securityQuestions.getAdapter();
		int spinnerPosition = myAdap.getPosition(receivedIntent.getStringExtra(QUESTION));
		//set the default according to value
		securityQuestions.setSelection(spinnerPosition);
		
		EditText answerEditText = (EditText) findViewById(R.id.security_answer_input);
		answerEditText.setText(receivedIntent.getStringExtra(ANSWER), EditText.BufferType.EDITABLE);
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
	 * Responds to the next button click and sends the information that has been input
	 * to be validated.
	 * @param view The current view
	 */
	public void gotoValidator(View view) {
		Intent intent = new Intent(this, EmptyValidatorActivity.class);
		
		EditText usernameEditText = (EditText) findViewById(R.id.username_input);
		String username = usernameEditText.getText().toString().trim();
		intent.putExtra(USERNAME, username);
		
		EditText passwordEditText = (EditText) findViewById(R.id.password_input);
		String password = passwordEditText.getText().toString().trim();
		intent.putExtra(PASSWORD, password);
		
		EditText passwordConfirmationEditText = (EditText) findViewById(R.id.confirm_password_input);
		String passwordConfirmation = passwordConfirmationEditText.getText().toString().trim();
		intent.putExtra(PASSWORD_CONFIRMATION, passwordConfirmation);
		
		Spinner securityQuestions = (Spinner) findViewById(R.id.security_question_spinner);
		// Gives a string representation of whatever item is selected in the spinner
		String question = securityQuestions.getSelectedItem().toString();
		intent.putExtra(QUESTION, question);
		
		EditText answerEditText = (EditText) findViewById(R.id.security_answer_input);
		String answer = answerEditText.getText().toString().trim();
		intent.putExtra(ANSWER, answer);
		
		startActivity(intent);
		finish();
	}
}
