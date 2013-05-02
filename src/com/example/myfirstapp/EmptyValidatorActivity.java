package com.example.myfirstapp;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

public class EmptyValidatorActivity extends Activity {
	private static final String PASSWORD_PATTERN = 
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Intent receivedIntent = getIntent();
	    String firstName = receivedIntent.getStringExtra("firstName");
	    String lastName = receivedIntent.getStringExtra("lastName");
	    String username = receivedIntent.getStringExtra("username");
	    String email = receivedIntent.getStringExtra("email");
	    String password = receivedIntent.getStringExtra("password");
	    String passwordConfirmation = receivedIntent.getStringExtra("passwordConfirmation");
	    Intent intent;
	    if (validEmail(email) && validPassword(password) && matchingPasswords(password, passwordConfirmation)) { //everything passes
	        // THIS IS THE MAIN SUCCESS SCENARIO. AT THIS POINT WE SHOULD SAVE THE INPUT TO THE DATABASE
	    	intent = new Intent(this, LoginActivity.class);
	    } else { //if something does not pass
	    	intent = new Intent(this, SignupActivity.class);
	    	intent.putExtra("isFirstView", false);
	    	intent.putExtra("isValidEmail", validEmail(email));
	    	intent.putExtra("isValidPassword", validPassword(password));
	    	intent.putExtra("passwordsMatch", matchingPasswords(password, passwordConfirmation));
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
	
	private boolean validEmail(String email) {
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		return pattern.matcher(email).matches();
	}
	
	private boolean validPassword(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		return pattern.matcher(password).matches();
	}
	
	private boolean matchingPasswords(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}
}
