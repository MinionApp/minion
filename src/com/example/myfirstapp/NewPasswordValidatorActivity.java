package com.example.myfirstapp;

import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

/**
 * NewPasswordValidatorActivity is an activity with no content, but serves as an intermediary step
 * to validate the correctness of the new password as well as if the new password and confirmation
 * password match before the user's password is reset.
 * @author Elijah Elefson (elefse)
 *
 */
public class NewPasswordValidatorActivity extends Activity {
	private static final String PASSWORD = "password";
	private static final String PASSWORD_CONFIRMATION = "passwordConfirmation";
	private static final String IS_FIRST_VIEW = "isFirstView";
	private static final String IS_VALID_PASSWORD = "isValidPassword";
	private static final String PASSWORDS_MATCH = "passwordsMatch";
	
	/**
	 *  The regex that will only allow string with the following qualities:
	 *  	1.) Must be at least 8 characters long
	 *  	2.) Must contains at least 1 numerical digit
	 *  	3.) Must contain at least 1 lowercase character
	 *  	4.) Must contain at least 1 uppercase character
	 *  	5.) Must contain at least 1 of the special symbols @#$%^&+=
	 *  	6.) Must contain no spaces
	 */
	private static final String PASSWORD_PATTERN = 
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	
	/**
	 * Directs the intent to the correct activity based on the validity of the
	 * passwords given to the password recovery form.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Intent receivedIntent = getIntent();
	    // Receives the data the user input into the password recovery form
	    String password = receivedIntent.getStringExtra(PASSWORD);
	    String passwordConfirmation = receivedIntent.getStringExtra(PASSWORD_CONFIRMATION);
	    Intent intent;
	    // If password is valid and the password and confirmation password match
	    if (validPassword(password) && matchingPasswords(password, passwordConfirmation)) {
	    	intent = new Intent(this, LoginActivity.class);
	    // If any of the above conditions are not true
	    } else {
	    	intent = new Intent(this, PasswordResetActivity.class);
	    	// Determines if the user should see a blank signup form 
	    	intent.putExtra(IS_FIRST_VIEW, false);
	    	// Stores if the given password is valid
	    	intent.putExtra(IS_VALID_PASSWORD, validPassword(password));
	    	// Stores if the given password and confirmation password match
	    	intent.putExtra(PASSWORDS_MATCH, matchingPasswords(password, passwordConfirmation));
		}
	    startActivity(intent);
	    finish();
	    // note we never called setContentView()
	}
	
	/**
	 * Checks that the given password is a valid password.
	 * @param password The password the user input.
	 * @return true if password is formatted correctly, false otherwise.
	 */
	private boolean validPassword(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		return pattern.matcher(password).matches();
	}
	
	/**
	 * Checks that the given password and the given confirmation password match one another.
	 * @param password The password the user input
	 * @param passwordConfirmation The confirmation password the user input
	 * @return true if password and confirmation password match, false otherwise.
	 */
	private boolean matchingPasswords(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}

}
