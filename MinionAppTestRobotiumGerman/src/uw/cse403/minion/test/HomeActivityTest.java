package uw.cse403.minion.test;
import com.jayway.android.robotium.solo.Solo;

import uw.cse403.minion.CharactersActivity;
import uw.cse403.minion.GroupsActivity;
import uw.cse403.minion.HomeActivity;
import uw.cse403.minion.SaveSharedPreference;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Whitebox test of HomeActivity that verifies that Home buttons go to
 * the correct activities
 * 
 * @author Loki White (lokiw)
 *
 */
public class HomeActivityTest extends
		ActivityInstrumentationTestCase2<HomeActivity> {

	private Solo solo;
	private Activity charCreate;
	
	public HomeActivityTest() {
		super(HomeActivity.class);
	}
	
	/**
	 * setup() instantiates Solo and stores the CharactersActivity.
	 */
	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		charCreate = getActivity();
		SaveSharedPreference.clearPreferences(charCreate);
		
	}
	
	/**
	 * Tests the Manage Characters button to make sure it brings the user
	 * to the Characters Activity
	 */
	public void testManageCharacers() {
		SaveSharedPreference.clearPreferences(charCreate);

		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_manage_characters));	
		solo.assertCurrentActivity("CharactersActivity", CharactersActivity.class);
	}
	
	/**
	 * Tests the Manage Groups button to make sure it brings the user 
	 * to the Groups Activity
	 */
	public void testManageGroups() {
		SaveSharedPreference.clearPreferences(charCreate);

		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_manage_groups));	
		solo.assertCurrentActivity("CharactersActivity", GroupsActivity.class);
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		
	}
}
