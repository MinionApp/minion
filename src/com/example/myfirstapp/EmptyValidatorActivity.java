package com.example.myfirstapp;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

/**
 * EmptyValidatorActivity is an activity with no content, but serves as an intermediary step
 * to validate the correctness of the email and password as well as if the password and
 * confirmation password match.
 * @author Elijah Elefson (elefse)
 *
 */
public class EmptyValidatorActivity extends Activity {
	
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
	 * Directs the intent to the correct activity based on the validity of what
	 * has been input in the signup form.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Intent receivedIntent = getIntent();
	    // Receives the data the user input into the signup form
	    String firstName = receivedIntent.getStringExtra("firstName");
	    String lastName = receivedIntent.getStringExtra("lastName");
	    String username = receivedIntent.getStringExtra("username");
	    String email = receivedIntent.getStringExtra("email");
	    String password = receivedIntent.getStringExtra("password");
	    String passwordConfirmation = receivedIntent.getStringExtra("passwordConfirmation");
	    Intent intent;
	    // If email is valid, password is valid, and the password and confirmation password match
	    if (validEmail(email) && validPassword(password) && 
	    		matchingPasswords(password, passwordConfirmation)) {
	    	intent = new Intent(this, LoginActivity.class);
	    // If any of the above conditions are not true
	    } else {
	    	intent = new Intent(this, SignupActivity.class);
	    	// Determines if the user should see a blank signup form 
	    	intent.putExtra("isFirstView", false);
	    	// Stores if the given email is valid
	    	intent.putExtra("isValidEmail", validEmail(email));
	    	// Stores if the given password is valid
	    	intent.putExtra("isValidPassword", validPassword(password));
	    	// Stores if the given password and confirmation password match
	    	intent.putExtra("passwordsMatch", matchingPasswords(password, passwordConfirmation));
	    	// Sends the input information back to the signup form so user doesn't have to reenter
			intent.putExtra("email", email);
			intent.putExtra("password", password);
			intent.putExtra("firstName", firstName);
			intent.putExtra("lastName", lastName);
			intent.putExtra("username", username);
			intent.putExtra("passwordConfirmation", passwordConfirmation);
		}
	    startActivity(intent);
	    finish();
	    // note we never called setContentView()
	}
	
	/**
	 * Checks that the given email is a valid email address.
	 * @param email The email address the user input.
	 * @return true if email is formatted correctly, false otherwise.
	 */
	private boolean validEmail(String email) {
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		return pattern.matcher(email).matches();
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
