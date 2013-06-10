package uw.cse403.minion.test;

import uw.cse403.minion.SavingThrow;
import junit.framework.TestCase;

/**
 * This JUnit file tests the SavingThrows class against its documentation and internal rep.
 * @author sahabp
 *
 */

public class SavingThrowsTest extends TestCase {
		
	// black box
	public void test_getModifier_ArgNotInTable() {
		SavingThrow s = new SavingThrow(-1, 3);
		int modifier = s.getModifier("sample");
		assertEquals(0, modifier);
	}
	
	// black box
	public void test_addModifier_getModifier_ValidArgsInTable() {
		SavingThrow s = new SavingThrow(-1, 3);
		s.addModifier("sample", 10);
		int modifier = s.getModifier("sample");
		assertEquals(10, modifier);
	}
	
	// black box
	public void test_addModifier_NullNameArg() {
		try {
			SavingThrow s = new SavingThrow(-1, 3);
			s.addModifier(null, 10);
			fail("setBaseSave did not throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// test passes
		}
	}
	
	// black box
	public void test_addModifier_NoPrevValue() {
		SavingThrow s = new SavingThrow(-1, 3);
		int prev = s.addModifier("sample", 10);
		assertEquals(0, prev);
	}
	
	// black box
	public void test_addModifier_SomePrevValue() {
		SavingThrow s = new SavingThrow(-1, 3);
		s.addModifier("sample", 5);
		int prev = s.addModifier("sample", 10);
		assertEquals(5, prev);
	}
	
	// white box
	public void test_removeModifier_NullModifier() {
		// ensure remove does not crash with null -- permitted but dumb
		SavingThrow s = new SavingThrow(-1, 3);
		s.removeModifier(null);
	}
	
	// white box
	public void test_removeModifier_NonexistentModifier() {
		// ensure remove does not crash
		SavingThrow s = new SavingThrow(-1, 3);
		s.removeModifier("sample");
	}
	
	// white box
	public void test_removeModifier_ModifierExists() {
		SavingThrow s = new SavingThrow(-1, 3);
		s.addModifier("sample", 5);
		s.removeModifier("sample");
		int modifier = s.getModifier("sample");
		assertEquals(0, modifier);
	}
	
	/*
	 * Cannot unit test getTotal because it requires database access.
	 * 
	public void test_getTotal_NewSavingThrow() {
		SavingThrow s = new SavingThrow(-1, 3);
		int total = s.getTotal(new Ability(1, AbilityName.WISDOM, 5));
		assertEquals(5, total);
	}

	public void test_getTotal_MismatchAbility() {
		try {
			SavingThrow s = new SavingThrow(-1, 3);
			s.getTotal(new Ability(1, AbilityName.INTELLIGENCE, 5));
			fail("getTotal did not throw an exception");
		} catch (IllegalArgumentException e) {
			// test passes
		}
	}
	
	public void test_getTotal_NonZeroBaseSave() {
		SavingThrow s = new SavingThrow(-1, 3);
		s.setBaseSave(10);
		int total = s.getTotal(new Ability(1, AbilityName.WISDOM, 5));
		assertEquals(15, total);
	}
	
	public void test_getTotal_NonZeroModifiers() {
		SavingThrow s = new SavingThrow(-1, 3);
		s.addModifier("sample", 10);
		int total = s.getTotal(new Ability(1, AbilityName.WISDOM, 5));
		assertEquals(15, total);
	}
	*/
}
