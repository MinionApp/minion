package uw.cse403.minion.test;

import uw.cse403.minion.EmptyValidatorActivity;
import uw.cse403.minion.NewPasswordValidatorActivity;
import uw.cse403.minion.PasswordResetActivity;
import uw.cse403.minion.R;
import uw.cse403.minion.SignupActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

public class PasswordResetActivityTest extends
		ActivityInstrumentationTestCase2<PasswordResetActivity> {
	
	private static final String VALID_PASSWORD = "abcDEF123@";
	private static final String INVALID_PASSWORD = "abc";
	
	public PasswordResetActivityTest() {
		super(PasswordResetActivity.class);
	}
	
	@Override
	public void setUp() {
		
	}
	
	public void testCanEnterText() {
		Solo solo = new Solo(getInstrumentation(), getActivity());
		EditText passwordEdit = (EditText) solo.getView(uw.cse403.minion.R.id.password_input);
		EditText passwordConfirmEdit = (EditText) solo.getView(uw.cse403.minion.R.id.confirm_password_input);
		
		solo.typeText(passwordEdit, VALID_PASSWORD);
		solo.typeText(passwordConfirmEdit, VALID_PASSWORD);
		
		String password = passwordEdit.getText().toString();
		String passwordConfirm = passwordConfirmEdit.getText().toString();
		
		assertTrue(password.equals(VALID_PASSWORD));
		assertTrue(passwordConfirm.equals(VALID_PASSWORD));
		
		solo.finishOpenedActivities();
	}
	
	public void testInvalidPassword() {
		Solo solo = new Solo(getInstrumentation(), getActivity());
		EditText passwordEdit = (EditText) solo.getView(uw.cse403.minion.R.id.password_input);
		EditText passwordConfirmEdit = (EditText) solo.getView(uw.cse403.minion.R.id.confirm_password_input);
		Activity reset = getActivity();
		solo.typeText(passwordEdit, INVALID_PASSWORD);
		solo.typeText(passwordConfirmEdit, INVALID_PASSWORD);
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.change_password_button));
		
		solo.waitForActivity(NewPasswordValidatorActivity.class);
		solo.waitForActivity(PasswordResetActivity.class);
		TextView errorView = (TextView) reset.findViewById(uw.cse403.minion.R.id.invalid_password_error);
		assertNotNull(errorView);
		solo.finishOpenedActivities();
	}
	
	public void testNonMatchingPassword() {
		Solo solo = new Solo(getInstrumentation(), getActivity()); 
		EditText password = (EditText) getActivity().findViewById(uw.cse403.minion.R.id.password_input);
		EditText passwordMatch = (EditText) getActivity().findViewById(uw.cse403.minion.R.id.confirm_password_input);
		solo.typeText(password, VALID_PASSWORD);
		solo.typeText(passwordMatch, INVALID_PASSWORD);
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.change_password_button));
		
		solo.waitForActivity(NewPasswordValidatorActivity.class);
		solo.waitForActivity(SignupActivity.class);
		TextView errorView = (TextView) solo.getText(solo.getString(uw.cse403.minion.R.string.non_matching_passwords_message));
		assertTrue(errorView.isShown());
		solo.finishOpenedActivities();
	}


	
}