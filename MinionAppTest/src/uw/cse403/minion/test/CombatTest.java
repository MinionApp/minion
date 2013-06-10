package uw.cse403.minion.test;

import uw.cse403.minion.Combat;
import junit.framework.TestCase;

/**
 * Whitebox test of backend Combat object
 * 
 * @author loki
 *
 */
public class CombatTest extends TestCase {

	/**
	 * Test getting a single armor modifier
	 * Depends on constructor
	 */
	public void testGetArmorModifier() {
		Combat c = new Combat(-1);
		assertEquals(0, c.getArmorModifier("sheild"));
	}

	/**
	 * Test setting a single armor modifier
	 * Depends on constructor and getArmorModifier
	 */
	public void testAddArmorModifier() {
		Combat c = new Combat(-1);
		assertEquals(0, c.getArmorModifier("sheild"));
	
		c.addArmorModifier("sheild", 2);
		assertEquals(2, c.getArmorModifier("sheild"));
	}

	/**
	 * Test get base speed
	 * Depends on constructor
	 */
	public void testGetSpeed() {
		Combat c = new Combat(-1);
		assertEquals(0, c.getSpeed());
	}

	/**
	 * Test set base speed
	 * Depends on constructor and getSpeed
	 */
	public void testSetSpeed() {
		Combat c = new Combat(-1);
		assertEquals(0, c.getSpeed());
	
		c.setSpeed(30,10);
		assertEquals(20, c.getSpeed());
	}

	/**
	 * Test get total armor class
	 * Depends on constructor, addArmorModifiers
	 */
	public void testGetArmorTotal() {
		//TODO: Consider Dexterity
		Combat c = new Combat(-1);
		assertEquals(10, c.getArmorTotal());
		
		c.addArmorModifier("armor", 4);
		assertEquals(14, c.getArmorTotal());

		c.addArmorModifier("size", -1);
		assertEquals(13, c.getArmorTotal());
	}

}
