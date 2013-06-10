package uw.cse403.minion;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class LoadCharactersActivity extends Activity {

	/** Class constants for string representations **/
	private static final String PHP_ADDRESS = "http://homes.cs.washington.edu/~elefse/downloadBasicInfo.php";
	private static final String PHP_ADDRESS2 = "http://homes.cs.washington.edu/~elefse/downloadAbilities.php";
	private static final String PHP_ADDRESS3 = "http://homes.cs.washington.edu/~elefse/downloadSkills.php";
	private static final String PHP_ADDRESS4 = "http://homes.cs.washington.edu/~elefse/downloadCombat.php";
	private static final String PHP_ADDRESS5 = "http://homes.cs.washington.edu/~elefse/downloadSavingThrows.php";

	/** The current user's username **/
	private String username;
	
	/** The local database source **/
	private CharacterDataSource datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_characters);
		username = SaveSharedPreference.getPersistentUserName(LoadCharactersActivity.this);
		// removes all locally stored characters
		datasource = new CharacterDataSource(this);
		System.out.println("OPENING DATASOURCE");
		datasource.open();
		CharacterDataSource.deleteAllCharacters();
		DownloadCharacterTask task = new DownloadCharacterTask(this);
		task.execute();
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
		getMenuInflater().inflate(R.menu.skills, menu);
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
	 * DownloadCharacterTask is a private inner class that allows requests to be made to the remote
	 * MySQL database parallel to the main UI thread. It downloads all characters for the current user
	 * from the remote database.
	 */
	private class DownloadCharacterTask extends AsyncTask<String, Void, String> {
		private Context context;
		private String basicInfoJSON;
		private String abilitiesJSON;
		private String skillsJSON;
		private String combatJSON;
		private String savingThrowsJSON;

		/**
		 * Constructs a new UploadCharacterTask object.
		 * @param context The current Activity's context
		 */
		private DownloadCharacterTask (Context context) {
			this.context = context;
		}

		/**
		 * Makes the HTTP request and returns the result as a String.
		 */
		@Override
		protected String doInBackground(String... args) {
			// the data to send
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("un", username));
			
			basicInfoJSON = null;
			abilitiesJSON = null;
			skillsJSON = null;
			combatJSON = null;
			savingThrowsJSON = null;

			//http post for basic info
			String basicInfo;
			try{
				basicInfoJSON = CustomHttpClient.executeHttpPost(PHP_ADDRESS, postParameters);
				basicInfo = basicInfoJSON.toString();       
			} catch (Exception e) {   
				basicInfo = e.toString();
			}
			Log.i("basicInfo", basicInfoJSON);
			
			//http post for ability info
			String abilitiesInfo;
			try{
				abilitiesJSON = CustomHttpClient.executeHttpPost(PHP_ADDRESS2, postParameters);
				abilitiesInfo = abilitiesJSON.toString();       
			} catch (Exception e) {   
				abilitiesInfo = e.toString();
			}
			Log.i("abilitiesInfo", abilitiesJSON);
			
			//http post for skills info
			String skillsInfo;
			try{
				skillsJSON = CustomHttpClient.executeHttpPost(PHP_ADDRESS3, postParameters);
				skillsInfo = skillsJSON.toString();       
			} catch (Exception e) {   
				skillsInfo = e.toString();
			}
			Log.i("skillsInfo", skillsJSON);
			
			//http post for combat info
			String combatInfo;
			try{
				combatJSON = CustomHttpClient.executeHttpPost(PHP_ADDRESS4, postParameters);
				combatInfo = combatJSON.toString();       
			} catch (Exception e) {   
				combatInfo = e.toString();
			}
			Log.i("combatInfo", combatJSON);
			
			//http post for saving throws info
			String savingThrowsInfo;
			try{
				savingThrowsJSON = CustomHttpClient.executeHttpPost(PHP_ADDRESS5, postParameters);
				savingThrowsInfo = savingThrowsJSON.toString();       
			} catch (Exception e) {   
				savingThrowsInfo = e.toString();
			}
			Log.i("savingThrowsInfo", savingThrowsJSON);
			
			return "";
		}

		/**
		 * Parses the String result and directs to the correct Activity
		 */
		@Override
		protected void onPostExecute(String result) {
			try {
				JSONObject basicInfoObject  = new JSONObject(basicInfoJSON);
				JSONArray basicInfo = basicInfoObject.getJSONArray("basicInfo");
				
				JSONObject abilitiesObject  = new JSONObject(abilitiesJSON);
				JSONArray abilities = abilitiesObject.getJSONArray("abilitiesInfo");
				
				JSONObject skillsObject  = new JSONObject(skillsJSON);
				JSONArray skills = skillsObject.getJSONArray("skillsInfo");
				
				JSONObject combatObject  = new JSONObject(combatJSON);
				JSONArray combat = combatObject.getJSONArray("combatInfo");
				
				JSONObject savingThrowsObject  = new JSONObject(savingThrowsJSON);
				JSONArray savingThrows = savingThrowsObject.getJSONArray("savingThrowsInfo");
				
				// looping through groups
				for(int i = 0; i < basicInfo.length(); i++){
					JSONObject basicInfoEntry = basicInfo.getJSONObject(i);
					JSONObject abilitiesEntry = abilities.getJSONObject(i);
					JSONObject skillsEntry = skills.getJSONObject(i);
					JSONObject combatEntry = combat.getJSONObject(i);
					JSONObject savingThrowsEntry = savingThrows.getJSONObject(i);
	
					// Storing basic info into local db
					CharacterDescription baseInfo = new CharacterDescription(i);
					// Storing each json item in variable
					baseInfo.name = basicInfoEntry.getString("name");
					baseInfo.alignment = basicInfoEntry.getString("alignment");
					baseInfo.level = basicInfoEntry.getInt("level");
					baseInfo.deity = basicInfoEntry.getString("diety");
					baseInfo.homeLand = basicInfoEntry.getString("homeland");
					baseInfo.race = basicInfoEntry.getString("race");
					baseInfo.size = basicInfoEntry.getString("size");
					baseInfo.gender = basicInfoEntry.getString("gender");
					baseInfo.age = basicInfoEntry.getInt("age");
					baseInfo.height = basicInfoEntry.getInt("height");
					baseInfo.weight = basicInfoEntry.getInt("weight");
					baseInfo.hair = basicInfoEntry.getString("hair");
					baseInfo.eyes = basicInfoEntry.getString("eyes");
					baseInfo.writeToDB();
					
					JSONArray abilitiesJSONArray = abilitiesEntry.getJSONArray("abilities");
					for(int j = 0; j < abilitiesJSONArray.length(); j++) {
						JSONObject singleAbility = abilitiesJSONArray.getJSONObject(j);
						String abilityName = singleAbility.getString("name");
						int abilityID = -1;
						if(abilityName.equals("STRENGTH")) {
							abilityID = Ability.STRENGTH_ID;
						} else if (abilityName.equals("DEXTERITY")) {
							abilityID = Ability.DEXTERITY_ID;
						} else if (abilityName.equals("CONSTITUTION")) {
							abilityID = Ability.CONSTITUTION_ID;
						} else if (abilityName.equals("INTELLIGENCE")) {
							abilityID = Ability.INTELLIGENCE_ID;
						} else if (abilityName.equals("WISDOM")) {
							abilityID = Ability.WISDOM_ID;
						} else {
							abilityID = Ability.CHARISMA_ID;
						}
						Ability ability = new Ability(i, abilityID);
						ability.baseScore = singleAbility.getInt("score");
						ability.writeToDB();
					}
					
					JSONArray skillsJSONArray = skillsEntry.getJSONArray("skills");
					Log.i("SKILLSJSONARRAY", skillsJSONArray.toString());
					for(int k = 0; k < skillsJSONArray.length(); k++) {
						JSONObject singleSkill= skillsJSONArray.getJSONObject(k);
						int skillID = singleSkill.getInt("ref_id");
						Skill skill = new Skill(i, skillID);
						
						if(Skill.isTitledSkillID(skillID)) {
							skill.title = singleSkill.getString("title");
						}
						
						skill.rank = singleSkill.getInt("ranks");
						skill.miscMod =  singleSkill.getInt("misc_mod");
						skill.writeToDB();
					}
					
					// Storing combat info into local db
					Combat combatInfo = new Combat(i);
					// Storing each json item in variable
					combatInfo.baseHP = combatEntry.getInt("hp_total");
					combatInfo.damageReduction = combatEntry.getInt("hp_dr");
					combatInfo.setSpeed(combatEntry.getInt("speed_base"), combatEntry.getInt("speed_armor"));
					combatInfo.initModifier = combatEntry.getInt("init_misc_mod");
					combatInfo.bAb = combatEntry.getInt("base_attack_bonus");
					combatInfo.addArmorModifier("armorBonus", combatEntry.getInt("armor_bonus"));
					combatInfo.addArmorModifier("armorShield", combatEntry.getInt("shield_bonus"));
					combatInfo.addArmorModifier("armorNatural", combatEntry.getInt("nat_armor"));
					combatInfo.addArmorModifier("armorDeflection", combatEntry.getInt("deflection_mod"));
					combatInfo.addArmorModifier("armorMisc", combatEntry.getInt("misc_mod"));
					combatInfo.writeToDB();
					
					JSONArray savingThrowsJSONArray = savingThrowsEntry.getJSONArray("savingThrows");
					for(int l = 0; l < savingThrowsJSONArray.length(); l++) {
						JSONObject singleSavingThrow = savingThrowsJSONArray.getJSONObject(l);
						int throwID = singleSavingThrow.getInt("ref_id");
						SavingThrow savingThrow = new SavingThrow(i, throwID);
						savingThrow.baseSave = singleSavingThrow.getInt("base_save");
						savingThrow.addModifier("magic", singleSavingThrow.getInt("magic_mod"));
						savingThrow.addModifier("misc", singleSavingThrow.getInt("misc_mod"));
						savingThrow.addModifier("temp", singleSavingThrow.getInt("temp_mod"));
						savingThrow.writeToDB();
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Intent intent = new Intent(context, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}
}