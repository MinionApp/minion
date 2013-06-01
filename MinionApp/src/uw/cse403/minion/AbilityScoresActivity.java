package uw.cse403.minion;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class AbilityScoresActivity extends Activity {
	private static final String CHARACTER_ID = "cid";
	private static final String SAMPLE_MODIFIER = "sampleModifier";
	
	private long charID;
	private Ability[] abilities;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ability_scores);
		
		charID = this.getIntent().getExtras().getLong(CHARACTER_ID);
		abilities = new Ability[6];
		abilities[0] = new Ability(charID, AbilityName.STRENGTH);
		abilities[1] = new Ability(charID, AbilityName.DEXTERITY);
		abilities[2] = new Ability(charID, AbilityName.CONSTITUTION);
		abilities[3] = new Ability(charID, AbilityName.INTELLIGENCE);
		abilities[4] = new Ability(charID, AbilityName.WISDOM);
		abilities[5] = new Ability(charID, AbilityName.CHARISMA);
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

	public void loadData() {
		int[] scoreFields = { R.id.str_ab_score, R.id.dex_ab_score, R.id.con_ab_score, 
						R.id.int_ab_score, R.id.wis_ab_score, R.id.cha_ab_score };
		int[] modFields = { R.id.str_ab_mod, R.id.dex_ab_mod, R.id.con_ab_mod, 
						R.id.int_ab_mod, R.id.wis_ab_mod, R.id.cha_ab_mod };
		for (int i = 0; i < abilities.length; i++) {
			if (!abilities[i].isNew) {
				System.out.println(i + "  " + abilities[i].getBase());
				EditText abScoreEnter = (EditText) findViewById(scoreFields[i]);
				abScoreEnter.setText(""+abilities[i].getBase());

				EditText modEnter = (EditText) findViewById(modFields[i]);
				modEnter.setText(""+abilities[i].getMod());
			}
		}
	}
	
	/**
	 * On "done" button on ability scores screen get an ability
	 * for each category and add them to an abilities array
	 * Add the abilities score to the character
	 */
	public void abilityScores(View view) {
		//TODO: Replace values with ones loaded from db
		int defaultScore = 10;
		
		Ability str = getStr(defaultScore);
		Ability dex = getDex(defaultScore);
		Ability con = getCon(defaultScore);
		Ability intel = getInt(defaultScore);
		Ability wis = getWis(defaultScore);
		Ability cha = getCha(defaultScore);
		abilities[0] = str;
		abilities[1] = dex;
		abilities[2] = con;
		abilities[3] = intel;
		abilities[4] = wis;
		abilities[5] = cha;
		
		// write to DB
		for (int i = 0; i < abilities.length; i++) {
			abilities[i].writeToDB();
			System.out.println("Writing charID=" + charID + " abilityID=" + i + " base=" + abilities[i].getBase());
		}
		
		// return to character creation main screen
		Intent intent = new Intent(this, CharCreateMainActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
	
	/**
	 * 
	 * Get the values for the strength ability and store them
	 * 
	 * @return str the strength Ability
	 */
	private Ability getStr(int defaultValue) {
		EditText strAbScoreEnter = (EditText) findViewById(R.id.str_ab_score);
		String strAbScoreRaw = strAbScoreEnter.getText().toString().trim();
		Integer strAbScore;
		if (!strAbScoreRaw.matches("")) {
			strAbScore = Integer.parseInt(strAbScoreRaw);
		} else {
			strAbScore = defaultValue;
		}
		Ability str = abilities[0];
		str.setBase(strAbScore);

		EditText strTempModEnter = (EditText) findViewById(R.id.str_temp);
		String strTempModRaw = strTempModEnter.getText().toString().trim();
		if (!strTempModRaw.matches("")) {
			Integer strTempMod = Integer.parseInt(strTempModRaw);
			str.addTempModifier(SAMPLE_MODIFIER, strTempMod);

		}
		
		return str;
	}
	
	/**
	 * 
	 * Get the values for the dexterity ability and store them
	 * 
	 * @return dex the dexterity Ability
	 */	
	private Ability getDex(int defaultValue) {
		EditText dexAbScoreEnter = (EditText) findViewById(R.id.dex_ab_score);
		String dexAbScoreRaw = dexAbScoreEnter.getText().toString().trim();
		Integer dexAbScore;
		if (!dexAbScoreRaw.matches("")) {
			dexAbScore = Integer.parseInt(dexAbScoreRaw);
		} else {
			dexAbScore = defaultValue;
		}
		Ability dex = abilities[1];
		dex.setBase(dexAbScore);

		EditText dexTempModEnter = (EditText) findViewById(R.id.dex_temp);
		String dexTempModRaw = dexTempModEnter.getText().toString().trim();
		if (!dexTempModRaw.matches("")) {
			Integer dexTempMod = Integer.parseInt(dexTempModRaw);
			dex.addTempModifier(SAMPLE_MODIFIER, dexTempMod);

		}
		
		return dex;
	}
	
	/**
	 * 
	 * Get the values for the constitution ability and store them
	 * 
	 * @return con the constitution Ability
	 */
	private Ability getCon(int defaultValue) {
		EditText conAbScoreEnter = (EditText) findViewById(R.id.con_ab_score);
		String conAbScoreRaw = conAbScoreEnter.getText().toString().trim();
		Integer conAbScore;
		if (!conAbScoreRaw.matches("")) {
			conAbScore = Integer.parseInt(conAbScoreRaw);
		} else {
			conAbScore = defaultValue;
		}
		Ability con = abilities[2];
		con.setBase(conAbScore);

		EditText conTempModEnter = (EditText) findViewById(R.id.con_temp);
		String conTempModRaw = conTempModEnter.getText().toString().trim();
		if (!conTempModRaw.matches("")) {
			Integer conTempMod = Integer.parseInt(conTempModRaw);
			con.addTempModifier(SAMPLE_MODIFIER, conTempMod);

		}
		
		return con;
	}
	
	/**
	 * 
	 * Get the values for the intelligence ability and store them
	 * 
	 * @return intel the intelligence Ability
	 */
	private Ability getInt(int defaultValue) {
		EditText intelAbScoreEnter = (EditText) findViewById(R.id.int_ab_score);
		String intelAbScoreRaw = intelAbScoreEnter.getText().toString().trim();
		Integer intelAbScore;
		if (!intelAbScoreRaw.matches("")) {
			intelAbScore = Integer.parseInt(intelAbScoreRaw);
		} else {
			intelAbScore = defaultValue;
		}
		Ability intel = abilities[3];
		intel.setBase(intelAbScore);

		EditText intelTempModEnter = (EditText) findViewById(R.id.int_temp);
		String intelTempModRaw = intelTempModEnter.getText().toString().trim();
		if (!intelTempModRaw.matches("")) {
			Integer intelTempMod = Integer.parseInt(intelTempModRaw);
			intel.addTempModifier(SAMPLE_MODIFIER, intelTempMod);

		}
		
		return intel;
	}
	
	/**
	 * 
	 * Get the values for the wisdom ability and store them
	 * 
	 * @return wis the wisdom Ability
	 */
	private Ability getWis(int defaultValue) {
		EditText wisAbScoreEnter = (EditText) findViewById(R.id.wis_ab_score);
		String wisAbScoreRaw = wisAbScoreEnter.getText().toString().trim();
		Integer wisAbScore;
		if (!wisAbScoreRaw.matches("")) {
			wisAbScore = Integer.parseInt(wisAbScoreRaw);
		} else {
			wisAbScore = defaultValue;
		}
		Ability wis = abilities[4];
		wis.setBase(wisAbScore);

		EditText wisTempModEnter = (EditText) findViewById(R.id.wis_temp);
		String wisTempModRaw = wisTempModEnter.getText().toString().trim();
		if (!wisTempModRaw.matches("")) {
			Integer wisTempMod = Integer.parseInt(wisTempModRaw);
			wis.addTempModifier(SAMPLE_MODIFIER, wisTempMod);

		}
		
		return wis;
	}
	
	/**
	 * 
	 * Get the values for the charisma ability and store them
	 * 
	 * @return cha the charisma Ability
	 */
	private Ability getCha(int defaultValue) {
		EditText chaAbScoreEnter = (EditText) findViewById(R.id.cha_ab_score);
		String chaAbScoreRaw = chaAbScoreEnter.getText().toString().trim();
		Integer chaAbScore;
		if (!chaAbScoreRaw.matches("")) {
			chaAbScore = Integer.parseInt(chaAbScoreRaw);
		} else {
			chaAbScore = defaultValue;
		}
		Ability cha = abilities[5];
		cha.setBase(chaAbScore);

		EditText chaTempModEnter = (EditText) findViewById(R.id.cha_temp);
		String chaTempModRaw = chaTempModEnter.getText().toString().trim();
		if (!chaTempModRaw.matches("")) {
			Integer chaTempMod = Integer.parseInt(chaTempModRaw);
			cha.addTempModifier(SAMPLE_MODIFIER, chaTempMod);

		}
		
		return cha;
	}

}
