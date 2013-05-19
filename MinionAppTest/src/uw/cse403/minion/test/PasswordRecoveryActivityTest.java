package uw.cse403.minion.test;

import uw.cse403.minion.LoginActivity;
import uw.cse403.minion.PasswordRecoveryActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class PasswordRecoveryActivityTest extends
		ActivityInstrumentationTestCase2<PasswordRecoveryActivity> {
	
	public PasswordRecoveryActivityTest() {
		super(PasswordRecoveryActivity.class);
	}
	
	public void testCancel() {
		Solo solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.cancel_button));
		solo.assertCurrentActivity("Login Activity", LoginActivity.class);
		solo.finishOpenedActivities();
		
	}

}
