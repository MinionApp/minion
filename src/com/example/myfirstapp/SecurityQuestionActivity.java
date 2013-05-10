package com.example.myfirstapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * SecurityQuestionActivity is an activity that provides a security question and answer
 * form for the user.
 * @author Elijah Elefson (elefse)
 *
 */
public class SecurityQuestionActivity extends Activity {
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String PASSWORD_CONFIRMATION = "passwordConfirmation";
	
	/**
	 * Stores the username the user gave in their signup form.
	 */
	private String username;
	
	/**
	 * Stores the password the user gave in their signup form.
	 */
	private String password;
	
	/**
	 * Stores the confirmation password the user gave in their signup form.
	 */
	private String confirmationPassword;
	
	/**
	 * Displays the security question selection page.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent receivedIntent = getIntent();
		username = receivedIntent.getStringExtra(USERNAME);
		password = receivedIntent.getStringExtra(PASSWORD);
		confirmationPassword = receivedIntent.getStringExtra(PASSWORD_CONFIRMATION);
		setContentView(R.layout.activity_security_question);
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
	 * Responds to the signup button click and returns user to login page.
	 * @param view The current view
	 */
	public void gotoLogin(View view) {
		Intent intent;
		Spinner securityQuestions = (Spinner) findViewById(R.id.securityQuestionSpinner);
		// Gives a string representation of whatever item is selected in the spinner
		String question = securityQuestions.getSelectedItem().toString();
		
		EditText answerEditText = (EditText) findViewById(R.id.securityAnswerInput);
		String answer = answerEditText.getText().toString().trim();
		
    	// Checks for internet connectivity
    	if (ConnectionChecker.hasConnection(this)) {
    		// Updates security question on remote database
        	SignupTask task = new SignupTask(username, password, question, answer, this);
    		task.execute(username);
    	} else {
    	    Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
    	    //intent = new Intent(this, SecurityQuestionActivity.class);
    	    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	    //finish();
    	}
	}
	
	private class SignupTask extends AsyncTask<String, Void, String> {
		private String un;
		private String pw;
		private String question;
		private String answer;
		private Context context;
		
		private SignupTask (String username, String password, String question, String answer, Context context) {
			this.un = username;
			this.pw = password;
			this.question = question;
			this.answer = answer;
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
	        	result = CustomHttpClient.executeHttpPost("http://10.0.2.2/checkLogin.php", postParameters);  //Enetr Your remote PHP,ASP, Servlet file link
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
	    	TextView error = (TextView) findViewById(R.id.error);
        	if (result.equals("1")) {  
        		error.setText("Correct Username or Password");
        		// Login succeeds, go to homepage.
        		Intent intent = new Intent(context, LoginActivity.class);
        		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(intent);
            	finish();
        	} else {
        		error.setText("Username already in use. Please choose another!");  
        	}
	    }
	 
	}

}
