package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * NewPasswordValidatorActivity is an activity with no content, but serves as an intermediary step
 * to validate the correctness of the new password as well as if the new password and confirmation
 * password match before the user's password is reset.
 * @author Elijah Elefson (elefse)
 *
 */
public class NewPasswordValidatorActivity extends Activity {
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String PASSWORD_CONFIRMATION = "passwordConfirmation";
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
	    String username = receivedIntent.getStringExtra(USERNAME);
	    String password = receivedIntent.getStringExtra(PASSWORD);
	    String passwordConfirmation = receivedIntent.getStringExtra(PASSWORD_CONFIRMATION);
	    Intent intent;
	    // If password is valid and the password and confirmation password match
	    if (validPassword(password) && matchingPasswords(password, passwordConfirmation)) {
	    	// Checks for internet connectivity
	    	if (ConnectionChecker.hasConnection(this)) {
		    	// Updates login credentials on remote database
	        	ResetPasswordTask task = new ResetPasswordTask(username, password, passwordConfirmation, this);
	    		task.execute(username);
	    	} else {
	    		Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
	    	}
	    // If any of the above conditions are not true
	    } else {
	    	intent = new Intent(this, PasswordResetActivity.class);
	    	intent.putExtra(USERNAME, username);
	    	// Stores if the given password is valid
	    	intent.putExtra(IS_VALID_PASSWORD, validPassword(password));
	    	// Stores if the given password and confirmation password match
	    	intent.putExtra(PASSWORDS_MATCH, matchingPasswords(password, passwordConfirmation));
	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	startActivity(intent);
	 	   	finish();
		}
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

	private class ResetPasswordTask extends AsyncTask<String, Void, String> {
		private String un;
		private String pw;
		private String passwordConfirmation;
		private Context context;
		
		private ResetPasswordTask (String username, String password, String passwordConfirmation, Context context) {
			this.un = username;
			this.pw = password;
			this.passwordConfirmation = passwordConfirmation;
			this.context = context;
		}
		
	    /**
	     * Let's make the http request and return the result as a String.
	     */
	    protected String doInBackground(String... args) {
	        //the data to send
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("username", un));
	        postParameters.add(new BasicNameValuePair("password", pw));
	
	        //String valid = "1";
			String result = null;
	        
	        //http post
			String res;
	        try{
	        	result = CustomHttpClient.executeHttpPost("http://10.0.2.2/resetPassword.php", postParameters);  //Enter Your remote PHP,ASP, Servlet file link
	        	res = result.toString();  
	        	//res = res.trim();  
	        	res= res.replaceAll("\\s+","");  
	        	//error.setText(res);  
	        } catch (Exception e) {  
	        	//un.setText(e.toString()); 
	        	res = e.toString();
	        }
	        return res;
	    }
	 
	    /**
	     * Parse the String result, and create a new array adapter for the list
	     * view.
	     */
	    protected void onPostExecute(String result) {
	    	if (result.equals("1")) {  
	    		// Login succeeds, go to homepage.
	    		Intent intent = new Intent(context, LoginActivity.class);
	    		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	startActivity(intent);
	        	finish();
	    	} else {
	    		Intent intent = new Intent(context, PasswordResetActivity.class);
		    	// Stores if the given password is valid
		    	intent.putExtra(IS_VALID_PASSWORD, true);
		    	// Stores if the given password and confirmation password match
		    	intent.putExtra(PASSWORDS_MATCH, true);
		    	// Sends the input information back to the signup form so user doesn't have to reenter
				intent.putExtra(PASSWORD, pw);
				intent.putExtra(USERNAME, un);
				intent.putExtra(PASSWORD_CONFIRMATION, passwordConfirmation);
	    		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	startActivity(intent);
	        	finish(); 
	    	}
	    }
	}
}
