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
 * PasswordRecoveryActivity is an activity that provides a form that the user can use
 * in order to recover their password. (NOT YET IMPLEMENTED)
 * @author Elijah Elefson (elefse)
 *
 */
public class PasswordRecoveryActivity extends Activity {
	private static final String USERNAME = "username";
	
	/**
	 * Displays the password recovery page.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_recovery);
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
	 * Responds to the recover password button click and goes to the security question page.
	 * @param view The current view
	 */
	public void gotoSecurityQuestion(View view) {
        Intent intent = new Intent(this, PasswordRecoveryQuestionActivity.class);
		EditText usernameEditText = (EditText) findViewById(R.id.usernameInput);
		String username = usernameEditText.getText().toString().trim();
		intent.putExtra(USERNAME, username);
		startActivity(intent);
	}
	
	/**
	 * Responds to the cancel button click and returns to the login page.
	 * @param view The current view
	 */
	public void gotoLogin(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
}
