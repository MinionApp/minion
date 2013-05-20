package uw.cse403.minion.test;

import uw.cse403.minion.CharCreateMainActivity;
import uw.cse403.minion.SavingThrowsActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

public class SavingThrowsActivityTest extends ActivityInstrumentationTestCase2<SavingThrowsActivity> {
	private static final String FILLER_NUMBER = "10";
	
	public SavingThrowsActivityTest() {
		super(SavingThrowsActivity.class);
	}
	
	@Override
	protected void setUp() {
		
	}
	
	// white box
	public void testCanEnterFortitudeText() {
		Intent i = new Intent();
		i.putExtra("cid", 10);
		setActivityIntent(i);
		Solo solo = new Solo(getInstrumentation(), getActivity());
		
		EditText total = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_total); 
		EditText base = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_base);
		EditText ability = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_ability);
		EditText magic = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_magic);
		EditText misc = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_misc);
		EditText temp = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_temp);
		
		solo.typeText(total, FILLER_NUMBER);
		solo.typeText(base, FILLER_NUMBER);
		solo.typeText(ability, FILLER_NUMBER);
		solo.typeText(magic, FILLER_NUMBER);
		solo.typeText(misc, FILLER_NUMBER);
		solo.typeText(temp, FILLER_NUMBER);
		
		String totalStr = total.getText().toString();
		String baseStr = base.getText().toString();
		String abilityStr = ability.getText().toString();
		String magicStr = magic.getText().toString();
		String miscStr = misc.getText().toString();
		String tempStr = temp.getText().toString();
		
		assertTrue(FILLER_NUMBER.equals(totalStr));
		assertTrue(FILLER_NUMBER.equals(baseStr));
		assertTrue(FILLER_NUMBER.equals(abilityStr));
		assertTrue(FILLER_NUMBER.equals(magicStr));
		assertTrue(FILLER_NUMBER.equals(miscStr));
		assertTrue(FILLER_NUMBER.equals(tempStr));
		
		solo.finishOpenedActivities();
	}
	
	// white box
	public void testCanEnterReflexText() {
		Intent i = new Intent();
		i.putExtra("cid", 10);
		setActivityIntent(i);
		Solo solo = new Solo(getInstrumentation(), getActivity());
		
		EditText total = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_total); 
		EditText base = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_base);
		EditText ability = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_ability);
		EditText magic = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_magic);
		EditText misc = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_misc);
		EditText temp = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_temp);
		
		solo.typeText(total, FILLER_NUMBER);
		solo.typeText(base, FILLER_NUMBER);
		solo.typeText(ability, FILLER_NUMBER);
		solo.typeText(magic, FILLER_NUMBER);
		solo.typeText(misc, FILLER_NUMBER);
		solo.typeText(temp, FILLER_NUMBER);
		
		String totalStr = total.getText().toString();
		String baseStr = base.getText().toString();
		String abilityStr = ability.getText().toString();
		String magicStr = magic.getText().toString();
		String miscStr = misc.getText().toString();
		String tempStr = temp.getText().toString();
		
		assertTrue(FILLER_NUMBER.equals(totalStr));
		assertTrue(FILLER_NUMBER.equals(baseStr));
		assertTrue(FILLER_NUMBER.equals(abilityStr));
		assertTrue(FILLER_NUMBER.equals(magicStr));
		assertTrue(FILLER_NUMBER.equals(miscStr));
		assertTrue(FILLER_NUMBER.equals(tempStr));
		
		solo.finishOpenedActivities();
	}
	
	// white box
	public void testCanEnterWillText() {
		Intent i = new Intent();
		i.putExtra("cid", 10);
		setActivityIntent(i);
		Solo solo = new Solo(getInstrumentation(), getActivity());
		
		EditText total = (EditText) solo.getView(uw.cse403.minion.R.id.will_total); 
		EditText base = (EditText) solo.getView(uw.cse403.minion.R.id.will_base);
		EditText ability = (EditText) solo.getView(uw.cse403.minion.R.id.will_ability);
		EditText magic = (EditText) solo.getView(uw.cse403.minion.R.id.will_magic);
		EditText misc = (EditText) solo.getView(uw.cse403.minion.R.id.will_misc);
		EditText temp = (EditText) solo.getView(uw.cse403.minion.R.id.will_temp);
		
		solo.typeText(total, FILLER_NUMBER);
		solo.typeText(base, FILLER_NUMBER);
		solo.typeText(ability, FILLER_NUMBER);
		solo.typeText(magic, FILLER_NUMBER);
		solo.typeText(misc, FILLER_NUMBER);
		solo.typeText(temp, FILLER_NUMBER);
		
		String totalStr = total.getText().toString();
		String baseStr = base.getText().toString();
		String abilityStr = ability.getText().toString();
		String magicStr = magic.getText().toString();
		String miscStr = misc.getText().toString();
		String tempStr = temp.getText().toString();
		
		assertTrue(FILLER_NUMBER.equals(totalStr));
		assertTrue(FILLER_NUMBER.equals(baseStr));
		assertTrue(FILLER_NUMBER.equals(abilityStr));
		assertTrue(FILLER_NUMBER.equals(magicStr));
		assertTrue(FILLER_NUMBER.equals(miscStr));
		assertTrue(FILLER_NUMBER.equals(tempStr));
		
		solo.finishOpenedActivities();
	}
	
	// white box
	public void testSubmitEmptyPage() {
		Intent i = new Intent();
		i.putExtra("cid", 10);
		setActivityIntent(i);
		Solo solo = new Solo(getInstrumentation(), getActivity());
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		assertTrue(solo.waitForActivity(CharCreateMainActivity.class));
	}
	
	// white box
	public void testSubmitPartiallyFilledPage() {
		Intent i = new Intent();
		i.putExtra("cid", 10);
		setActivityIntent(i);
		Solo solo = new Solo(getInstrumentation(), getActivity());
		
		EditText fort = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_total); 
		solo.typeText(fort, FILLER_NUMBER);
		String fortStr = fort.getText().toString();
		assertTrue(FILLER_NUMBER.equals(fortStr));
		
		EditText refl = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_total); 
		solo.typeText(refl, FILLER_NUMBER);
		String reflStr = refl.getText().toString();
		assertTrue(FILLER_NUMBER.equals(reflStr));
		
		EditText wise = (EditText) solo.getView(uw.cse403.minion.R.id.will_total); 
		solo.typeText(wise, FILLER_NUMBER);
		String wiseStr = wise.getText().toString();
		assertTrue(FILLER_NUMBER.equals(wiseStr));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		assertTrue(solo.waitForActivity(CharCreateMainActivity.class));
	}
	
	// white box
	public void testSubmitCompletelyFilledPage() {
		Intent i = new Intent();
		i.putExtra("cid", 10);
		setActivityIntent(i);
		Solo solo = new Solo(getInstrumentation(), getActivity());
		
		EditText fort_total = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_total); 
		EditText fort_base = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_base);
		EditText fort_ability = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_ability);
		EditText fort_magic = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_magic);
		EditText fort_misc = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_misc);
		EditText fort_temp = (EditText) solo.getView(uw.cse403.minion.R.id.fortitude_temp);
		
		solo.typeText(fort_total, FILLER_NUMBER);
		solo.typeText(fort_base, FILLER_NUMBER);
		solo.typeText(fort_ability, FILLER_NUMBER);
		solo.typeText(fort_magic, FILLER_NUMBER);
		solo.typeText(fort_misc, FILLER_NUMBER);
		solo.typeText(fort_temp, FILLER_NUMBER);
		
		EditText refl_total = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_total); 
		EditText refl_base = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_base);
		EditText refl_ability = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_ability);
		EditText refl_magic = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_magic);
		EditText refl_misc = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_misc);
		EditText refl_temp = (EditText) solo.getView(uw.cse403.minion.R.id.reflex_temp);
		
		solo.typeText(refl_total, FILLER_NUMBER);
		solo.typeText(refl_base, FILLER_NUMBER);
		solo.typeText(refl_ability, FILLER_NUMBER);
		solo.typeText(refl_magic, FILLER_NUMBER);
		solo.typeText(refl_misc, FILLER_NUMBER);
		solo.typeText(refl_temp, FILLER_NUMBER);
		
		EditText wise_total = (EditText) solo.getView(uw.cse403.minion.R.id.will_total); 
		EditText wise_base = (EditText) solo.getView(uw.cse403.minion.R.id.will_base);
		EditText wise_ability = (EditText) solo.getView(uw.cse403.minion.R.id.will_ability);
		EditText wise_magic = (EditText) solo.getView(uw.cse403.minion.R.id.will_magic);
		EditText wise_misc = (EditText) solo.getView(uw.cse403.minion.R.id.will_misc);
		EditText wise_temp = (EditText) solo.getView(uw.cse403.minion.R.id.will_temp);
		
		solo.typeText(wise_total, FILLER_NUMBER);
		solo.typeText(wise_base, FILLER_NUMBER);
		solo.typeText(wise_ability, FILLER_NUMBER);
		solo.typeText(wise_magic, FILLER_NUMBER);
		solo.typeText(wise_misc, FILLER_NUMBER);
		solo.typeText(wise_temp, FILLER_NUMBER);

		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		assertTrue(solo.waitForActivity(CharCreateMainActivity.class));
	}
	
	
	@Override
	protected void tearDown() {
		Intent i = new Intent();
		i.putExtra("cid", 10);
		setActivityIntent(i);
		Solo solo = new Solo(getInstrumentation(), getActivity());
		solo.finishOpenedActivities();
	}
}
