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
	private static final String ARMOR_BONUS_STRING = "armorBonus";
	private static final String ARMOR_SHIELD_STRING = "armorShield";
	private static final String ARMOR_DEX_STRING = "armorDex";
	private static final String ARMOR_SIZE_STRING = "armorSize";
	private static final String ARMOR_NATURAL_STRING = "armorNatural";
	private static final String ARMOR_DEFLECTION_STRING = "armorDeflection";
	private static final String ARMOR_MISC_STRING = "armorMisc";

	private long charID;
	private Combat combat;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_combat);
		charID = this.getIntent().getExtras().getLong("cid");
		// Show the Up button in the action bar.
		setupActionBar();
		combat = new Combat();
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
		
		combat.setSpeed(speedBase - speedArmor);
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
			combat.addArmorModifiers(ARMOR_BONUS_STRING, armorBonus);
		}
		
		EditText armorShieldEnter = (EditText) findViewById(R.id.armor_shield_enter);
		String armorShieldRaw = armorShieldEnter.getText().toString().trim();
		if (!armorShieldRaw.matches("")) {
			int armorShield = Integer.parseInt(armorShieldRaw);
			combat.addArmorModifiers(ARMOR_SHIELD_STRING, armorShield);
		}
		
		EditText armorSizeEnter = (EditText) findViewById(R.id.armor_size_enter);
		String armorSizeRaw = armorSizeEnter.getText().toString().trim();
		if (!armorSizeRaw.matches("")) {
			int armorSize = Integer.parseInt(armorSizeRaw);
			combat.addArmorModifiers(ARMOR_SIZE_STRING, armorSize);
		}
		
		EditText armorNaturalEnter = (EditText) findViewById(R.id.armor_natural_enter);
		String armorNaturalRaw = armorNaturalEnter.getText().toString().trim();
		if (!armorNaturalRaw.matches("")) {
			int armorNatural = Integer.parseInt(armorNaturalRaw);
			combat.addArmorModifiers(ARMOR_NATURAL_STRING, armorNatural);
		}
		
		EditText armorDeflectionEnter = (EditText) findViewById(R.id.armor_deflection_enter);
		String armorDeflectionRaw = armorDeflectionEnter.getText().toString().trim();
		if (!armorDeflectionRaw.matches("")) {
			int armorDeflection = Integer.parseInt(armorDeflectionRaw);
			combat.addArmorModifiers(ARMOR_DEFLECTION_STRING, armorDeflection);
		}
		
		EditText armorMiscEnter = (EditText) findViewById(R.id.armor_misc_enter);
		String armorMiscRaw = armorMiscEnter.getText().toString().trim();
		if (!armorMiscRaw.matches("")) {
			int armorMisc = Integer.parseInt(armorMiscRaw);
			combat.addArmorModifiers(ARMOR_MISC_STRING, armorMisc);
		}
		
	}
}
