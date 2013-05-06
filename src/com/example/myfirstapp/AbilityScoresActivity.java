package com.example.myfirstapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class AbilityScoresActivity extends Activity {
	private static final String SAMPLE_MODIFIER = "sampleModifier";
	
	private Ability[] abilities;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ability_scores);
		// Show the Up button in the action bar.
		setupActionBar();
		abilities = new Ability[6];
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
		getMenuInflater().inflate(R.menu.ability_scores, menu);
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
	
	/**
	 * On "done" button on ability scores screen get and ability
	 * for each category and add them to an abilities array
	 * Add the abilities score to the character
	 */
	public void abilityScores() {
		Ability str = getStr();
		Ability dex = getDex();
		Ability con = getCon();
		Ability intel = getInt();
		Ability wis = getWis();
		Ability cha = getCha();
		abilities[0] = str;
		abilities[1] = dex;
		abilities[2] = con;
		abilities[3] = intel;
		abilities[4] = wis;
		abilities[5] = cha;
		Intent recievedIntent = getIntent();
		Character newChar = (Character) recievedIntent.getSerializableExtra("new character");
		newChar.setAbilityScores(abilities);
	}
	
	/**
	 * 
	 * Get the values for the strength ability and store them
	 * 
	 * @return str the strength Ability
	 */
	private Ability getStr() {
		EditText strAbScoreEnter = (EditText) findViewById(R.id.str_ab_score);
		String strAbScoreRaw = strAbScoreEnter.getText().toString().trim();
		Integer strAbScore = Integer.getInteger(strAbScoreRaw);
		
		EditText strAbModEnter = (EditText) findViewById(R.id.str_ab_mod);
		String strAbModRaw = strAbModEnter.getText().toString().trim();
		Integer strAbMod = Integer.getInteger(strAbModRaw);
		
		EditText strTempAdjustEnter = (EditText) findViewById(R.id.str_temp_adjust);
		String strTempAdjustRaw = strTempAdjustEnter.getText().toString().trim();
		Integer strTempAdjust = Integer.getInteger(strTempAdjustRaw);
		
		EditText strTempModEnter = (EditText) findViewById(R.id.str_temp_mod);
		String strTempModRaw = strTempModEnter.getText().toString().trim();
		Integer strTempMod = Integer.getInteger(strTempModRaw);
		
		Ability str = new Ability(AbilityName.STRENGTH, strAbScore);
		str.addTempModifier(SAMPLE_MODIFIER, strTempMod);
		return str;
	}
	
	/**
	 * 
	 * Get the values for the dexterity ability and store them
	 * 
	 * @return dex the dexterity Ability
	 */	
	private Ability getDex() {
		EditText dexAbScoreEnter = (EditText) findViewById(R.id.dex_ab_score);
		String dexAbScoreRaw = dexAbScoreEnter.getText().toString().trim();
		Integer dexAbScore = Integer.getInteger(dexAbScoreRaw);
		
		EditText dexAbModEnter = (EditText) findViewById(R.id.dex_ab_mod);
		String dexAbModRaw = dexAbModEnter.getText().toString().trim();
		Integer dexAbMod = Integer.getInteger(dexAbModRaw);
		
		EditText dexTempAdjustEnter = (EditText) findViewById(R.id.dex_temp_adjust);
		String dexTempAdjustRaw = dexTempAdjustEnter.getText().toString().trim();
		Integer dexTempAdjust = Integer.getInteger(dexTempAdjustRaw);
		
		EditText dexTempModEnter = (EditText) findViewById(R.id.dex_temp_mod);
		String dexTempModRaw = dexTempModEnter.getText().toString().trim();
		Integer dexTempMod = Integer.getInteger(dexTempModRaw);
		
		Ability dex = new Ability(AbilityName.DEXTERITY, dexAbScore);
		dex.addTempModifier(SAMPLE_MODIFIER, dexTempMod);
		return dex;
	}
	
	/**
	 * 
	 * Get the values for the constitution ability and store them
	 * 
	 * @return con the constitution Ability
	 */
	private Ability getCon() {
		EditText conAbScoreEnter = (EditText) findViewById(R.id.con_ab_score);
		String conAbScoreRaw = conAbScoreEnter.getText().toString().trim();
		Integer conAbScore = Integer.getInteger(conAbScoreRaw);
		
		EditText conAbModEnter = (EditText) findViewById(R.id.con_ab_mod);
		String conAbModRaw = conAbModEnter.getText().toString().trim();
		Integer conAbMod = Integer.getInteger(conAbModRaw);
		
		EditText conTempAdjustEnter = (EditText) findViewById(R.id.con_temp_adjust);
		String conTempAdjustRaw = conTempAdjustEnter.getText().toString().trim();
		Integer conTempAdjust = Integer.getInteger(conTempAdjustRaw);
		
		EditText conTempModEnter = (EditText) findViewById(R.id.con_temp_mod);
		String conTempModRaw = conTempModEnter.getText().toString().trim();
		Integer conTempMod = Integer.getInteger(conTempModRaw);
		
		Ability con = new Ability(AbilityName.CONSTITUTION, conAbScore);
		con.addTempModifier(SAMPLE_MODIFIER, conTempMod);
		return con;
	}
	
	/**
	 * 
	 * Get the values for the intelligence ability and store them
	 * 
	 * @return intel the intelligence Ability
	 */
	private Ability getInt() {
		EditText intAbScoreEnter = (EditText) findViewById(R.id.int_ab_score);
		String intAbScoreRaw = intAbScoreEnter.getText().toString().trim();
		Integer intAbScore = Integer.getInteger(intAbScoreRaw);
		
		EditText intAbModEnter = (EditText) findViewById(R.id.int_ab_mod);
		String intAbModRaw = intAbModEnter.getText().toString().trim();
		Integer intAbMod = Integer.getInteger(intAbModRaw);
		
		EditText intTempAdjustEnter = (EditText) findViewById(R.id.int_temp_adjust);
		String intTempAdjustRaw = intTempAdjustEnter.getText().toString().trim();
		Integer intTempAdjust = Integer.getInteger(intTempAdjustRaw);
		
		EditText intTempModEnter = (EditText) findViewById(R.id.int_temp_mod);
		String intTempModRaw = intTempModEnter.getText().toString().trim();
		Integer intTempMod = Integer.getInteger(intTempModRaw);
		
		Ability intel = new Ability(AbilityName.INTELLIGENCE, intAbScore);
		intel.addTempModifier(SAMPLE_MODIFIER, intTempMod);
		return intel;
	}
	
	/**
	 * 
	 * Get the values for the wisdom ability and store them
	 * 
	 * @return wis the wisdom Ability
	 */
	private Ability getWis() {
		EditText wisAbScoreEnter = (EditText) findViewById(R.id.wis_ab_score);
		String wisAbScoreRaw = wisAbScoreEnter.getText().toString().trim();
		Integer wisAbScore = Integer.getInteger(wisAbScoreRaw);
		
		EditText wisAbModEnter = (EditText) findViewById(R.id.wis_ab_mod);
		String wisAbModRaw = wisAbModEnter.getText().toString().trim();
		Integer wisAbMod = Integer.getInteger(wisAbModRaw);
		
		EditText wisTempAdjustEnter = (EditText) findViewById(R.id.wis_temp_adjust);
		String wisTempAdjustRaw = wisTempAdjustEnter.getText().toString().trim();
		Integer wisTempAdjust = Integer.getInteger(wisTempAdjustRaw);
		
		EditText wisTempModEnter = (EditText) findViewById(R.id.wis_temp_mod);
		String wisTempModRaw = wisTempModEnter.getText().toString().trim();
		Integer wisTempMod = Integer.getInteger(wisTempModRaw);
		
		Ability wis = new Ability(AbilityName.WISDOME, wisAbScore);
		wis.addTempModifier(SAMPLE_MODIFIER, wisTempMod);
		return wis;
	}
	
	/**
	 * 
	 * Get the values for the charisma ability and store them
	 * 
	 * @return cha the charisma Ability
	 */
	private Ability getCha() {
		EditText chaAbScoreEnter = (EditText) findViewById(R.id.cha_ab_score);
		String chaAbScoreRaw = chaAbScoreEnter.getText().toString().trim();
		Integer chaAbScore = Integer.getInteger(chaAbScoreRaw);
		
		EditText chaAbModEnter = (EditText) findViewById(R.id.cha_ab_mod);
		String chaAbModRaw = chaAbModEnter.getText().toString().trim();
		Integer chaAbMod = Integer.getInteger(chaAbModRaw);
		
		EditText chaTempAdjustEnter = (EditText) findViewById(R.id.cha_temp_adjust);
		String chaTempAdjustRaw = chaTempAdjustEnter.getText().toString().trim();
		Integer chaTempAdjust = Integer.getInteger(chaTempAdjustRaw);
		
		EditText chaTempModEnter = (EditText) findViewById(R.id.cha_temp_mod);
		String chaTempModRaw = chaTempModEnter.getText().toString().trim();
		Integer chaTempMod = Integer.getInteger(chaTempModRaw);
		
		Ability cha = new Ability(AbilityName.CHARISMA, chaAbScore);
		cha.addTempModifier(SAMPLE_MODIFIER, chaTempMod);
		return cha;
	}

}
