package uw.cse403.minion;

import java.util.ArrayList;
import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
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
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

/**
 * CharactersActivity is an activity that provides the user a UI with which
 * they can access their previously created characters as well as move to
 * another screen that will allow them to create more characters.
 * @author Elijah Elefson (elefse)
 */
public class CharactersActivity extends Activity {
	/** Class constants for string representations **/
	private static final String CHARACTER_ID = "cid";

	/** The local database source **/
	private CharacterDataSource datasource;

	/** Declares the necessary UI components **/
	private ListView charListView;

	/** Collection of all the characters a user has **/
	private static ArrayList<String> characterArray;

	/** Adapter for connecting the characterArray to the UI view **/
	private ArrayAdapter<String> adapter;

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
}
