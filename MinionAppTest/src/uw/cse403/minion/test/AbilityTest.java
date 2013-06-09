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
		assertEquals(0, a.getBase());
	}


	/*
	 * Get Base Ability Score
	 * Test Dependent on constructor and setBase
	 */
	public void testGetBase() {
		int baseScoreNeg = 0;
		int baseScoreBig = 16;
		int baseScoreAve = 10;
		
		Ability ba = new Ability(-1, 0);
		
		ba.setBase(-3);
		assertEquals(baseScoreNeg,ba.getBase());
		
		ba.setBase(16);
		assertEquals(baseScoreBig,ba.getBase());
		
		ba.setBase(10);
		assertEquals(baseScoreAve,ba.getBase());
	}


	/*
	 * Set base score
	 * Depends on three parameter constructor and getBase()
	 */
	public void testSetBase() {
		int baseScore = 10;
		Ability a = new Ability(-1, 0);
		assertEquals(baseScore, a.getBase());
		
		int newBase = 22;
		a.setBase(newBase);
		assertEquals(newBase, a.getBase());
		
		newBase = 3;
		a.setBase(newBase);
		assertEquals(newBase, a.getBase());
	}

	

	public void testGetScore() {
		Ability a = new Ability(-1, 0);
		a.setBase(10);
		assertEquals(10, a.getTotal());
		
		a.tempMod = 4;
		assertEquals(14, a.getTotal());
		
		a.tempMod += -2;
		assertEquals(12, a.getTotal());
	}

	public void testGetMod() {
		Ability ab = new Ability(-1, 0);
		
		ab.setBase(1);
		assertEquals(-5, ab.getMod());
		ab.setBase(2);
		assertEquals(-4, ab.getMod());
		ab.setBase(5);
		assertEquals(-3, ab.getMod());
		ab.setBase(8);
		assertEquals(-1, ab.getMod());
		ab.setBase(9);
		assertEquals(-1, ab.getMod());
		ab.setBase(10);
		assertEquals(0, ab.getMod());
		ab.setBase(11);
		assertEquals(0, ab.getMod());
		ab.setBase(12);
		assertEquals(1, ab.getMod());
		ab.setBase(16);
		assertEquals(3, ab.getMod());
		ab.setBase(19);
		assertEquals(4, ab.getMod());
		ab.setBase(20);
		assertEquals(5, ab.getMod());
		ab.setBase(44);
		assertEquals(17, ab.getMod());
		ab.setBase(100);
		assertEquals(45, ab.getMod());
	}

}
