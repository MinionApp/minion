package uw.cse403.minion.test;

import uw.cse403.minion.CharCreateMainActivity;
import uw.cse403.minion.CharactersActivity;
import uw.cse403.minion.SaveSharedPreference;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

public class SavingThrowsActivityTest extends ActivityInstrumentationTestCase2<CharactersActivity> {
	private static final String FILLER_NUMBER = "10";
	
	private Solo solo;
	private Activity charCreate;
	
	public SavingThrowsActivityTest() {
		super(CharactersActivity.class);
	}
	
	@Override
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
		charCreate = getActivity();
		SaveSharedPreference.clearPreferences(charCreate);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));	
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_basic_info));
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_saving_throws));
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		getActivity().finish();
	}
	
	// white box
	public void testCanEnterFortitudeText() { 
		EditText base = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_base);
		EditText magic = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_magic);
		EditText misc = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_misc);
		EditText temp = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_temp);
		
		solo.typeText(base, FILLER_NUMBER);
		solo.typeText(magic, FILLER_NUMBER);
		solo.typeText(misc, FILLER_NUMBER);
		solo.typeText(temp, FILLER_NUMBER);
		
		String baseStr = base.getText().toString();
		String magicStr = magic.getText().toString();
		String miscStr = misc.getText().toString();
		String tempStr = temp.getText().toString();
		
		assertTrue(FILLER_NUMBER.equals(baseStr));
		assertTrue(FILLER_NUMBER.equals(magicStr));
		assertTrue(FILLER_NUMBER.equals(miscStr));
		assertTrue(FILLER_NUMBER.equals(tempStr));
	}
	
	// white box
	public void testCanEnterReflexText() {		
		EditText base = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_base);
		EditText magic = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_magic);
		EditText misc = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_misc);
		EditText temp = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_temp);
		
		solo.typeText(base, FILLER_NUMBER);
		solo.typeText(magic, FILLER_NUMBER);
		solo.typeText(misc, FILLER_NUMBER);
		solo.typeText(temp, FILLER_NUMBER);
		
		String baseStr = base.getText().toString();
		String magicStr = magic.getText().toString();
		String miscStr = misc.getText().toString();
		String tempStr = temp.getText().toString();
		
		assertTrue(FILLER_NUMBER.equals(baseStr));
		assertTrue(FILLER_NUMBER.equals(magicStr));
		assertTrue(FILLER_NUMBER.equals(miscStr));
		assertTrue(FILLER_NUMBER.equals(tempStr));
	}
	
	// white box
	public void testCanEnterWillText() {
		EditText base = (EditText) solo.getView(uw.cse403.minion.R.id.will_base);
		EditText magic = (EditText) solo.getView(uw.cse403.minion.R.id.will_magic);
		EditText misc = (EditText) solo.getView(uw.cse403.minion.R.id.will_misc);
		EditText temp = (EditText) solo.getView(uw.cse403.minion.R.id.will_temp);
		
		solo.typeText(base, FILLER_NUMBER);
		solo.typeText(magic, FILLER_NUMBER);
		solo.typeText(misc, FILLER_NUMBER);
		solo.typeText(temp, FILLER_NUMBER);
		
		String baseStr = base.getText().toString();
		String magicStr = magic.getText().toString();
		String miscStr = misc.getText().toString();
		String tempStr = temp.getText().toString();
		
		assertTrue(FILLER_NUMBER.equals(baseStr));
		assertTrue(FILLER_NUMBER.equals(magicStr));
		assertTrue(FILLER_NUMBER.equals(miscStr));
		assertTrue(FILLER_NUMBER.equals(tempStr));
	}
	
	// white box
	public void testSubmitEmptyPage() {
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		assertTrue(solo.waitForActivity(CharCreateMainActivity.class));
	}
	
	// white box
	public void testSubmitPartiallyFilledPage() {
		EditText fort = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_base); 
		solo.typeText(fort, FILLER_NUMBER);
		String fortStr = fort.getText().toString();
		assertTrue(FILLER_NUMBER.equals(fortStr));
		
		EditText refl = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_base); 
		solo.typeText(refl, FILLER_NUMBER);
		String reflStr = refl.getText().toString();
		assertTrue(FILLER_NUMBER.equals(reflStr));
		
		EditText wise = (EditText) solo.getView(uw.cse403.minion.R.id.will_base); 
		solo.typeText(wise, FILLER_NUMBER);
		String wiseStr = wise.getText().toString();
		assertTrue(FILLER_NUMBER.equals(wiseStr));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		assertTrue(solo.waitForActivity(CharCreateMainActivity.class));
	}
	
	// white box
	public void testSubmitCompletelyFilledPage() {
		EditText fort_magic = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_magic);
		EditText fort_misc = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_misc);
		EditText fort_temp = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_temp);
		
		solo.typeText(fort_magic, FILLER_NUMBER);
		solo.typeText(fort_misc, FILLER_NUMBER);
		solo.typeText(fort_temp, FILLER_NUMBER);
		
		EditText refl_magic = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_magic);
		EditText refl_misc = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_misc);
		EditText refl_temp = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_temp);
		
		solo.typeText(refl_magic, FILLER_NUMBER);
		solo.typeText(refl_misc, FILLER_NUMBER);
		solo.typeText(refl_temp, FILLER_NUMBER);
		
		EditText wise_magic = (EditText) solo.getView(uw.cse403.minion.R.id.will_magic);
		EditText wise_misc = (EditText) solo.getView(uw.cse403.minion.R.id.will_misc);
		EditText wise_temp = (EditText) solo.getView(uw.cse403.minion.R.id.will_temp);
		
		solo.typeText(wise_magic, FILLER_NUMBER);
		solo.typeText(wise_misc, FILLER_NUMBER);
		solo.typeText(wise_temp, FILLER_NUMBER);

		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		assertTrue(solo.waitForActivity(CharCreateMainActivity.class));
	}
}
