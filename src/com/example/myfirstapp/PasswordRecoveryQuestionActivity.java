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
import android.widget.TextView;
import android.widget.Toast;

/**
 * PasswordRecoveryQuestionActivity is an activity that gives the user their security question
 * and then allows them to answer it in order to determine if they can reset their password.
 * @author Elijah Elefson (elefse)
 *
 */
public class PasswordRecoveryQuestionActivity extends Activity {
	private static final String USERNAME = "username";
	private static final String QUESTION = "question";
	
	/**
	 * Stores the user's username.
	 */
	private String username;
	
	/**
	 * Stores the relevant security question for the user.
	 */
	private String question;
	
	/**
	 * Displays the password recovery security question page.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_recovery_question);
		
		Intent receivedIntent = getIntent();
		username = receivedIntent.getStringExtra(USERNAME);
		question = receivedIntent.getStringExtra(QUESTION);
		TextView securityQuestionTextView = (TextView)findViewById(R.id.securityQuestion);
		securityQuestionTextView.setText(question);
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
	 * Responds to the answer question button click and sends user to password reset page.
	 * @param view The current view
	 */
	public void gotoLogin(View view) {
		EditText answerEditText = (EditText) findViewById(R.id.questionInput);
		String answer = answerEditText.getText().toString().trim();
		
    	// Checks for internet connectivity
    	if (ConnectionChecker.hasConnection(this)) {
    		// Tests security question for user on remote database
    		CheckAnswerTask task = new CheckAnswerTask(username, question, answer, this);
			task.execute(answer);
    	} else {
    	   Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
    	}
	}

	private class CheckAnswerTask extends AsyncTask<String, Void, String> {
		private String un;
		private String question;
		private String answer;
		private Context context;
		
		private CheckAnswerTask (String username, String question, String answer, Context context) {
			this.un = username;
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
	        postParameters.add(new BasicNameValuePair("question", question));
	        postParameters.add(new BasicNameValuePair("answer", answer));

	        //String valid = "1";
			String result = null;
	        
	        //http post
			String res;
	        try{
	        	result = CustomHttpClient.executeHttpPost("http://homes.cs.washington.edu/~elefse/checkAnswer.php", postParameters);  //Enter Your remote PHP,ASP, Servlet file link
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
	    	TextView error = (TextView) findViewById(R.id.incorrectAnswerError);
        	if (result.equals("1")) {
        		Intent intent = new Intent(context, PasswordResetActivity.class);
        		intent.putExtra(USERNAME, un);
        		startActivity(intent);
        		//finish();
        	} else {
        		error.setVisibility(View.VISIBLE);  
        	}
	    } 
	}
}
