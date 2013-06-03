package uw.cse403.minion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * EmptyValidatorActivity is an activity with no content, but serves as an intermediary step
 * to validate the correctness of the email and password as well as if the password and
 * confirmation password match.
 * @author Elijah Elefson (elefse)
 */
public class EmptyValidatorActivity extends Activity {
	
	/** Class constants for string representations **/
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String PASSWORD_CONFIRMATION = "passwordConfirmation";
	private static final String QUESTION = "question";
	private static final String ANSWER = "answer";
	private static final String IS_VALID_PASSWORD = "isValidPassword";
	private static final String PASSWORDS_MATCH = "passwordsMatch";
	private static final String USERNAME_IN_USE = "usernameInUse";
	private static final String IS_VALID_USERNAME = "isValidUsername";

	/**
	 * Directs the intent to the correct activity based on the validity of what
	 * has been input in the signup form.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent receivedIntent = getIntent();
		// Receives the data the user input into the signup form
		String username = receivedIntent.getStringExtra(USERNAME);
		String password = receivedIntent.getStringExtra(PASSWORD);
		String passwordConfirmation = receivedIntent.getStringExtra(PASSWORD_CONFIRMATION);
		String question = receivedIntent.getStringExtra(QUESTION);
		String answer = receivedIntent.getStringExtra(ANSWER);
		AccountUtils validator = new AccountUtils();
		Intent intent;
		boolean validPassword = validator.validPassword(password);
		boolean match = validator.matchingPasswords(password, passwordConfirmation);
		boolean validUsername = validator.validUsername(username);
		// If email is valid, password is valid, and the password and confirmation password match
		if (validPassword && match && validUsername) {
			// Checks for internet connectivity
			if (ConnectionChecker.hasConnection(this)) {
				// Updates login credentials on remote database
				boolean success = validator.startSignupTask(username, password, passwordConfirmation,
						question, answer);
				if (success) {
					intent = new Intent(this,LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				} else {
					intent = new Intent(this, SignupActivity.class);
					// Stores if the given password is valid
					intent.putExtra(IS_VALID_PASSWORD, true);
					// Stores if the given password and confirmation password match
					intent.putExtra(PASSWORDS_MATCH, true);
					// Stores if the given username is valid
					intent.putExtra(IS_VALID_USERNAME, true);
					// Sends the input information back to the signup form so user doesn't have to reenter
					intent.putExtra(PASSWORD, password);
					intent.putExtra(USERNAME, username);
					intent.putExtra(PASSWORD_CONFIRMATION, passwordConfirmation);
					intent.putExtra(QUESTION, question);
					intent.putExtra(ANSWER, answer);
					// If username is already in use
					intent.putExtra(USERNAME_IN_USE, true);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish(); 
				}
			} else {
				Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
				intent = new Intent(this, SignupActivity.class);
				// Stores if the given password is valid
				intent.putExtra(IS_VALID_PASSWORD, validPassword);
				// Stores if the given password and confirmation password match
				intent.putExtra(PASSWORDS_MATCH, match);
				// Stores if the given username is valid
				intent.putExtra(IS_VALID_USERNAME, validUsername);
				// Sends the input information back to the signup form so user doesn't have to reenter
				intent.putExtra(PASSWORD, password);
				intent.putExtra(USERNAME, username);
				intent.putExtra(PASSWORD_CONFIRMATION, passwordConfirmation);
				intent.putExtra(QUESTION, question);
				intent.putExtra(ANSWER, answer);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
			// If any of the above conditions are not true
		} else {
			intent = new Intent(this, SignupActivity.class);
			// Stores if the given password is valid
			intent.putExtra(IS_VALID_PASSWORD, validPassword);
			// Stores if the given password and confirmation password match
			intent.putExtra(PASSWORDS_MATCH, match);
			// Stores if the given username is valid
			intent.putExtra(IS_VALID_USERNAME, validUsername);
			// Sends the input information back to the signup form so user doesn't have to reenter
			intent.putExtra(PASSWORD, password);
			intent.putExtra(USERNAME, username);
			intent.putExtra(PASSWORD_CONFIRMATION, passwordConfirmation);
			intent.putExtra(QUESTION, question);
			intent.putExtra(ANSWER, answer);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
		// note we never called setContentView()
	}
}
