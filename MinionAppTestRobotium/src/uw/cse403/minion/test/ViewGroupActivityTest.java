package uw.cse403.minion.test;

import junit.framework.Assert;
import uw.cse403.minion.CharCreateMainActivity;
import uw.cse403.minion.SaveSharedPreference;
import uw.cse403.minion.ViewGroupActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.jayway.android.robotium.solo.Solo;

/**
 * 
 * @author Thomas Eberlein (uwte)
 * 
 * ViewGroupActivityTest tests the ViewGroupActivity by using Robotium and checking
 * that proper values are displayed on the screen. NOTE: All tests are done assuming
 * that the user is test, and that the group clicked has the correct set of users
 * (user_1, user_2, user_3, user_4, user_5) and the correct set of characters corresponding
 * to those users (user_1_character, user_2_character, user_3_character, user_4_character,
 * user_5_character). This is a black box unit test. This is the testing portion of 
 * our test-driven development for the ViewGroupActivity component of our application. It
 * tests the currently stubbed out version of ViewGroupActivity.java.
 * 
 */
public class ViewGroupActivityTest extends 
	ActivityInstrumentationTestCase2<ViewGroupActivity> {
	
	private Solo solo;
	private static final String VALID_USERNAME = "test";
	
	public ViewGroupActivityTest() {
		super(ViewGroupActivity.class);
	}
	
	/**
	 * setup() instantiates Solo to be on the ViewGroupActivity.
	 */
	@Override
	public void setUp() throws Exception{
		solo = new Solo(getInstrumentation(), getActivity());
		SaveSharedPreference.setUserName(getActivity(), VALID_USERNAME);
	}
	
	/**
	 * testListOfUsersDisplayed() tests whether the correct number of members
	 * in the group are displayed on the screen.
	 */
	public void testListOfUsersDisplayed(){
		ListView chars = solo.getView(ListView.class, 0);
		Assert.assertEquals(5, chars.getAdapter().getCount());
		solo.finishOpenedActivities();
	}
	
	/**
	 * testDisplayUsersInGroup() tests whether the correct users are displayed in
	 * the group, based on username.
	 */
	public void testDisplaysUsersInGroup(){
		Assert.assertTrue(solo.searchText("user_1"));
		Assert.assertTrue(solo.searchText("user_2"));
		Assert.assertTrue(solo.searchText("user_3"));
		Assert.assertTrue(solo.searchText("user_4"));
		Assert.assertTrue(solo.searchText("user_5"));
		solo.finishOpenedActivities();
	}
	
	/**
	 * testDisplayUsersCharactersNames() tests whether the correct character name
	 * for each user is displayed.  	
	 */
	public void testDisplaysUsersCharactersNames(){
		Assert.assertTrue(solo.searchText("user_1_character"));
		Assert.assertTrue(solo.searchText("user_2_character"));
		Assert.assertTrue(solo.searchText("user_3_character"));
		Assert.assertTrue(solo.searchText("user_4_character"));
		Assert.assertTrue(solo.searchText("user_5_character"));		
		solo.finishOpenedActivities();
	}
	
	/**
	 * testGoToCharactersStats() tests that when a user in the group is clicked,
	 * the client is taken to the CharCreateMainActivity to be able to view that
	 * user's character's stats. 
	 */
	public void testGoToCharacterStats(){
		solo.clickOnText("user_1");
		solo.assertCurrentActivity("Character Info", CharCreateMainActivity.class);
		solo.finishOpenedActivities();
	}
}
