package uw.cse403.minion.test;

import uw.cse403.minion.EditGroupActivity;
import uw.cse403.minion.GroupCreateActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
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
	 * Tests that the game master label appears with correct text. 
	 */
	public void testGameMasterAppears(){
		LinearLayout gmInfo = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.game_master_info);
		TextView title = (TextView) gmInfo.getChildAt(0);
		assertEquals("Game Master:", title.getText());
	}
	
	/**
	 * Tests that the players label appears with the correct text.
	 */
	public void testPlayersTitleAppears(){
		TextView players = (TextView) getActivity().findViewById(uw.cse403.minion.R.id.players_title);
		assertEquals("Players", players.getText());
	}
	
	/**
	 * Tests that the invite button appears, and has the correct text. 
	 */
	public void testInviteButtonAppears(){
		LinearLayout buttons = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.editing_buttons);
		Button inviteButton = (Button) buttons.getChildAt(0);
		assertEquals("Invite User", inviteButton.getText());
	}
	
	/**
	 * Tests that the finish button appears, and has the correct text. 
	 */
	public void testDoneButtonAppears(){
		LinearLayout buttons = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.editing_buttons);
		Button finishButton = (Button) buttons.getChildAt(1);
		assertEquals("Finish", finishButton.getText());
	}
}
