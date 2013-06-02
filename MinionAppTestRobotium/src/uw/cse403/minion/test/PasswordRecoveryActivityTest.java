package uw.cse403.minion.test;

import uw.cse403.minion.LoginActivity;
import uw.cse403.minion.PasswordRecoveryActivity;
import uw.cse403.minion.PasswordRecoveryQuestionActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

public class PasswordRecoveryActivityTest extends
		ActivityInstrumentationTestCase2<PasswordRecoveryActivity> {
	
	private static final String SPINNER_QUESTION_0 = "What is your quest?";
	private static final String SPINNER_QUESTION_1 = "What is your favorite color?";
	private static final String SPINNER_QUESTION_2 = "What is the air-speed velocity of an unladen swallow?";
	private static final String INVALID_USERNAME = "patrick";
	private static final String VALID_USERNAME = "test2";
	
	public PasswordRecoveryActivityTest() {
		super(PasswordRecoveryActivity.class);
	}
	
	public void testCancel() {
		Solo solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.cancel_button));
		solo.assertCurrentActivity("Login Activity", LoginActivity.class);
		solo.finishOpenedActivities();
		
	}
	
	public void testInvalidUsername() {
		Solo solo = new Solo(getInstrumentation(), getActivity());
		EditText usernameEdit = (EditText) getActivity().findViewById(uw.cse403.minion.R.id.username_input);
		solo.typeText(usernameEdit, INVALID_USERNAME);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.recover_password_button));

		TextView errorView = solo.getText(solo.getString(uw.cse403.minion.R.string.invalid_username_error), true);
		assertNotNull(errorView);
		solo.finishOpenedActivities();
	}
	
	public void testValidUsername() {
		Solo solo = new Solo(getInstrumentation(), getActivity());
		EditText usernameEdit = (EditText) getActivity().findViewById(uw.cse403.minion.R.id.username_input);
		solo.typeText(usernameEdit, VALID_USERNAME);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.recover_password_button));
		solo.assertCurrentActivity("RecoveryQuestion", PasswordRecoveryQuestionActivity.class);
		solo.finishOpenedActivities();
	}
	
	public void testValidIntent() {
		Solo solo = new Solo(getInstrumentation(), getActivity());
		EditText usernameEdit = (EditText) getActivity().findViewById(uw.cse403.minion.R.id.username_input);
		solo.typeText(usernameEdit, VALID_USERNAME);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.recover_password_button));
		solo.assertCurrentActivity("RecoveryQuestion", PasswordRecoveryQuestionActivity.class);
		Intent i = solo.getCurrentActivity().getIntent();
		String username = i.getStringExtra("username");
		String question = i.getStringExtra("question");
		assertTrue(username.equals(VALID_USERNAME));
		boolean validQuestion = question.equals(SPINNER_QUESTION_0);
		validQuestion = validQuestion || question.equals(SPINNER_QUESTION_1);
		validQuestion = validQuestion || question.equals(SPINNER_QUESTION_2);
		assertTrue(validQuestion);
		solo.finishOpenedActivities();
		
	}

}
