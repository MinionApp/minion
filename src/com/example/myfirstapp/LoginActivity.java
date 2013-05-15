package com.example.myfirstapp;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * LoginActivity is an activity that provides the user with a basic login form. It also gives
 * them the ability to choose to remain logged in, recover their password or signup for the
 * first time. If the user has chosen to remain logged in this activity is bypassed and they
 * are directed to the home page.
 * @author Elijah Elefson (elefse)
 */
public class LoginActivity extends Activity {
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/checkLogin.php";
	
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
		if (ConnectionChecker.hasConnection(this)) {
			// Get user login info.
			EditText usernameEditText = (EditText) findViewById(R.id.username_input);
			EditText passwordEditText = (EditText) findViewById(R.id.password_input);
			//TextView error = (TextView) findViewById(R.id.error);
			String un = usernameEditText.getText().toString().trim();
			String pw = passwordEditText.getText().toString().trim();
			
			CheckLoginTask task = new CheckLoginTask(un, pw, keepLoggedIn, this);
			task.execute(un);
    	} else {
    		Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
    	}
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
	
	/**
	 * CheckLoginTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It checks if the user provided 
	 * login credentials are correct and then directs to the correct Activity based on the
	 * result.
	 */
	private class CheckLoginTask extends AsyncTask<String, Void, String> {
		private String un;
		private String pw;
		private boolean keepLoggedIn;
		private Context context;
		
		/**
		 * Constructs a new CheckLoginTask object.
		 * @param username The user given username
		 * @param password The user given password
		 * @param keepLoggedIn If the user has selected to remain logged in or not
		 * @param context The current Activity's context
		 */
		private CheckLoginTask (String username, String password, boolean keepLoggedIn, Context context) {
			this.un = username;
			this.pw = password;
			this.keepLoggedIn = keepLoggedIn;
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
	    	TextView error = (TextView) findViewById(R.id.error);
        	if (result.equals("1")) {  
        		// Stores the username into preferences.
        		if (keepLoggedIn) {
        			SaveSharedPreference.setUserName(context, un);
        		}
        		// Login succeeds, go to homepage.
        		Intent intent = new Intent(context, HomeActivity.class);
        		startActivity(intent);
        		finish();
        	} else {
        		error.setVisibility(View.VISIBLE); 
        	}
	    }
	 
	}
}
