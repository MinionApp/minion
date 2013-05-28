package uw.cse403.minion.test;

import uw.cse403.minion.Ability;
import uw.cse403.minion.AbilityName;
import uw.cse403.minion.Skill;
import junit.framework.TestCase;

/**
 * Whitebox test of backend skill object
 * 
 * @author loki
 *
 */
public class SkillTest extends TestCase {

	/**
	 * Test two parameter constructor for fatal failure only
	 */
	public void testSkillStringAbilityName() {
		Skill acro = new Skill("Acrobatics", AbilityName.DEXTERITY);
		Skill climb = new Skill("Climb", AbilityName.STRENGTH);
		Skill jibberish = new Skill("#jSe820", AbilityName.CHARISMA);
		
		assertTrue(acro != null);
		assertTrue(climb != null);
		assertTrue(jibberish != null);
	}

	/**
	 * Test three parameter constructor for fatal failure only
	 */
	public void testSkillStringAbilityNameIntBoolean() {
		Skill acro = new Skill(-1, "Acrobatics", AbilityName.DEXTERITY, 1, true);
		Skill climb = new Skill(-1, "Climb", AbilityName.STRENGTH, 0, false);
		Skill jibberish = new Skill(-1, "#jSe820", AbilityName.CHARISMA, 4, false);
		
		assertTrue(acro != null);
		assertTrue(climb != null);
		assertTrue(jibberish != null);	
	}

	/**
	 * Test get Name
	 * Depends on two parameter constructor
	 */
	public void testGetName() {
		//Ensure equally spelled/mispelled throughout test
		String acroName = "Acrobatics";
		String climbName = "Climb";
		String jibberName = "#jSe820";
		
		Skill acro = new Skill(acroName, AbilityName.DEXTERITY);
		Skill climb = new Skill(climbName, AbilityName.STRENGTH);
		Skill jibberish = new Skill(jibberName, AbilityName.CHARISMA);
	
		assertEquals(acroName, acro.getName());
		assertEquals(climbName, climb.getName());
		assertEquals(jibberName, jibberish.getName());
	}

	/**
	 * Test get rank
	 * Depends on constructor
	 */
	public void testGetRank() {
		Skill acro = new Skill("Acrobatics", AbilityName.DEXTERITY);
		assertEquals(0, acro.getRank());
		
		Skill climb = new Skill(-1, "Climb", AbilityName.DEXTERITY, 1, true);
		assertEquals(1, climb.getRank());
	}
	
	/**
	 * Test add to skill rank
	 * Depends on constructor and getRank
	 */
	public void testAddToRank() {
		Skill acro = new Skill("Acrobatics", AbilityName.DEXTERITY);
		assertEquals(0, acro.getRank());
		
		acro.addToRank(1);
		assertEquals(1, acro.getRank());

		acro.addToRank(3);
		assertEquals(4, acro.getRank());
	}

	/**
	 * Test get modifiers to skill
	 * Depends on constructor
	 */
	public void testGetModifier() {
		Skill acro = new Skill("Acrobatics", AbilityName.DEXTERITY);
		assertEquals(0, acro.getModifier("racial"));
	}

	/**
	 * Test add a modifier to skill
	 * Depends on constructor and getModifier
	 */
	public void testAddModifier() {
		Skill acro = new Skill("Acrobatics", AbilityName.DEXTERITY);
		assertEquals(0, acro.getModifier("racial"));
	
		acro.addModifier("racial", 8);
		assertEquals(8, acro.getModifier("racial"));
		
		acro.addModifier("magic", 2);
		assertEquals(8, acro.getModifier("racial"));
		assertEquals(2, acro.getModifier("magic"));
	}
	
	/**
	 * Test remove a modifier to a skill
	 * Depends on constructor, getModifier and addModifier
	 */
	public void testRemoveModifier() {
		Skill acro = new Skill("Acrobatics", AbilityName.DEXTERITY);
		assertEquals(0, acro.getModifier("racial"));
		acro.removeModifier("racial");
		assertEquals(0, acro.getModifier("racial"));
	
		acro.addModifier("racial", 8);
		assertEquals(8, acro.getModifier("racial"));
		
		acro.removeModifier("racial");
		assertEquals(0, acro.getModifier("racial"));
	}

	/**
	 * Test get total skill bonus
	 * Depends on constructor, addModifier and Ability class
	 */
	public void testGetBonus() {
		Skill acro = new Skill(-1, "Acrobatics", AbilityName.DEXTERITY, 4, true);
		Skill climb = new Skill(-1, "Climb", AbilityName.STRENGTH, 0, true);

		Ability dex = new Ability(-1, AbilityName.DEXTERITY, 12);
		Ability str = new Ability(-1, AbilityName.STRENGTH, 8);
		
		assertEquals(8, acro.getBonus(dex));
		assertEquals(-1, climb.getBonus(str));
		
		acro.addModifier("racial", 4);
		acro.addModifier("flaw", -8);
		assertEquals(4, acro.getBonus(dex));
	}

	public void testWriteToDB() {
		//TODO: Test Database
	}

}
