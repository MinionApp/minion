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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * ViewInvitesActivity is the activity that provides the user with a view of what pending invites
 * they have and the ability to accept or decline them.
 * @author Elijah Elefson (elefse)
 */
public class ViewInvitesActivity extends ListActivity {
	private static final String CHARACTER_ID = "cid";
	private static final String GROUPNAME = "groupname";
	private static final String GAME_MASTER = "gm";
	
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/getInvites.php";
	private static final String PHP_ADDRESS2 = "http://homes.cs.washington.edu/~elefse/acceptInvite.php";
	private static final String PHP_ADDRESS3 = "http://homes.cs.washington.edu/~elefse/declineInvite.php";
	private String username;
	private String group;
	private String character;
	private static Dialog dialog;
	private static Dialog noCharactersAlert;
	private CharacterDataSource datasource;
	
	/**
	 * Declare the UI components
	 */
	private ListView invitesListView;

	/**
	 * Change this array's name and contents to be the character information
	 * received from the database
	 */
	private static ArrayList<String> testArray;
	private static ArrayList<String> testArray2;

	/**
	 * Adapter for connecting the array above to the UI view
	 */
	private ArrayAdapter<String> adapter;
	
	/**
	 * Displays the view invites page for the current user.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_invites);
		username = SaveSharedPreference.getPersistentUserName(ViewInvitesActivity.this);
		GetInvitesTask task = new GetInvitesTask(this);
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
	 * Updates the view if it is reached via back button presses.
	 */
	@Override
	public void onResume() {
		super.onResume();
		GetInvitesTask task = new GetInvitesTask(this);
		task.execute(username);
	}
	
	/**
	 * Responds to the accept invite button click by adding the user to the
	 * group.
	 * @param view The current view
	 */
	public void acceptInvite(View view) {
		datasource = new CharacterDataSource(this);
	    datasource.open();
		// To test reading from database:
	    testArray2 = new ArrayList<String>();
        //GETS ALL CHAR
        Cursor cursor = SQLiteHelperBasicInfo.db.query(SQLiteHelperBasicInfo.TABLE_NAME, new String[]{SQLiteHelperBasicInfo.COLUMN_NAME}, 
        		null, null, null, null, null);
        if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) { 
				// Columns: COLUMN_CHAR_ID, COLUMN_NAME
				String characterName = cursor.getString(0);
				testArray2.add(characterName);
				cursor.moveToNext();
			}
		}
        cursor.close();
        
        if(testArray2.size() == 0) {
        	AlertDialog.Builder noCharactersBuilder = new AlertDialog.Builder(this);
        	noCharactersBuilder.setMessage("You have no character with which to play with! Please go create a character!");
        	noCharactersBuilder.setTitle("No Characters Warning");
        	noCharactersBuilder.setPositiveButton("Ok",
        		    new DialogInterface.OnClickListener() {
        		        public void onClick(DialogInterface dialog, int which) {
        		        	noCharactersAlert.dismiss(); 
        		        }
        		    });
        	noCharactersBuilder.setCancelable(true);
        	noCharactersAlert = noCharactersBuilder.create();
        	noCharactersAlert.show();
        } else {
		    AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle("Pick a Character");
	
		    ListView modeList = new ListView(this);
		    ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, testArray2);
		    modeList.setAdapter(modeAdapter);
		    builder.setView(modeList);
		    dialog = builder.create();
	
		    dialog.show();
	        modeList.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// When clicked, show a toast with the TextView text
					character = ((TextView) view).getText().toString();
		            dialog.dismiss();
		    	    group = (String) getListView().getItemAtPosition(position);
		    		acceptInviteTask task = new acceptInviteTask(view.getContext());
		    		task.execute(username);
				}
	          });
        }
	}
	
	/**
	 * Responds to the decline invite button click by removing any pending invites
	 * the user has from the current group.
	 * @param view The current view
	 */
	public void declineInvite(View view) {
		int position = getListView().getPositionForView((View) view.getParent());
	    group = (String) getListView().getItemAtPosition(position);
		declineInviteTask task = new declineInviteTask(this);
		task.execute(username);
	}
	
	/**
	 * GetInvitesTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It gets all of the pending invites
	 * for the current user and displays them in a ListView.
	 */
	private class GetInvitesTask extends AsyncTask<String, Void, ArrayList<String>> {
		private Context context;
		
		/**
		 * Constructs a new GetInvitesTask object.
		 * @param context The current Activity's context.
		 */
		private GetInvitesTask (Context context) {
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

		        // Create an empty adapter we will use to display the loaded data.
		        // We pass null for the cursor, then update it in onLoadFinished()
		        adapter = new ArrayAdapter<String>(context, R.layout.custom_invite_list_item, R.id.invite, testArray);
		        invitesListView.setAdapter(adapter);
		        
		        invitesListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// When clicked, show a toast with the TextView text
			            //Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(context, ViewGroupActivity.class);
						TextView groupTextView = (TextView) view.findViewById(R.id.invite);
						String groupname = groupTextView.getText().toString();
						intent.putExtra(GROUPNAME, groupname);
						intent.putExtra(GAME_MASTER, "test");
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
	        postParameters.add(new BasicNameValuePair("character", character));
	        
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
