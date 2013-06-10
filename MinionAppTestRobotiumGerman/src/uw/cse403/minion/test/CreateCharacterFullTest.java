package uw.cse403.minion.test;

import uw.cse403.minion.AbilityScoresActivity;
import uw.cse403.minion.BasicInfoActivity;
import uw.cse403.minion.CharCreateMainActivity;
import uw.cse403.minion.CharacterDataSource;
import uw.cse403.minion.CharactersActivity;
import uw.cse403.minion.HomeActivity;
import uw.cse403.minion.LoginActivity;
import uw.cse403.minion.SaveSharedPreference;
import uw.cse403.minion.SkillsActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

/**
 * @author Loki White (lokiw)
 * 
 * Automated UI test that creates a new character from scratch.
 */

public class CreateCharacterFullTest extends
	ActivityInstrumentationTestCase2<LoginActivity> {

	private static final String VALID_USERNAME = "test";
	private static final String VALID_PASSWORD = "abcDEF123@";
	private static final String EMPTY = "";
	private static final String CHAR_NAME = "Hero";
	private static final String BASE_LEVEL = "1";
	
	private static final String STR = "12";
	private static final String DEX = "8";
	private static final String CON = "13";
	private static final String INT = "11";
	private static final String WIS = "14";
	private static final String CHA = "16";
	
	private static final String POSITIVE_MOD = "2";
	private static final String NEGATIVE_MOD = "-2";

	private static final String SKILL_RANK = "1";
	private static final String SKILL_RANK_LARGE = "4";
	
	private Solo solo;
	private Activity loginActivity;

	public CreateCharacterFullTest() {
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
		SaveSharedPreference.clearPreferences(loginActivity);
	}
	
	/**
	 * Login to the application using valid user name and password.
	 */
	public void testCreateCharacterScratch() {
		//Login
		SaveSharedPreference.clearPreferences(loginActivity);
		EditText usernameField = (EditText) getActivity().findViewById(uw.cse403.minion.R.id.username_input); 
		EditText passwordField = (EditText) getActivity().findViewById(uw.cse403.minion.R.id.password_input); 

		solo.typeText(usernameField, VALID_USERNAME);
		solo.typeText(passwordField, VALID_PASSWORD);
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.login_button));
		solo.assertCurrentActivity("Login successful", HomeActivity.class);
		//Login Finished
		
		//Go To Character Activity
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_manage_characters));	
		solo.assertCurrentActivity("CharactersActivity", CharactersActivity.class);
		
		//Add a new character
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_add_character));	
		solo.assertCurrentActivity("charCreateMainActivity", CharCreateMainActivity.class);
		
		//Go to basic info
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_basic_info));
		solo.assertCurrentActivity("basic info", BasicInfoActivity.class);
		
		//Edit Basic Information
		EditText name = (EditText) solo.getView(uw.cse403.minion.R.id.char_name_enter); 
		EditText level = (EditText) solo.getView(uw.cse403.minion.R.id.char_level_enter);

		solo.clearEditText(name);
		solo.typeText(name, CHAR_NAME);
		solo.pressSpinnerItem(0, 4);
		solo.clearEditText(level);
		solo.typeText(level, BASE_LEVEL);
		
		String nameStr = name.getText().toString();
		boolean alignVal = solo.isSpinnerTextSelected(0, "Neutral");
		String levelStr = level.getText().toString();
		
		assertTrue(CHAR_NAME.equals(nameStr));
		assertTrue(alignVal);
		assertTrue(BASE_LEVEL.equals(levelStr));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		solo.assertCurrentActivity("save basic info", CharCreateMainActivity.class);
		
		//Go to Ability Scores
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_ability_scores));
		solo.assertCurrentActivity("ability scores", AbilityScoresActivity.class);
		
		//Edit Ability Score Information
		EditText strength = (EditText) solo.getView(uw.cse403.minion.R.id.str_base);
		EditText dexterity = (EditText) solo.getView(uw.cse403.minion.R.id.dex_base);
		EditText constitution = (EditText) solo.getView(uw.cse403.minion.R.id.con_base);
		EditText intelligence = (EditText) solo.getView(uw.cse403.minion.R.id.int_base);
		EditText wisdom = (EditText) solo.getView(uw.cse403.minion.R.id.wis_base);
		EditText charisma = (EditText) solo.getView(uw.cse403.minion.R.id.cha_base);
		EditText tempModNeg = (EditText) solo.getView(uw.cse403.minion.R.id.con_temp);
		EditText tempModPos = (EditText) solo.getView(uw.cse403.minion.R.id.int_temp);

		solo.clearEditText(strength);
		solo.typeText(strength, STR);
		solo.clearEditText(dexterity);
		solo.typeText(dexterity, DEX);
		solo.clearEditText(constitution);
		solo.typeText(constitution, CON);
		solo.clearEditText(intelligence);
		solo.typeText(intelligence, INT);
		solo.clearEditText(wisdom);
		solo.typeText(wisdom, WIS);
		solo.clearEditText(charisma);
		solo.typeText(charisma, CHA);
		
		solo.clearEditText(tempModNeg);
		solo.typeText(tempModNeg, NEGATIVE_MOD);
		solo.clearEditText(tempModPos);
		solo.typeText(tempModPos, POSITIVE_MOD);

		String strText = strength.getText().toString();
		String dexText = dexterity.getText().toString();
		String conText = constitution.getText().toString();
		String intText = intelligence.getText().toString();
		String wisText = wisdom.getText().toString();
		String chaText = charisma.getText().toString();
		String posModText = tempModPos.getText().toString();
		String negModText = tempModNeg.getText().toString();

		assertTrue(STR.equals(strText));
		assertTrue(DEX.equals(dexText));
		assertTrue(CON.equals(conText));
		assertTrue(INT.equals(intText));
		assertTrue(WIS.equals(wisText));
		assertTrue(CHA.equals(chaText));
		assertTrue(NEGATIVE_MOD.equals(negModText));
		assertTrue(POSITIVE_MOD.equals(posModText));
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		solo.assertCurrentActivity("save basic info", CharCreateMainActivity.class);
		

		//Go to Skills
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.button_skills));
		solo.assertCurrentActivity("skills", SkillsActivity.class);
		
		//Skills to modify: Bluff, Craft (Armor), Knowledge (History), Perform (String)
		//	Profession (Brewer), SpellCraft
		EditText bluff = (EditText) solo.getView(uw.cse403.minion.R.id.bluff_ranks);
		EditText craftA = (EditText) solo.getView(uw.cse403.minion.R.id.craft1_ranks);
		EditText knowH = (EditText) solo.getView(uw.cse403.minion.R.id.knowledge_history_ranks);
		EditText perS = (EditText) solo.getView(uw.cse403.minion.R.id.perform2_ranks);
		EditText profBr = (EditText) solo.getView(uw.cse403.minion.R.id.profession1_ranks);
		EditText spellCraft = (EditText) solo.getView(uw.cse403.minion.R.id.spellcraft_ranks);
		EditText craftMisc = (EditText) solo.getView(uw.cse403.minion.R.id.craft1_misc_mod);
		EditText bluffMisc = (EditText) solo.getView(uw.cse403.minion.R.id.bluff_misc_mod);

		solo.clearEditText(bluff);
		solo.clearEditText(bluffMisc);
		solo.typeText(bluff, SKILL_RANK);
		solo.scrollToSide(Solo.RIGHT);
		solo.typeText(bluffMisc, NEGATIVE_MOD);
		solo.scrollToSide(Solo.LEFT);
		
		solo.pressSpinnerItem(0, 1);
		solo.clearEditText(craftA);
		solo.clearEditText(craftMisc);
		solo.typeText(craftA, SKILL_RANK);
		solo.scrollToSide(Solo.RIGHT);
		solo.typeText(craftMisc, POSITIVE_MOD);
		solo.scrollToSide(Solo.LEFT);
		
		solo.clearEditText(knowH);
		solo.typeText(knowH, SKILL_RANK_LARGE);
		solo.pressSpinnerItem(1, 6);
		solo.clearEditText(perS);
		solo.typeText(perS, SKILL_RANK);
		solo.pressSpinnerItem(2, 3);
		solo.clearEditText(profBr);
		solo.typeText(profBr, SKILL_RANK);
		solo.clearEditText(spellCraft);
		solo.typeText(spellCraft, SKILL_RANK_LARGE);
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.save));
		solo.assertCurrentActivity("save basic info", CharCreateMainActivity.class);
		
		solo.clickOnButton(solo.getString(uw.cse403.minion.R.string.done));

		getActivity().finish();
	}
	
	@Override
	protected void tearDown() throws Exception {
		CharacterDataSource.deleteAllCharacters();
		SaveSharedPreference.setUserName(loginActivity, EMPTY);
		solo.finishOpenedActivities();
		
	}
}
