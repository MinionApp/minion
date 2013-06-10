package uw.cse403.minion;

import java.util.List;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class SQLiteTestActivity extends ListActivity {
	private CharacterDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite_test);
		// Show the Up button in the action bar.
		setupActionBar();

		datasource = new CharacterDataSource(this);
		System.out.println("datasource opening");
		datasource.open();
		System.out.println("datasource opened");

		List<Character> values = datasource.getAllCharacters();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Character> adapter = new ArrayAdapter<Character>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
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
		getMenuInflater().inflate(R.menu.sqlite_test, menu);
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

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	public void addCharacter(View view) {
		EditText editText = (EditText) findViewById(R.id.add_character);
		String cName = editText.getText().toString().trim();
		editText.setText("");

		if (!cName.equals("")) { // string is not empty
			@SuppressWarnings("unchecked")
			ArrayAdapter<Character> adapter = (ArrayAdapter<Character>) getListAdapter();
			//	    	Character character = datasource.createCharacter(cName);
			//			adapter.add(character);
			adapter.notifyDataSetChanged();
			Intent intent = new Intent(this, CharCreateMainActivity.class);
			startActivity(intent);
		}
	}

	public void removeCharacter(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Character> adapter = (ArrayAdapter<Character>) getListAdapter();
		if (!getListAdapter().isEmpty()) {
			Character character = (Character)getListAdapter().getItem(0);
			CharacterDataSource.deleteCharacter(character);
			adapter.remove(character);
		}
	}

	public void loadCharacters() {
		List<Character> values = datasource.getAllCharacters();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Character> adapter = new ArrayAdapter<Character>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

}
