package uw.cse403.minion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * NewPasswordValidatorActivity is an activity with no content, but serves as an intermediary step
 * to validate the correctness of the new password as well as if the new password and confirmation
 * password match before the user's password is reset.
 * @author Elijah Elefson (elefse)
 */
public class NewPasswordValidatorActivity extends Activity {
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String PASSWORD_CONFIRMATION = "passwordConfirmation";
	private static final String IS_VALID_PASSWORD = "isValidPassword";
	private static final String PASSWORDS_MATCH = "passwordsMatch";


	/**
	 * Directs the intent to the correct activity based on the validity of the
	 * passwords given to the password recovery form.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent receivedIntent = getIntent();
		// Receives the data the user input into the password recovery form
		String username = receivedIntent.getStringExtra(USERNAME);
		String password = receivedIntent.getStringExtra(PASSWORD);
		String passwordConfirmation = receivedIntent.getStringExtra(PASSWORD_CONFIRMATION);
		AccountUtils account = new AccountUtils();
		Intent intent;
		boolean valid = account.validPassword(password);
		boolean match = account.matchingPasswords(password, passwordConfirmation);
		// If password is valid and the password and confirmation password match
		if (valid && match) {
			// Checks for internet connectivity
			if (ConnectionChecker.hasConnection(this)) {
				// Updates login credentials on remote database
				boolean success = account.resetPassword(username, password);
				if (success) {
					// reset succeeds, go to homepage.
					intent = new Intent(this, LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				} else {
					intent = new Intent(this, PasswordResetActivity.class);
			    	// Stores if the given password is valid
			    	intent.putExtra(IS_VALID_PASSWORD, true);
			    	// Stores if the given password and confirmation password match
			    	intent.putExtra(PASSWORDS_MATCH, true);
			    	// Sends the input information back to the signup form so user doesn't have to reenter
					intent.putExtra(PASSWORD, password);
					intent.putExtra(USERNAME, username);
					intent.putExtra(PASSWORD_CONFIRMATION, passwordConfirmation);
		    		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        	startActivity(intent);
		        	finish(); 
				}
			} else {
				Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
			}
			// If any of the above conditions are not true
		} else {
			intent = new Intent(this, PasswordResetActivity.class);
			intent.putExtra(USERNAME, username);
			// Stores if the given password is valid
			intent.putExtra(IS_VALID_PASSWORD, valid);
			// Stores if the given password and confirmation password match
			intent.putExtra(PASSWORDS_MATCH, match);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
		// note we never called setContentView()
	}

}
