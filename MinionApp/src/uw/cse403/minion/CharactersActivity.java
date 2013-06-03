package uw.cse403.minion;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

/**
 * CharactersActivity is an activity that provides the user a UI with which
 * they can access their previously created characters as well as move to
 * another screen that will allow them to create more characters.
 * @author Elijah Elefson (elefse)
 */
public class CharactersActivity extends ListActivity {
	/** Class constants for string representations **/
	private static final String CHARACTER_ID = "cid";
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/deleteCharacter.php";

	/** The local database source **/
	private CharacterDataSource datasource;

	/** Declares the necessary UI components **/
	private ListView charListView;

	/** Collection of all the characters a user has **/
	private static ArrayList<String> characterArray;

	/** Adapter for connecting the characterArray to the UI view **/
	private ArrayAdapter<String> adapter;
	
	/** The character that has been clicked on **/
	private String character;
	
	/** The current user's username **/
	private String username;
	
	/** The unique id for a character **/
	private long charID;

	/*
	 * Testing Results:
	 * A lot of graphical displaying massively outweighs the rest of the code.
	 * Nothing significant to be done.
	 */
	/**
	 * Displays the user's character page.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (TraceControl.TRACE)
			Debug.startMethodTracing("CharactersActivity_onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_characters);
		// Show the Up button in the action bar.
		setupActionBar();
		System.out.println("CREATING DATASOURCE");
		datasource = new CharacterDataSource(this);
		System.out.println("OPENING DATASOURCE");
		datasource.open();

		characterArray = new ArrayList<String>();

		// Initialize the UI components
		charListView = (ListView) findViewById(R.id.charListView);

		// Gets all the characters
		Cursor cursor = SQLiteHelperBasicInfo.db.query(SQLiteHelperBasicInfo.TABLE_NAME, new String[]{SQLiteHelperBasicInfo.COLUMN_NAME}, 
				null, null, null, null, null);
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) { 
				// Columns: COLUMN_CHAR_ID, COLUMN_NAME
				String characterName = cursor.getString(0);
				characterArray.add(characterName);
				cursor.moveToNext();
			}
		}
		cursor.close();
		// Create an empty adapter we will use to display the loaded data.
		// We pass null for the cursor, then update it in onLoadFinished()
		adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, characterArray);
		charListView.setAdapter(adapter);
		charListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), CharCreateMainActivity.class);
				String charName = ((TextView) view).getText().toString();
				// Gets charID based on character name
				Cursor cursor2 = SQLiteHelperBasicInfo.db.query(SQLiteHelperBasicInfo.TABLE_NAME, new String[]{SQLiteHelperBasicInfo.COLUMN_ID}, 
						SQLiteHelperBasicInfo.COLUMN_NAME + " = \"" + charName + "\"", null, null, null, null);
				long cid = 0;
				if (cursor2.moveToFirst()) {
					cid = cursor2.getLong(0);
				}
				cursor2.close();
				intent.putExtra(CHARACTER_ID, cid);
				startActivity(intent);
			}
		});
		
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
		getMenuInflater().inflate(R.menu.characters, menu);
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
		characterArray = new ArrayList<String>();
		// Initialize the UI components
		charListView = (ListView) findViewById(R.id.charListView);

		// Gets all characters
		Cursor cursor = SQLiteHelperBasicInfo.db.query(SQLiteHelperBasicInfo.TABLE_NAME, new String[]{SQLiteHelperBasicInfo.COLUMN_NAME}, 
				null, null, null, null, null);
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) { 
				// Columns: COLUMN_CHAR_ID, COLUMN_NAME
				String characterName = cursor.getString(0);
				characterArray.add(characterName);
				cursor.moveToNext();
			}
		}
		cursor.close();

		// Create an empty adapter we will use to display the loaded data.
		// We pass null for the cursor, then update it in onLoadFinished()
		adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, characterArray);
		charListView.setAdapter(adapter);
		charListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), CharCreateMainActivity.class);
				String charName = ((TextView) view).getText().toString();
				// Gets charID based on character name
				Cursor cursor2 = SQLiteHelperBasicInfo.db.query(SQLiteHelperBasicInfo.TABLE_NAME, new String[]{SQLiteHelperBasicInfo.COLUMN_ID}, 
						SQLiteHelperBasicInfo.COLUMN_NAME + " = \"" + charName + "\"", null, null, null, null);
				long cid = 0;
				if (cursor2.moveToFirst()) {
					cid = cursor2.getLong(0);
				}
				cursor2.close();
				intent.putExtra(CHARACTER_ID, cid);
				startActivity(intent);
			}
		});
	}

	/** Responds to the add character button click and takes the user to
	 *  the main character creation screen.
	 *  @param view The current view
	 */
	public void addCharacter(View view) {
		Intent intent = new Intent(this, CharCreateMainActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Responds to the delete character button click by removing the associated
	 * character.
	 * @param view The current view
	 */
	public void deleteCharacter(View view) {
		character = ((TextView) view).getText().toString();
		// Gets charID based on character name
		Cursor cursor2 = SQLiteHelperBasicInfo.db.query(SQLiteHelperBasicInfo.TABLE_NAME, new String[]{SQLiteHelperBasicInfo.COLUMN_ID}, 
				SQLiteHelperBasicInfo.COLUMN_NAME + " = \"" + character + "\"", null, null, null, null);
		charID = 0;
		if (cursor2.moveToFirst()) {
			charID = cursor2.getLong(0);
		}
		cursor2.close();
		DeleteCharacterTask task = new DeleteCharacterTask(this);
		task.execute(username);
	}
	
	/**
	 * RemovePlayerTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. Removes the players specified by the user
	 * from the group. Updates the database accordingly.
	 */
	private class DeleteCharacterTask extends AsyncTask<String, Void, String> {
		private Context context;

		/**
		 * Constructs a new RemovePlayerTask object.
		 * @param context The current Activity's context
		 */
		private DeleteCharacterTask(Context context) {
			this.context = context;
		}

		/**
		 * Makes the HTTP request and returns the result as a String.
		 */
		protected String doInBackground(String... args) {
			//the data to send
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", username));
			postParameters.add(new BasicNameValuePair("char_id", String.valueOf(charID)));

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
			// INSERT CODE TO DELETE FROM LOCAL DB HERE
			Intent intent = new Intent(context, CharactersActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
