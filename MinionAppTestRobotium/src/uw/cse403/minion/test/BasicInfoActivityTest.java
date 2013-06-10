package uw.cse403.minion.test;

import com.jayway.android.robotium.solo.Solo;

import uw.cse403.minion.CharCreateMainActivity;
import uw.cse403.minion.CharactersActivity;
import uw.cse403.minion.SaveSharedPreference;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

public class BasicInfoActivityTest extends ActivityInstrumentationTestCase2<CharactersActivity> {
	private static final String FILLER_ALPHA = "Fish";
	private static final String FILLER_NUMBER = "10";
	private static Solo solo;
	private Activity charCreate;
	
	public BasicInfoActivityTest() {
		super(CharactersActivity.class);
	}
	
	@Override
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
		charCreate = getActivity();
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_basic_info));
	}
	
	@Override
	protected void tearDown() {
		solo.finishOpenedActivities();
	}
	
	
	/**
	 * Test to see that save button returns you to correct Activity after not entering
	 * any text into the fields
	 */
	public void testDoneWithOnlyNameInput() {
		EditText name = (EditText) solo.getView(uw.cse403.minion.R.id.char_name_enter); 
		solo.typeText(name, "Phil Ernst");
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.done));
		solo.assertCurrentActivity("return to char create", CharCreateMainActivity.class);
	}
	
	public void testEnterMajorAlphaText() {
		EditText name = (EditText) solo.getView(uw.cse403.minion.R.id.char_name_enter); 
		EditText level = (EditText) solo.getView(uw.cse403.minion.R.id.char_level_enter);
		EditText deity = (EditText) solo.getView(uw.cse403.minion.R.id.deity_enter);
		EditText homeland = (EditText) solo.getView(uw.cse403.minion.R.id.homeland_enter);
		
		solo.typeText(name, FILLER_ALPHA);
		solo.pressSpinnerItem(0, 4);
		solo.typeText(level, FILLER_ALPHA);
		solo.typeText(deity, FILLER_ALPHA);
		solo.typeText(homeland, FILLER_ALPHA);
		
		String nameStr = name.getText().toString();
		boolean alignVal = solo.isSpinnerTextSelected(0, "Neutral");
		String levelStr = level.getText().toString();
		String deityStr = deity.getText().toString();
		String homelandStr = homeland.getText().toString();
		
		assertTrue(FILLER_ALPHA.equals(nameStr));
		assertTrue(alignVal);
		assertTrue(FILLER_ALPHA.equals(levelStr));
		assertTrue(FILLER_ALPHA.equals(deityStr));
		assertTrue(FILLER_ALPHA.equals(homelandStr));
	}
	
	public void testEnterMajorNumberText() {
		EditText name = (EditText) solo.getView(uw.cse403.minion.R.id.char_name_enter); 
		EditText level = (EditText) solo.getView(uw.cse403.minion.R.id.char_level_enter);
		EditText deity = (EditText) solo.getView(uw.cse403.minion.R.id.deity_enter);
		EditText homeland = (EditText) solo.getView(uw.cse403.minion.R.id.homeland_enter);
		
		solo.typeText(name, FILLER_NUMBER);
		solo.pressSpinnerItem(0, 5);
		solo.typeText(level, FILLER_NUMBER);
		solo.typeText(deity, FILLER_NUMBER);
		solo.typeText(homeland, FILLER_NUMBER);
		
		String nameStr = name.getText().toString();
		boolean alignVal = solo.isSpinnerTextSelected(0, "Neutral Evil");
		boolean alignValG = solo.isSpinnerTextSelected(0, "Neutral Böse");
		String levelStr = level.getText().toString();
		String deityStr = deity.getText().toString();
		String homelandStr = homeland.getText().toString();
		
		assertTrue(FILLER_NUMBER.equals(nameStr));
		assertTrue(alignVal || alignValG);
		assertTrue(FILLER_NUMBER.equals(levelStr));
		assertTrue(FILLER_NUMBER.equals(deityStr));
		assertTrue(FILLER_NUMBER.equals(homelandStr));
	}
	
	public void testEnterMinorAlphaText() {
		EditText race = (EditText) solo.getView(uw.cse403.minion.R.id.race_enter); 
		EditText gender = (EditText) solo.getView(uw.cse403.minion.R.id.gender_enter);
		EditText age = (EditText) solo.getView(uw.cse403.minion.R.id.age_enter);
		EditText height = (EditText) solo.getView(uw.cse403.minion.R.id.height_enter);
		EditText weight = (EditText) solo.getView(uw.cse403.minion.R.id.weight_enter);
		EditText hair = (EditText) solo.getView(uw.cse403.minion.R.id.hair_enter);
		EditText eyes = (EditText) solo.getView(uw.cse403.minion.R.id.eyes_enter);
		
		solo.typeText(race, FILLER_ALPHA);
		solo.pressSpinnerItem(1, 4);
		solo.typeText(gender, FILLER_ALPHA);
		solo.typeText(age, FILLER_ALPHA);
		solo.typeText(height, FILLER_ALPHA);
		solo.typeText(weight, FILLER_ALPHA);
		solo.typeText(hair, FILLER_ALPHA);
		solo.typeText(eyes, FILLER_ALPHA);
		
		String raceStr = race.getText().toString();
		boolean sizeVal = solo.isSpinnerTextSelected(1, "Medium");
		boolean sizeValG = solo.isSpinnerTextSelected(1, "Mittelgroß");
		String genderStr = gender.getText().toString();
		String ageStr = age.getText().toString();
		String heightStr = height.getText().toString();
		String weightStr = weight.getText().toString();
		String hairStr = hair.getText().toString();
		String eyesStr = eyes.getText().toString();
		
		assertTrue(FILLER_ALPHA.equals(raceStr));
		assertTrue(sizeVal || sizeValG);
		assertTrue(FILLER_ALPHA.equals(genderStr));
		assertTrue(FILLER_ALPHA.equals(ageStr));
		assertTrue(FILLER_ALPHA.equals(heightStr));
		assertTrue(FILLER_ALPHA.equals(weightStr));
		assertTrue(FILLER_ALPHA.equals(hairStr));
		assertTrue(FILLER_ALPHA.equals(eyesStr));
	}
	
	public void testEnterMinorNumberText() {
		EditText race = (EditText) solo.getView(uw.cse403.minion.R.id.race_enter); 
		EditText gender = (EditText) solo.getView(uw.cse403.minion.R.id.gender_enter);
		EditText age = (EditText) solo.getView(uw.cse403.minion.R.id.age_enter);
		EditText height = (EditText) solo.getView(uw.cse403.minion.R.id.height_enter);
		EditText weight = (EditText) solo.getView(uw.cse403.minion.R.id.weight_enter);
		EditText hair = (EditText) solo.getView(uw.cse403.minion.R.id.hair_enter);
		EditText eyes = (EditText) solo.getView(uw.cse403.minion.R.id.eyes_enter);
		
		solo.typeText(race, FILLER_NUMBER);
		solo.typeText(gender, FILLER_NUMBER);
		solo.pressSpinnerItem(1, 0);
		solo.typeText(age, FILLER_NUMBER);
		solo.typeText(height, FILLER_NUMBER);
		solo.typeText(weight, FILLER_NUMBER);
		solo.typeText(hair, FILLER_NUMBER);
		solo.typeText(eyes, FILLER_NUMBER);
		
		String raceStr = race.getText().toString();
		boolean sizeVal = solo.isSpinnerTextSelected(1, "Fine");
		boolean sizeValG = solo.isSpinnerTextSelected(1, "Fein");
		String genderStr = gender.getText().toString();
		String ageStr = age.getText().toString();
		String heightStr = height.getText().toString();
		String weightStr = weight.getText().toString();
		String hairStr = hair.getText().toString();
		String eyesStr = eyes.getText().toString();
		
		assertTrue(FILLER_NUMBER.equals(raceStr));
		assertTrue(sizeVal || sizeValG);
		assertTrue(FILLER_NUMBER.equals(genderStr));
		assertTrue(FILLER_NUMBER.equals(ageStr));
		assertTrue(FILLER_NUMBER.equals(heightStr));
		assertTrue(FILLER_NUMBER.equals(weightStr));
		assertTrue(FILLER_NUMBER.equals(hairStr));
		assertTrue(FILLER_NUMBER.equals(eyesStr));
	}
	
	public void testSubmitEmptyPage() {
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		assertTrue(solo.waitForActivity(CharCreateMainActivity.class));
	}
	
	public void testSubmitPartiallyFilledPage() {
		EditText name = (EditText) solo.getView(uw.cse403.minion.R.id.char_name_enter);
		EditText level = (EditText) solo.getView(uw.cse403.minion.R.id.char_level_enter);
		EditText race = (EditText) solo.getView(uw.cse403.minion.R.id.race_enter); 
		EditText age = (EditText) solo.getView(uw.cse403.minion.R.id.age_enter);
		
		solo.typeText(name, FILLER_ALPHA);
		solo.typeText(level, FILLER_NUMBER);
		solo.typeText(race, FILLER_ALPHA);
		solo.typeText(age, FILLER_NUMBER);
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		assertTrue(solo.waitForActivity(CharCreateMainActivity.class));
	}
	
	public void testSubmitCompletelyFilledPage() {
		EditText name = (EditText) solo.getView(uw.cse403.minion.R.id.char_name_enter); 
		EditText level = (EditText) solo.getView(uw.cse403.minion.R.id.char_level_enter);
		EditText deity = (EditText) solo.getView(uw.cse403.minion.R.id.deity_enter);
		EditText homeland = (EditText) solo.getView(uw.cse403.minion.R.id.homeland_enter);
		EditText race = (EditText) solo.getView(uw.cse403.minion.R.id.race_enter); 
		EditText gender = (EditText) solo.getView(uw.cse403.minion.R.id.gender_enter);
		EditText age = (EditText) solo.getView(uw.cse403.minion.R.id.age_enter);
		EditText height = (EditText) solo.getView(uw.cse403.minion.R.id.height_enter);
		EditText weight = (EditText) solo.getView(uw.cse403.minion.R.id.weight_enter);
		EditText hair = (EditText) solo.getView(uw.cse403.minion.R.id.hair_enter);
		EditText eyes = (EditText) solo.getView(uw.cse403.minion.R.id.eyes_enter);
		
		solo.typeText(name, FILLER_ALPHA);
		solo.pressSpinnerItem(0, 4);
		solo.typeText(level, FILLER_NUMBER);
		solo.typeText(deity, FILLER_ALPHA);
		solo.typeText(homeland, FILLER_ALPHA);
		solo.typeText(race, FILLER_ALPHA);
		solo.pressSpinnerItem(1, 4);
		solo.typeText(gender, FILLER_ALPHA);
		solo.typeText(age, FILLER_NUMBER);
		solo.typeText(height, FILLER_NUMBER);
		solo.typeText(weight, FILLER_NUMBER);
		solo.typeText(hair, FILLER_ALPHA);
		solo.typeText(eyes, FILLER_ALPHA);
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		assertTrue(solo.waitForActivity(CharCreateMainActivity.class));
	}
	
}
