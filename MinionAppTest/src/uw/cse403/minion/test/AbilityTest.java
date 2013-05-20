package uw.cse403.minion.test;

import uw.cse403.minion.Ability;
import uw.cse403.minion.AbilityName;
import junit.framework.TestCase;

public class AbilityTest extends TestCase {

	/*
	 * Constructor with two parameters
	 * Test Dependent on getBase() and getName()
	 */
	public void testAbilityLongAbilityName() {
		Ability a = new Ability(-1, AbilityName.STRENGTH);
		assertEquals(-1, a.getBase());
		assertEquals(AbilityName.STRENGTH,a.getName());
	}

	/*
	 * Constructor with three parameters
	 * Test Dependent on getBase() and getName()
	 */
	public void testAbilityLongAbilityNameInt() {
		int baseScore = 18;
		Ability a = new Ability(-1, AbilityName.STRENGTH,baseScore);
		assertEquals(baseScore, a.getBase());
		assertEquals(AbilityName.STRENGTH,a.getName());
	}

	/*
	 * Get Ability Name
	 * Test Dependent on two parameter constructor
	 */
	public void testGetName() {
		Ability s = new Ability(-1, AbilityName.STRENGTH);
		Ability d = new Ability(-1, AbilityName.DEXTERITY);
		Ability co = new Ability(-1, AbilityName.CONSTITUTION);
		Ability i = new Ability(-1, AbilityName.INTELLIGENCE);
		Ability w = new Ability(-1, AbilityName.WISDOM);
		Ability ch = new Ability(-1, AbilityName.CHARISMA);
		
		assertEquals(AbilityName.STRENGTH,s.getName());
		assertEquals(AbilityName.DEXTERITY,d.getName());
		assertEquals(AbilityName.CONSTITUTION,co.getName());
		assertEquals(AbilityName.INTELLIGENCE,i.getName());
		assertEquals(AbilityName.WISDOM,w.getName());
		assertEquals(AbilityName.CHARISMA,ch.getName());

	}

	/*
	 * Get Base Ability Score
	 * Test Dependent on three parameter constructor
	 */
	public void testGetBase() {
		int baseScoreNeg = -5;
		int baseScoreBig = 16;
		int baseScoreAve = 10;
		
		Ability neg = new Ability(-1, AbilityName.STRENGTH,baseScoreNeg);
		Ability big = new Ability(-1, AbilityName.STRENGTH,baseScoreBig);
		Ability ave = new Ability(-1, AbilityName.STRENGTH,baseScoreAve);

		assertEquals(baseScoreNeg,neg.getBase());
		assertEquals(baseScoreBig,big.getBase());
		assertEquals(baseScoreAve,ave.getBase());
	}

	/*
	 * Add to base score
	 * Test Depends on getBase() and three parameter constructor
	 */
	public void testAddToBase() {
		int baseScore = 10;
		Ability a = new Ability(-1, AbilityName.STRENGTH, baseScore);
		assertEquals(baseScore, a.getBase());
		
		int toAdd = 5;
		a.addToBase(toAdd);
		assertEquals(baseScore + toAdd, a.getBase());
		
		int toSub = -10;
		a.addToBase(toSub);
		assertEquals(baseScore + toAdd + toSub, a.getBase());
	}

	/*
	 * Set base score
	 * Depends on three parameter constructor and getBase()
	 */
	public void testSetBase() {
		int baseScore = 10;
		Ability a = new Ability(-1, AbilityName.STRENGTH, baseScore);
		assertEquals(baseScore, a.getBase());
		
		int newBase = 22;
		a.setBase(newBase);
		assertEquals(newBase, a.getBase());
		
		newBase = 3;
		a.setBase(newBase);
		assertEquals(newBase, a.getBase());
	}

	public void testGetTempModifier() {
		Ability a = new Ability(-1, AbilityName.STRENGTH, 10);
		int mod = a.getTempModifier("temp");
		assertEquals(0, mod);
		
		a.addTempModifier("temp", 2);
		mod = a.getTempModifier("temp");
		assertEquals(2, mod);
	}

	/*
	 * Add Temporary Modifier
	 * Dependent on three parameter constructor and geTempModifier()
	 */
	public void testAddTempModifier() {
		Ability a = new Ability(-1, AbilityName.STRENGTH, 10);
		a.addTempModifier("item", 2);
		a.addTempModifier("temp", 4);
		a.addTempModifier("poison", -3);
		a.addTempModifier("nill", 0);
		
		assertEquals(2,a.getTempModifier("item"));
		assertEquals(4,a.getTempModifier("temp"));
		assertEquals(-3,a.getTempModifier("poison"));
		assertEquals(0, a.getTempModifier("nill"));
	}
	
	public void testRemoveTempModifier() {
		Ability a = new Ability(-1, AbilityName.STRENGTH, 10);
		a.addTempModifier("item", 2);
		a.addTempModifier("temp", 4);
		a.addTempModifier("poison", -3);
		
		assertEquals(2,a.getTempModifier("item"));
		assertEquals(4,a.getTempModifier("temp"));
		assertEquals(-3,a.getTempModifier("poison"));
		
		a.removeTempModifier("temp");
		assertEquals(0, a.getTempModifier("temp"));
		
		a.removeTempModifier("item");
		a.removeTempModifier("poison");
		
		assertEquals(0, a.getTempModifier("item"));
		assertEquals(0, a.getTempModifier("poison"));
	}

	public void testGetScore() {
		Ability a = new Ability(-1, AbilityName.STRENGTH, 10);
		assertEquals(10, a.getScore());
		
		a.addTempModifier("rage", 4);
		assertEquals(14, a.getScore());
		
		a.addTempModifier("poison", -2);
		assertEquals(12, a.getScore());
	}

	public void testGetMod() {
		Ability one = new Ability(-1, AbilityName.STRENGTH, 1);
		Ability two = new Ability(-1, AbilityName.STRENGTH, 2);
		Ability five = new Ability(-1, AbilityName.STRENGTH, 5);
		Ability eight = new Ability(-1, AbilityName.STRENGTH, 8);
		Ability nine = new Ability(-1, AbilityName.STRENGTH, 9);
		Ability ten = new Ability(-1, AbilityName.STRENGTH, 10);
		Ability eleven = new Ability(-1, AbilityName.STRENGTH, 11);
		Ability twelve = new Ability(-1, AbilityName.STRENGTH, 12);
		Ability sixteen = new Ability(-1, AbilityName.STRENGTH, 16);
		Ability nineteen = new Ability(-1, AbilityName.STRENGTH, 19);
		Ability twenty = new Ability(-1, AbilityName.STRENGTH, 20);
		Ability fortyfour = new Ability(-1, AbilityName.STRENGTH, 44);
		Ability onehundred = new Ability(-1, AbilityName.STRENGTH, 100);
		
		assertEquals(-5, one.getMod());
		assertEquals(-4, two.getMod());
		assertEquals(-3, five.getMod());
		assertEquals(-1, eight.getMod());
		assertEquals(-1, nine.getMod());
		assertEquals(0, ten.getMod());
		assertEquals(0, eleven.getMod());
		assertEquals(1, twelve.getMod());
		assertEquals(3, sixteen.getMod());
		assertEquals(4, nineteen.getMod());
		assertEquals(5, twenty.getMod());
		assertEquals(17, fortyfour.getMod());
		assertEquals(45, onehundred.getMod());
	}

	public void testWriteToDB() {
		//fail("Not yet implemented");
		//TODO: Test database
	}

}
