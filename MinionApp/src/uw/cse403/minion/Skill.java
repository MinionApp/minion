package uw.cse403.minion;

import android.content.ContentValues;

/**
 * A representation of a single skill in Pathfinder, such as 
 * stealth, perception or Knowledge(Dance).
 * 
 * @author Loki White (lokiw)
 */
public class Skill {
	private static final int CLASS_BONUS = 3;

	public static final int ACROBATICS_ID 				= 1;
	public static final int APPRAISE_ID 				= 2;
	public static final int BLUFF_ID 					= 3;
	public static final int CLIMB_ID 					= 4;
	public static final int CRAFT1_ID 					= 5;
	public static final int CRAFT2_ID 					= 6;
	public static final int CRAFT3_ID 					= 7;
	public static final int DIPLOMACY_ID 				= 8;
	public static final int DISABLE_DEVICE_ID 			= 9;
	public static final int DISGUISE_ID 				= 10;
	public static final int ESCAPE_ARTIST_ID 			= 11;
	public static final int FLY_ID 						= 12;
	public static final int HANDLE_ANIMAL_ID 			= 13;
	public static final int HEAL_ID 					= 14;
	public static final int INTIMIDATE_ID 				= 15;
	public static final int KNOWLEDGE_ARCANA_ID 		= 16;
	public static final int KNOWLEDGE_DUNGEONEERING_ID 	= 17;
	public static final int KNOWLEDGE_ENGINEERING_ID 	= 18;
	public static final int KNOWLEDGE_GEOGRAPHY_ID 		= 19;
	public static final int KNOWLEDGE_HISTORY_ID 		= 20;
	public static final int KNOWLEDGE_LOCAL_ID 			= 21;
	public static final int KNOWLEDGE_NATURE_ID 		= 22;
	public static final int KNOWLEDGE_NOBILITY_ID 		= 23;
	public static final int KNOWLEDGE_PLANES_ID 		= 24;
	public static final int KNOWLEDGE_RELIGION_ID 		= 25;
	public static final int LINGUISTICS_ID 				= 26;
	public static final int PERCEPTION_ID 				= 27;
	public static final int PERFORM1_ID 				= 28;
	public static final int PERFORM2_ID 				= 29;
	public static final int PROFESSION1_ID 				= 30;
	public static final int PROFESSION2_ID 				= 31;
	public static final int RIDE_ID 					= 32;
	public static final int SENSE_MOTIVE_ID 			= 33;
	public static final int SLEIGHT_OF_HAND_ID 			= 34;
	public static final int SPELLCRAFT_ID 				= 35;
	public static final int STEALTH_ID 					= 36;
	public static final int SURVIVAL_ID 				= 37;
	public static final int SWIM_ID 					= 38;
	public static final int USE_MAGIC_DEVICE_ID 		= 39;

	public static final int NUM_SKILLS = USE_MAGIC_DEVICE_ID;
	

	/** Various components the make up the skill information about a character **/
	private long charID;
	public int skillID; // defined above
	public String title; // write-in names for Craft, Perform, Profession
	public int rank;
	public boolean classSkill; // not currently supported
	public int miscMod;
	public int abMod; // value of ability modifier, loaded and set by SkillsAll

	/**
	 * Initializes a new Skill object
	 * @param charID		ID of character
	 * @param skillID		ID of skill
	 */
	public Skill(long charID, int skillID) {
		this.charID = charID;
		this.skillID = skillID;
	}

	/**
	 * Returns the total bonus of the skill accounting for ranks, class bonus,
	 * and miscellaneous modifiers.
	 * @param mod	the Ability for the given skill. Throws IllegalArgumentException 
	 * 				exception if given ability not associated with that skill.
	 * @return an int total bonus for given skill
	 */
	public int getBonus(Ability mod){
		//Add rank and class modifier (if appropriate)
		int bonus = rank;
		if (classSkill && rank > 0) {
			bonus += CLASS_BONUS;
		}

		//Add associated ability modifier to bonus
		bonus += mod.getMod();

		//Add miscellaneous modifier(s) to bonus
		bonus += miscMod;

		return bonus;
	}

	/**
	 * Gets the skill total for this skill.
	 * @return the skill total
	 */
	public int getTotal() {
		//int mod = modifiers.get(modifiers.keySet().iterator().next());
		return rank + miscMod;
	}
	
	/**
	 * Returns whether skillID corresponds to a skill with a title (Spinner)
	 * @param skillID id of Skill, defined as class constants
	 * @return true if skill has a title, false otherwise
	 */
	public static boolean isTitledSkillID(int skillID) {
		return (skillID == CRAFT1_ID || skillID == CRAFT2_ID || skillID == CRAFT3_ID || 
				skillID == PERFORM1_ID || skillID == PERFORM2_ID ||
				skillID == PROFESSION1_ID || skillID == PROFESSION2_ID);
	}

	/** 
	 * Writes Skill to database. 
	 */
	public void writeToDB() {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelperSkills.COLUMN_CHAR_ID, charID);
		values.put(SQLiteHelperSkills.COLUMN_REF_S_ID, skillID);
		if (isTitledSkillID(skillID)) {
			values.put(SQLiteHelperSkills.COLUMN_TITLE, title);
		}
		values.put(SQLiteHelperSkills.COLUMN_RANKS, rank);
		values.put(SQLiteHelperSkills.COLUMN_MISC_MOD, miscMod);
		SQLiteHelperSkills.db.insert(SQLiteHelperSkills.TABLE_NAME, null, values);
	}
}
