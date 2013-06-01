package uw.cse403.minion;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

public class CharCreateMainActivity extends Activity {
	private static final String CHARACTER_ID = "cid";
	
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/uploadCharacter.php";
	private String username;
	
	private Character newChar = null;
	private long charID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_char_create_main);
		username = SaveSharedPreference.getPersistentUserName(CharCreateMainActivity.this);
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
    	// Checks for internet connectivity
    	if (ConnectionChecker.hasConnection(this)) {
    	    // Updates login credentials on remote database
    		UploadCharacterTask task = new UploadCharacterTask(this);
    	    task.execute(username);
    	} else {
    		Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
    	}
	}


	/**
	 * UploadCharacterTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It uploads any currently filled out character
	 * information to the remote database for storage.
	 */
	private class UploadCharacterTask extends AsyncTask<String, Void, String> {
		private Context context;
		private ProgressDialog dialog;
		
		/**
		 * Constructs a new UploadCharacterTask object.
		 * @param context The current Activity's context
		 */
		private UploadCharacterTask (Context context) {
			this.context = context;
			dialog = new ProgressDialog(context);
		}
		
		protected void onPreExecute() {
            this.dialog.setMessage("Uploading character...");
            this.dialog.show();
        }
		
	    /**
	     * Makes the HTTP request and returns the result as a String.
	     */
	    protected String doInBackground(String... args) {
	    	
	    	// Creates JSON object for basic character information
	    	CharacterDescription baseInfo = new CharacterDescription(charID);
	    	JSONObject basicInfo = new JSONObject();
	    	try {
	    		long id = charID;
				basicInfo.put("local_id", id);
		    	basicInfo.put("name", baseInfo.name);
		    	
		    	basicInfo.put("alignment", baseInfo.alignment);
		    	basicInfo.put("level", baseInfo.level);
		    	basicInfo.put("diety", baseInfo.deity);
		    	basicInfo.put("homeland", baseInfo.homeLand);
		    	basicInfo.put("race", baseInfo.race);
		    	basicInfo.put("size", baseInfo.size);
		    	basicInfo.put("gender", baseInfo.gender);
		    	basicInfo.put("age", baseInfo.age);
		    	basicInfo.put("height", baseInfo.height);
		    	basicInfo.put("weight", baseInfo.weight);
		    	basicInfo.put("hair", baseInfo.hair);
		    	basicInfo.put("eyes", baseInfo.eyes);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	// Creates JSON object for character abilities
	    	Ability[] abilities = new Ability[6];
			abilities[0] = new Ability(charID, AbilityName.STRENGTH);
			abilities[1] = new Ability(charID, AbilityName.DEXTERITY);
			abilities[2] = new Ability(charID, AbilityName.CONSTITUTION);
			abilities[3] = new Ability(charID, AbilityName.INTELLIGENCE);
			abilities[4] = new Ability(charID, AbilityName.WISDOM);
			abilities[5] = new Ability(charID, AbilityName.CHARISMA);
			
			JSONObject abilityObject = new JSONObject();
	    	JSONArray abilityScores = new JSONArray();
	    	try {
	    		for(int i = 0; i < 6; i++) {
			    	JSONObject ability = new JSONObject();
			    	ability.put("name", abilities[i].getName());
			    	ability.put("score", abilities[i].getScore());
			    	ability.put("mod", abilities[i].getMod());
			    	ability.put("ref_id", abilities[i].getRefID());
			    	abilityScores.put(ability);
	    		}
	    		abilityObject.put("abilities", abilityScores);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}    	
	    	
	    	Cursor cursor = SQLiteHelperSkills.db.query(SQLiteHelperSkills.TABLE_NAME, SQLiteHelperSkills.ALL_COLUMNS, 
					SQLiteHelperSkills.COLUMN_CHAR_ID + " = " + charID, null, null, null, null);
	    	JSONObject skillsObject = new JSONObject();
	    	JSONArray skills = new JSONArray();
	    	try {
		    	if (cursor.moveToFirst()) {
					while (!cursor.isAfterLast()) { 
						// Columns: COLUMN_CHAR_ID, COLUMN_REF_S_ID, COLUMN_RANKS, COLUMN_MISC_MOD
						int skillID = cursor.getInt(1);
						int ranks = cursor.getInt(2);
						int miscMod = cursor.getInt(3);
						JSONObject skill = new JSONObject();
				    	skill.put("ref_id", skillID);
				    	skill.put("ranks", ranks);
				    	skill.put("misc_mod", miscMod);
				    	skills.put(skill);
						cursor.moveToNext();
					}
					skillsObject.put("skills", skills);
				}
	    	} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cursor.close();
			
	    	Combat combat = new Combat(charID);
	    	JSONObject combatObject = new JSONObject();
	    	try {
		    	combatObject.put("hp_total", combat.getBaseHP());
		    	combatObject.put("hp_dr", combat.getDamageReduction());
		    	combatObject.put("speed_base", combat.speedBase);
		    	combatObject.put("speed_armor", combat.speedArmor);
		    	combatObject.put("init_misc_mod", combat.getInitModifier());
		    	combatObject.put("base_attack_bonus", combat.getbAb());
		    	
		    	combatObject.put("armor_bonus", combat.getArmorModifier("armorBonus"));
		    	combatObject.put("shield_bonus", combat.getArmorModifier("armorShield"));
		    	combatObject.put("nat_armor", combat.getArmorModifier("armorNatural"));
		    	combatObject.put("deflection_mod", combat.getArmorModifier("armorDeflection"));
		    	combatObject.put("misc_mod", combat.getArmorModifier("armorMisc"));
		    	
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	Cursor cursor2 = SQLiteHelperSavingThrows.db.query(SQLiteHelperSavingThrows.TABLE_NAME, SQLiteHelperSavingThrows.ALL_COLUMNS, 
					SQLiteHelperSavingThrows.COLUMN_CHAR_ID + " = " + charID, null, null, null, null);
	    	JSONObject savingThrowsObject = new JSONObject();
	    	JSONArray savingThrows = new JSONArray();
	    	try {
		    	if (cursor2.moveToFirst()) {
					while (!cursor2.isAfterLast()) { 
						// Columns: COLUMN_CHAR_ID, COLUMN_REF_ST_ID, COLUMN_BASE_SAVE, COLUMN_MAGIC_MOD,
						// COLUMN_MISC_MOD, COLUMN_TEMP_MOD
						int throwID = cursor2.getInt(1);
						String name;
						if (throwID == 1) {
							name = "CONSTITUTION";
						} else if (throwID == 2) {
							name = "DEXTERITY";
						} else {
							name = "WISDOM";
						}
						int base_save = cursor2.getInt(2);
						
						int magic_mod = cursor2.getInt(3);
						int misc_mod = cursor2.getInt(4);
						int temp_mod = cursor2.getInt(5);
						
						JSONObject savingThrow = new JSONObject();
						savingThrow.put("ref_id", throwID);
						savingThrow.put("name", name);
						savingThrow.put("base_save", base_save);
						savingThrow.put("magic_mod", magic_mod);
						savingThrow.put("misc_mod", misc_mod);
						savingThrow.put("temp_mod", temp_mod);
				    	savingThrows.put(savingThrow);
						cursor2.moveToNext();
					}
					savingThrowsObject.put("savingThrows", savingThrows);
				}
	    	} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cursor2.close();
	    	
	        // the data to send
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("username", username));
	        postParameters.add(new BasicNameValuePair("basicInfo", basicInfo.toString()));
	        postParameters.add(new BasicNameValuePair("abilities", abilityObject.toString()));
	        postParameters.add(new BasicNameValuePair("skills", skillsObject.toString()));
	        postParameters.add(new BasicNameValuePair("combat", combatObject.toString()));
	        postParameters.add(new BasicNameValuePair("savingThrows", savingThrowsObject.toString()));
	        Log.i("JSON", savingThrowsObject.toString());
			String result = null;
	        
	        //http post
			String res;
	        try{
	        	result = CustomHttpClient.executeHttpPost(PHP_ADDRESS, postParameters);
	        	res = result.toString();       
	        } catch (Exception e) {   
	        	res = e.toString();
	        }
	        Log.i("RESULT", res);
	        return res;
	    }
	 
	    /**
	     * Parses the String result and directs to the correct Activity
	     */
	    protected void onPostExecute(String result) {
	    	dialog.dismiss();
        	Intent intent = new Intent(context, CharactersActivity.class);
        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
	    }
	}	 
}
