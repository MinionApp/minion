package uw.cse403.minion;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class AbilityScoresActivity extends Activity {
	private static final String CHARACTER_ID = "cid";
	
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
		int[] abScoreFields = { R.id.str_ab_score, R.id.dex_ab_score, R.id.con_ab_score, 
				R.id.int_ab_score, R.id.wis_ab_score, R.id.cha_ab_score };
		int[] abModFields = { R.id.str_ab_mod, R.id.dex_ab_mod, R.id.con_ab_mod, 
				R.id.int_ab_mod, R.id.wis_ab_mod, R.id.cha_ab_mod };
		int[] baseFields = { R.id.str_base, R.id.dex_base, R.id.con_base, 
						R.id.int_base, R.id.wis_base, R.id.cha_base };
		int[] tempFields = { R.id.str_temp, R.id.dex_temp, R.id.con_temp, 
						R.id.int_temp, R.id.wis_temp, R.id.cha_temp };
		for (int i = 0; i < abilities.length; i++) {
			if (!abilities[i].isNew) {
				System.out.println(i + "  " + abilities[i].getBase());

				TextView abScoreView = (TextView) findViewById(abScoreFields[i]);
				abScoreView.setText(""+abilities[i].getScore());

				TextView abModView = (TextView) findViewById(abModFields[i]);
				abModView.setText(""+abilities[i].getMod());

				EditText baseEnter = (EditText) findViewById(baseFields[i]);
				baseEnter.setText(""+abilities[i].getBase());

				EditText tempEnter = (EditText) findViewById(tempFields[i]);
				tempEnter.setText(""+abilities[i].getTempModifier(Ability.SAMPLE_MODIFIER));
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
		EditText strBaseEnter = (EditText) findViewById(R.id.str_base);
		String strBaseRaw = strBaseEnter.getText().toString().trim();
		Integer strBase;
		if (!strBaseRaw.matches("")) {
			strBase = Integer.parseInt(strBaseRaw);
		} else {
			strBase = defaultValue;
		}
		Ability str = abilities[0];
		str.setBase(strBase);

		EditText strTempEnter = (EditText) findViewById(R.id.str_temp);
		String strTempRaw = strTempEnter.getText().toString().trim();
		if (!strTempRaw.matches("")) {
			Integer strTemp = Integer.parseInt(strTempRaw);
			str.addTempModifier(Ability.SAMPLE_MODIFIER, strTemp);

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
		EditText dexBaseEnter = (EditText) findViewById(R.id.dex_base);
		String dexBaseRaw = dexBaseEnter.getText().toString().trim();
		Integer dexBase;
		if (!dexBaseRaw.matches("")) {
			dexBase = Integer.parseInt(dexBaseRaw);
		} else {
			dexBase = defaultValue;
		}
		Ability dex = abilities[1];
		dex.setBase(dexBase);

		EditText dexTempEnter = (EditText) findViewById(R.id.dex_temp);
		String dexTempRaw = dexTempEnter.getText().toString().trim();
		if (!dexTempRaw.matches("")) {
			Integer dexTemp = Integer.parseInt(dexTempRaw);
			dex.addTempModifier(Ability.SAMPLE_MODIFIER, dexTemp);

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
		EditText conBaseEnter = (EditText) findViewById(R.id.con_base);
		String conBaseRaw = conBaseEnter.getText().toString().trim();
		Integer conBase;
		if (!conBaseRaw.matches("")) {
			conBase = Integer.parseInt(conBaseRaw);
		} else {
			conBase = defaultValue;
		}
		Ability con = abilities[2];
		con.setBase(conBase);

		EditText conTempEnter = (EditText) findViewById(R.id.con_temp);
		String conTempRaw = conTempEnter.getText().toString().trim();
		if (!conTempRaw.matches("")) {
			Integer conTemp = Integer.parseInt(conTempRaw);
			con.addTempModifier(Ability.SAMPLE_MODIFIER, conTemp);

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
		EditText intelBaseEnter = (EditText) findViewById(R.id.int_base);
		String intelBaseRaw = intelBaseEnter.getText().toString().trim();
		Integer intelBase;
		if (!intelBaseRaw.matches("")) {
			intelBase = Integer.parseInt(intelBaseRaw);
		} else {
			intelBase = defaultValue;
		}
		Ability intel = abilities[3];
		intel.setBase(intelBase);

		EditText intelTempEnter = (EditText) findViewById(R.id.int_temp);
		String intelTempRaw = intelTempEnter.getText().toString().trim();
		if (!intelTempRaw.matches("")) {
			Integer intelTemp = Integer.parseInt(intelTempRaw);
			intel.addTempModifier(Ability.SAMPLE_MODIFIER, intelTemp);

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
		EditText wisBaseEnter = (EditText) findViewById(R.id.wis_base);
		String wisBaseRaw = wisBaseEnter.getText().toString().trim();
		Integer wisBase;
		if (!wisBaseRaw.matches("")) {
			wisBase = Integer.parseInt(wisBaseRaw);
		} else {
			wisBase = defaultValue;
		}
		Ability wis = abilities[4];
		wis.setBase(wisBase);

		EditText wisTempEnter = (EditText) findViewById(R.id.wis_temp);
		String wisTempRaw = wisTempEnter.getText().toString().trim();
		if (!wisTempRaw.matches("")) {
			Integer wisTemp = Integer.parseInt(wisTempRaw);
			wis.addTempModifier(Ability.SAMPLE_MODIFIER, wisTemp);

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
		EditText chaBaseEnter = (EditText) findViewById(R.id.cha_base);
		String chaBaseRaw = chaBaseEnter.getText().toString().trim();
		Integer chaBase;
		if (!chaBaseRaw.matches("")) {
			chaBase = Integer.parseInt(chaBaseRaw);
		} else {
			chaBase = defaultValue;
		}
		Ability cha = abilities[5];
		cha.setBase(chaBase);

		EditText chaTempEnter = (EditText) findViewById(R.id.cha_temp);
		String chaTempRaw = chaTempEnter.getText().toString().trim();
		if (!chaTempRaw.matches("")) {
			Integer chaTemp = Integer.parseInt(chaTempRaw);
			cha.addTempModifier(Ability.SAMPLE_MODIFIER, chaTemp);

		}
		
		return cha;
	}

}
