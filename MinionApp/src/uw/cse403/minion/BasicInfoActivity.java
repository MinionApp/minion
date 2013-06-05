package uw.cse403.minion;

import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

/**
 * BasicInfoActivity is an activity that provides the user with the UI they
 * can use to enter and edit basic information about their character. It
 * handles both the population of the UI after loading the relevant information
 * from the local database as well as the task of writing any new or updated
 * information into the local database.
 * @author 
 */
public class BasicInfoActivity extends Activity {
	/** Class constants for string representations **/
	private static final String CHARACTER_ID = "cid";

	/** The unique id for a character **/
	private long charID;

	/** Object that stores all the basic information about the character **/
	CharacterDescription baseInfo;

	/*
	 * Testing Results:
	 * All of the most expensive operations were related to drawing content on the screen and not
	 * processing data. Even if data were being accessed, we would be forced to go through SQLite
	 * and deal with the string building therein. Again, nothing could be refactored here.
	 */
	/**
	 * Displays the basic information page and loads in any previously entered information
	 * from the local database.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (TraceControl.TRACE)
			Debug.startMethodTracing("BasicInfoActivity_onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_info);

		charID = this.getIntent().getExtras().getLong(CHARACTER_ID);
		baseInfo = new CharacterDescription(charID);
		loadData();
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
		getMenuInflater().inflate(R.menu.basic_info, menu);
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
	 * Loads all of the basic information for the current character from the database.
	 */
	public void loadData() {
		if (!baseInfo.isNew) {
			//Name
			EditText charName = (EditText) findViewById(R.id.char_name_enter);
			charName.setText(baseInfo.name);

			//Alignment
			Spinner alignments = (Spinner) findViewById(R.id.alignment_spinner);
			ArrayAdapter<CharSequence> myAdap = ArrayAdapter.createFromResource(this, R.array.alignment_array,
					R.layout.multiline_spinner_dropdown_item);
			alignments.setAdapter(myAdap);
			int spinnerPosition = myAdap.getPosition(baseInfo.alignment);
			//set the default according to value
			alignments.setSelection(spinnerPosition);

			//Level
			EditText levelEnter = (EditText) findViewById(R.id.char_level_enter);
			levelEnter.setText("" + baseInfo.level);

			//Deity
			EditText deityEnter = (EditText) findViewById(R.id.deity_enter);
			deityEnter.setText(baseInfo.deity);

			//Homeland
			EditText homelandEnter = (EditText) findViewById(R.id.homeland_enter);
			homelandEnter.setText(baseInfo.homeLand);

			//Race
			EditText raceEnter = (EditText) findViewById(R.id.race_enter);
			raceEnter.setText(baseInfo.race);

			//Size
			Spinner sizes = (Spinner) findViewById(R.id.size_spinner);
			ArrayAdapter<CharSequence> myAdap2 = ArrayAdapter.createFromResource(this, R.array.size_array,
					R.layout.multiline_spinner_dropdown_item);
			sizes.setAdapter(myAdap2);
			int spinnerPosition2 = myAdap2.getPosition(baseInfo.size);
			//set the default according to value
			sizes.setSelection(spinnerPosition2);

			//Gender
			EditText genderEnter = (EditText) findViewById(R.id.gender_enter);
			genderEnter.setText(baseInfo.gender);

			//Age
			EditText ageEnter = (EditText) findViewById(R.id.age_enter);
			ageEnter.setText("" + baseInfo.age);
			
			//Height
			EditText heightEnter = (EditText) findViewById(R.id.height_enter);
			heightEnter.setText("" + baseInfo.height);
			
			//Weight
			EditText weightEnter = (EditText) findViewById(R.id.weight_enter);
			weightEnter.setText("" + baseInfo.weight);

			//Hair
			EditText hairEnter = (EditText) findViewById(R.id.hair_enter);
			hairEnter.setText(baseInfo.hair);

			//Eyes
			EditText eyesEnter = (EditText) findViewById(R.id.eyes_enter);
			eyesEnter.setText(baseInfo.eyes);
		}
	}

	/**
	 * Responds to the Save button click and writes all of the currently
	 * entered basic information to the local database. Then sends the
	 * user back to the main character creation screen.
	 */
	public void basicInfo(View view){
		EditText charName = (EditText) findViewById(R.id.char_name_enter);
		String cName = charName.getText().toString().trim();
		if (!cName.matches("")) {
			baseInfo.name = cName;		
		}

		//Alignment
		Spinner alignments = (Spinner) findViewById(R.id.alignment_spinner);
		// Gives a string representation of whatever item is selected in the spinner
		String align = alignments.getSelectedItem().toString();
		if (!align.matches("")) {
			baseInfo.alignment = align;
		}

		//Level
		EditText levelEnter = (EditText) findViewById(R.id.char_level_enter);
		String level = levelEnter.getText().toString().trim();
		System.out.println("NO USER INPUT: " + level);
		if (!level.matches("")) {
			System.out.println("REACHED THIS POINT");
			baseInfo.level = Integer.parseInt(level);
		}

		//Deity
		EditText deityEnter = (EditText) findViewById(R.id.deity_enter);
		String deity = deityEnter.getText().toString().trim();
		if (!deity.matches("")) {
			baseInfo.deity = deity;
		}

		//Homeland
		EditText homelandEnter = (EditText) findViewById(R.id.homeland_enter);
		String homeland = homelandEnter.getText().toString().trim();
		if (!homeland.matches("")) {
			baseInfo.homeLand = homeland;
		}

		//Race
		EditText raceEnter = (EditText) findViewById(R.id.race_enter);
		String race = raceEnter.getText().toString().trim();
		if (!race.matches("")) {
			baseInfo.race = race;
		}

		//Size
		Spinner sizes = (Spinner) findViewById(R.id.size_spinner);
		// Gives a string representation of whatever item is selected in the spinner
		String size = sizes.getSelectedItem().toString();
		if (!size.matches("")) {
			baseInfo.size = size;
		}

		//Gender
		EditText genderEnter = (EditText) findViewById(R.id.gender_enter);
		String gender = genderEnter.getText().toString().trim();
		if (!gender.matches("")) {
			baseInfo.gender = gender;
		}

		//Age
		EditText ageEnter = (EditText) findViewById(R.id.age_enter);
		String age = ageEnter.getText().toString().trim();
		if (!age.matches("")) {
			baseInfo.age = Integer.parseInt(age);
		}

		//Height
		EditText heightEnter = (EditText) findViewById(R.id.height_enter);
		String height = heightEnter.getText().toString().trim();
		if (!height.matches("")) {
			baseInfo.height = Integer.parseInt(height);
		}

		//Weight
		EditText weightEnter = (EditText) findViewById(R.id.weight_enter);
		String weight = weightEnter.getText().toString().trim();
		if (!weight.matches("")) {
			baseInfo.weight = Integer.parseInt(weight);
		}

		//Hair
		EditText hairEnter = (EditText) findViewById(R.id.hair_enter);
		String hair = hairEnter.getText().toString().trim();
		if (!hair.matches("")) {
			baseInfo.hair = hair;
		}

		//Eyes
		EditText eyesEnter = (EditText) findViewById(R.id.eyes_enter);
		String eyes = eyesEnter.getText().toString().trim();
		if (!eyes.matches("")) {
			baseInfo.eyes = eyes;
		}

		// write data to database
		baseInfo.writeToDB();

		// return to character creation main screen
		Intent intent = new Intent(this, CharCreateMainActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
}
