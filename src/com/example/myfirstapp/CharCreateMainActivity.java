package com.example.myfirstapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class CharCreateMainActivity extends Activity {
	private Character newChar = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_char_create_main);
		if(newChar == null){
			newChar = new Character();
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
		intent.putExtra("cid", newChar.id);
		
		//Bundle bundle = new Bundle();
		//bundle.putSerializable("character", new Character());
		startActivity(intent);
	}
	
	public void gotoAbilityScores(View view) {
		Intent intent = new Intent(this, AbilityScoresActivity.class);
		intent.putExtra("cid", newChar.id);
		startActivity(intent);
	}
	
	public void gotoSkills(View view) {
		Intent intent = new Intent(this, SkillsActivity.class);
		intent.putExtra("cid", newChar.id);
		startActivity(intent);
	}
	
	public void gotoCombat(View view) {
		Intent intent = new Intent(this, CombatActivity.class);
		intent.putExtra("cid", newChar.id);
		startActivity(intent);
	}
	
	public void gotoSavingThrows(View view) {
		Intent intent = new Intent(this, SavingThrowsActivity.class);
		intent.putExtra("cid", newChar.id);
		startActivity(intent);
	}

}
