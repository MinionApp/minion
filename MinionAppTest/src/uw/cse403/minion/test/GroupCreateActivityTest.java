package uw.cse403.minion.test;

import java.util.ArrayList;

import uw.cse403.minion.GroupCreateActivity;
import uw.cse403.minion.SaveSharedPreference;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * White box texts for GroupCreateActivity, testing that the basic
 * UI elements are displayed correctly. 
 * @author Thomas Eberlein
 *
 */
public class GroupCreateActivityTest extends
	ActivityInstrumentationTestCase2<GroupCreateActivity>  {
	
	public GroupCreateActivityTest(){
		super(GroupCreateActivity.class);
	}
	
	/**
	 * Tests to make sure the proper prompt is displayed for the entering
	 * the new group's name. 
	 */
	public void testGroupNameAppears(){
		LinearLayout groupName = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.group_name);
		TextView groupPrompt = (TextView) groupName.getChildAt(0);
		assertEquals("Enter Group Name", groupPrompt.getText());
	}
	
	/**
	 * Tests to make sure correct hint is displayed for entering a group's name. 
	 */
	public void testEditGroupNameRightPrompt(){
		LinearLayout groupName = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.group_name);
		EditText groupPrompt = (EditText) groupName.getChildAt(1);
		assertEquals("Name", groupPrompt.getHint());
	}
	
	/**
	 * Tests that add button appears, and has the correct text.
	 */
	public void testAddButtonAppears(){
		Button addButton = (Button) getActivity().findViewById(uw.cse403.minion.R.id.add_user_button);
		assertEquals("Add", addButton.getText());
	}
	
	/**
	 * Tests that the done button appears, and has the correct text. 
	 */
	public void testDoneButtonAppears(){
		Button doneButton = (Button) getActivity().findViewById(uw.cse403.minion.R.id.create_group_button);
		assertEquals("Done", doneButton.getText());
	}
}
