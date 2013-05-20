package uw.cse403.minion.test;

import com.jayway.android.robotium.solo.Solo;

import uw.cse403.minion.AbilityScoresActivity;
import uw.cse403.minion.BasicInfoActivity;
import uw.cse403.minion.CharCreateMainActivity;
import uw.cse403.minion.CharactersActivity;
import uw.cse403.minion.CombatActivity;
import uw.cse403.minion.SaveSharedPreference;
import uw.cse403.minion.SavingThrowsActivity;
import uw.cse403.minion.SkillsActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Testing of CharCreateMainActivity, but must start in CharacterActivity for 
 * database objects to initialize properly. Thus we move to the CharCreateMainActivity
 * at the beginning of each method in order to properly initialize.
 * 
 * @author loki
 *
 */
public class CharCreateMainActivityTest extends
		ActivityInstrumentationTestCase2<CharactersActivity> {
	
	private Solo solo;
	private Activity charCreate;
	
	//If extras are passed, make an intent here, in this case
	// the database isn't an extra so we have to go one activity back
	public CharCreateMainActivityTest() {
		super(CharactersActivity.class);

	}
	
	/**
	 * setup() instantiates Solo and stores the CharCreateMainActivity.
	 */
	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		charCreate = getActivity();
		SaveSharedPreference.clearPreferences(charCreate);
		
	}
	
	/**
	 * Test that setup to get to charCreateMainActivity at beginning of each test works
	 */
	public void testGetToCharCreateMainActivity() {
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));	
		solo.assertCurrentActivity("charCreateMainActivity", CharCreateMainActivity.class);
		getActivity().finish(); 

	}
	
	/**
	 * Test to see that basicInfo button goes to correct activity
	 */
	public void testGotoBasicInfo() {
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_basic_info));
		solo.assertCurrentActivity("basic info", BasicInfoActivity.class);
		getActivity().finish(); 
	}
	
	/**
	 * Test to see that ability scores button goes to correct activity
	 */
	public void testGotoAbilityScores() {
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_ability_scores));
		solo.assertCurrentActivity("ability scores", AbilityScoresActivity.class);
		getActivity().finish(); 
	}
	
	/**
	 * Test to see that skills button goes to correct activity
	 */
	public void testGotoSkills() {
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_skills));
		solo.assertCurrentActivity("skills", SkillsActivity.class);
		getActivity().finish(); 
	}
	
	/**
	 * Test to see that combat button goes to correct activity
	 */
	public void testGotoCombat() {
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_combat));
		solo.assertCurrentActivity("combat", CombatActivity.class);
		getActivity().finish(); 
	}
	
	/**
	 * Test to see that saving throws button goes to correct activity
	 */
	public void testGotoSavingThrows() {
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_saving_throws));
		solo.assertCurrentActivity("saving throws", SavingThrowsActivity.class);
		getActivity().finish(); 
	}
	
	/**
	 * Test to see that done button goes to correct activity
	 */
	public void testDone() {
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.done));
		solo.assertCurrentActivity("done", CharactersActivity.class);
		getActivity().finish(); 
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		
	}

}
