package uw.cse403.minion;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * HomeActivity is the activity that provides the user with the home page for the application.
 * This page is displayed after all login activities have been completed or bypassed. This page
 * also provides the means to get the the character management page, group management page, settings
 * page and the logout option.
 * @author Elijah Elefson (elefse)
 *
 */
public class ViewInvitesActivity extends ListActivity {
	private static final String GROUPNAME = "groupname";
	
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/getInvites.php";
	private static final String PHP_ADDRESS2 = "http://homes.cs.washington.edu/~elefse/acceptInvite.php";
	private static final String PHP_ADDRESS3 = "http://homes.cs.washington.edu/~elefse/declineInvite.php";
	private String username;
	private String group;
	
	// Declare the UI components
	private ListView invitesListView;

	// Change this array's name and contents to be the character information
	// received from the database
	private static ArrayList<String> testArray;

	// Adapter for connecting the array above to the UI view
	private ArrayAdapter<String> adapter;
	
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
	
	@Override
	public void onResume() {
		super.onResume();
		getInvitesTask task = new getInvitesTask(this);
		task.execute(username);
	}
	
	public void acceptInvite(View view) {
		int position = getListView().getPositionForView((View) view.getParent());
	    group = (String) getListView().getItemAtPosition(position);

	    //Toast.makeText(ViewInvitesActivity.this, String.format("Accepting", group), Toast.LENGTH_SHORT).show();
		acceptInviteTask task = new acceptInviteTask(this);
		task.execute(username);
	}
	
	public void declineInvite(View view) {
		int position = getListView().getPositionForView((View) view.getParent());
	    group = (String) getListView().getItemAtPosition(position);

	    //Toast.makeText(ViewInvitesActivity.this, String.format("Accepting", group), Toast.LENGTH_SHORT).show();
		declineInviteTask task = new declineInviteTask(this);
		task.execute(username);
	}
	
	/**
	 * SendInvitesTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It updates the database to include the
	 * specified players as part of a group and puts the current as the game master.
	 */
	private class getInvitesTask extends AsyncTask<String, Void, ArrayList<String>> {
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
	    protected ArrayList<String> doInBackground(String... args) {
	        //the data to send
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("un", username));
	        
	     // Hashmap for ListView
	        ArrayList<String> invitesArray = new ArrayList<String>();
			String result = null;
	        
	        //http post
			String res;
	        try{
	        	result = CustomHttpClient.executeHttpPost(PHP_ADDRESS, postParameters);
	        	res = result.toString();   
	        	//res = res.replaceAll("\\s+", ""); 
	        	JSONObject results  = new JSONObject(res);
	        	JSONArray invites = results.getJSONArray("items");
	        	// looping through groups
	            for(int i = 0; i < invites.length(); i++){
	                JSONObject c = invites.getJSONObject(i);
	                 
	                // Storing each json item in variable
	                String groupName = c.getString("groupname");

	                invitesArray.add(groupName);
	            }
	        } catch (Exception e) {  
	        	res = e.toString();
	        }
	        return invitesArray;
	    }
	 
	    /**
	     * Parses the String result and directs to the correct Activity
	     */
	    protected void onPostExecute(ArrayList<String> result) {
	    	if(result.size() == 0) {
	    		ListView list = (ListView) findViewById(android.R.id.list);
	    		list.setVisibility(View.GONE);
				TextView noInvitesTextView = (TextView) findViewById(R.id.no_pending_invites);
				noInvitesTextView.setVisibility(View.VISIBLE);
	    	} else {
	    		testArray = result;
		    	
			    // Initialize the UI components
		        invitesListView = (ListView) findViewById(android.R.id.list);

			    //int[] toViews = {android.R.id.text1}; // The TextView in activity_characters

		        // Create an empty adapter we will use to display the loaded data.
		        // We pass null for the cursor, then update it in onLoadFinished()
		        adapter = //new ArrayAdapter<String>(context, R.layout.custom_invite_list_item, testArray);
		        new ArrayAdapter<String>(context, R.layout.custom_invite_list_item, 
		        		   R.id.invite, testArray);
		        invitesListView.setAdapter(adapter);
		        
		        invitesListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// When clicked, show a toast with the TextView text
			            //Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(context, ViewGroupActivity.class);
						String groupname = ((TextView) view).getText().toString();
						intent.putExtra(GROUPNAME, groupname);
						startActivity(intent);
					}
		          });
	    	}
	    }
	 
	}
	
	/**
	 * SendInvitesTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It updates the database to include the
	 * specified players as part of a group and puts the current as the game master.
	 */
	private class acceptInviteTask extends AsyncTask<String, Void, String> {
		private Context context;
		
		/**
		 * Constructs a new GetNumberOfInvitesTask object.
		 * @param context The current Activity's context.
		 */
		private acceptInviteTask (Context context) {
			this.context = context;
		}
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected String doInBackground(String... args) {
	        //the data to send
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("un", username));
	        postParameters.add(new BasicNameValuePair("group", group));
	        
			String result = null;
	        
	        //http post
			String res;
	        try{
	        	result = CustomHttpClient.executeHttpPost(PHP_ADDRESS2, postParameters);
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
	    	Intent intent = new Intent(context, ViewInvitesActivity.class);
	    	startActivity(intent);
			finish();
	    }
	 
	}
	
	/**
	 * SendInvitesTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It updates the database to include the
	 * specified players as part of a group and puts the current as the game master.
	 */
	private class declineInviteTask extends AsyncTask<String, Void, String> {
		private Context context;
		
		/**
		 * Constructs a new GetNumberOfInvitesTask object.
		 * @param context The current Activity's context.
		 */
		private declineInviteTask (Context context) {
			this.context = context;
		}
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected String doInBackground(String... args) {
	        //the data to send
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("un", username));
	        postParameters.add(new BasicNameValuePair("group", group));

			String result = null;
	        
	        //http post
			String res;
	        try{
	        	result = CustomHttpClient.executeHttpPost(PHP_ADDRESS3, postParameters);
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
	    	Intent intent = new Intent(context, ViewInvitesActivity.class);
	    	startActivity(intent);
			finish();
	    }
	 
	}
}
