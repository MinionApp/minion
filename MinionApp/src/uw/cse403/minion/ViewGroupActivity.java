package uw.cse403.minion;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * ViewGroupActivity is an activity that lets the user view information about a group
 * they either own or are a part of. Currently stubbed out because it is a part of 
 * our test-driven development requirement.
 * @author Elijah Elefson (elefse)
 */
public class ViewGroupActivity extends Activity {

	/**
	 * Displays the view group page for the selected group.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Creates Options Menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Sets up the Up button
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Responds to the edit group button click by directing the user to the 
	 * EditGroupActivity. Only works for the owner of the group. Disabled
	 * otherwise.
	 * @param view The current view
	 */
	public void editGroup(View view) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Responds to the accept invite button click by adding the user to the
	 * group. Only works if the user has yet to respond to a pending invite
	 * from the group. Disabled otherwise.
	 * @param view The current view
	 */
	public void acceptInvite(View view) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Responds to the decline invite button click by removing any pending invites
	 * the user has from the current group. Only works if the user has yet to 
	 * respond to a pending invite from the group. Disabled otherwise.
	 * @param view The current view
	 */
	public void declineInvite(View view) {
		// TODO Auto-generated method stub
	}

	/**
	 * HasPendingInviteTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. Checks if the user has any pending invites
	 * from the currently viewed group. Disables the accept and decline invite buttons if the user
	 * has no pending invites, enables them otherwise.
	 */
	private class HasPendingInviteTask extends AsyncTask<String, Void, String> {
		
		/**
		 * Constructs a new HasPendingInviteTask object.
		 * @param context The current Activity's context
		 */
		private HasPendingInviteTask(Context context) {
			// TODO Auto-generated method stub
		}
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected String doInBackground(String... args) {
	    	// TODO Auto-generated method stub
	    	return "";
	    }
	 
	    /**
	     * Parses the String result and directs to the correct Activity
	     */
	    protected void onPostExecute(String result) {
	    	// TODO Auto-generated method stub
	    }
	}
	
	/**
	 * AcceptInviteTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. Adds the user to the group and updates
	 * the database appropriately.
	 */
	private class AcceptInviteTask extends AsyncTask<String, Void, String> {
		
		/**
		 * Constructs a new AcceptInviteTask object.
		 * @param context The current Activity's context
		 */
		private AcceptInviteTask(Context context) {
			// TODO Auto-generated method stub
		}
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected String doInBackground(String... args) {
	    	// TODO Auto-generated method stub
	    	return "";
	    }
	 
	    /**
	     * Parses the String result and directs to the correct Activity
	     */
	    protected void onPostExecute(String result) {
	    	// TODO Auto-generated method stub
	    }
	 
	}
	
	/**
	 * DeclineInviteTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. Removes the group's pending invite to
	 * the user and updates the database accordingly.
	 */
	private class DeclineInviteTask extends AsyncTask<String, Void, String> {

		/**
		 * Constructs a new DeclineInviteTask object.
		 * @param context The current Activity's context
		 */
		private DeclineInviteTask(Context context) {
			// TODO Auto-generated method stub
		}
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected String doInBackground(String... args) {
	    	// TODO Auto-generated method stub
	    	return "";
	    }
	 
	    /**
	     * Parses the String result and directs to the correct Activity
	     */
	    protected void onPostExecute(String result) {
	    	// TODO Auto-generated method stub
	    }
	}
}
