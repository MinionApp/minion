package uw.cse403.minion.test;

import uw.cse403.minion.GroupCreateActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GroupCreateActivityTest extends
	ActivityInstrumentationTestCase2<GroupCreateActivity>  {
	
	public GroupCreateActivityTest(){
		super(GroupCreateActivity.class);
	}
	
	public void testGroupNameAppears(){
		LinearLayout group_name = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.group_name);
		TextView group_prompt = (TextView) group_name.getChildAt(0);
		assertEquals("EnterGroupName", group_prompt.getText());
	}
	
	public void testEditGroupNameRightPrompt(){
		LinearLayout group_name = (LinearLayout) getActivity().findViewById(uw.cse403.minion.R.id.group_name);
		EditText group_prompt = (EditText) group_name.getChildAt(1);
		assertEquals("Name", group_prompt.getHint());
	}
	
	public void testAddButtonAppears(){
		Button add_button = (Button) getActivity().findViewById(uw.cse403.minion.R.id.add_user_button);
		assertEquals("Add", add_button.getText());
	}
	
	public void testDoneButtonAppears(){
		Button done_button = (Button) getActivity().findViewById(uw.cse403.minion.R.id.create_group_button);
		assertEquals("Done", done_button.getText());
	}
}
