package uw.cse403.minion.test;

import com.jayway.android.robotium.solo.Solo;

import uw.cse403.minion.AbilityScoresActivity;
import uw.cse403.minion.BasicInfoActivity;
import uw.cse403.minion.CharCreateMainActivity;
import uw.cse403.minion.CharactersActivity;
import uw.cse403.minion.SaveSharedPreference;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Whitebox test for AbilityScoresActivity to make sure text fields and buttons
 * are functional.
 * 
 * @author loki
 *
 */
public class AbilityScoresActivityTest extends
		ActivityInstrumentationTestCase2<CharactersActivity> {
	
	private Solo solo;
	private Activity charCreate;
	
	public AbilityScoresActivityTest() {
		super(CharactersActivity.class);

	}
	
	@Override
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
		charCreate = getActivity();
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));	
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_basic_info));
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
	}
	
	@Override
	protected void tearDown() {
		solo.finishOpenedActivities();
		getActivity().finish();
	}
	
	/**
	 * Test that setup to get to CharCreateMainActivity, BasicInfoActivity, and then to
	 * AbilityScoresActivity at beginning of each test works.
	 */
	public void testGetToAndReturnFromAbilityScoresActivity() {
		
		solo.assertCurrentActivity("CharCreateMainActivity", CharCreateMainActivity.class);
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_ability_scores));
		solo.assertCurrentActivity("AbilityScoresActivity", AbilityScoresActivity.class);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		solo.assertCurrentActivity("CharCreateMainActivity", CharCreateMainActivity.class);
	}
}
