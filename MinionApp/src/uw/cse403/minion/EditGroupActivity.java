package uw.cse403.minion;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * EditGroupActivity is an activity that provides the user with the ability to edit a
 * group that they are currently the game master of. This includes changing the name
 * of the group, inviting new players to the group or removing current players from 
 * the group. Currently stubbed out so it can be a placeholder until we fully implement
 * the groups feature.
 * @author Elijah Elefson (elefse)
 */
public class EditGroupActivity extends Activity {
	
	private ListView membersListView;
	
	/**
	 * Change this array's name and contents to be the character information
	 * received from the database
	 */
	private static ArrayList<HashMap<String, String>> testArray;

	/**
	 * Adapter for connecting the array above to the UI view
	 */
	private SimpleAdapter adapter;
	
	private String username;
	
	/**
	 * Displays the edit group page for the selected group.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_groups);
		// Show the Up button in the action bar.
		setupActionBar();
		username = SaveSharedPreference.getPersistentUserName(EditGroupActivity.this);
		
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
	 * Responds to the finish editing button click by saving any changes made
	 * and then returning to the ViewGroupActivity.
	 * @param view The current view
	 */
	public void finishEditing(View view) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Responds to the invite new players button click by directing the user
	 * to the invite players page.
	 * @param view The current view
	 */
	public void inviteNewPlayers(View view) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Responds to the remove player button click by removing the selected player
	 * from the group.
	 * @param view The current view
	 */
	public void removePlayerFromGroup(View view) {
		// TODO Auto-generated method stub
	}

	/**
	 * UpdateGroupInfoTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. Updates the group information on the remote
	 * database to reflect any changes made by the user.
	 */
	private class UpdateGroupInfoTask extends AsyncTask<String, Void, String> {
		
		/**
		 * Constructs a new UpdateGroupInfoTask object.
		 * @param context The current Activity's context
		 */
		private UpdateGroupInfoTask(Context context) {
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
	 * SendNewInvitesTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. Sends the new invites to the players
	 * specified by the user. Updates the database accordingly.
	 */
	private class SendNewInvitesTask extends AsyncTask<String, Void, String> {
		
		/**
		 * Constructs a new SendNewInvitesTaskk object.
		 * @param context The current Activity's context
		 */
		private SendNewInvitesTask(Context context) {
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
	 * RemovePlayerTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. Removes the players specified by the user
	 * from the group. Updates the database accordingly.
	 */
	private class RemovePlayerTask extends AsyncTask<String, Void, String> {

		/**
		 * Constructs a new RemovePlayerTask object.
		 * @param context The current Activity's context
		 */
		private RemovePlayerTask(Context context) {
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
