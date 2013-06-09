package uw.cse403.minion;

import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

/**
 * CombatActivity is an activity that provides the user with the UI they
 * can use to enter and edit combat information about their character. It
 * handles both the population of the UI after loading the relevant information
 * from the local database as well as the task of writing any new or updated
 * information into the local database.
 * @author Kevin Dong (kevinxd3)
 */
public class CombatActivity extends Activity {

	/** The unique id for a character **/
	private long charID;

	/** Object that stores all the combat information about the character **/
	private Combat combat;

	/*
	 * Testing Results:
	 * As has been said throughout these comments (beginning in Ability and AbilityScoresActivity),
	 * much of the computation here is being done by the graphical drawing.
	 */
	/**
	 * Displays the combat page and loads in any previously entered information
	 * from the local database.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (TraceControl.TRACE)
			Debug.startMethodTracing("CombatActivity_onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_combat);
		charID = this.getIntent().getExtras().getLong("cid");
		combat = new Combat(charID);

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
		getMenuInflater().inflate(R.menu.combat, menu);
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
	 * Loads all of the combat information for the current character from the database.
	 */
	private void loadData() {
		// get dexterity modifier
		Ability dexterity = new Ability(charID, 1);
		combat.dexMod = dexterity.getMod();

		String size = "";
		Cursor cursor = SQLiteHelperBasicInfo.db.query(SQLiteHelperBasicInfo.TABLE_NAME, 
				new String[] {SQLiteHelperBasicInfo.COLUMN_SIZE}, 
				SQLiteHelperBasicInfo.COLUMN_ID + " = " + charID, null, null, null, null);
		if (cursor.moveToFirst()) {
			size = cursor.getString(0);
		}
		cursor.close();
		// size modifier determined by size
		// defined here: http://paizo.com/prd/combat.html#ac-enhancement-bonuses
		if (size.equals("Colossal"))
			combat.sizeMod = -8;
		if (size.equals("Gargantuan"))
			combat.sizeMod = -4;
		if (size.equals("Huge"))
			combat.sizeMod = -2;
		if (size.equals("Large"))
			combat.sizeMod = -1;
		if (size.equals("Medium"))
			combat.sizeMod = 0;
		if (size.equals("Small"))
			combat.sizeMod = 1;
		if (size.equals("Tiny"))
			combat.sizeMod = 2;
		if (size.equals("Diminutive"))
			combat.sizeMod = 4;
		if (size.equals("Fine"))
			combat.sizeMod = 8;

		// load user data
		if (!combat.isNew) {
			EditText hitPointsTotalEnter = (EditText) findViewById(R.id.hit_point_total_enter);
			hitPointsTotalEnter.setText("" + combat.getBaseHP());
			
			EditText hitPointsDrEnter = (EditText) findViewById(R.id.hit_point_dr_enter);
			hitPointsDrEnter.setText("" + combat.getDamageReduction());

			EditText speedBaseEnter = (EditText) findViewById(R.id.speed_base_enter);
			speedBaseEnter.setText("" + combat.speedBase);

			EditText speedArmorEnter = (EditText) findViewById(R.id.speed_armor_enter);
			speedArmorEnter.setText("" + combat.speedArmor);

			// initiative stuff
			TextView initiativeTotalField = (TextView) findViewById(R.id.initiative_total);
			initiativeTotalField.setText("" + combat.getInitTotal());
			
			TextView initiativeDexModField = (TextView) findViewById(R.id.initiative_dex_modifier_enter);
			initiativeDexModField.setText("" + combat.dexMod);

			EditText initiativeMiscModEnter = (EditText) findViewById(R.id.initiative_misc_modifier_enter);
			initiativeMiscModEnter.setText("" + combat.getInitModifier());

			// armor stuff
			TextView armorTotalField = (TextView) findViewById(R.id.armor_total);
			armorTotalField.setText("" + combat.getArmorTotal());
			
			EditText armorBonusEnter = (EditText) findViewById(R.id.armor_bonus_enter);
			armorBonusEnter.setText("" + combat.getArmorModifier(Combat.ARMOR_BONUS_STRING));
			
			EditText armorShieldEnter = (EditText) findViewById(R.id.armor_shield_enter);
			armorShieldEnter.setText("" + combat.getArmorModifier(Combat.ARMOR_SHIELD_STRING));
			
			TextView armorDexModField = (TextView) findViewById(R.id.armor_dex);
			armorDexModField.setText("" + combat.dexMod);
			
			TextView armorSizeModField = (TextView) findViewById(R.id.armor_size);
			armorSizeModField.setText("" + combat.sizeMod);
			
			EditText armorNaturalEnter = (EditText) findViewById(R.id.armor_natural_enter);
			armorNaturalEnter.setText("" + combat.getArmorModifier(Combat.ARMOR_NATURAL_STRING));
			
			EditText armorDeflectionEnter = (EditText) findViewById(R.id.armor_deflection_enter);
			armorDeflectionEnter.setText("" + combat.getArmorModifier(Combat.ARMOR_DEFLECTION_STRING));
			
			EditText armorMiscEnter = (EditText) findViewById(R.id.armor_misc_enter);
			armorMiscEnter.setText("" + combat.getArmorModifier(Combat.ARMOR_MISC_STRING));
			
			// base attack bonus
			EditText babEnter = (EditText) findViewById(R.id.attack_bonus_enter);
			babEnter.setText("" + combat.getbAb());
		}
	}

	/**
	 * Get all of the combat scores and store them.
	 */
	public void combatScores(View view) {
		int defaultSpeed = 0;
		int defaultArmorPen = 0;

		setHp();
		setSpeed(defaultSpeed, defaultArmorPen);
		setInitiative();	
		setArmor();
		// set base attack bonus
		EditText babEnter = (EditText) findViewById(R.id.attack_bonus_enter);
		String babRaw = babEnter.getText().toString().trim();
		if (!babRaw.matches("")) {
			int bab = Integer.parseInt(babRaw);
			combat.setbAb(bab);
		}

		combat.writeToDB();

		// return to character creation main screen
		Intent intent = new Intent(this, CharCreateMainActivity.class);
		intent.putExtra("cid", charID);
		startActivity(intent);
	}

	/**
	 * Parse the hit points and save them
	 */
	private void setHp() {
		EditText hitPointsTotalEnter = (EditText) findViewById(R.id.hit_point_total_enter);
		String hitPointsTotalRaw = hitPointsTotalEnter.getText().toString().trim();
		if (!hitPointsTotalRaw.matches("")) {
			int hitPointsTotal = Integer.parseInt(hitPointsTotalRaw);
			combat.setBaseHP(hitPointsTotal);
		}

		EditText hitPointsDrEnter = (EditText) findViewById(R.id.hit_point_dr_enter);
		String hitPointsDrRaw = hitPointsDrEnter.getText().toString().trim();
		if (!hitPointsDrRaw.matches("")) {
			int hitPointsDr = Integer.parseInt(hitPointsDrRaw);
			combat.setDamageReduction(hitPointsDr);
		}
	}

	/**
	 * Parse the speed and calculate the overall speed modifier
	 */
	private void setSpeed(int defaultSpeed, int defaultArmorPen) {
		EditText speedBaseEnter = (EditText) findViewById(R.id.speed_base_enter);
		String speedBaseRaw = speedBaseEnter.getText().toString().trim();
		int speedBase = defaultSpeed;
		if (!speedBaseRaw.matches("")) {
			speedBase = Integer.parseInt(speedBaseRaw);
		}

		EditText speedArmorEnter = (EditText) findViewById(R.id.speed_armor_enter);
		String speedArmorRaw = speedArmorEnter.getText().toString().trim();
		int speedArmor = defaultArmorPen;
		if (!speedArmorRaw.matches("")) {
			speedArmor = Integer.parseInt(speedArmorRaw);
		}

		combat.setSpeed(speedBase, speedArmor);
	}

	/**
	 * Parse the initiative values and calculate the overall initiative modifier
	 */
	private void setInitiative() {		
		EditText initiativeMiscModEnter = (EditText) findViewById(R.id.initiative_misc_modifier_enter);
		String initiativeMiscModRaw = initiativeMiscModEnter.getText().toString().trim();
		if (!initiativeMiscModRaw.matches("")) {
			int initiativeMiscMod = Integer.parseInt(initiativeMiscModRaw);
			combat.setInitModifiers(initiativeMiscMod);
		}
	}

	/**
	 * Parse and set the armor values in the combat object.
	 */
	private void setArmor() {
		EditText armorBonusEnter = (EditText) findViewById(R.id.armor_bonus_enter);
		String armorBonusRaw = armorBonusEnter.getText().toString().trim();
		if (!armorBonusRaw.matches("")) {
			int armorBonus = Integer.parseInt(armorBonusRaw);
			combat.addArmorModifier(Combat.ARMOR_BONUS_STRING, armorBonus);
		}

		EditText armorShieldEnter = (EditText) findViewById(R.id.armor_shield_enter);
		String armorShieldRaw = armorShieldEnter.getText().toString().trim();
		if (!armorShieldRaw.matches("")) {
			int armorShield = Integer.parseInt(armorShieldRaw);
			combat.addArmorModifier(Combat.ARMOR_SHIELD_STRING, armorShield);
		}

		EditText armorNaturalEnter = (EditText) findViewById(R.id.armor_natural_enter);
		String armorNaturalRaw = armorNaturalEnter.getText().toString().trim();
		if (!armorNaturalRaw.matches("")) {
			int armorNatural = Integer.parseInt(armorNaturalRaw);
			combat.addArmorModifier(Combat.ARMOR_NATURAL_STRING, armorNatural);
		}

		EditText armorDeflectionEnter = (EditText) findViewById(R.id.armor_deflection_enter);
		String armorDeflectionRaw = armorDeflectionEnter.getText().toString().trim();
		if (!armorDeflectionRaw.matches("")) {
			int armorDeflection = Integer.parseInt(armorDeflectionRaw);
			combat.addArmorModifier(Combat.ARMOR_DEFLECTION_STRING, armorDeflection);
		}

		EditText armorMiscEnter = (EditText) findViewById(R.id.armor_misc_enter);
		String armorMiscRaw = armorMiscEnter.getText().toString().trim();
		if (!armorMiscRaw.matches("")) {
			int armorMisc = Integer.parseInt(armorMiscRaw);
			combat.addArmorModifier(Combat.ARMOR_MISC_STRING, armorMisc);
		}

		combat.writeToDB();
	}
}
