package com.example.myapp.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfirstapp.HomeActivity;
import com.example.myfirstapp.LoginActivity;
import com.example.myfirstapp.PasswordRecoveryActivity;
import com.example.myfirstapp.SaveSharedPreference;
import com.example.myfirstapp.SignupActivity;
import com.jayway.android.robotium.solo.Solo;

/**
 * @author Mary Jones (mlidge)
 * 
 * LoginActivityTest tests the LoginActivity class by using Robotium and 
 * by checking application preferences.
 */

public class LoginActivityTest extends
	ActivityInstrumentationTestCase2<LoginActivity> {

	private static final String VALID_USERNAME = "test";
	private static final String VALID_PASSWORD = "abcDEF123@";
	private static final String INVALID_CREDENTIAL = "failure";
	private static final String EMPTY = "";
	private static final int REMEMBER_ME = 0;
	private Solo solo;
	private Activity loginActivity;

	public LoginActivityTest() {
		super(LoginActivity.class);
	}
	
	/**
	 * setup() instantiates Solo and stores the LoginActivity.
	 * loginActivity is later used to clear out the "Remember me" settings
	 * from the application preferences to ensure clean state for each test.
	 */
	@Override
	protected void setUp() throws Exception {

		solo = new Solo(getInstrumentation(), getActivity());
		loginActivity = getActivity();
		SaveSharedPreference.setUserName(loginActivity, EMPTY);
		
	}
	
	/**
	 * testCanLoginValid() tests the ability to login with a valid
	 * username and password.
	 */
	public void testCanLoginValid() {
		SaveSharedPreference.setUserName(loginActivity, EMPTY);
		EditText usernameField = (EditText) solo.getView(com.example.myfirstapp.R.id.username_input); 
		EditText passwordField = (EditText) solo.getView(com.example.myfirstapp.R.id.password_input); 
		
		solo.typeText(usernameField, VALID_USERNAME);
		solo.typeText(passwordField, VALID_PASSWORD);
		solo.clickOnButton(solo.getString(com.example.myfirstapp.R.string.login_button));
		solo.assertCurrentActivity("Login successful", HomeActivity.class);
		solo.finishOpenedActivities();
	}
	
	public void testCannotLoginInvalidUsername() {
		SaveSharedPreference.setUserName(loginActivity, EMPTY);
		EditText usernameField = (EditText) solo.getView(com.example.myfirstapp.R.id.username_input); 
		EditText passwordField = (EditText) solo.getView(com.example.myfirstapp.R.id.password_input); 
		
		solo.typeText(usernameField, INVALID_CREDENTIAL);
		solo.typeText(passwordField, VALID_PASSWORD);
		solo.clickOnButton(solo.getString(com.example.myfirstapp.R.string.login_button));
		
		
		TextView errorView = solo.getText(solo.getString(com.example.myfirstapp.R.string.invalid_login), true);
		assertNotNull(errorView);
		solo.assertCurrentActivity("Login successful", LoginActivity.class);
		solo.finishOpenedActivities();
	}
	
	public void testCannotLoginInvalidPassword() {
		SaveSharedPreference.setUserName(loginActivity, EMPTY);
		EditText usernameField = (EditText) solo.getView(com.example.myfirstapp.R.id.username_input); 
		EditText passwordField = (EditText) solo.getView(com.example.myfirstapp.R.id.password_input); 
		
		solo.typeText(usernameField, VALID_USERNAME);
		solo.typeText(passwordField, INVALID_CREDENTIAL);
		solo.clickOnButton(solo.getString(com.example.myfirstapp.R.string.login_button));
		
		
		TextView errorView = solo.getText(solo.getString(com.example.myfirstapp.R.string.invalid_login), true);
		assertNotNull(errorView);
		solo.assertCurrentActivity("Login successful", LoginActivity.class);
		solo.finishOpenedActivities();
	}
	
	public void testCannotLoginInvalidBoth() {
		SaveSharedPreference.setUserName(loginActivity, EMPTY);
		EditText usernameField = (EditText) solo.getView(com.example.myfirstapp.R.id.username_input); 
		EditText passwordField = (EditText) solo.getView(com.example.myfirstapp.R.id.password_input); 
		
		solo.typeText(usernameField, INVALID_CREDENTIAL);
		solo.typeText(passwordField, INVALID_CREDENTIAL);
		solo.clickOnButton(solo.getString(com.example.myfirstapp.R.string.login_button));
		
		
		TextView errorView = solo.getText(solo.getString(com.example.myfirstapp.R.string.invalid_login), true);
		assertNotNull(errorView);
		solo.assertCurrentActivity("Login successful", LoginActivity.class);
		solo.finishOpenedActivities();
	}
	
	public void testKeepLoggedInTrue() {
		SaveSharedPreference.setUserName(loginActivity, EMPTY);
		EditText usernameField = (EditText) solo.getView(com.example.myfirstapp.R.id.username_input); 
		EditText passwordField = (EditText) solo.getView(com.example.myfirstapp.R.id.password_input); 
		
		solo.typeText(usernameField, VALID_USERNAME);
		solo.typeText(passwordField, VALID_PASSWORD);
		solo.clickOnCheckBox(REMEMBER_ME);
		solo.clickOnButton(solo.getString(com.example.myfirstapp.R.string.login_button));
		
		solo.assertCurrentActivity("Login successful", HomeActivity.class);
		String username = SaveSharedPreference.getUserName(getActivity());
		assertTrue(VALID_USERNAME.equals(username));
		solo.finishOpenedActivities();
		
	}
	
	public void testKeepLoggedInFalse() {
		SaveSharedPreference.setUserName(loginActivity, EMPTY);
		EditText usernameField = (EditText) solo.getView(com.example.myfirstapp.R.id.username_input); 
		EditText passwordField = (EditText) solo.getView(com.example.myfirstapp.R.id.password_input); 
		
		solo.typeText(usernameField, VALID_USERNAME);
		solo.typeText(passwordField, VALID_PASSWORD);
		solo.clickOnButton(solo.getString(com.example.myfirstapp.R.string.login_button));
		
		solo.assertCurrentActivity("Login successful", HomeActivity.class);
		String username = SaveSharedPreference.getUserName(getActivity());
		assertFalse(VALID_USERNAME.equals(username));
		solo.finishOpenedActivities();
	}

	public void testGoToSignUp() {
		solo.clickOnText(solo.getString(com.example.myfirstapp.R.string.signup_clickable_text));
		solo.assertCurrentActivity("Went to SignpActivity", SignupActivity.class);
		solo.finishOpenedActivities();
	}
	
	public void testGoToPasswordRecovery() {
		solo.clickOnText(solo.getString(com.example.myfirstapp.R.string.forgot_password_clickable_text));
		solo.assertCurrentActivity("Went to PasswordRecoveryActivity", PasswordRecoveryActivity.class);
		solo.finishOpenedActivities();
	}
	
	@Override
	protected void tearDown() throws Exception {
		SaveSharedPreference.setUserName(loginActivity, EMPTY);
		solo.finishOpenedActivities();
		
	}
}
