package uw.cse403.minion.test;

import com.jayway.android.robotium.solo.Solo;

import uw.cse403.minion.AbilityScoresActivity;
import uw.cse403.minion.CharCreateMainActivity;
import uw.cse403.minion.CharactersActivity;
import uw.cse403.minion.SaveSharedPreference;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public class AbilityScoresActivityTest extends
		ActivityInstrumentationTestCase2<CharactersActivity> {
	
	private Solo solo;
	private Activity charCreate;
	
	public AbilityScoresActivityTest() {
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
	public void testGetToAbilityScoresActivity() {
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));	
		solo.assertCurrentActivity("charCreateMainActivity", CharCreateMainActivity.class);
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_ability_scores));
		solo.assertCurrentActivity("charCreateMainActivity", AbilityScoresActivity.class);
		getActivity().finish();  

	}
	
	/**
	 * Test to see that save button returns you to correct Activity after not entering
	 * any text into the fields
	 */
	public void testDoneWithNoTextInput() {
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_ability_scores));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		solo.assertCurrentActivity("return to char create", CharCreateMainActivity.class);
		getActivity().finish(); 
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		
	}
}
