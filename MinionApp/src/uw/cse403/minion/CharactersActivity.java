package uw.cse403.minion;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;


import android.os.Bundle;
import android.app.Activity;
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

public class CharactersActivity extends Activity {
	private static final String CHARACTER_ID = "cid";
	
	private CharacterDataSource datasource;
	
	// Declare the UI components
	private ListView charListView;
	
	// Change this array's name and contents to be the character information
	// received from the database
	private static ArrayList<String> testArray;
	
	// Adapter for connecting the array above to the UI view
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_characters);
		// Show the Up button in the action bar.
		setupActionBar();
        System.out.println("CREATING DATASOURCE");
        datasource = new CharacterDataSource(this);
        System.out.println("OPENING DATASOURCE");
	    datasource.open();

	    datasource.printTables();
	    // To test reading from database:
	    //List<Character> values = datasource.getAllCharacters();
	    //testArray = values.toArray(testArray);
	    
	    // Initialize the UI components
        charListView = (ListView) findViewById(R.id.charListView);

        //GETS ALL CHAR
        Cursor cursor = SQLiteHelperBasicInfo.db.query(SQLiteHelperBasicInfo.TABLE_NAME, new String[]{SQLiteHelperBasicInfo.COLUMN_NAME}, 
        		null, null, null, null, null);
        if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) { 
				// Columns: COLUMN_CHAR_ID, COLUMN_NAME
				String characterName = cursor.getString(1);
				testArray.add(characterName);
				cursor.moveToNext();
			}
		}
        cursor.close();
        
        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, testArray);
        charListView.setAdapter(adapter);
        charListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// When clicked, show a toast with the TextView text
	            //Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), CharCreateMainActivity.class);
				String charName = ((TextView) view).getText().toString();
		        //GETS charID based on character name
		        Cursor cursor2 = SQLiteHelperBasicInfo.db.query(SQLiteHelperBasicInfo.TABLE_NAME, SQLiteHelperBasicInfo.ALL_COLUMNS, 
		        		SQLiteHelperBasicInfo.COLUMN_NAME + " = " + charName, null, null, null, null);
		        int cid = cursor2.getInt(1);
		        cursor2.close();
				intent.putExtra(CHARACTER_ID, cid);
				startActivity(intent);
			}
          });
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
		getMenuInflater().inflate(R.menu.characters, menu);
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
	
	public void addCharacter(View view) {
		Intent intent = new Intent(this, CharCreateMainActivity.class);
		startActivity(intent);
	}
	 
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
    }

}
