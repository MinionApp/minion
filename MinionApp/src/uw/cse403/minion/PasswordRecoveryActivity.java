package uw.cse403.minion;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * PasswordRecoveryActivity is an activity that provides a form that the user inputs
 * their username into to get their corresponding security question and continue
 * on to the next stage of the password recovery process.
 * @author Elijah Elefson (elefse)
 */
public class PasswordRecoveryActivity extends Activity {
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/getSecurityQuestion.php";
	private static final String USERNAME = "username";
	private static final String QUESTION = "question";
	
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
		if (ConnectionChecker.hasConnection(this)) {
			// Get user login info.
			EditText usernameEditText = (EditText) findViewById(R.id.username_input);
			String un = usernameEditText.getText().toString().trim();
			
			GetSecurityQuestionTask task = new GetSecurityQuestionTask(un, this);
			task.execute(un);
    	} else {
    		Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
    	}
	}
	
	/**
	 * Responds to the cancel button click and returns to the login page.
	 * @param view The current view
	 */
	public void gotoLogin(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	/**
	 * GetSecurityQuestionTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It takes the given username and retrieves the
	 * corresponding security question for that user from the remote database. This information
	 * is then passed on to the next Activity.
	 */
	private class GetSecurityQuestionTask extends AsyncTask<String, Void, String> {
		private String un;
		private Context context;
		
		/**
		 * Constructs a new GetSecurityQuestionTask object.
		 * @param username The user given username
		 * @param context The current Activity's context
		 */
		private GetSecurityQuestionTask (String username, Context context) {
			this.un = username;
			this.context = context;
		}
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected String doInBackground(String... args) {
	        //the data to send
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("username", un));

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
	    	TextView error = (TextView) findViewById(R.id.username_error);
        	if (result.equals("0")) {
        		error.setVisibility(View.VISIBLE); 
        	} else { 		
        		Intent intent = new Intent(context, PasswordRecoveryQuestionActivity.class);
        		intent.putExtra(USERNAME, un);
        		intent.putExtra(QUESTION, result.replace("_", " ")); //to fix question string
        		startActivity(intent);
        	}
	    } 
	}
}
