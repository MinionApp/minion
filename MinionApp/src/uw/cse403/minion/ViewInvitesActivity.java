package uw.cse403.minion;

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
import android.widget.Button;
import android.widget.TextView;

/**
 * HomeActivity is the activity that provides the user with the home page for the application.
 * This page is displayed after all login activities have been completed or bypassed. This page
 * also provides the means to get the the character management page, group management page, settings
 * page and the logout option.
 * @author Elijah Elefson (elefse)
 *
 */
public class ViewInvitesActivity extends Activity {
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/getInvites.php";
	private String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_invites);
		username = SaveSharedPreference.getPersistentUserName(ViewInvitesActivity.this);
		getInvitesTask task = new getInvitesTask(this);
		task.execute(username);
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
	 * SendInvitesTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It updates the database to include the
	 * specified players as part of a group and puts the current as the game master.
	 */
	private class getInvitesTask extends AsyncTask<String, Void, String> {
		private Context context;
		
		/**
		 * Constructs a new GetNumberOfInvitesTask object.
		 * @param context The current Activity's context.
		 */
		private getInvitesTask (Context context) {
			this.context = context;
		}
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected String doInBackground(String... args) {
	        //the data to send
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("un", username));
	        

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
	    	TextView inviteTextView = (TextView) findViewById(R.id.invite1);
			inviteTextView.setText(result);
	    }
	 
	}
}
