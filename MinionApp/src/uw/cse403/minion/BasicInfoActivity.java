package uw.cse403.minion;


import android.os.Bundle;
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
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

public class BasicInfoActivity extends Activity {
	private static final String CHARACTER_ID = "cid";
	private long charID;
	CharacterDescription baseInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_info);

		charID = this.getIntent().getExtras().getLong(CHARACTER_ID);
		baseInfo = new CharacterDescription(charID);
		loadData();
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
		getMenuInflater().inflate(R.menu.basic_info, menu);
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

			//Player
			//EditText playerEnter = (EditText) findViewById(R.id.player_enter);
			//playerEnter.setText(baseInfo.player);

			//Level
			EditText levelEnter = (EditText) findViewById(R.id.char_level_enter);
			levelEnter.setText(""+baseInfo.level);

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
			ageEnter.setText(""+baseInfo.age);

			//Height
			EditText heightEnter = (EditText) findViewById(R.id.height_enter);
			heightEnter.setText(""+baseInfo.height);

			//Weight
			EditText weightEnter = (EditText) findViewById(R.id.weight_enter);
			weightEnter.setText(""+baseInfo.weight);

			//Hair
			EditText hairEnter = (EditText) findViewById(R.id.hair_enter);
			hairEnter.setText(baseInfo.hair);

			//Eyes
			EditText eyesEnter = (EditText) findViewById(R.id.eyes_enter);
			eyesEnter.setText(baseInfo.eyes);
		}
	}

	public void basicInfo(View view){
		//System.out.println("BASIC INFO");
		//TODO: Handle empty cases

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

		//Player
		//EditText playerEnter = (EditText) findViewById(R.id.player_enter);
		//String player = playerEnter.getText().toString().trim();
		//if (!player.matches("")) {
		//	baseInfo.player = player;
		//}

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
