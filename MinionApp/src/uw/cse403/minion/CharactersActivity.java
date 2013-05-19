package uw.cse403.minion;

import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class CharactersActivity extends Activity {
	private CharacterDataSource datasource;
	
	// Declare the UI components
	private ListView charListView;
	
	// Change this array's name and contents to be the character information
	// received from the database
	private static String[] testArray = {"testing", "one", "two", "three"};
	
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
	    
	    //int[] toViews = {android.R.id.text1}; // The TextView in activity_characters

        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, testArray);
        charListView.setAdapter(adapter);
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
