package uw.cse403.minion.test;

import uw.cse403.minion.PasswordRecoveryQuestionActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

/**
 * White box tests designed to test the password recovery question
 * mechanism (mainly UI)
 * @author Mary Jones (mlidge)
 */
public class PasswordRecoveryQuestionActivityTest extends
		ActivityInstrumentationTestCase2<PasswordRecoveryQuestionActivity> {
	
	private static final String VALID_ANSWER = "yay";
	private static final String INVALID_ANSWER = "boo";
	
	
	public PasswordRecoveryQuestionActivityTest() {
		super(PasswordRecoveryQuestionActivity.class);
	}
	
	/**
	 * Make sure the question passed to the Activity can be displayed
	 */
	public void testDisplayQuestion() {
		Intent intent = new Intent();
		intent.putExtra("question", "What is your favorite color?");
		setActivityIntent(intent);
		Solo solo = new Solo(getInstrumentation(), getActivity());
		Intent i = solo.getCurrentActivity().getIntent();
		String question = i.getStringExtra("question");
		TextView questionActualView = ((TextView) solo.getCurrentActivity().findViewById(uw.cse403.minion.R.id.security_question));
		String questionActual = (String) questionActualView.getText();
		assertTrue(question.equals(questionActual));
	}
	

}
