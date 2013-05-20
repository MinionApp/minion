
package uw.cse403.minion.test;

import uw.cse403.minion.Ability;
import uw.cse403.minion.AbilityName;
import uw.cse403.minion.Character;
import uw.cse403.minion.CharacterDescription;
import junit.framework.TestCase;

/**
 * @author loki
 *
 */
public class CharacterTest extends TestCase {

	/**
	 * Test method for {@link uw.cse403.minion.Character#Character()}.
	 */
	public void testCharacter() {
		Character c = new Character();
		assertTrue(c != null);
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#setDescriptions(uw.cse403.minion.CharacterDescription)}.
	 */
	public void testSetDescriptions() {
		CharacterDescription cd = new CharacterDescription(-1);
		Character c = new Character();
		c.setDescriptions(cd);
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#setAbilityScores(uw.cse403.minion.Ability[])}.
	 */
	public void testSetAbilityScores() {
		Ability str = new Ability(-1, AbilityName.STRENGTH);
		Ability dex = new Ability(-1, AbilityName.DEXTERITY);
		Ability con = new Ability(-1, AbilityName.CONSTITUTION);
		Ability intel = new Ability(-1, AbilityName.INTELLIGENCE);
		Ability wis = new Ability(-1, AbilityName.WISDOM);
		Ability cha = new Ability(-1, AbilityName.CHARISMA);
		
		Ability scores[] = {str, dex, con, intel, wis, cha};
		Character c = new Character();
		c.setAbilityScores(scores);
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#getId()}.
	 */
	public void testGetId() {
		Character one = new Character();
		assertTrue(one.getId() > 0);
		
		Character two = new Character();
		assertEquals(1, two.getId() - one.getId());
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#setId(long)}.
	 */
	public void testSetId() {
		Character c = new Character();
		c.setId(-1);
		assertEquals(-1, c.getId());
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#getName()}.
	 */
	public void testGetName() {
		CharacterDescription cd = new CharacterDescription(-1);
		cd.name = "Marty";
		Character c = new Character();
		c.setDescriptions(cd);
		assertEquals("Marty", c.getName());
	}


	/**
	 * Test method for {@link uw.cse403.minion.Character#setName(java.lang.String)}.
	 */
	public void testSetName() {
		CharacterDescription cd = new CharacterDescription(-1);
		Character c = new Character();
		c.setDescriptions(cd);
		c.setName("Marty");
		assertEquals("Marty", c.getName());
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#getCMD()}.
	 */
	public void testGetCMD() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#getCMB()}.
	 */
	public void testGetCMB() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#getWillSave()}.
	 */
	public void testGetWillSave() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#getFortitudeSave()}.
	 */
	public void testGetFortitudeSave() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#getReflexSave()}.
	 */
	public void testGetReflexSave() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#setSkillsToDefault()}.
	 */
	public void testSetSkillsToDefault() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#getTotalHitPoints()}.
	 */
	public void testGetTotalHitPoints() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#getCurrentHitPoints()}.
	 */
	public void testGetCurrentHitPoints() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#writeToDB(android.database.sqlite.SQLiteDatabase, android.database.sqlite.SQLiteDatabase, android.database.sqlite.SQLiteDatabase, android.database.sqlite.SQLiteDatabase, android.database.sqlite.SQLiteDatabase, android.database.sqlite.SQLiteDatabase, android.database.sqlite.SQLiteDatabase, android.database.sqlite.SQLiteDatabase)}.
	 */
	public void testWriteToDB() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link uw.cse403.minion.Character#toString()}.
	 */
	public void testToString() {
		fail("Not yet implemented");
	}
}
