package uw.cse403.minion.test;

import uw.cse403.minion.EditGroupActivity;
import uw.cse403.minion.GroupCreateActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Thomas Eberlein
 *
 */
public class EditGroupActivityTest extends
	ActivityInstrumentationTestCase2<EditGroupActivity>  {
	
	public EditGroupActivityTest(){
		super(EditGroupActivity.class);
	}
	
	public void testGameMasterAppears(){
		LinearLayout gm_info = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.game_master_info);
		TextView title = (TextView) gm_info.getChildAt(0);
		assertEquals("Game Master:", title.getText());
	}
	
	public void testPlayersTitleAppears(){
		TextView players = (TextView) getActivity().findViewById(uw.cse403.minion.R.id.players_title);
		assertEquals("Players", players.getText());
	}
	
	public void testInviteButtonAppears(){
		LinearLayout buttons = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.editing_buttons);
		Button invite_button = (Button) buttons.getChildAt(0);
		assertEquals("Invite User", invite_button.getText());
	}
	
	public void testDoneButtonAppears(){
		LinearLayout buttons = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.editing_buttons);
		Button finish_button = (Button) buttons.getChildAt(1);
		assertEquals("Finish", finish_button.getText());
	}
}
