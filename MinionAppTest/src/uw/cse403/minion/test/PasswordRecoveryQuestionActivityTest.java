package uw.cse403.minion.test;

import uw.cse403.minion.PasswordRecoveryQuestionActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

public class PasswordRecoveryQuestionActivityTest extends
		ActivityInstrumentationTestCase2<PasswordRecoveryQuestionActivity> {
	
	private static final String VALID_ANSWER = "yay";
	private static final String INVALID_ANSWER = "boo";
	
	
	public PasswordRecoveryQuestionActivityTest() {
		super(PasswordRecoveryQuestionActivity.class);
	}
	
	public void testDisplayQuestion() {
		Intent intent = new Intent();
		intent.putExtra("question", "What is your favorite color?");
		setActivityIntent(intent);
		Solo solo = new Solo(getInstrumentation(), getActivity());
		Intent i = solo.getCurrentActivity().getIntent();
		String question = i.getStringExtra("question");
		TextView questionActualView = ((TextView) solo.getText(solo.getString(uw.cse403.minion.R.string.placeholder_text)));
		String questionActual = (String) questionActualView.getText();
		assertTrue(question.equals(questionActual));
	}
	
	public void testCheckAnswerValid() {
		Solo solo = new Solo(getInstrumentation(), getActivity());
		EditText answerInput = (EditText) getActivity().findViewById(uw.cse403.minion.R.id.question_input);
		solo.typeText(answerInput, VALID_ANSWER);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.answer_button));
		
	}

}
