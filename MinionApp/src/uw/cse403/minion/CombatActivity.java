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

public class CombatActivity extends Activity {
//	public static final String ARMOR_BONUS_STRING = "armorBonus";
//	public static final String ARMOR_SHIELD_STRING = "armorShield";
//	public static final String ARMOR_DEX_STRING = "armorDex";
//	public static final String ARMOR_SIZE_STRING = "armorSize";
//	public static final String ARMOR_NATURAL_STRING = "armorNatural";
//	public static final String ARMOR_DEFLECTION_STRING = "armorDeflection";
//	public static final String ARMOR_MISC_STRING = "armorMisc";

	private long charID;
	private Combat combat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_combat);
		charID = this.getIntent().getExtras().getLong("cid");
		combat = new Combat(charID);

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
		getMenuInflater().inflate(R.menu.combat, menu);
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

	private void loadData() {
		// TODO Auto-generated method stub
		if (!combat.isNew) {
			EditText hitPointsTotalEnter = (EditText) findViewById(R.id.hit_point_total_enter);
			hitPointsTotalEnter.setText(""+combat.getBaseHP());
			
			EditText hitPointsDrEnter = (EditText) findViewById(R.id.hit_point_dr_enter);
			hitPointsDrEnter.setText(""+combat.getDamageReduction());

			EditText speedBaseEnter = (EditText) findViewById(R.id.speed_base_enter);
			speedBaseEnter.setText(""+combat.speedBase);
			
			EditText speedArmorEnter = (EditText) findViewById(R.id.speed_armor_enter);
			speedArmorEnter.setText(""+combat.speedArmor);

			EditText initiativeMiscModEnter = (EditText) findViewById(R.id.initiative_misc_modifier_enter);
			initiativeMiscModEnter.setText(""+combat.getInitModifier());

			// armor stuff
			EditText armorBonusEnter = (EditText) findViewById(R.id.armor_bonus_enter);
			armorBonusEnter.setText(""+combat.getArmorModifier(Combat.ARMOR_BONUS_STRING));
			
			EditText armorShieldEnter = (EditText) findViewById(R.id.armor_shield_enter);
			armorShieldEnter.setText(""+combat.getArmorModifier(Combat.ARMOR_SHIELD_STRING));
			
			EditText armorNaturalEnter = (EditText) findViewById(R.id.armor_natural_enter);
			armorNaturalEnter.setText(""+combat.getArmorModifier(Combat.ARMOR_NATURAL_STRING));
			
			EditText armorDeflectionEnter = (EditText) findViewById(R.id.armor_deflection_enter);
			armorDeflectionEnter.setText(""+combat.getArmorModifier(Combat.ARMOR_DEFLECTION_STRING));
			
			EditText armorMiscEnter = (EditText) findViewById(R.id.armor_misc_enter);
			armorMiscEnter.setText(""+combat.getArmorModifier(Combat.ARMOR_MISC_STRING));
			
			// base attack bonus
			EditText babEnter = (EditText) findViewById(R.id.attack_bonus_enter);
			babEnter.setText(""+combat.getbAb());
		}
	}

	/**
	 * Get all of the combat scores and store them.
	 */
	public void combatScores(View view) {
		//TODO: Set defaults with db
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
		
		combat.writeToDB(charID);
		
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
		
//		EditText armorSizeEnter = (EditText) findViewById(R.id.armor_size_enter);
//		String armorSizeRaw = armorSizeEnter.getText().toString().trim();
//		if (!armorSizeRaw.matches("")) {
//			int armorSize = Integer.parseInt(armorSizeRaw);
//			combat.addArmorModifier(Combat.ARMOR_SIZE_STRING, armorSize);
//		}
		
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
		
		combat.writeToDB(charID);
		
	}
}
