package uw.cse403.minion;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * HomeActivity is the activity that provides the user with the home page for the application.
 * This page is displayed after all login activities have been completed or bypassed. This page
 * also provides the means to get the the character management page, group management page, and
 * the logout option.
 * @author Kevin Dong (kevinxd3)
 */
public class HomeActivity extends Activity {

	/** Class constants for string representations **/
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/getNumberOfInvites.php";

	/** The current user's username **/
	private String username;

	/*
	 * Testing Results:
	 * The top two expensive operations of this code were generating a bitmap to be drawn and 
	 * DDMS. Rest assured, DDMS checks are turned off for the actual APK, for performance.
	 * Inclusive CPU time similarly reflects the graphical emphasis.
	 */
	/**
	 * Displays the home page.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (TraceControl.TRACE)
			Debug.startMethodTracing("HomeActivity_onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		username = SaveSharedPreference.getPersistentUserName(HomeActivity.this);
		if (ConnectionChecker.hasConnection(this)) {
			GetNumberOfInvitesTask task = new GetNumberOfInvitesTask();
			task.execute(username);
		} else {
			Resources res = getResources();
			String noNetwork = res.getString(R.string.no_network);
			Toast.makeText(getApplicationContext(), noNetwork, Toast.LENGTH_LONG).show();
		}
		// Show the Up button in the action bar.
		//setupActionBar();
		
		if (TraceControl.TRACE)
			Debug.stopMethodTracing();
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
	 * Updates the view if it is reached via back button presses.
	 */
	@Override
	public void onResume() {
		super.onResume();
		Button goToGroupsButton = (Button) findViewById(R.id.button2);
		Resources res = getResources();
		String manageGroups = res.getString(R.string.button_manage_groups);
		goToGroupsButton.setText(manageGroups);
		username = SaveSharedPreference.getPersistentUserName(HomeActivity.this);
		GetNumberOfInvitesTask task = new GetNumberOfInvitesTask();
		task.execute(username);
	}

	/**
	 * Responds to the manage characters button click and goes to the manage
	 * characters page.
	 * @param view The current view
	 */
	public void gotoCharacters(View view) {
		Intent intent = new Intent(this, CharactersActivity.class);
		startActivity(intent);
	}

	/**
	 * Responds to the logout button click, logs the user out, and goes to the login page.
	 * @param view The current view
	 */
	public void logout(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		SaveSharedPreference.setUserName(HomeActivity.this, "");
		SaveSharedPreference.setPersistentUserName(HomeActivity.this, "");
		startActivity(intent);
		finish();
	}

	/**
	 * Responds to the manage groups button click and goes to the manage
	 * groups page. 
	 * @param view The current view
	 */
	public void gotoGroups(View view) {
		Intent intent = new Intent(this, GroupsActivity.class);
		startActivity(intent);
	}

	/**
	 * GetNumberOfInvitesTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It gets the number of pending invites for the
	 * current user and updates the text on the manage groups button to reflect this number.
	 */
	private class GetNumberOfInvitesTask extends AsyncTask<String, Void, String> {

		/**
		 * Constructs a new GetNumberOfInvitesTask object.
		 * @param context The current Activity's context.
		 */
		private GetNumberOfInvitesTask () {

		}

		/**
		 * Makes the HTTP request and returns the result as a String.
		 */
		@Override
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
		@Override
		protected void onPostExecute(String result) {
			try {
				int resultAsNumber = Integer.parseInt(result);
				if(resultAsNumber > 0) {
					Button goToGroupsButton = (Button) findViewById(R.id.button2);
					Resources res = getResources();
					String manageGroups = res.getString(R.string.button_manage_groups);
					goToGroupsButton.setText(manageGroups + " (" + result + ")");
				}
			} catch (Exception e) {

			}
		}

	}

}
