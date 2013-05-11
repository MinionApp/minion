package com.example.myfirstapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

public class BasicInfoActivity extends Activity {
	private long charID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("BASIC INFO ONCREATE");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_info);
		System.out.println("INTENT GET");
		charID = this.getIntent().getExtras().getLong("cid");
		System.out.println("CHARACTER ID GET");
		// Show the Up button in the action bar.
		setupActionBar();
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
	
	public void basicInfo(View view){
		System.out.println("BASIC INFO");
		CharacterDescription baseInfo = new CharacterDescription(charID);
		//TODO: Handle empty cases
		
		EditText char_name = (EditText) findViewById(R.id.char_name_enter);
		String cName = char_name.getText().toString().trim();
		if (!cName.matches("")) {
			baseInfo.name = cName;		
		}
		
		//TODO: Make alignment a dropdown menu
		//Alignment
		EditText align_enter = (EditText) findViewById(R.id.alignment_enter);
		String align = align_enter.getText().toString().trim();
		if (!align.matches("")) {
			baseInfo.alignment = align;
		}
		
		//Player
		EditText player_enter = (EditText) findViewById(R.id.player_enter);
		String player = player_enter.getText().toString().trim();
		if (!player.matches("")) {
			baseInfo.player = player;
		}
		
		//Level
		EditText level_enter = (EditText) findViewById(R.id.char_level_enter);
		String level = level_enter.getText().toString().trim();
		System.out.println("NO USER INPUT: " + level);
		if (!level.matches("")) {
			System.out.println("REACHED THIS POINT");
			//baseInfo.level = Integer.parseInt(level);
		}

		
		//Deity
		EditText deity_enter = (EditText) findViewById(R.id.deity_enter);
		String deity = deity_enter.getText().toString().trim();
		if (!deity.matches("")) {
			baseInfo.diety = deity;
		}
		
		//Homeland
		EditText homeland_enter = (EditText) findViewById(R.id.homeland_enter);
		String homeland = homeland_enter.getText().toString().trim();
		if (!homeland.matches("")) {
			baseInfo.homeLand = homeland;
		}
		
		//Race
		EditText race_enter = (EditText) findViewById(R.id.race_enter);
		String race = race_enter.getText().toString().trim();
		if (!race.matches("")) {
			baseInfo.race = race;
		}
		
		//TODO: Make size dropdown
		//Size
		EditText size_enter = (EditText) findViewById(R.id.size_enter);
		String size = size_enter.getText().toString().trim();
		if (!size.matches("")) {
			baseInfo.size = size;
		}
		
		//Gender
		EditText gender_enter = (EditText) findViewById(R.id.gender_enter);
		String gender = gender_enter.getText().toString().trim();
		if (!gender.matches("")) {
			baseInfo.gender = gender;
		}
		
		//TODO: Figure out how to get out numbers
		//Age
		EditText age_enter = (EditText) findViewById(R.id.age_enter);
		String age = age_enter.getText().toString().trim();
		if (!age.matches("")) {
			baseInfo.age = Integer.parseInt(age);
		}
		
		//Height
		EditText height_enter = (EditText) findViewById(R.id.height_enter);
		String height = height_enter.getText().toString().trim();
		if (!height.matches("")) {
			baseInfo.height = Integer.parseInt(height);
		}
		
		//Weight
		EditText weight_enter = (EditText) findViewById(R.id.weight_enter);
		String weight = weight_enter.getText().toString().trim();
		if (!weight.matches("")) {
			baseInfo.weight = Integer.parseInt(weight);
		}
		
		//Hair
		EditText hair_enter = (EditText) findViewById(R.id.hair_enter);
		String hair = hair_enter.getText().toString().trim();
		if (!hair.matches("")) {
			baseInfo.hair = hair;
		}
		
		//Eyes
		EditText eyes_enter = (EditText) findViewById(R.id.eyes_enter);
		String eyes = eyes_enter.getText().toString().trim();
		if (!eyes.matches("")) {
			baseInfo.eyes = eyes;
		}
		
		//newChar.setDescriptions(baseInfo);
//		newChar.writeToDB(SQLiteHelperBasicInfo.db,
//			SQLiteHelperAbilityScores.db, 
//			SQLiteHelperASTempMods.db, 
//			SQLiteHelperSkills.db, 
//			SQLiteHelperCombat.db, 
//			SQLiteHelperArmor.db, 
//			SQLiteHelperSavingThrows.db, 
//			SQLiteHelperWeapons.db);
		//SQLiteDatabase db = SQLiteHelperBasicInfo.db;
		
		// write data to database
		baseInfo.writeToDB();
		
		// return to character creation main screen
		Intent intent = new Intent(this, CharCreateMainActivity.class);
		startActivity(intent);
		
	}

}
