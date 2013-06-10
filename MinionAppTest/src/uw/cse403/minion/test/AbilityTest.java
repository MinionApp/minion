package uw.cse403.minion.test;

import uw.cse403.minion.Ability;
import junit.framework.TestCase;

/**
 * Whitebox test of backend Ability Object
 * 
 * @author loki
 *
 */
public class AbilityTest extends TestCase {

	/*
	 * Constructor with two parameters
	 * Test Dependent on getBase()
	 */
	public void testAbilityConstructor() {
		Ability a = new Ability(-1, 1);
		assertEquals(0, a.baseScore);
	}

	public void testGetScore() {
		Ability a = new Ability(-1, 0);
		a.baseScore = 10;
		assertEquals(10, a.getTotal());
		
		a.tempMod = 4;
		assertEquals(14, a.getTotal());
		
		a.tempMod += -2;
		assertEquals(12, a.getTotal());
	}

	public void testGetMod() {
		Ability ab = new Ability(-1, 0);
		
		ab.baseScore = 1;
		assertEquals(-5, ab.getMod());
		ab.baseScore = 2;
		assertEquals(-4, ab.getMod());
		ab.baseScore = 5;
		assertEquals(-3, ab.getMod());
		ab.baseScore = 8;
		assertEquals(-1, ab.getMod());
		ab.baseScore = 9;
		assertEquals(-1, ab.getMod());
		ab.baseScore = 10;
		assertEquals(0, ab.getMod());
		ab.baseScore = 11;
		assertEquals(0, ab.getMod());
		ab.baseScore = 12;
		assertEquals(1, ab.getMod());
		ab.baseScore = 16;
		assertEquals(3, ab.getMod());
		ab.baseScore = 19;
		assertEquals(4, ab.getMod());
		ab.baseScore = 20;
		assertEquals(5, ab.getMod());
		ab.baseScore = 44;
		assertEquals(17, ab.getMod());
		ab.baseScore = 100;
		assertEquals(45, ab.getMod());
	}

}
