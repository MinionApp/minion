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
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * LoginActivity is an activity that provides the user with a basic login form. It also gives
 * them the ability to choose to remain logged in, recover their password or signup for the
 * first time. If the user has chosen to remain logged in this activity is bypassed and they
 * are directed to the home page.
 * @author Elijah Elefson (elefse)
 *
 */
public class LoginActivity extends Activity {
	/**
	 * Stores if the user has selected to remain logged in.
	 */
	private boolean keepLoggedIn;
	
	/**
	 * Displays the login page if the user has not chosen to remain logged in or if it is their
	 * first time logging in. Otherwise it displays the home page if they have chosen to remain
	 * logged.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Intent intent;
        // If the user has not chosen to remain logged in, sends to login page
        if (SaveSharedPreference.getUserName(LoginActivity.this).length() == 0) {
    		setContentView(R.layout.activity_login);
    	// If the user has chosen to remain logged in, sends to home page
        } else {
        	intent = new Intent(this, HomeActivity.class);
    	    startActivity(intent);
    	    finish();
        }
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
	 * Responds to the login button click and goes to the home page while also storing if the user
	 * has specified whether or not they wish to remain logged in.
	 * @param view The current view
	 */
	public void gotoHomepage(View view) {
		//Intent intent; // next activity to be set.
		
		// Get user login info.
		EditText usernameEditText = (EditText) findViewById(R.id.usernameInput);
		EditText passwordEditText = (EditText) findViewById(R.id.passwordInput);
		String username = usernameEditText.getText().toString().trim();
		String password = passwordEditText.getText().toString().trim();
		// Login succeeds, go to homepage.
		RemoteDbAccess.loginAttempt(username, password, keepLoggedIn, "login", this);
	}
	
	/**
	 * Responds to the signup button click and goes to the signup page.
	 * @param view The current view
	 */
	public void gotoSignup(View view) {
		Intent intent = new Intent(this, SignupActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Responds to the recover password button click and goes to the password recovery page.
	 * @param view The current view
	 */
	public void gotoPasswordRecovery(View view) {
		Intent intent = new Intent(this, PasswordRecoveryActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Responds to the toggling of the remember me checkbox and stores a boolean based on
	 * whether or not the box is checked. True if checked, false otherwise.
	 * @param view The current view
	 */
	public void keepLoggedIn(View view) {
	    // Is the view now checked?
		keepLoggedIn = ((CheckBox) view).isChecked();
	}
}
