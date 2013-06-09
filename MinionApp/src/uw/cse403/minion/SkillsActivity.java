package uw.cse403.minion;

import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

/**
 * SkillsActivity is an activity that provides the user with the UI they
 * can use to enter and edit skills information about their character. It
 * handles both the population of the UI after loading the relevant information
 * from the local database as well as the task of writing any new or updated
 * information into the local database.
 * @author Kevin Dong (kevinxd3)
 */
public class SkillsActivity extends Activity {
	
	/** The unique id for a character **/
	private long charID;
	private SkillsAll allSkills;

	/*
	 * Testing Results:
	 * This one is interesting because the MethodTracer began tracing other calls to the
	 * MethodTracer due to the calls to Skills and Ability. However, once those calls are ignored, 
	 * the same conclusion arises: a lot of database operation, and a lot of drawing.
	 * Again, rest assured, all method tracing is wrapped in a boolean that is controlled by a
	 * static class, and the shipped APK has it turned off.
	 */
	/**
	 * Displays the skills page and loads in any previously entered information
	 * from the local database.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_skills);
		charID = this.getIntent().getExtras().getLong("cid");
		
		if (TraceControl.TRACE)
			Debug.startMethodTracing("SkillsActivity_database");
		
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
	 * Loads all of the skills for the current character from the database.
	 */
	public void loadData() {
		allSkills = new SkillsAll(charID);
		if (!allSkills.isNew) {
			for (int i = 1; i <= Skill.NUM_SKILLS; i++) {
				Skill skill = allSkills.getSkill(i);
				if (skill != null) {
					// these will store IDs referring to UI elements
					int arrayID = 0; // for titled Skills
					int titleFieldID = 0;
					int totalFieldID = 0;
					int abModFieldID = 0;
					int ranksFieldID = 0;
					int modsFieldID = 0;
					
					switch (i) { // i is the skillID
					case Skill.ACROBATICS_ID:
						totalFieldID = R.id.acrobatics_total;
						abModFieldID = R.id.acrobatics_ab_mod;
						ranksFieldID = R.id.acrobatics_ranks;
						modsFieldID = R.id.acrobatics_misc_mod; break;
					case Skill.APPRAISE_ID:
						totalFieldID = R.id.appraise_total;
						abModFieldID = R.id.appraise_ab_mod;
						ranksFieldID = R.id.appraise_ranks;
						modsFieldID = R.id.appraise_misc_mod; break;
					case Skill.BLUFF_ID:
						totalFieldID = R.id.bluff_total;
						abModFieldID = R.id.bluff_ab_mod;
						ranksFieldID = R.id.bluff_ranks;
						modsFieldID = R.id.bluff_misc_mod; break;
					case Skill.CLIMB_ID:
						totalFieldID = R.id.climb_total;
						abModFieldID = R.id.climb_ab_mod;
						ranksFieldID = R.id.climb_ranks;
						modsFieldID = R.id.climb_misc_mod; break;
					case Skill.CRAFT1_ID:
						arrayID = R.array.craft_array;
						titleFieldID = R.id.craft1_spinner;
						totalFieldID = R.id.craft1_total;
						abModFieldID = R.id.craft1_ab_mod;
						ranksFieldID = R.id.craft1_ranks;
						modsFieldID = R.id.craft1_misc_mod; break;
					case Skill.CRAFT2_ID:
						arrayID = R.array.craft_array;
						titleFieldID = R.id.craft2_spinner;
						totalFieldID = R.id.craft2_total;
						abModFieldID = R.id.craft2_ab_mod;
						ranksFieldID = R.id.craft2_ranks;
						modsFieldID = R.id.craft2_misc_mod; break;
					case Skill.CRAFT3_ID:
						arrayID = R.array.craft_array;
						titleFieldID = R.id.craft3_spinner;
						totalFieldID = R.id.craft3_total;
						abModFieldID = R.id.craft3_ab_mod;
						ranksFieldID = R.id.craft3_ranks;
						modsFieldID = R.id.craft3_misc_mod; break;
					case Skill.DIPLOMACY_ID:
						totalFieldID = R.id.diplomacy_total;
						abModFieldID = R.id.diplomacy_ab_mod;
						ranksFieldID = R.id.diplomacy_ranks;
						modsFieldID = R.id.diplomacy_misc_mod; break;
					case Skill.DISABLE_DEVICE_ID:
						totalFieldID = R.id.disable_device_total;
						abModFieldID = R.id.disable_device_ab_mod;
						ranksFieldID = R.id.disable_device_ranks;
						modsFieldID = R.id.disable_device_misc_mod; break;
					case Skill.DISGUISE_ID:
						totalFieldID = R.id.disguise_total;
						abModFieldID = R.id.disguise_ab_mod;
						ranksFieldID = R.id.disguise_ranks;
						modsFieldID = R.id.disguise_misc_mod; break;
					case Skill.ESCAPE_ARTIST_ID:
						totalFieldID = R.id.escape_artist_total;
						abModFieldID = R.id.escape_artist_ab_mod;
						ranksFieldID = R.id.escape_artist_ranks;
						modsFieldID = R.id.escape_artist_misc_mod; break;
					case Skill.FLY_ID:
						totalFieldID = R.id.fly_total;
						abModFieldID = R.id.fly_ab_mod;
						ranksFieldID = R.id.fly_ranks;
						modsFieldID = R.id.fly_misc_mod; break;
					case Skill.HANDLE_ANIMAL_ID:
						totalFieldID = R.id.handle_animal_total;
						abModFieldID = R.id.handle_animal_ab_mod;
						ranksFieldID = R.id.handle_animal_ranks;
						modsFieldID = R.id.handle_animal_misc_mod; break;
					case Skill.HEAL_ID:
						totalFieldID = R.id.heal_total;
						abModFieldID = R.id.heal_ab_mod;
						ranksFieldID = R.id.heal_ranks;
						modsFieldID = R.id.heal_misc_mod; break;
					case Skill.INTIMIDATE_ID:
						totalFieldID = R.id.intimidate_total;
						abModFieldID = R.id.intimidate_ab_mod;
						ranksFieldID = R.id.intimidate_ranks;
						modsFieldID = R.id.intimidate_misc_mod; break;
					case Skill.KNOWLEDGE_ARCANA_ID:
						totalFieldID = R.id.knowledge_arcana_total;
						abModFieldID = R.id.knowledge_arcana_ab_mod;
						ranksFieldID = R.id.knowledge_arcana_ranks;
						modsFieldID = R.id.knowledge_arcana_misc_mod; break;
					case Skill.KNOWLEDGE_DUNGEONEERING_ID:
						totalFieldID = R.id.knowledge_dungeoneering_total;
						abModFieldID = R.id.knowledge_dungeoneering_ab_mod;
						ranksFieldID = R.id.knowledge_dungeoneering_ranks;
						modsFieldID = R.id.knowledge_dungeoneering_misc_mod; break;
					case Skill.KNOWLEDGE_ENGINEERING_ID:
						totalFieldID = R.id.knowledge_engineering_total;
						abModFieldID = R.id.knowledge_engineering_ab_mod;
						ranksFieldID = R.id.knowledge_engineering_ranks;
						modsFieldID = R.id.knowledge_engineering_misc_mod; break;
					case Skill.KNOWLEDGE_GEOGRAPHY_ID:
						totalFieldID = R.id.knowledge_geography_total;
						abModFieldID = R.id.knowledge_geography_ab_mod;
						ranksFieldID = R.id.knowledge_geography_ranks;
						modsFieldID = R.id.knowledge_geography_misc_mod; break;
					case Skill.KNOWLEDGE_HISTORY_ID:
						totalFieldID = R.id.knowledge_history_total;
						abModFieldID = R.id.knowledge_history_ab_mod;
						ranksFieldID = R.id.knowledge_history_ranks;
						modsFieldID = R.id.knowledge_history_misc_mod; break;
					case Skill.KNOWLEDGE_LOCAL_ID:
						totalFieldID = R.id.knowledge_local_total;
						abModFieldID = R.id.knowledge_local_ab_mod;
						ranksFieldID = R.id.knowledge_local_ranks;
						modsFieldID = R.id.knowledge_local_misc_mod; break;
					case Skill.KNOWLEDGE_NATURE_ID:
						totalFieldID = R.id.knowledge_nature_total;
						abModFieldID = R.id.knowledge_nature_ab_mod;
						ranksFieldID = R.id.knowledge_nature_ranks;
						modsFieldID = R.id.knowledge_nature_misc_mod; break;
					case Skill.KNOWLEDGE_NOBILITY_ID:
						totalFieldID = R.id.knowledge_nobility_total;
						abModFieldID = R.id.knowledge_nobility_ab_mod;
						ranksFieldID = R.id.knowledge_nobility_ranks;
						modsFieldID = R.id.knowledge_nobility_misc_mod; break;
					case Skill.KNOWLEDGE_PLANES_ID:
						totalFieldID = R.id.knowledge_planes_total;
						abModFieldID = R.id.knowledge_planes_ab_mod;
						ranksFieldID = R.id.knowledge_planes_ranks;
						modsFieldID = R.id.knowledge_planes_misc_mod; break;
					case Skill.KNOWLEDGE_RELIGION_ID:
						totalFieldID = R.id.knowledge_religion_total;
						abModFieldID = R.id.knowledge_religion_ab_mod;
						ranksFieldID = R.id.knowledge_religion_ranks;
						modsFieldID = R.id.knowledge_religion_misc_mod; break;
					case Skill.LINGUISTICS_ID:
						totalFieldID = R.id.linguistics_total;
						abModFieldID = R.id.linguistics_ab_mod;
						ranksFieldID = R.id.linguistics_ranks;
						modsFieldID = R.id.linguistics_misc_mod; break;
					case Skill.PERCEPTION_ID:
						totalFieldID = R.id.perception_total;
						abModFieldID = R.id.perception_ab_mod;
						ranksFieldID = R.id.perception_ranks;
						modsFieldID = R.id.perception_misc_mod; break;
					case Skill.PERFORM1_ID:
						arrayID = R.array.perform_array;
							titleFieldID = R.id.perform1_spinner;
							totalFieldID = R.id.perform1_total;
							abModFieldID = R.id.perform1_ab_mod;
							ranksFieldID = R.id.perform1_ranks;
							modsFieldID = R.id.perform1_misc_mod; break;
					case Skill.PERFORM2_ID:
						arrayID = R.array.perform_array;
						titleFieldID = R.id.perform2_spinner;
						totalFieldID = R.id.perform2_total;
						abModFieldID = R.id.perform2_ab_mod;
						ranksFieldID = R.id.perform2_ranks;
						modsFieldID = R.id.perform2_misc_mod; break;
					case Skill.PROFESSION1_ID:
						arrayID = R.array.profession_array;
						titleFieldID = R.id.profession1_spinner;
						totalFieldID = R.id.profession1_total;
						abModFieldID = R.id.profession1_ab_mod;
						ranksFieldID = R.id.profession1_ranks;
						modsFieldID = R.id.profession1_misc_mod; break;
					case Skill.PROFESSION2_ID:
						arrayID = R.array.profession_array;
						titleFieldID = R.id.profession2_spinner;
						totalFieldID = R.id.profession2_total;
						abModFieldID = R.id.profession2_ab_mod;
						ranksFieldID = R.id.profession2_ranks;
						modsFieldID = R.id.profession2_misc_mod; break;
					case Skill.RIDE_ID:
						totalFieldID = R.id.ride_total;
						abModFieldID = R.id.ride_ab_mod;
						ranksFieldID = R.id.ride_ranks;
						modsFieldID = R.id.ride_misc_mod; break;
					case Skill.SENSE_MOTIVE_ID:
						totalFieldID = R.id.sense_motive_total;
						abModFieldID = R.id.sense_motive_ab_mod;
						ranksFieldID = R.id.sense_motive_ranks;
						modsFieldID = R.id.sense_motive_misc_mod; break;
					case Skill.SLEIGHT_OF_HAND_ID:
						totalFieldID = R.id.sleight_of_hand_total;
						abModFieldID = R.id.sleight_of_hand_ab_mod;
						ranksFieldID = R.id.sleight_of_hand_ranks;
						modsFieldID = R.id.sleight_of_hand_misc_mod; break;
					case Skill.SPELLCRAFT_ID:
						totalFieldID = R.id.spellcraft_total;
						abModFieldID = R.id.spellcraft_ab_mod;
						ranksFieldID = R.id.spellcraft_ranks;
						modsFieldID = R.id.spellcraft_misc_mod; break;
					case Skill.STEALTH_ID:
						totalFieldID = R.id.stealth_total;
						abModFieldID = R.id.stealth_ab_mod;
						ranksFieldID = R.id.stealth_ranks;
						modsFieldID = R.id.stealth_misc_mod; break;
					case Skill.SURVIVAL_ID:
						totalFieldID = R.id.survival_total;
						abModFieldID = R.id.survival_ab_mod;
						ranksFieldID = R.id.survival_ranks;
						modsFieldID = R.id.survival_misc_mod; break;
					case Skill.SWIM_ID:
						totalFieldID = R.id.swim_total;
						abModFieldID = R.id.swim_ab_mod;
						ranksFieldID = R.id.swim_ranks;
						modsFieldID = R.id.swim_misc_mod; break;
					case Skill.USE_MAGIC_DEVICE_ID:
						totalFieldID = R.id.use_magic_device_total;
						abModFieldID = R.id.use_magic_device_ab_mod;
						ranksFieldID = R.id.use_magic_device_ranks;
						modsFieldID = R.id.use_magic_device_misc_mod; break;
					}
					
					// put data in UI
					if (Skill.isTitledSkillID(i)) {
						Spinner spinner = (Spinner) findViewById(titleFieldID);
						ArrayAdapter<CharSequence> myAdap = ArrayAdapter.createFromResource(this, arrayID,
								R.layout.smaller_multiline_spinner_dropdown_item);
						spinner.setAdapter(myAdap);
						int spinnerPosition = myAdap.getPosition(skill.title);
						//set the default according to value
						spinner.setSelection(spinnerPosition);
					}
					
					TextView totalField = (TextView) findViewById(totalFieldID);
					totalField.setText("" + (skill.getTotal() + skill.abMod));
					
					TextView abModField = (TextView) findViewById(abModFieldID);
					abModField.setText("" + skill.abMod);
	
					EditText ranksEnterField = (EditText) findViewById(ranksFieldID);
					ranksEnterField.setText("" + skill.rank);
					
					EditText modEnterField = (EditText) findViewById(modsFieldID);
					modEnterField.setText("" + skill.miscMod);
				}
			}
		}
	}
	
	/**
	 * Responds to the Save button click and writes all of the currently
	 * entered skills information to the local database. Then sends the
	 * user back to the main character creation screen.
	 */
	public void skills(View view) {
		// Acrobatics
		EditText acrobaticsRanksEnter = (EditText) findViewById(R.id.acrobatics_ranks);
		EditText acrobaticsMiscEnter = (EditText) findViewById(R.id.acrobatics_misc_mod);
		String acrobaticsRanks = acrobaticsRanksEnter.getText().toString().trim();
		String acrobaticsMisc = acrobaticsMiscEnter.getText().toString().trim();

		if (acrobaticsRanks.matches("")) {
			acrobaticsRanks = "0";
		}
		
		if (acrobaticsMisc.matches("")) {
			acrobaticsMisc = "0";
		}

		if (!acrobaticsRanks.matches("")) {
			int acrobaticsRank = Integer.parseInt(acrobaticsRanks);
			Skill skill = new Skill(charID, Skill.ACROBATICS_ID);
			skill.rank = acrobaticsRank;
			if (!acrobaticsMisc.matches("")) {
				int acrobaticsMod = Integer.parseInt(acrobaticsMisc);
				skill.miscMod = acrobaticsMod;
			}
			allSkills.addSkill(skill);
		}

		// Appraise
		EditText appraiseRanksEnter = (EditText) findViewById(R.id.appraise_ranks);
		EditText appraiseMiscEnter = (EditText) findViewById(R.id.appraise_misc_mod);
		String appraiseRanks = appraiseRanksEnter.getText().toString().trim();
		String appraiseMisc = appraiseMiscEnter.getText().toString().trim();
		if (appraiseRanks.matches("")) {
			appraiseRanks = "0";
		}
		
		if (appraiseMisc.matches("")) {
			appraiseMisc = "0";
		}
		if (!appraiseRanks.matches("")) {
			int appraiseRank = Integer.parseInt(appraiseRanks);
			Skill skill = new Skill(charID, Skill.APPRAISE_ID);
			skill.rank = appraiseRank;
			if (!appraiseMisc.matches("")) {
				int appraiseMod = Integer.parseInt(appraiseMisc);
				skill.miscMod = appraiseMod;
			}
			allSkills.addSkill(skill);
		}

		// Bluff
		EditText bluffRanksEnter = (EditText) findViewById(R.id.bluff_ranks);
		EditText bluffMiscEnter = (EditText) findViewById(R.id.bluff_misc_mod);
		String bluffRanks = bluffRanksEnter.getText().toString().trim();
		String bluffMisc = bluffMiscEnter.getText().toString().trim();
		if (bluffRanks.matches("")) {
			bluffRanks = "0";
		}
		
		if (bluffMisc.matches("")) {
			bluffMisc = "0";
		}
		if (!bluffRanks.matches("")) {
			int bluffRank = Integer.parseInt(bluffRanks);
			Skill skill = new Skill(charID, Skill.BLUFF_ID);
			skill.rank = bluffRank;
			if (!bluffMisc.matches("")) {
				int bluffMod = Integer.parseInt(bluffMisc);
				skill.miscMod = bluffMod;
			}
			allSkills.addSkill(skill);
		}

		// Climb
		EditText climbRanksEnter = (EditText) findViewById(R.id.climb_ranks);
		EditText climbMiscEnter = (EditText) findViewById(R.id.climb_misc_mod);
		String climbRanks = climbRanksEnter.getText().toString().trim();
		String climbMisc = climbMiscEnter.getText().toString().trim();
		if (climbRanks.matches("")) {
			climbRanks = "0";
		}
		
		if (climbMisc.matches("")) {
			climbMisc = "0";
		}

		if (!climbRanks.matches("")) {
			int climbRank = Integer.parseInt(climbRanks);
			Skill skill = new Skill(charID, Skill.CLIMB_ID);
			skill.rank = climbRank;
			if (!climbMisc.matches("")) {
				int climbMod = Integer.parseInt(climbMisc);
				skill.miscMod = climbMod;
			}
			allSkills.addSkill(skill);
		}

		// Craft1
		Spinner craft1Spinner = (Spinner) findViewById(R.id.craft1_spinner);
		// Gives a string representation of whatever item is selected in the spinner
		String craft1Title = craft1Spinner.getSelectedItem().toString();

		EditText craft1RanksEnter = (EditText) findViewById(R.id.craft1_ranks);
		EditText craft1MiscEnter = (EditText) findViewById(R.id.craft1_misc_mod);
		String craft1Ranks = craft1RanksEnter.getText().toString().trim();
		String craft1Misc = craft1MiscEnter.getText().toString().trim();
		if (craft1Ranks.matches("")) {
			craft1Ranks = "0";
		}
		
		if (craft1Misc.matches("")) {
			craft1Misc = "0";
		}
		if (!craft1Ranks.matches("")) {
			int craft1Rank = Integer.parseInt(craft1Ranks);
			Skill skill = new Skill(charID, Skill.CRAFT1_ID);
			skill.title = craft1Title;
			skill.rank = craft1Rank;
			if (!craft1Misc.matches("")) {
				int craft1Mod = Integer.parseInt(craft1Misc);
				skill.miscMod = craft1Mod;
			}
			allSkills.addSkill(skill);
		}

		// Craft2
		Spinner craft2Spinner = (Spinner) findViewById(R.id.craft2_spinner);
		// Gives a string representation of whatever item is selected in the spinner
		String craft2Title = craft2Spinner.getSelectedItem().toString();

		EditText craft2RanksEnter = (EditText) findViewById(R.id.craft2_ranks);
		EditText craft2MiscEnter = (EditText) findViewById(R.id.craft2_misc_mod);
		String craft2Ranks = craft2RanksEnter.getText().toString().trim();
		String craft2Misc = craft2MiscEnter.getText().toString().trim();
		if (craft2Ranks.matches("")) {
			craft2Ranks = "0";
		}
		
		if (craft2Misc.matches("")) {
			craft2Misc = "0";
		}
		if (!craft2Ranks.matches("")) {
			int craft2Rank = Integer.parseInt(craft2Ranks);
			Skill skill = new Skill(charID, Skill.CRAFT2_ID);
			skill.title = craft2Title;
			skill.rank = craft2Rank;
			if (!craft2Misc.matches("")) {
				int craft2Mod = Integer.parseInt(craft2Misc);
				skill.miscMod = craft2Mod;
			}
			allSkills.addSkill(skill);
		}


		// Craft3
		Spinner craft3Spinner = (Spinner) findViewById(R.id.craft3_spinner);
		// Gives a string representation of whatever item is selected in the spinner
		String craft3Title = craft3Spinner.getSelectedItem().toString();

		EditText craft3RanksEnter = (EditText) findViewById(R.id.craft3_ranks);
		EditText craft3MiscEnter = (EditText) findViewById(R.id.craft3_misc_mod);
		String craft3Ranks = craft3RanksEnter.getText().toString().trim();
		String craft3Misc = craft3MiscEnter.getText().toString().trim();
		if (craft3Ranks.matches("")) {
			craft3Ranks = "0";
		}
		
		if (craft3Misc.matches("")) {
			craft3Misc = "0";
		}
		if (!craft3Ranks.matches("")) {
			int craft3Rank = Integer.parseInt(craft3Ranks);
			Skill skill = new Skill(charID, Skill.CRAFT3_ID); 
			skill.title = craft3Title;
			skill.rank = craft3Rank;
			if (!craft3Misc.matches("")) {
				int craft3Mod = Integer.parseInt(craft3Misc);
				skill.miscMod = craft3Mod;
			}
			allSkills.addSkill(skill);
		}


		// Diplomacy
		EditText diplomacyRanksEnter = (EditText) findViewById(R.id.diplomacy_ranks);
		EditText diplomacyMiscEnter = (EditText) findViewById(R.id.diplomacy_misc_mod);
		String diplomacyRanks = diplomacyRanksEnter.getText().toString().trim();
		String diplomacyMisc = diplomacyMiscEnter.getText().toString().trim();
		if (diplomacyRanks.matches("")) {
			diplomacyRanks = "0";
		}
		
		if (diplomacyMisc.matches("")) {
			diplomacyMisc = "0";
		}
		if (!diplomacyRanks.matches("")) {
			int diplomacyRank = Integer.parseInt(diplomacyRanks);
			Skill skill = new Skill(charID, Skill.DIPLOMACY_ID);
			skill.rank = diplomacyRank;
			if (!diplomacyMisc.matches("")) {
				int diplomacyMod = Integer.parseInt(diplomacyMisc);
				skill.miscMod = diplomacyMod;
			}
			allSkills.addSkill(skill);
		}

		// Disable Device
		EditText disableDeviceRanksEnter = (EditText) findViewById(R.id.disable_device_ranks);
		EditText disableDeviceMiscEnter = (EditText) findViewById(R.id.disable_device_misc_mod);
		String disableDeviceRanks = disableDeviceRanksEnter.getText().toString().trim();
		String disableDeviceMisc = disableDeviceMiscEnter.getText().toString().trim();
		if (disableDeviceRanks.matches("")) {
			disableDeviceRanks = "0";
		}
		
		if (disableDeviceMisc.matches("")) {
			disableDeviceMisc = "0";
		}
		if (!disableDeviceRanks.matches("")) {
			int disableDeviceRank = Integer.parseInt(disableDeviceRanks);
			Skill skill = new Skill(charID, Skill.DISABLE_DEVICE_ID);
			skill.rank = disableDeviceRank;
			if (!disableDeviceRanks.matches("")) {
				int disableDeviceMod = Integer.parseInt(disableDeviceMisc);
				skill.miscMod = disableDeviceMod;
			}
			allSkills.addSkill(skill);
		}

		// Disguise
		EditText disguiseRanksEnter = (EditText) findViewById(R.id.disguise_ranks);
		EditText disguiseMiscEnter = (EditText) findViewById(R.id.disguise_misc_mod);
		String disguiseRanks = disguiseRanksEnter.getText().toString().trim();
		String disguiseMisc = disguiseMiscEnter.getText().toString().trim();
		if (disguiseRanks.matches("")) {
			disguiseRanks = "0";
		}
		
		if (disguiseMisc.matches("")) {
			disguiseMisc = "0";
		}
		if (!disguiseRanks.matches("")) {
			int disguiseRank = Integer.parseInt(disguiseRanks);
			Skill skill = new Skill(charID, Skill.DISGUISE_ID);
			skill.rank = disguiseRank;
			if (!disguiseMisc.matches("")) {
				int disguiseMod = Integer.parseInt(disguiseMisc);
				skill.miscMod = disguiseMod;
			}
			allSkills.addSkill(skill);
		}

		// Escape Artist
		EditText escapeArtistRanksEnter = (EditText) findViewById(R.id.escape_artist_ranks);
		EditText escapeArtistMiscEnter = (EditText) findViewById(R.id.escape_artist_misc_mod);
		String escapeArtistRanks = escapeArtistRanksEnter.getText().toString().trim();
		String escapeArtistMisc = escapeArtistMiscEnter.getText().toString().trim();
		if (escapeArtistRanks.matches("")) {
			escapeArtistRanks = "0";
		}
		
		if (escapeArtistMisc.matches("")) {
			escapeArtistMisc = "0";
		}
		if (!escapeArtistRanks.matches("")) {
			int escapeArtistRank = Integer.parseInt(escapeArtistRanks);
			Skill skill = new Skill(charID, Skill.ESCAPE_ARTIST_ID);
			skill.rank = escapeArtistRank;
			if (!escapeArtistMisc.matches("")) {
				int escapeArtistMod = Integer.parseInt(escapeArtistMisc);
				skill.miscMod = escapeArtistMod;
			}
			allSkills.addSkill(skill);
		}

		// Fly
		EditText flyRanksEnter = (EditText) findViewById(R.id.fly_ranks);
		EditText flyMiscEnter = (EditText) findViewById(R.id.fly_misc_mod);
		String flyRanks = flyRanksEnter.getText().toString().trim();
		String flyMisc = flyMiscEnter.getText().toString().trim();
		if (flyRanks.matches("")) {
			flyRanks = "0";
		}
		
		if (flyMisc.matches("")) {
			flyMisc = "0";
		}
		if (!flyRanks.matches("")) {
			int flyRank = Integer.parseInt(flyRanks);
			Skill skill = new Skill(charID, Skill.FLY_ID);
			skill.rank = flyRank;
			if (!flyMisc.matches("")) {
				int flyMod = Integer.parseInt(flyMisc);
				skill.miscMod = flyMod;
			}
			allSkills.addSkill(skill);
		}

		// Handle Animal
		EditText handleAnimalRanksEnter = (EditText) findViewById(R.id.handle_animal_ranks);
		EditText handleAnimalMiscEnter = (EditText) findViewById(R.id.handle_animal_misc_mod);
		String handleAnimalRanks = handleAnimalRanksEnter.getText().toString().trim();
		String handleAnimalMisc = handleAnimalMiscEnter.getText().toString().trim();
		if (handleAnimalRanks.matches("")) {
			handleAnimalRanks = "0";
		}
		
		if (handleAnimalMisc.matches("")) {
			handleAnimalMisc = "0";
		}
		if (!handleAnimalRanks.matches("")) {
			int handleAnimalRank = Integer.parseInt(handleAnimalRanks);
			Skill skill = new Skill(charID, Skill.HANDLE_ANIMAL_ID);
			skill.rank = handleAnimalRank;
			if (!handleAnimalMisc.matches("")) {
				int handleAnimalMod = Integer.parseInt(handleAnimalMisc);
				skill.miscMod = handleAnimalMod;
			}
			allSkills.addSkill(skill);
		}

		// Heal
		EditText healRanksEnter = (EditText) findViewById(R.id.heal_ranks);
		EditText healMiscEnter = (EditText) findViewById(R.id.heal_misc_mod);
		String healRanks = healRanksEnter.getText().toString().trim();
		String healMisc = healMiscEnter.getText().toString().trim();
		if (healRanks.matches("")) {
			healRanks = "0";
		}
		
		if (healMisc.matches("")) {
			healMisc = "0";
		}
		if (!healRanks.matches("")) {
			int healRank = Integer.parseInt(healRanks);
			Skill skill = new Skill(charID, Skill.HEAL_ID);
			skill.rank = healRank;
			if (!healMisc.matches("")) {
				int healMod = Integer.parseInt(healMisc);
				skill.miscMod = healMod;
			}
			allSkills.addSkill(skill);
		}

		// Intimidate
		EditText intimidateRanksEnter = (EditText) findViewById(R.id.intimidate_ranks);
		EditText intimidateMiscEnter = (EditText) findViewById(R.id.intimidate_misc_mod);
		String intimidateRanks = intimidateRanksEnter.getText().toString().trim();
		String intimidateMisc = intimidateMiscEnter.getText().toString().trim();
		if (intimidateRanks.matches("")) {
			intimidateRanks = "0";
		}
		
		if (intimidateMisc.matches("")) {
			intimidateMisc = "0";
		}
		if (!intimidateRanks.matches("")) {
			int intimidateRank = Integer.parseInt(intimidateRanks);
			Skill skill = new Skill(charID, Skill.INTIMIDATE_ID);
			skill.rank = intimidateRank;
			if (!intimidateMisc.matches("")) {
				int intimidateMod = Integer.parseInt(intimidateMisc);
				skill.miscMod = intimidateMod;
			}
			allSkills.addSkill(skill);
		}

		// Knowledge (Arcana)
		EditText knowledgeArcanaRanksEnter = (EditText) findViewById(R.id.knowledge_arcana_ranks);
		EditText knowledgeArcanaMiscEnter = (EditText) findViewById(R.id.knowledge_arcana_misc_mod);
		String knowledgeArcanaRanks = knowledgeArcanaRanksEnter.getText().toString().trim();
		String knowledgeArcanaMisc = knowledgeArcanaMiscEnter.getText().toString().trim();
		if (knowledgeArcanaRanks.matches("")) {
			knowledgeArcanaRanks = "0";
		}
		
		if (knowledgeArcanaMisc.matches("")) {
			knowledgeArcanaMisc = "0";
		}
		if (!knowledgeArcanaRanks.matches("")) {
			int knowledgeArcanaRank = Integer.parseInt(knowledgeArcanaRanks);
			Skill skill = new Skill(charID, Skill.KNOWLEDGE_ARCANA_ID);
			skill.rank = knowledgeArcanaRank;
			if (!knowledgeArcanaMisc.matches("")) {
				int knowledgeArcanaMod = Integer.parseInt(knowledgeArcanaMisc);
				skill.miscMod = knowledgeArcanaMod;
			}
			allSkills.addSkill(skill);
		}

		// Knowledge (Dungeoneering)
		EditText knowledgeDungeoneeringRanksEnter = (EditText) findViewById(R.id.knowledge_dungeoneering_ranks);
		EditText knowledgeDungeoneeringMiscEnter = (EditText) findViewById(R.id.knowledge_dungeoneering_misc_mod);
		String knowledgeDungeoneeringRanks = knowledgeDungeoneeringRanksEnter.getText().toString().trim();
		String knowledgeDungeoneeringMisc = knowledgeDungeoneeringMiscEnter.getText().toString().trim();
		if (knowledgeDungeoneeringRanks.matches("")) {
			knowledgeDungeoneeringRanks = "0";
		}
		
		if (knowledgeDungeoneeringMisc.matches("")) {
			knowledgeDungeoneeringMisc = "0";
		}
		if (!knowledgeDungeoneeringRanks.matches("")) {
			int knowledgeDungeoneeringRank = Integer.parseInt(knowledgeDungeoneeringRanks);
			Skill skill = new Skill(charID, Skill.KNOWLEDGE_DUNGEONEERING_ID);
			skill.rank = knowledgeDungeoneeringRank;
			if (!knowledgeDungeoneeringMisc.matches("")) {
				int knowledgeDungeoneeringMod = Integer.parseInt(knowledgeDungeoneeringMisc);
				skill.miscMod = knowledgeDungeoneeringMod;
			}
			allSkills.addSkill(skill);
		}

		// Knowledge (Engineering)
		EditText knowledgeEngineeringRanksEnter = (EditText) findViewById(R.id.knowledge_engineering_ranks);
		EditText knowledgeEngineeringMiscEnter = (EditText) findViewById(R.id.knowledge_engineering_misc_mod);
		String knowledgeEngineeringRanks = knowledgeEngineeringRanksEnter.getText().toString().trim();
		String knowledgeEngineeringMisc = knowledgeEngineeringMiscEnter.getText().toString().trim();
		if (knowledgeEngineeringRanks.matches("")) {
			knowledgeEngineeringRanks = "0";
		}
		
		if (knowledgeEngineeringMisc.matches("")) {
			knowledgeEngineeringMisc = "0";
		}
		if (!knowledgeEngineeringRanks.matches("")) {
			int knowledgeEngineeringRank = Integer.parseInt(knowledgeEngineeringRanks);
			Skill skill = new Skill(charID, Skill.KNOWLEDGE_ENGINEERING_ID);
			skill.rank = knowledgeEngineeringRank;
			if (!knowledgeEngineeringMisc.matches("")) {
				int knowledgeEngineeringMod = Integer.parseInt(knowledgeEngineeringMisc);
				skill.miscMod = knowledgeEngineeringMod;
			}
			allSkills.addSkill(skill);
		}

		// Knowledge (Geography)
		EditText knowledgeGeographyRanksEnter = (EditText) findViewById(R.id.knowledge_geography_ranks);
		EditText knowledgeGeographyMiscEnter = (EditText) findViewById(R.id.knowledge_geography_misc_mod);
		String knowledgeGeographyRanks = knowledgeGeographyRanksEnter.getText().toString().trim();
		String knowledgeGeographyMisc = knowledgeGeographyMiscEnter.getText().toString().trim();
		if (knowledgeGeographyRanks.matches("")) {
			knowledgeGeographyRanks = "0";
		}
		
		if (knowledgeGeographyMisc.matches("")) {
			knowledgeGeographyMisc = "0";
		}
		if (!knowledgeGeographyRanks.matches("")) {
			int knowledgeGeographyRank = Integer.parseInt(knowledgeGeographyRanks);
			Skill skill = new Skill(charID, Skill.KNOWLEDGE_GEOGRAPHY_ID);
			skill.rank = knowledgeGeographyRank;
			if (!knowledgeGeographyMisc.matches("")) {
				int knowledgeGeographyMod = Integer.parseInt(knowledgeGeographyMisc);
				skill.miscMod = knowledgeGeographyMod;
			}
			allSkills.addSkill(skill);
		}

		// Knowledge (History)
		EditText knowledgeHistoryRanksEnter = (EditText) findViewById(R.id.knowledge_history_ranks);
		EditText knowledgeHistoryMiscEnter = (EditText) findViewById(R.id.knowledge_history_misc_mod);
		String knowledgeHistoryRanks = knowledgeHistoryRanksEnter.getText().toString().trim();
		String knowledgeHistoryMisc = knowledgeHistoryMiscEnter.getText().toString().trim();
		if (knowledgeHistoryRanks.matches("")) {
			knowledgeHistoryRanks = "0";
		}
		
		if (knowledgeHistoryMisc.matches("")) {
			knowledgeHistoryMisc = "0";
		}
		if (!knowledgeHistoryRanks.matches("")) {
			int knowledgeHistoryRank = Integer.parseInt(knowledgeHistoryRanks);
			Skill skill = new Skill(charID, Skill.KNOWLEDGE_HISTORY_ID);
			skill.rank = knowledgeHistoryRank;
			if (!knowledgeHistoryMisc.matches("")) {
				int knowledgeHistoryMod = Integer.parseInt(knowledgeHistoryMisc);
				skill.miscMod = knowledgeHistoryMod;
			}
			allSkills.addSkill(skill);
		}

		// Knowledge (Local)
		EditText knowledgeLocalRanksEnter = (EditText) findViewById(R.id.knowledge_local_ranks);
		EditText knowledgeLocalMiscEnter = (EditText) findViewById(R.id.knowledge_local_misc_mod);
		String knowledgeLocalRanks = knowledgeLocalRanksEnter.getText().toString().trim();
		String knowledgeLocalMisc = knowledgeLocalMiscEnter.getText().toString().trim();
		if (knowledgeLocalRanks.matches("")) {
			knowledgeLocalRanks = "0";
		}
		
		if (knowledgeLocalMisc.matches("")) {
			knowledgeLocalMisc = "0";
		}
		if (!knowledgeLocalRanks.matches("")) {
			int knowledgeLocalRank = Integer.parseInt(knowledgeLocalRanks);
			Skill skill = new Skill(charID, Skill.KNOWLEDGE_LOCAL_ID);
			skill.rank = knowledgeLocalRank;
			if (!knowledgeLocalMisc.matches("")) {
				int knowledgeLocalMod = Integer.parseInt(knowledgeLocalMisc);
				skill.miscMod = knowledgeLocalMod;
			}
			allSkills.addSkill(skill);
		}

		// Knowledge (Nature)
		EditText knowledgeNatureRanksEnter = (EditText) findViewById(R.id.knowledge_nature_ranks);
		EditText knowledgeNatureMiscEnter = (EditText) findViewById(R.id.knowledge_nature_misc_mod);
		String knowledgeNatureRanks = knowledgeNatureRanksEnter.getText().toString().trim();
		String knowledgeNatureMisc = knowledgeNatureMiscEnter.getText().toString().trim();
		if (knowledgeNatureRanks.matches("")) {
			knowledgeNatureRanks = "0";
		}
		
		if (knowledgeNatureMisc.matches("")) {
			knowledgeNatureMisc = "0";
		}
		if (!knowledgeNatureRanks.matches("")) {
			int knowledgeNatureRank = Integer.parseInt(knowledgeNatureRanks);
			Skill skill = new Skill(charID, Skill.KNOWLEDGE_NATURE_ID);
			skill.rank = knowledgeNatureRank;
			if (!knowledgeNatureMisc.matches("")) {
				int knowledgeNatureMod = Integer.parseInt(knowledgeNatureMisc);
				skill.miscMod = knowledgeNatureMod;
			}
			allSkills.addSkill(skill);
		}

		// Knowledge (Nobility)
		EditText knowledgeNobilityRanksEnter = (EditText) findViewById(R.id.knowledge_nobility_ranks);
		EditText knowledgeNobilityMiscEnter = (EditText) findViewById(R.id.knowledge_nobility_misc_mod);
		String knowledgeNobilityRanks = knowledgeNobilityRanksEnter.getText().toString().trim();
		String knowledgeNobilityMisc = knowledgeNobilityMiscEnter.getText().toString().trim();
		if (knowledgeNobilityRanks.matches("")) {
			knowledgeNobilityRanks = "0";
		}
		
		if (knowledgeNobilityMisc.matches("")) {
			knowledgeNobilityMisc = "0";
		}
		if (!knowledgeNobilityRanks.matches("")) {
			int knowledgeNobilityRank = Integer.parseInt(knowledgeNobilityRanks);
			Skill skill = new Skill(charID, Skill.KNOWLEDGE_NOBILITY_ID);
			skill.rank = knowledgeNobilityRank;
			if (!knowledgeNobilityMisc.matches("")) {
				int knowledgeNobilityMod = Integer.parseInt(knowledgeNobilityMisc);
				skill.miscMod = knowledgeNobilityMod;
			}
			allSkills.addSkill(skill);
		}

		// Knowledge (Planes)
		EditText knowledgePlanesRanksEnter = (EditText) findViewById(R.id.knowledge_planes_ranks);
		EditText knowledgePlanesMiscEnter = (EditText) findViewById(R.id.knowledge_planes_misc_mod);
		String knowledgePlanesRanks = knowledgePlanesRanksEnter.getText().toString().trim();
		String knowledgePlanesMisc = knowledgePlanesMiscEnter.getText().toString().trim();
		if (knowledgePlanesRanks.matches("")) {
			knowledgePlanesRanks = "0";
		}
		
		if (knowledgePlanesMisc.matches("")) {
			knowledgePlanesMisc = "0";
		}
		if (!knowledgePlanesRanks.matches("")) {
			int knowledgePlanesRank = Integer.parseInt(knowledgePlanesRanks);
			Skill skill = new Skill(charID, Skill.KNOWLEDGE_PLANES_ID);
			skill.rank = knowledgePlanesRank;
			if (!knowledgePlanesMisc.matches("")) {
				int knowledgePlanesMod = Integer.parseInt(knowledgePlanesMisc);
				skill.miscMod = knowledgePlanesMod;
			}
			allSkills.addSkill(skill);
		}

		// Knowledge (Religion)
		EditText knowledgeReligionRanksEnter = (EditText) findViewById(R.id.knowledge_religion_ranks);
		EditText knowledgeReligionMiscEnter = (EditText) findViewById(R.id.knowledge_religion_misc_mod);
		String knowledgeReligionRanks = knowledgeReligionRanksEnter.getText().toString().trim();
		String knowledgeReligionMisc = knowledgeReligionMiscEnter.getText().toString().trim();
		if (knowledgeReligionRanks.matches("")) {
			knowledgeReligionRanks = "0";
		}
		
		if (knowledgeReligionMisc.matches("")) {
			knowledgeReligionMisc = "0";
		}
		if (!knowledgeReligionRanks.matches("")) {
			int knowledgeReligionRank = Integer.parseInt(knowledgeReligionRanks);
			Skill skill = new Skill(charID, Skill.KNOWLEDGE_RELIGION_ID);
			skill.rank = knowledgeReligionRank;
			if (!knowledgeReligionMisc.matches("")) {
				int knowledgeReligionMod = Integer.parseInt(knowledgeReligionMisc);
				skill.miscMod = knowledgeReligionMod;
			}
			allSkills.addSkill(skill);
		}

		// Linguistics
		EditText linguisticsRanksEnter = (EditText) findViewById(R.id.linguistics_ranks);
		EditText linguisticsMiscEnter = (EditText) findViewById(R.id.linguistics_misc_mod);
		String linguisticsRanks = linguisticsRanksEnter.getText().toString().trim();
		String linguisticsMisc = linguisticsMiscEnter.getText().toString().trim();
		if (linguisticsRanks.matches("")) {
			linguisticsRanks = "0";
		}
		
		if (linguisticsMisc.matches("")) {
			linguisticsMisc = "0";
		}
		if (!linguisticsRanks.matches("")) {
			int linguisticsRank = Integer.parseInt(linguisticsRanks);
			Skill skill = new Skill(charID, Skill.LINGUISTICS_ID);
			skill.rank = linguisticsRank;
			if (!linguisticsMisc.matches("")) {
				int linguisticsMod = Integer.parseInt(linguisticsMisc);
				skill.miscMod = linguisticsMod;
			}
			allSkills.addSkill(skill);
		}

		// Perception
		EditText perceptionRanksEnter = (EditText) findViewById(R.id.perception_ranks);
		EditText perceptionMiscEnter = (EditText) findViewById(R.id.perception_misc_mod);
		String perceptionRanks = perceptionRanksEnter.getText().toString().trim();
		String perceptionMisc = perceptionMiscEnter.getText().toString().trim();
		if (perceptionRanks.matches("")) {
			perceptionRanks = "0";
		}
		
		if (perceptionMisc.matches("")) {
			perceptionMisc = "0";
		}
		if (!perceptionRanks.matches("")) {
			int perceptionRank = Integer.parseInt(perceptionRanks);
			Skill skill = new Skill(charID, Skill.PERCEPTION_ID);
			skill.rank = perceptionRank;
			if (!perceptionMisc.matches("")) {
				int perceptionMod = Integer.parseInt(perceptionMisc);
				skill.miscMod = perceptionMod;
			}
			allSkills.addSkill(skill);
		}

		// ... seriously I'm going to refactor this because there is a stupid amount of redundancy

		// Perform1
		Spinner perform1Spinner = (Spinner) findViewById(R.id.perform1_spinner);
		// Gives a string representation of whatever item is selected in the spinner
		String perform1Title = perform1Spinner.getSelectedItem().toString();

		EditText perform1RanksEnter = (EditText) findViewById(R.id.perform1_ranks);
		EditText perform1MiscEnter = (EditText) findViewById(R.id.perform1_misc_mod);
		String perform1Ranks = perform1RanksEnter.getText().toString().trim();
		String perform1Misc = perform1MiscEnter.getText().toString().trim();
		if (perform1Ranks.matches("")) {
			perform1Ranks = "0";
		}
		
		if (perform1Misc.matches("")) {
			perform1Misc = "0";
		}
		if (!perform1Ranks.matches("")) {
			int perform1Rank = Integer.parseInt(perform1Ranks);
			Skill skill = new Skill(charID, Skill.PERFORM1_ID);
			skill.title = perform1Title;
			skill.rank = perform1Rank;
			if (!perform1Misc.matches("")) {
				int perform1Mod = Integer.parseInt(perform1Misc);
				skill.miscMod = perform1Mod;
			}
			allSkills.addSkill(skill);
		}

		// Perform2
		Spinner perform2Spinner = (Spinner) findViewById(R.id.perform2_spinner);
		// Gives a string representation of whatever item is selected in the spinner
		String perform2Title = perform2Spinner.getSelectedItem().toString();

		EditText perform2RanksEnter = (EditText) findViewById(R.id.perform2_ranks);
		EditText perform2MiscEnter = (EditText) findViewById(R.id.perform2_misc_mod);
		String perform2Ranks = perform2RanksEnter.getText().toString().trim();
		String perform2Misc = perform2MiscEnter.getText().toString().trim();
		if (perform2Ranks.matches("")) {
			perform2Ranks = "0";
		}
		
		if (perform2Misc.matches("")) {
			perform2Misc = "0";
		}
		if (!perform2Ranks.matches("")) {
			int perform2Rank = Integer.parseInt(perform2Ranks);
			Skill skill = new Skill(charID, Skill.PERFORM2_ID);
			skill.title = perform2Title;
			skill.rank = perform2Rank;
			if (!perform2Misc.matches("")) {
				int perform2Mod = Integer.parseInt(perform2Misc);
				skill.miscMod = perform2Mod;
			}
			allSkills.addSkill(skill);
		}

		// Profession1
		Spinner profession1Spinner = (Spinner) findViewById(R.id.profession1_spinner);
		// Gives a string representation of whatever item is selected in the spinner
		String profession1Title = profession1Spinner.getSelectedItem().toString();
		EditText profession1RanksEnter = (EditText) findViewById(R.id.profession1_ranks);
		EditText profession1MiscEnter = (EditText) findViewById(R.id.profession1_misc_mod);
		String profession1Ranks = profession1RanksEnter.getText().toString().trim();
		String profession1Misc = profession1MiscEnter.getText().toString().trim();
		if (profession1Ranks.matches("")) {
			profession1Ranks = "0";
		}
		
		if (profession1Misc.matches("")) {
			profession1Misc = "0";
		}
		if (!profession1Ranks.matches("")) {
			int profession1Rank = Integer.parseInt(profession1Ranks);
			Skill skill = new Skill(charID, Skill.PROFESSION1_ID); 
			skill.title = profession1Title;
			skill.rank = profession1Rank;
			if (!profession1Misc.matches("")) {
				int profession1Mod = Integer.parseInt(profession1Misc);
				skill.miscMod = profession1Mod;
			}
			allSkills.addSkill(skill);
		}

		// Profession2
		Spinner profession2Spinner = (Spinner) findViewById(R.id.profession2_spinner);
		// Gives a string representation of whatever item is selected in the spinner
		String profession2Title = profession2Spinner.getSelectedItem().toString();
		EditText profession2RanksEnter = (EditText) findViewById(R.id.profession2_ranks);
		EditText profession2MiscEnter = (EditText) findViewById(R.id.profession2_misc_mod);
		String profession2Ranks = profession2RanksEnter.getText().toString().trim();
		String profession2Misc = profession2MiscEnter.getText().toString().trim();
		if (profession2Ranks.matches("")) {
			profession2Ranks = "0";
		}
		
		if (profession2Misc.matches("")) {
			profession2Misc = "0";
		}
		if (!profession2Ranks.matches("")) {
			int profession2Rank = Integer.parseInt(profession2Ranks);
			Skill skill = new Skill(charID, Skill.PROFESSION2_ID);
			skill.title = profession2Title;
			skill.rank = profession2Rank;
			if (!profession2Misc.matches("")) {
				int profession2Mod = Integer.parseInt(profession2Misc);
				skill.miscMod = profession2Mod;
			}
			allSkills.addSkill(skill);
		}

		// Ride
		EditText rideRanksEnter = (EditText) findViewById(R.id.ride_ranks);
		EditText rideMiscEnter = (EditText) findViewById(R.id.ride_misc_mod);
		String rideRanks = rideRanksEnter.getText().toString().trim();
		String rideMisc = rideMiscEnter.getText().toString().trim();
		if (rideRanks.matches("")) {
			rideRanks = "0";
		}
		
		if (rideMisc.matches("")) {
			rideMisc = "0";
		}
		if (!rideRanks.matches("")) {
			int rideRank = Integer.parseInt(rideRanks);
			Skill skill = new Skill(charID, Skill.RIDE_ID);
			skill.rank = rideRank;
			if (!rideMisc.matches("")) {
				int rideMod = Integer.parseInt(rideMisc);
				skill.miscMod = rideMod;
			}
			allSkills.addSkill(skill);
		}

		// Sense Motive
		EditText senseMotiveRanksEnter = (EditText) findViewById(R.id.sense_motive_ranks);
		EditText senseMotiveMiscEnter = (EditText) findViewById(R.id.sense_motive_misc_mod);
		String senseMotiveRanks = senseMotiveRanksEnter.getText().toString().trim();
		String senseMotiveMisc = senseMotiveMiscEnter.getText().toString().trim();
		if (senseMotiveRanks.matches("")) {
			senseMotiveRanks = "0";
		}
		
		if (senseMotiveMisc.matches("")) {
			senseMotiveMisc = "0";
		}
		if (!senseMotiveRanks.matches("")) {
			int senseMotiveRank = Integer.parseInt(senseMotiveRanks);
			Skill skill = new Skill(charID, Skill.SENSE_MOTIVE_ID);
			skill.rank = senseMotiveRank;
			if (!senseMotiveMisc.matches("")) {
				int senseMotiveMod = Integer.parseInt(senseMotiveMisc);
				skill.miscMod = senseMotiveMod;
			}
			allSkills.addSkill(skill);
		}

		// Sleight of Hand
		EditText sleightOfHandRanksEnter = (EditText) findViewById(R.id.sleight_of_hand_ranks);
		EditText sleightOfHandMiscEnter = (EditText) findViewById(R.id.sleight_of_hand_misc_mod);
		String sleightOfHandRanks = sleightOfHandRanksEnter.getText().toString().trim();
		String sleightOfHandMisc = sleightOfHandMiscEnter.getText().toString().trim();
		if (sleightOfHandRanks.matches("")) {
			sleightOfHandRanks = "0";
		}
		
		if (sleightOfHandMisc.matches("")) {
			sleightOfHandMisc = "0";
		}
		if (!sleightOfHandRanks.matches("")) {
			int sleightOfHandRank = Integer.parseInt(sleightOfHandRanks);
			Skill skill = new Skill(charID, Skill.SLEIGHT_OF_HAND_ID);
			skill.rank = sleightOfHandRank;
			if (!sleightOfHandMisc.matches("")) {
				int sleightOfHandMod = Integer.parseInt(sleightOfHandMisc);
				skill.miscMod = sleightOfHandMod;
			}
			allSkills.addSkill(skill);
		}

		// Spellcraft
		EditText spellcraftRanksEnter = (EditText) findViewById(R.id.spellcraft_ranks);
		EditText spellcraftMiscEnter = (EditText) findViewById(R.id.spellcraft_misc_mod);
		String spellcraftRanks = spellcraftRanksEnter.getText().toString().trim();
		String spellcraftMisc = spellcraftMiscEnter.getText().toString().trim();
		if (spellcraftRanks.matches("")) {
			spellcraftRanks = "0";
		}
		
		if (spellcraftMisc.matches("")) {
			spellcraftMisc = "0";
		}
		if (!spellcraftRanks.matches("")) {
			int spellcraftRank = Integer.parseInt(spellcraftRanks);
			Skill skill = new Skill(charID, Skill.SPELLCRAFT_ID);
			skill.rank = spellcraftRank;
			if (!spellcraftMisc.matches("")) {
				int spellcraftMod = Integer.parseInt(spellcraftMisc);
				skill.miscMod = spellcraftMod;
			}
			allSkills.addSkill(skill);
		}

		// Stealth
		EditText stealthRanksEnter = (EditText) findViewById(R.id.stealth_ranks);
		EditText stealthMiscEnter = (EditText) findViewById(R.id.stealth_misc_mod);
		String stealthRanks = stealthRanksEnter.getText().toString().trim();
		String stealthMisc = stealthMiscEnter.getText().toString().trim();
		if (stealthRanks.matches("")) {
			stealthRanks = "0";
		}
		
		if (stealthMisc.matches("")) {
			stealthMisc = "0";
		}
		if (!stealthRanks.matches("")) {
			int stealthRank = Integer.parseInt(stealthRanks);
			Skill skill = new Skill(charID, Skill.STEALTH_ID);
			skill.rank = stealthRank;
			if (!stealthMisc.matches("")) {
				int stealthMod = Integer.parseInt(stealthMisc);
				skill.miscMod = stealthMod;
			}
			allSkills.addSkill(skill);
		}

		// Survival
		EditText survivalRanksEnter = (EditText) findViewById(R.id.survival_ranks);
		EditText survivalMiscEnter = (EditText) findViewById(R.id.survival_misc_mod);
		String survivalRanks = survivalRanksEnter.getText().toString().trim();
		String survivalMisc = survivalMiscEnter.getText().toString().trim();
		if (survivalRanks.matches("")) {
			survivalRanks = "0";
		}
		
		if (survivalMisc.matches("")) {
			survivalMisc = "0";
		}
		if (!survivalRanks.matches("")) {
			int survivalRank = Integer.parseInt(survivalRanks);
			Skill skill = new Skill(charID, Skill.SURVIVAL_ID);
			skill.rank = survivalRank;
			if (!survivalMisc.matches("")) {
				int survivalMod = Integer.parseInt(survivalMisc);
				skill.miscMod = survivalMod;
			}
			allSkills.addSkill(skill);
		}

		// Swim
		EditText swimRanksEnter = (EditText) findViewById(R.id.swim_ranks);
		EditText swimMiscEnter = (EditText) findViewById(R.id.swim_misc_mod);
		String swimRanks = swimRanksEnter.getText().toString().trim();
		String swimMisc = swimMiscEnter.getText().toString().trim();
		if (swimRanks.matches("")) {
			swimRanks = "0";
		}
		
		if (swimMisc.matches("")) {
			swimMisc = "0";
		}
		if (!swimRanks.matches("")) {
			int swimRank = Integer.parseInt(swimRanks);
			Skill skill = new Skill(charID, Skill.SWIM_ID);
			skill.rank = swimRank;
			if (!swimMisc.matches("")) {
				int swimMod = Integer.parseInt(swimMisc);
				skill.miscMod = swimMod;
			}
			allSkills.addSkill(skill);
		}

		// Use Magic Device
		EditText useMagicDeviceRanksEnter = (EditText) findViewById(R.id.use_magic_device_ranks);
		EditText useMagicDeviceMiscEnter = (EditText) findViewById(R.id.use_magic_device_misc_mod);
		String useMagicDeviceRanks = useMagicDeviceRanksEnter.getText().toString().trim();
		String useMagicDeviceMisc = useMagicDeviceMiscEnter.getText().toString().trim();
		if (useMagicDeviceRanks.matches("")) {
			useMagicDeviceRanks = "0";
		}
		
		if (useMagicDeviceMisc.matches("")) {
			useMagicDeviceMisc = "0";
		}
		if (!useMagicDeviceRanks.matches("")) {
			int useMagicDeviceRank = Integer.parseInt(useMagicDeviceRanks);
			Skill skill = new Skill(charID, Skill.USE_MAGIC_DEVICE_ID);
			skill.rank = useMagicDeviceRank;
			if (!useMagicDeviceMisc.matches("")) {
				int useMagicDeviceMod = Integer.parseInt(useMagicDeviceMisc);
				skill.miscMod = useMagicDeviceMod;
			}
			allSkills.addSkill(skill);
		}

		// write all data to DB
		allSkills.writeToDB();

		// return to character creation main screen
		Intent intent = new Intent(this, CharCreateMainActivity.class);
		intent.putExtra("cid", charID);
		startActivity(intent);
	}	
}
