package uw.cse403.minion;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class CharCreateMainActivity extends Activity {
	private static final String CHARACTER_ID = "cid";
	
	private Character newChar = null;
	private long charID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_char_create_main);
		if(newChar == null){
			newChar = new Character();
		}
		try {
			charID = this.getIntent().getExtras().getLong(CHARACTER_ID);
		} catch (Exception e) {
			charID = CharacterDataSource.getNewID();
			// disable buttons until Basic Info is completed.
			Button b1 = (Button)findViewById(R.id.ability_scores); b1.setEnabled(false);
			Button b2 = (Button)findViewById(R.id.skills); b2.setEnabled(false);
			Button b3 = (Button)findViewById(R.id.combat); b3.setEnabled(false);
			Button b4 = (Button)findViewById(R.id.saving_throws); b4.setEnabled(false);
			Button b5 = (Button)findViewById(R.id.done_button); b5.setEnabled(false);
		}
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
		getMenuInflater().inflate(R.menu.char_create_main, menu);
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
	
	public void gotoBasicInfo(View view) {
		Intent intent = new Intent(this, BasicInfoActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
	
	public void gotoAbilityScores(View view) {
		Intent intent = new Intent(this, AbilityScoresActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
	
	public void gotoSkills(View view) {
		Intent intent = new Intent(this, SkillsActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
	
	public void gotoCombat(View view) {
		Intent intent = new Intent(this, CombatActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
	
	public void gotoSavingThrows(View view) {
		Intent intent = new Intent(this, SavingThrowsActivity.class);
		intent.putExtra(CHARACTER_ID, charID);
		startActivity(intent);
	}
	
	public void gotoCharacterList(View view) {
		Intent intent = new Intent(this, CharactersActivity.class);
		startActivity(intent);
	}

}
