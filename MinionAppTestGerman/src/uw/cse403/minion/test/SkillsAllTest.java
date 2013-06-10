package uw.cse403.minion.test;

import uw.cse403.minion.Skill;
import uw.cse403.minion.SkillsAll;
import junit.framework.TestCase;

/**
 * 
 * 
 * @author Loki White (lokiw)
 *
 */
public class SkillsAllTest extends TestCase {

	/*
	 * Verify constructor does not break
	 */
	public void testSkillsAll() {
		SkillsAll all = new SkillsAll(-1);
		assertTrue(all != null);
	}

	/*
	 * Test Get Skill
	 * Dependent on add skill
	 */
	public void testGetSkill() {
		SkillsAll all = new SkillsAll(-1);
		Skill acro = new Skill(-1, 1);
		
		all.addSkill(acro);
		Skill res = all.getSkill(1);
		assertEquals(1,res.skillID);
	}

	/*
	 * Test Add Skill
	 * Dependent on get skill
	 */
	public void testAddSkill() {
		SkillsAll all = new SkillsAll(-1);
		Skill acro = new Skill(-1, 1);
		acro.rank = 18;
		
		all.addSkill(acro);
		Skill res = all.getSkill(1);
		assertEquals(1,res.skillID);
		assertEquals(18,res.rank);
	}

	/*
	 * 
	 */
	public void testClear() {
		//fail("Not yet implemented");
	}

}
