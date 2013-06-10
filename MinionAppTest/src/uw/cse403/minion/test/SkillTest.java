package uw.cse403.minion.test;

import uw.cse403.minion.Ability;
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
	public void testSkillConstructor() {
		Skill acro = new Skill(-1, 1);
		Skill climb = new Skill(-1, 4);
		
		assertTrue(acro != null);
		assertTrue(climb != null);
	}

	/**
	 * Test get total skill bonus
	 * Depends on constructor, addModifier and Ability class
	 */
	public void testGetBonus() {
		Skill acro = new Skill(-1, 1);
		Skill climb = new Skill(-1, 4);
		
		acro.rank += 4;

		Ability dex = new Ability(-1, 1);
		dex.baseScore = 12;
		Ability str = new Ability(-1, 0);
		str.baseScore = 8;
		
		acro.classSkill = true;
		
		assertEquals(8, acro.getBonus(dex));
		assertEquals(-1, climb.getBonus(str));
		
		acro.miscMod += 4;
		acro.miscMod += -8;
		assertEquals(4, acro.getBonus(dex));
	}

	public void testWriteToDB() {
		//TODO: Test Database
	}

}
