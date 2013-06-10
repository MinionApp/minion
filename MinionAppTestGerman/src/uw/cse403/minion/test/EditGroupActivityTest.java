package uw.cse403.minion.test;

import java.util.ArrayList;
import uw.cse403.minion.EditGroupActivity;
import uw.cse403.minion.SaveSharedPreference;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * White box texts for EditGroupActivity, testing that the basic
 * UI elements are displayed correctly. 
 * @author Thomas Eberlein
 *
 */
public class EditGroupActivityTest extends
	ActivityInstrumentationTestCase2<EditGroupActivity>  {
	
	public EditGroupActivityTest(){
		super(EditGroupActivity.class);
		
	}
	
	/**
	 * setup() instantiates Solo and stores the LoginActivity.
	 * loginActivity is later used to clear out the "Remember me" settings
	 * from the application preferences to ensure clean state for each test.
	 */
	@Override
	protected void setUp() throws Exception {
		SaveSharedPreference.setPersistentUserName(this.getInstrumentation().getContext(), "test");
		ArrayList<String> testPlayers = new ArrayList<String>();
		testPlayers.add("testPlayer");
		Intent i = new Intent();
		i.putExtra("groupname", "testValue");
		i.putExtra("gm", "testGM");
		i.putExtra("players", testPlayers);
		setActivityIntent(i);	
	}
	
	/**
	 * Tests that the game master label appears with correct text. 
	 */
	public void testGameMasterAppears(){
		LinearLayout gmInfo = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.game_master_info);
		TextView title = (TextView) gmInfo.getChildAt(0);
		assertEquals("Spielmeister:", title.getText());
	}
	
	/**
	 * Tests that the players label appears with the correct text.
	 */
	public void testPlayersTitleAppears(){
		TextView players = (TextView) getActivity().findViewById(uw.cse403.minion.R.id.players_title);
		assertEquals("Spieler", players.getText());
	}
	
	/**
	 * Tests that the invite button appears, and has the correct text. 
	 */
	public void testInviteButtonAppears(){
		LinearLayout buttons = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.editing_buttons);
		Button inviteButton = (Button) buttons.getChildAt(0);
		assertEquals("Benutzer einladen", inviteButton.getText());
	}
	
	/**
	 * Tests that the finish button appears, and has the correct text. 
	 */
	public void testDoneButtonAppears(){
		LinearLayout buttons = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.editing_buttons);
		Button finishButton = (Button) buttons.getChildAt(1);
		assertEquals("Fertig", finishButton.getText());
	}
}
