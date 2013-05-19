package uw.cse403.minion;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

public class SkillsActivity extends Activity {
	private long charID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_skills);
		charID = this.getIntent().getExtras().getLong("cid");
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
		getMenuInflater().inflate(R.menu.skills, menu);
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
	
	// helper class for storing a string and int together
	// mostly for making the following code readable
	private class StrInt {
		int i;
		String s;
		public StrInt(String s, int i) {
			this.i = i;
			this.s = s;
		}
	}
	/**
	 * Called when Done button is clicked.
	 */
	public void skills(View view) {
		// TODO write method
		//Skill sk = new Skill(null, null);
		ArrayList<Skill> skills = new ArrayList<Skill>();
		
// this segment of code is for factoring the code so it's not so redundant
// however the priority is getting this to work, so we can finish this later
//		// load skills from DB
//		Cursor cursor = SQLiteHelperRefTables.db.query(SQLiteHelperRefTables.TABLE_REF_SKILLS, 
//				SQLiteHelperRefTables.ALL_COLUMNS_S, null, null, null, null, null);
//		Map<Integer, StrInt> skillsRef = new HashMap<Integer, StrInt>(); 
//		// ^ this is essentially a map from skill id to skill name and ability score id
//		if (cursor.moveToFirst()) {
//			// Columns: COLUMN_S_ID, COLUMN_S_NAME, COLUMN_S_REF_AS_ID
//			int id 		= cursor.getInt(0);
//			String name = cursor.getString(1);
//			int asID 	= cursor.getInt(2);
//			skillsRef.put(id, new StrInt(name, asID));
//		}
//		cursor.close();
		
		// Acrobatics
		EditText acrobaticsRanksEnter = (EditText) findViewById(R.id.acrobatics_ranks);
		EditText acrobaticsMiscEnter = (EditText) findViewById(R.id.acrobatics_misc_mod);
		String acrobaticsRanks = acrobaticsRanksEnter.getText().toString().trim();
		String acrobaticsMisc = acrobaticsMiscEnter.getText().toString().trim();
		int acrobaticsRank = Integer.parseInt(acrobaticsRanks);
		int acrobaticsMod = Integer.parseInt(acrobaticsMisc);
		if (!acrobaticsRanks.matches("")) {
			Skill skill = new Skill("Acrobatics", AbilityName.DEXTERITY, acrobaticsRank, false);
			skill.addModifier("acrobaticsMod", acrobaticsMod);
			skills.add(skill);
		}

		// Appraise
		EditText appraiseRanksEnter = (EditText) findViewById(R.id.appraise_ranks);
		EditText appraiseMiscEnter = (EditText) findViewById(R.id.appraise_misc_mod);
		String appraiseRanks = appraiseRanksEnter.getText().toString().trim();
		String appraiseMisc = appraiseMiscEnter.getText().toString().trim();
		int appraiseRank = Integer.parseInt(appraiseRanks);
		int appraiseMod = Integer.parseInt(appraiseMisc);
		if (!appraiseRanks.matches("")) {
			Skill skill = new Skill("Appraise", AbilityName.INTELLIGENCE, appraiseRank, false);
			skill.addModifier("appraiseMod", appraiseMod);
			skills.add(skill);
		}

		// Bluff
		EditText bluffRanksEnter = (EditText) findViewById(R.id.bluff_ranks);
		EditText bluffMiscEnter = (EditText) findViewById(R.id.bluff_misc_mod);
		String bluffRanks = bluffRanksEnter.getText().toString().trim();
		String bluffMisc = bluffMiscEnter.getText().toString().trim();
		int bluffRank = Integer.parseInt(bluffRanks);
		int bluffMod = Integer.parseInt(bluffMisc);
		if (!bluffRanks.matches("")) {
			Skill skill = new Skill("Bluff", AbilityName.CHARISMA, bluffRank, false);
			skill.addModifier("bluffMod", bluffMod);
			skills.add(skill);
		}

		// Climb
		EditText climbRanksEnter = (EditText) findViewById(R.id.climb_ranks);
		EditText climbMiscEnter = (EditText) findViewById(R.id.climb_misc_mod);
		String climbRanks = climbRanksEnter.getText().toString().trim();
		String climbMisc = climbMiscEnter.getText().toString().trim();
		int climbRank = Integer.parseInt(climbRanks);
		int climbMod = Integer.parseInt(climbMisc);
		if (!climbRanks.matches("")) {
			Skill skill = new Skill("Climb", AbilityName.STRENGTH, climbRank, false);
			skill.addModifier("climbMod", climbMod);
			skills.add(skill);
		}

		// Craft1
		EditText craft1NameEnter = (EditText) findViewById(R.id.craft1_enter);
		EditText craft1RanksEnter = (EditText) findViewById(R.id.craft1_ranks);
		EditText craft1MiscEnter = (EditText) findViewById(R.id.craft1_misc_mod);
		String craft1Name = craft1NameEnter.getText().toString().trim();
		String craft1Ranks = craft1RanksEnter.getText().toString().trim();
		String craft1Misc = craft1MiscEnter.getText().toString().trim();
		int craft1Rank = Integer.parseInt(craft1Ranks);
		int craft1Mod = Integer.parseInt(craft1Misc);
		if (!craft1Ranks.matches("")) {
			Skill skill = new Skill("Craft1", AbilityName.INTELLIGENCE, craft1Rank, false);
			skill.addModifier("craft1Mod", craft1Mod);
			skills.add(skill);
		}

		// Craft2
		EditText craft2NameEnter = (EditText) findViewById(R.id.craft2_enter);
		EditText craft2RanksEnter = (EditText) findViewById(R.id.craft2_ranks);
		EditText craft2MiscEnter = (EditText) findViewById(R.id.craft2_misc_mod);
		String craft2Name = craft2NameEnter.getText().toString().trim();
		String craft2Ranks = craft2RanksEnter.getText().toString().trim();
		String craft2Misc = craft2MiscEnter.getText().toString().trim();
		int craft2Rank = Integer.parseInt(craft2Ranks);
		int craft2Mod = Integer.parseInt(craft2Misc);
		if (!craft2Ranks.matches("")) {
			Skill skill = new Skill("Craft2", AbilityName.INTELLIGENCE, craft2Rank, false);
			skill.addModifier("craft2Mod", craft1Mod);
			skills.add(skill);
		}

		// Craft3
		EditText craft3NameEnter = (EditText) findViewById(R.id.craft3_enter);
		EditText craft3RanksEnter = (EditText) findViewById(R.id.craft3_ranks);
		EditText craft3MiscEnter = (EditText) findViewById(R.id.craft3_misc_mod);
		String craft3Name = craft3NameEnter.getText().toString().trim();
		String craft3Ranks = craft3RanksEnter.getText().toString().trim();
		String craft3Misc = craft3MiscEnter.getText().toString().trim();
		int craft3Rank = Integer.parseInt(craft3Ranks);
		int craft3Mod = Integer.parseInt(craft3Misc);
		if (!craft3Ranks.matches("")) {
			Skill skill = new Skill("Craft3", AbilityName.INTELLIGENCE, craft3Rank, false);
			skill.addModifier("craft3Mod", craft3Mod);
			skills.add(skill);
		}

		// Diplomacy
		EditText diplomacyRanksEnter = (EditText) findViewById(R.id.diplomacy_ranks);
		EditText diplomacyMiscEnter = (EditText) findViewById(R.id.diplomacy_misc_mod);
		String diplomacyRanks = diplomacyRanksEnter.getText().toString().trim();
		String diplomacyMisc = diplomacyMiscEnter.getText().toString().trim();
		int diplomacyRank = Integer.parseInt(diplomacyRanks);
		int diplomacyMod = Integer.parseInt(diplomacyMisc);
		if (!diplomacyRanks.matches("")) {
			Skill skill = new Skill("Diplomacy", AbilityName.CHARISMA, diplomacyRank, false);
			skill.addModifier("diplomacyMod", diplomacyMod);
			skills.add(skill);
		}

		// Disable Device
		EditText disableDeviceRanksEnter = (EditText) findViewById(R.id.disable_device_ranks);
		EditText disableDeviceMiscEnter = (EditText) findViewById(R.id.disable_device_misc_mod);
		String disableDeviceRanks = disableDeviceRanksEnter.getText().toString().trim();
		String disableDeviceMisc = disableDeviceMiscEnter.getText().toString().trim();
		int disableDeviceRank = Integer.parseInt(disableDeviceRanks);
		int disableDeviceMod = Integer.parseInt(disableDeviceMisc);
		if (!disableDeviceRanks.matches("")) {
			Skill skill = new Skill("Disable Device", AbilityName.DEXTERITY, disableDeviceRank, false);
			skill.addModifier("disableDeviceMod", disableDeviceMod);
			skills.add(skill);
		}

		// Disguise
		EditText disguiseRanksEnter = (EditText) findViewById(R.id.disguise_ranks);
		EditText disguiseMiscEnter = (EditText) findViewById(R.id.disguise_misc_mod);
		String disguiseRanks = disguiseRanksEnter.getText().toString().trim();
		String disguiseMisc = disguiseMiscEnter.getText().toString().trim();
		int disguiseRank = Integer.parseInt(disguiseRanks);
		int disguiseMod = Integer.parseInt(disguiseMisc);
		if (!disguiseRanks.matches("")) {
			Skill skill = new Skill("Disguise", AbilityName.CHARISMA, disguiseRank, false);
			skill.addModifier("disguiseMod", disguiseMod);
			skills.add(skill);
		}

		// Escape Artist
		EditText escapeArtistRanksEnter = (EditText) findViewById(R.id.escape_artist_ranks);
		EditText escapeArtistMiscEnter = (EditText) findViewById(R.id.escape_artist_misc_mod);
		String escapeArtistRanks = escapeArtistRanksEnter.getText().toString().trim();
		String escapeArtistMisc = escapeArtistMiscEnter.getText().toString().trim();
		int escapeArtistRank = Integer.parseInt(escapeArtistRanks);
		int escapeArtistMod = Integer.parseInt(escapeArtistMisc);
		if (!escapeArtistRanks.matches("")) {
			Skill skill = new Skill("Escape Artist", AbilityName.DEXTERITY, escapeArtistRank, false);
			skill.addModifier("escapeArtistMod", escapeArtistMod);
			skills.add(skill);
		}

		// Fly
		EditText flyRanksEnter = (EditText) findViewById(R.id.fly_ranks);
		EditText flyMiscEnter = (EditText) findViewById(R.id.fly_misc_mod);
		String flyRanks = flyRanksEnter.getText().toString().trim();
		String flyMisc = flyMiscEnter.getText().toString().trim();
		int flyRank = Integer.parseInt(flyRanks);
		int flyMod = Integer.parseInt(flyMisc);
		if (!flyRanks.matches("")) {
			Skill skill = new Skill("Fly", AbilityName.DEXTERITY, flyRank, false);
			skill.addModifier("flyMod", flyMod);
			skills.add(skill);
		}

		// Handle Animal
		EditText handleAnimalRanksEnter = (EditText) findViewById(R.id.handle_animal_ranks);
		EditText handleAnimalMiscEnter = (EditText) findViewById(R.id.handle_animal_misc_mod);
		String handleAnimalRanks = handleAnimalRanksEnter.getText().toString().trim();
		String handleAnimalMisc = handleAnimalMiscEnter.getText().toString().trim();
		int handleAnimalRank = Integer.parseInt(handleAnimalRanks);
		int handleAnimalMod = Integer.parseInt(handleAnimalMisc);
		if (!handleAnimalRanks.matches("")) {
			Skill skill = new Skill("Handle Animal", AbilityName.CHARISMA, handleAnimalRank, false);
			skill.addModifier("handleAnimalMod", handleAnimalMod);
			skills.add(skill);
		}


		
		// return to character creation main screen
		Intent intent = new Intent(this, CharCreateMainActivity.class);
		intent.putExtra("cid", charID);
		startActivity(intent);
	}
	
}
