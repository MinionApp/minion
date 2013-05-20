package uw.cse403.minion;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

/**
 * EmptyValidatorActivity is an activity with no content, but serves as an intermediary step
 * to validate the correctness of the email and password as well as if the password and
 * confirmation password match.
 * @author Elijah Elefson (elefse)
 */
public class EmptyValidatorActivity extends Activity {
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/signup.php";
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
	    //setContentView(R.layout.activity_waiting);
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
	
	
	
	/**
	 * SignupTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It uploads the data given by the
	 * user in the signup form to the remote database if the username they chose is not
	 * already in use and then directs them to the correct Activity. 
	 */
	private class SignupTask extends AsyncTask<String, Void, String> {
		private String un;
		private String pw;
		private String passwordConfirmation;
		private String question;
		private String answer;
		private Context context;
		
		/**
		 * Constructs a new SignupTask object.
		 * @param username The user given username
		 * @param password The user given password
		 * @param passwordConfirmation The user given password confirmation
		 * @param question The user given security question
		 * @param answer The user given security answer
		 * @param context The current Activity's context
		 */
		private SignupTask (String username, String password, String passwordConfirmation, String question, String answer, Context context) {
			this.un = username;
			this.pw = password;
			this.passwordConfirmation = passwordConfirmation;
			this.question = question;
			this.answer = answer;
			this.context = context;
		}
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected String doInBackground(String... args) {
	        //the data to send
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("username", un));
	        postParameters.add(new BasicNameValuePair("password", pw));
	        postParameters.add(new BasicNameValuePair("question", question));
	        postParameters.add(new BasicNameValuePair("answer", answer));

			String result = null;
	        
	        //http post
			String res;
	        try{
	        	result = CustomHttpClient.executeHttpPost(PHP_ADDRESS, postParameters);
	        	res = result.toString();    
	        	res = res.replaceAll("\\s+", "");   
	        } catch (Exception e) {   
	        	res = e.toString();
	        }
	        return res;
	    }
	 
	    /**
	     * Parses the String result and directs to the correct Activity
	     */
	    protected void onPostExecute(String result) {
        	if (result.equals("1")) {  
        		// Login succeeds, go to homepage.
        		Intent intent = new Intent(context, LoginActivity.class);
        		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(intent);
            	finish();
        	} else {
        		Intent intent = new Intent(context, SignupActivity.class);
    	    	// Stores if the given password is valid
    	    	intent.putExtra(IS_VALID_PASSWORD, true);
    	    	// Stores if the given password and confirmation password match
    	    	intent.putExtra(PASSWORDS_MATCH, true);
    	    	// Sends the input information back to the signup form so user doesn't have to reenter
    			intent.putExtra(PASSWORD, pw);
    			intent.putExtra(USERNAME, un);
    			intent.putExtra(PASSWORD_CONFIRMATION, passwordConfirmation);
    			intent.putExtra(QUESTION, question);
    			intent.putExtra(ANSWER, answer);
    			// If username is already in use
    			intent.putExtra(USERNAME_IN_USE, true);
        		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(intent);
            	finish(); 
        	}
	    }
	 
	}
}
