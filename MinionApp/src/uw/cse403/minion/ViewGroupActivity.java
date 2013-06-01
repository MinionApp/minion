package uw.cse403.minion;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * ViewGroupActivity is an activity that lets the user view information about a group
 * they either own or are a part of. Currently stubbed out because it is a part of 
 * our test-driven development requirement.
 * @author Elijah Elefson (elefse)
 */
public class ViewGroupActivity extends Activity {
	private static final String GROUPNAME = "groupname";
	private static final String GAME_MASTER = "gm";
	private static final String CHARACTER_NAME = "characterName";
	private static final String PLAYER_NAME = "playerName";
	
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/getGroupInfo.php";
	private String username;
	private String groupName;
	
	/**
	 * Declare the UI components
	 */
	private ListView playersListView;

	/**
	 * Change this array's name and contents to be the character information
	 * received from the database
	 */
	private static ArrayList<HashMap<String, String>> testArray;

	/**
	 * Adapter for connecting the array above to the UI view
	 */
	private SimpleAdapter adapter;
	
	/**
	 * Displays the view group page for the selected group.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_group);
		// Show the Up button in the action bar.
		setupActionBar();
		username = SaveSharedPreference.getPersistentUserName(ViewGroupActivity.this);
		groupName = this.getIntent().getExtras().getString(GROUPNAME);
		String gm = this.getIntent().getExtras().getString(GAME_MASTER);
		TextView groupTitle = (TextView) findViewById(R.id.group_name);
		groupTitle.setText(groupName);
		TextView gameMasterText = (TextView) findViewById(R.id.game_master_name);
		gameMasterText.setText(gm);
		GetGroupInfoTask task = new GetGroupInfoTask(this);
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
		getMenuInflater().inflate(R.menu.groups, menu);
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

	private class GetGroupInfoTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {
		private Context context;
		private boolean pendingInvite;
		private boolean isGM;
		
		/**
		 * Constructs a new GetGroupInfoTask object.
		 * @param context The current Activity's context
		 */
		private GetGroupInfoTask(Context context) {
			this.context = context;
		}
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected ArrayList<HashMap<String, String>> doInBackground(String... args) {
	        //the data to send
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("un", username));
	        postParameters.add(new BasicNameValuePair("groupName", groupName));
	        
	        // Hashmap for ListView
	        ArrayList<HashMap<String, String>> playersArray = new ArrayList<HashMap<String, String>>();

			String result = null;
	        
	        //http post
			String res;
	        try{
	        	result = CustomHttpClient.executeHttpPost(PHP_ADDRESS, postParameters);
	        	res = result.toString();
	        	JSONObject results  = new JSONObject(res);
	        	pendingInvite = results.getBoolean("pendingInvite");
	        	isGM = results.getBoolean("isGM");
	        	JSONArray players = results.getJSONArray("items");
	        	// looping through players
	            for(int i = 0; i < players.length(); i++){
	                JSONObject c = players.getJSONObject(i);
	                 
	                // Storing each json item in variable
	                String characterName = c.getString("character");
	                String playerName = c.getString("username");
	                
	                // creating new HashMap
	                HashMap<String, String> map = new HashMap<String, String>();
	                map.put(CHARACTER_NAME, characterName);
	                map.put(PLAYER_NAME, playerName);
	                // adding HashList to ArrayList
	                playersArray.add(map);
	            }
	        } catch (Exception e) {
	        	res = e.toString();
	        }
	        return playersArray;
	    }
	 
	    /**
	     * Parses the String result and directs to the correct Activity
	     */
	    protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
	    	if(isGM) {
		    	Button editButton = (Button) findViewById(R.id.edit_button);
		    	editButton.setVisibility(View.VISIBLE);
	    	}
	    	
	    	if(pendingInvite) {
	    		LinearLayout inviteButtons = (LinearLayout) findViewById(R.id.invite_buttons);
	    		inviteButtons.setVisibility(View.VISIBLE);
	    	}
	    	
	    	testArray = result;
	    	
		    // Initialize the UI components
	        playersListView = (ListView) findViewById(R.id.playersListView);

	        // Create an empty adapter we will use to display the loaded data.
	        // We pass null for the cursor, then update it in onLoadFinished()
	        adapter = new SimpleAdapter(context, testArray,
	        		R.layout.custom_group_list_item, new String[] { CHARACTER_NAME, PLAYER_NAME }, new int[] {
	        			R.id.text1, R.id.text2 });
	        playersListView.setAdapter(adapter);
	        
	    }
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