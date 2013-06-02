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
import android.database.Cursor;
import android.os.Build;

public class SavingThrowsActivity extends Activity {
	private long charID;

	private SavingThrow fortitude;
	private SavingThrow reflex;
	private SavingThrow will;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saving_throws);
		charID = this.getIntent().getExtras().getLong("cid");

		fortitude = new SavingThrow(AbilityName.CONSTITUTION);
		reflex = new SavingThrow(AbilityName.DEXTERITY);
		will = new SavingThrow(AbilityName.WISDOM);

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
		getMenuInflater().inflate(R.menu.saving_throws, menu);
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
		// get relevant ability scores
		Ability constitution = new Ability(charID, AbilityName.CONSTITUTION);
		Ability dexterity = new Ability(charID, AbilityName.DEXTERITY);
		Ability wisdom = new Ability(charID, AbilityName.WISDOM);
		fortitude.abMod = constitution.getMod();
		reflex.abMod = dexterity.getMod();
		will.abMod = wisdom.getMod();

		if (!fortitude.isNew) {
			TextView fortTotalField = (TextView) findViewById(R.id.fortitude_total);
			fortTotalField.setText(""+fortitude.getTotal());

			EditText fortBaseEnter = (EditText) findViewById(R.id.fortitude_base);
			fortBaseEnter.setText(""+fortitude.getBaseSave());

			TextView fortAbModField = (TextView) findViewById(R.id.fortitude_ability);
			fortAbModField.setText(""+fortitude.abMod);

			EditText fortMagicEnter = (EditText) findViewById(R.id.fortitude_magic);
			fortMagicEnter.setText(""+fortitude.getModifier(SavingThrow.MAGIC_MOD_STRING));

			EditText fortMiscEnter = (EditText) findViewById(R.id.fortitude_misc);
			fortMiscEnter.setText(""+fortitude.getModifier(SavingThrow.MISC_MOD_STRING));

			EditText fortTempEnter = (EditText) findViewById(R.id.fortitude_temp);
			fortTempEnter.setText(""+fortitude.getModifier(SavingThrow.TEMP_MOD_STRING));
		}

		if (!reflex.isNew) {
			TextView reflexTotalField = (TextView) findViewById(R.id.reflex_total);
			reflexTotalField.setText(""+reflex.getTotal());

			EditText reflexBaseEnter = (EditText) findViewById(R.id.reflex_base);
			reflexBaseEnter.setText(""+reflex.getBaseSave());

			TextView reflexAbModField = (TextView) findViewById(R.id.reflex_ability);
			reflexAbModField.setText(""+reflex.abMod);

			EditText reflexMagicEnter = (EditText) findViewById(R.id.reflex_magic);
			reflexMagicEnter.setText(""+reflex.getModifier(SavingThrow.MAGIC_MOD_STRING));

			EditText reflexMiscEnter = (EditText) findViewById(R.id.reflex_misc);
			reflexMiscEnter.setText(""+reflex.getModifier(SavingThrow.MISC_MOD_STRING));

			EditText reflexTempEnter = (EditText) findViewById(R.id.reflex_temp);
			reflexTempEnter.setText(""+reflex.getModifier(SavingThrow.TEMP_MOD_STRING));
		}

		if (!will.isNew) {
			TextView willTotalField = (TextView) findViewById(R.id.will_total);
			willTotalField.setText(""+will.getTotal());

			EditText willBaseEnter = (EditText) findViewById(R.id.will_base);
			willBaseEnter.setText(""+will.getBaseSave());

			TextView willAbModField = (TextView) findViewById(R.id.will_ability);
			willAbModField.setText(""+will.abMod);

			EditText willMagicEnter = (EditText) findViewById(R.id.will_magic);
			willMagicEnter.setText(""+will.getModifier(SavingThrow.MAGIC_MOD_STRING));

			EditText willMiscEnter = (EditText) findViewById(R.id.will_misc);
			willMiscEnter.setText(""+will.getModifier(SavingThrow.MISC_MOD_STRING));

			EditText willTempEnter = (EditText) findViewById(R.id.will_temp);
			willTempEnter.setText(""+will.getModifier(SavingThrow.TEMP_MOD_STRING));
		}
	}

	public void savingThrows(View view) {
		setFortitude();
		setReflex();
		setWill();

		fortitude.writeToDB(charID);
		reflex.writeToDB(charID);
		will.writeToDB(charID);



		// return to character creation main screen
		Intent intent = new Intent(this, CharCreateMainActivity.class);
		intent.putExtra("cid", charID);
		startActivity(intent);
	}

	private void setFortitude() {
		EditText fortBaseEnter = (EditText) findViewById(R.id.fortitude_base);
		String fortBaseRaw = fortBaseEnter.getText().toString().trim();
		if(fortBaseRaw.matches("")) {
			fortBaseRaw = "0";
		}
		if (!fortBaseRaw.matches("")) {
			int fortBase = Integer.parseInt(fortBaseRaw);
			fortitude.setBaseSave(fortBase);
		}

		EditText fortMagicEnter = (EditText) findViewById(R.id.fortitude_magic);
		String fortMagicRaw = fortMagicEnter.getText().toString().trim();
		if(fortMagicRaw.matches("")) {
			fortBaseRaw = "0";
		}
		if (!fortMagicRaw.matches("")) {
			int fortMagic = Integer.parseInt(fortMagicRaw);
			fortitude.addModifier(SavingThrow.MAGIC_MOD_STRING, fortMagic);
		}

		EditText fortMiscEnter = (EditText) findViewById(R.id.fortitude_misc);
		String fortMiscRaw = fortMiscEnter.getText().toString().trim();
		if(fortMiscRaw.matches("")) {
			fortBaseRaw = "0";
		}
		if (!fortMiscRaw.matches("")) {
			int fortMisc = Integer.parseInt(fortMiscRaw);
			fortitude.addModifier(SavingThrow.MISC_MOD_STRING, fortMisc);
		}

		EditText fortTempEnter = (EditText) findViewById(R.id.fortitude_temp);
		String fortTempRaw = fortTempEnter.getText().toString().trim();
		if(fortTempRaw.matches("")) {
			fortBaseRaw = "0";
		}
		if (!fortTempRaw.matches("")) {
			int fortTemp = Integer.parseInt(fortTempRaw);
			fortitude.addModifier(SavingThrow.TEMP_MOD_STRING, fortTemp);
		}
	}

	private void setReflex() {
		EditText reflexBaseEnter = (EditText) findViewById(R.id.reflex_base);
		String reflexBaseRaw = reflexBaseEnter.getText().toString().trim();
		if(reflexBaseRaw.matches("")) {
			reflexBaseRaw = "0";
		}
		if (!reflexBaseRaw.matches("")) {
			int reflexBase = Integer.parseInt(reflexBaseRaw);
			reflex.setBaseSave(reflexBase);
		}

		EditText reflexMagicEnter = (EditText) findViewById(R.id.reflex_magic);
		String reflexMagicRaw = reflexMagicEnter.getText().toString().trim();
		if(reflexMagicRaw.matches("")) {
			reflexMagicRaw = "0";
		}
		if (!reflexMagicRaw.matches("")) {
			int reflexMagic = Integer.parseInt(reflexMagicRaw);
			reflex.addModifier(SavingThrow.MAGIC_MOD_STRING, reflexMagic);
		}

		EditText reflexMiscEnter = (EditText) findViewById(R.id.reflex_misc);
		String reflexMiscRaw = reflexMiscEnter.getText().toString().trim();
		if(reflexMiscRaw.matches("")) {
			reflexMiscRaw = "0";
		}
		if (!reflexMiscRaw.matches("")) {
			int reflexMisc = Integer.parseInt(reflexMiscRaw);
			reflex.addModifier(SavingThrow.MISC_MOD_STRING, reflexMisc);
		}

		EditText reflexTempEnter = (EditText) findViewById(R.id.reflex_temp);
		String reflexTempRaw = reflexTempEnter.getText().toString().trim();
		if(reflexTempRaw.matches("")) {
			reflexTempRaw = "0";
		}
		if (!reflexTempRaw.matches("")) {
			int reflexTemp = Integer.parseInt(reflexTempRaw);
			reflex.addModifier(SavingThrow.TEMP_MOD_STRING, reflexTemp);
		}
	}

	private void setWill() {
		EditText willBaseEnter = (EditText) findViewById(R.id.will_base);
		String willBaseRaw = willBaseEnter.getText().toString().trim();
		if(willBaseRaw.matches("")) {
			willBaseRaw = "0";
		}
		if (!willBaseRaw.matches("")) {
			int willBase = Integer.parseInt(willBaseRaw);
			will.setBaseSave(willBase);
		}

		EditText willMagicEnter = (EditText) findViewById(R.id.will_magic);
		String willMagicRaw = willMagicEnter.getText().toString().trim();
		if(willMagicRaw.matches("")) {
			willMagicRaw = "0";
		}
		if (!willMagicRaw.matches("")) {
			int willMagic = Integer.parseInt(willMagicRaw);
			will.addModifier(SavingThrow.MAGIC_MOD_STRING, willMagic);
		}

		EditText willMiscEnter = (EditText) findViewById(R.id.will_misc);
		String willMiscRaw = willMiscEnter.getText().toString().trim();
		if(willMiscRaw.matches("")) {
			willMiscRaw = "0";
		}
		if (!willMiscRaw.matches("")) {
			int willMisc = Integer.parseInt(willMiscRaw);
			will.addModifier(SavingThrow.MISC_MOD_STRING, willMisc);
		}

		EditText willTempEnter = (EditText) findViewById(R.id.will_temp);
		String willTempRaw = willTempEnter.getText().toString().trim();
		if(willTempRaw.matches("")) {
			willTempRaw = "0";
		}
		if (!willTempRaw.matches("")) {
			int willTemp = Integer.parseInt(willTempRaw);
			will.addModifier(SavingThrow.TEMP_MOD_STRING, willTemp);
		}
	}

}
