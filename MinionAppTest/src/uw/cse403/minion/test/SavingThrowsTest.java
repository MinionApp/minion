package uw.cse403.minion.test;

import uw.cse403.minion.Ability;
import uw.cse403.minion.AbilityName;
import uw.cse403.minion.SavingThrow;
import junit.framework.TestCase;

/**
 * This JUnit file tests the SavingThrows class against its documentation and internal rep.
 * @author sahabp
 *
 */

public class SavingThrowsTest extends TestCase {
	
	public void test_constructor_NullParamater() {
		try {
			SavingThrow s = new SavingThrow(null);
			fail("SavingThrow did not throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// test passes
		}
	}
	
	public void test_constructor_InvalidParameter() {
		try {
			SavingThrow s = new SavingThrow(AbilityName.INTELLIGENCE);
			fail("SavingThrow did not throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// test passes
		}
	}
	
	public void test_constructor_WisdomParamater() {
		try {
			SavingThrow s = new SavingThrow(AbilityName.WISDOM);
		} catch (IllegalArgumentException e) {
			fail("SavingThrow threw IllegalArgumentException");
		}
	}
	
	public void test_constructor_DexterityParamater() {
		try {
			SavingThrow s = new SavingThrow(AbilityName.DEXTERITY);
		} catch (IllegalArgumentException e) {
			fail("SavingThrow threw IllegalArgumentException");
		}
	}
	
	public void test_constructor_ConstitutionParamater() {
		try {
			SavingThrow s = new SavingThrow(AbilityName.CONSTITUTION);
		} catch (IllegalArgumentException e) {
			fail("SavingThrow threw IllegalArgumentException");
		}
	}
	
	public void test_setBaseSave_ArgLessThanZero() {
		try {
			SavingThrow s = new SavingThrow(AbilityName.WISDOM);
			s.setBaseSave(-1);
			fail("setBaseSave did not throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// test passes
		}
	}
	
	public void test_setBaseSave_ArgIsZero() {
		try {
			SavingThrow s = new SavingThrow(AbilityName.WISDOM);
			s.setBaseSave(0);
		} catch (IllegalArgumentException e) {
			fail("setBaseSave threw IllegalArgumentException");
		}
	}
	
	public void test_getModifier_ArgNotInTable() {
		SavingThrow s = new SavingThrow(AbilityName.WISDOM);
		int modifier = s.getModifier("sample");
		assertEquals(0, modifier);
	}
	
	public void test_addModifier_getModifier_ValidArgsInTable() {
		SavingThrow s = new SavingThrow(AbilityName.WISDOM);
		s.addModifier("sample", 10);
		int modifier = s.getModifier("sample");
		assertEquals(10, modifier);
	}
	
	public void test_addModifier_NullNameArg() {
		try {
			SavingThrow s = new SavingThrow(AbilityName.WISDOM);
			s.addModifier(null, 10);
			fail("setBaseSave did not throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// test passes
		}
	}
	
	public void test_addModifier_ZeroValueModifierArg() {
		try {
			SavingThrow s = new SavingThrow(AbilityName.WISDOM);
			s.addModifier("sample", 0);
			fail("setBaseSave did not throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// test passes
		}
	}
	
	public void test_addModifier_NoPrevValue() {
		SavingThrow s = new SavingThrow(AbilityName.WISDOM);
		int prev = s.addModifier("sample", 10);
		assertEquals(0, prev);
	}
	
	public void test_addModifier_SomePrevValue() {
		SavingThrow s = new SavingThrow(AbilityName.WISDOM);
		s.addModifier("sample", 5);
		int prev = s.addModifier("sample", 10);
		assertEquals(5, prev);
	}
	
	public void test_removeModifier_NullModifier() {
		// ensure remove does not crash with null -- permitted but dumb
		SavingThrow s = new SavingThrow(AbilityName.WISDOM);
		s.removeModifier(null);
	}
	
	public void test_removeModifier_NonexistentModifier() {
		// ensure remove does not crash
		SavingThrow s = new SavingThrow(AbilityName.WISDOM);
		s.removeModifier("sample");
	}
	
	public void test_removeModifier_ModifierExists() {
		SavingThrow s = new SavingThrow(AbilityName.WISDOM);
		s.addModifier("sample", 5);
		s.removeModifier("sample");
		int modifier = s.getModifier("sample");
		assertEquals(0, modifier);
	}
	
	/*
	 * Cannot unit test getTotal because it requires database access.
	 * 
	public void test_getTotal_NewSavingThrow() {
		SavingThrow s = new SavingThrow(AbilityName.WISDOM);
		int total = s.getTotal(new Ability(1, AbilityName.WISDOM, 5));
		assertEquals(5, total);
	}

	public void test_getTotal_MismatchAbility() {
		try {
			SavingThrow s = new SavingThrow(AbilityName.WISDOM);
			s.getTotal(new Ability(1, AbilityName.INTELLIGENCE, 5));
			fail("getTotal did not throw an exception");
		} catch (IllegalArgumentException e) {
			// test passes
		}
	}
	
	public void test_getTotal_NonZeroBaseSave() {
		SavingThrow s = new SavingThrow(AbilityName.WISDOM);
		s.setBaseSave(10);
		int total = s.getTotal(new Ability(1, AbilityName.WISDOM, 5));
		assertEquals(15, total);
	}
	
	public void test_getTotal_NonZeroModifiers() {
		SavingThrow s = new SavingThrow(AbilityName.WISDOM);
		s.addModifier("sample", 10);
		int total = s.getTotal(new Ability(1, AbilityName.WISDOM, 5));
		assertEquals(15, total);
	}
	*/
}
