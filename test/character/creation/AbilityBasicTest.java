package character.creation;

import junit.framework.TestCase;
import com.example.myfirstapp.Ability;

public class AbilityBasicTest extends TestCase {
	/*
	 * To Test:
		Ability(String name);
		Ability(String name, int score);
		getName()
		getBase();
		getTempModifier(String tempName);
		removeTempModifier(String tempName);
		getScore();
		getMod();
	*/	
	
	/*
	 * 
	 */
	public void testAbility(){
		Ability str = new Ability("Strength");
		Ability dex = new Ability("Dexterity", 13);
		
		assertEquals(10,str.getBase());
		assertEquals(13,dex.getBase());
	}

}
