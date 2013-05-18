package uw.cse403.minion;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class GroupsActivity extends Activity {
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/getGroups.php";
	private String username;
	
	// Declare the UI components
	private ListView groupsListView;

	// Change this array's name and contents to be the character information
	// received from the database
	private static ArrayList<String> testArray;

	// Adapter for connecting the array above to the UI view
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_groups);
		// Show the Up button in the action bar.
		setupActionBar();
		username = SaveSharedPreference.getPersistentUserName(GroupsActivity.this);
		
		GetGroupsTask task = new GetGroupsTask(this);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.groups, menu);
		return true;
	}

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
	
	public void gotoCreateGroup(View view) {
		Intent intent = new Intent(this, GroupCreateActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void gotoPendingInvites(View view) {
		Intent intent = new Intent(this, ViewInvitesActivity.class);
		startActivity(intent);
		finish();
	}

	private class GetGroupsTask extends AsyncTask<String, Void, ArrayList<String>> {
		private Context context;
		
		private GetGroupsTask(Context context) {
			this.context = context;
		}
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected ArrayList<String> doInBackground(String... args) {
	        //the data to send
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("un", username));
	        
	        ArrayList<String> groupsArray = new ArrayList<String>();
			String result = null;
	        
	        //http post
			String res;
	        try{
	        	result = CustomHttpClient.executeHttpPost(PHP_ADDRESS, postParameters);
	        	res = result.toString();   
	        	//res = res.replaceAll("\\s+", "");
	        	JSONObject results  = new JSONObject(res);
	        	JSONArray groups = results.getJSONArray("items");
	        	// looping through groups
	            for(int i = 0; i < groups.length(); i++){
	                JSONObject c = groups.getJSONObject(i);
	                 
	                // Storing each json item in variable
	                String groupName = c.getString("groupname");
	                Log.i("GROUP NAME", groupName);
	                groupsArray.add(groupName);
	            }
	        } catch (Exception e) {
	        	res = e.toString();
	        	Log.i("ERROR", res);
	        }
	        return groupsArray;
	    }
	 
	    /**
	     * Parses the String result and directs to the correct Activity
	     */
	    protected void onPostExecute(ArrayList<String> result) {
	    	testArray = result;
	    	
		    // Initialize the UI components
	        groupsListView = (ListView) findViewById(R.id.groupsListView);

		    //int[] toViews = {android.R.id.text1}; // The TextView in activity_characters

	        // Create an empty adapter we will use to display the loaded data.
	        // We pass null for the cursor, then update it in onLoadFinished()
	        adapter = new ArrayAdapter<String>(context, 
	        		android.R.layout.simple_list_item_1, testArray);
	        groupsListView.setAdapter(adapter);
	        
	        groupsListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// When clicked, show a toast with the TextView text
		            Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();	
				}
	          });
	    }
	 
	}
	
}
