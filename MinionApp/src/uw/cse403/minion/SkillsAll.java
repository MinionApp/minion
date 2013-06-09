package uw.cse403.minion;

import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;

public class SkillsAll {
	private static final int CLASS_BONUS = 3;

	public static final int ACROBATICS_ID 				= 1;
	public static final int APPRAISE_ID 				= 2;
	public static final int BLUFF_ID 					= 3;
	public static final int CLIMB_ID 					= 4;
	public static final int CRAFT_ID 					= 5;
	public static final int DIPLOMACY_ID 				= 6;
	public static final int DISABLE_DEVICE_ID 			= 7;
	public static final int DISGUISE_ID 				= 8;
	public static final int ESCAPE_ARTIST_ID 			= 9;
	public static final int FLY_ID 						= 10;
	public static final int HANDLE_ANIMAL_ID 			= 11;
	public static final int HEAL_ID 					= 12;
	public static final int INTIMIDATE_ID 				= 13;
	public static final int KNOWLEDGE_ARCANA_ID 		= 14;
	public static final int KNOWLEDGE_DUNGEONEERING_ID 	= 15;
	public static final int KNOWLEDGE_ENGINEERING_ID 	= 16;
	public static final int KNOWLEDGE_GEOGRAPHY_ID 		= 17;
	public static final int KNOWLEDGE_HISTORY_ID 		= 18;
	public static final int KNOWLEDGE_LOCAL_ID 			= 19;
	public static final int KNOWLEDGE_NATURE_ID 		= 20;
	public static final int KNOWLEDGE_NOBILITY_ID 		= 21;
	public static final int KNOWLEDGE_PLANES_ID 		= 22;
	public static final int KNOWLEDGE_RELIGION_ID 		= 23;
	public static final int LINGUISTICS_ID 				= 24;
	public static final int PERCEPTION_ID 				= 25;
	public static final int PERFORM_ID 					= 26;
	public static final int PROFESSION_ID 				= 27;
	public static final int RIDE_ID 					= 28;
	public static final int SENSE_MOTIVE_ID 			= 29;
	public static final int SLEIGHT_OF_HAND_ID 			= 30;
	public static final int SPELLCRAFT_ID 				= 31;
	public static final int STEALTH_ID 					= 32;
	public static final int SURVIVAL_ID 				= 33;
	public static final int SWIM_ID 					= 34;
	public static final int USE_MAGIC_DEVICE_ID 		= 35;
	
	private long charID;
	// map from skillID to Skill object EXCEPT Craft, Perform, Profession
	private Map<Integer, Skill> mostSkills;
	// map from skillID to Skill object ONLY Craft, Perform, Profession
	// keys in this map are defined thus: key = skillID * 10 + #, where # = 1 if craft1, etc.
	private Map<Integer, Skill> nameSkills;
	
	public SkillsAll(long charID){
		this.charID = charID;
		this.mostSkills = new HashMap<Integer, Skill>();
		this.nameSkills = new HashMap<Integer, Skill>();
		
		loadFromDB();
	}
	
	public Skill[] getSkill(int skillID) {
		//return mostSkills.get(skillID);
		Skill[] skills = new Skill[3];
		
		if (Skill.isTitledSkillID(skillID)) {
			skills[0] = nameSkills.get(skillID * 10 + 1);
			skills[1] = nameSkills.get(skillID * 10 + 2);
			
			if (skillID == Skill.CRAFT_ID) {
				skills[2] = nameSkills.get(skillID * 10 + 3);
			}
		} else {
			skills[0] = mostSkills.get(skillID);
		}
		return skills;
	}
	
	int crafts = 0; // number of Crafts loaded
	int performs = 0; // number of Performs loaded
	int professions = 0; // number of Professions loaded
	
	public void addSkill(Skill skill) {
		int skillID = skill.skillID;
		if (Skill.isTitledSkillID(skillID)) {
			switch(skillID) {
			case Skill.CRAFT_ID:
				crafts++;
				nameSkills.put(skillID * 10 + crafts, skill);
				break;
			case Skill.PERFORM_ID:
				performs++;
				nameSkills.put(skillID * 10 + performs, skill);
				break;
			case Skill.PROFESSION_ID:
				professions++;
				nameSkills.put(skillID * 10 + professions, skill);
				break;
			}
		} else {
			mostSkills.put(skillID, skill);
		}
	}
	
	public void clear() {
		mostSkills.clear();
		nameSkills.clear();
	}
	
	private void loadFromDB() {
		// get ability scores
		Ability[] abilities = new Ability[6];
		// these should auto load from DB
//		abilities[0] = new Ability(charID, AbilityName.STRENGTH);
//		abilities[1] = new Ability(charID, AbilityName.DEXTERITY);
//		abilities[2] = new Ability(charID, AbilityName.CONSTITUTION);
//		abilities[3] = new Ability(charID, AbilityName.INTELLIGENCE);
//		abilities[4] = new Ability(charID, AbilityName.WISDOM);
//		abilities[5] = new Ability(charID, AbilityName.CHARISMA);
		abilities[0] = new Ability(charID, Ability.STRENGTH_ID);
		abilities[1] = new Ability(charID, Ability.DEXTERITY_ID);
		abilities[2] = new Ability(charID, Ability.CONSTITUTION_ID);
		abilities[3] = new Ability(charID, Ability.INTELLIGENCE_ID);
		abilities[4] = new Ability(charID, Ability.WISDOM_ID);
		abilities[5] = new Ability(charID, Ability.CHARISMA_ID);
		
		// map from skillID to ability score modifier
		Map<Integer, Integer> skillToAbMod = new HashMap<Integer, Integer>();
		// get skillIDs and associated ability score IDs
		Cursor cursor = SQLiteHelperRefTables.db.query(SQLiteHelperRefTables.TABLE_REF_SKILLS, 
				null, null, null, null, null, null);
		// Columns: COLUMN_S_ID, COLUMN_S_NAME, COLUMN_S_REF_AS_ID
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				int abScoreID = cursor.getInt(2);
				skillToAbMod.put(cursor.getInt(0), abilities[abScoreID].getMod());
				cursor.moveToNext();
			}
		}
		cursor.close();
		
		// get skills from DB
		Cursor cursor2 = SQLiteHelperSkills.db.query(SQLiteHelperSkills.TABLE_NAME, SQLiteHelperSkills.ALL_COLUMNS, 
				SQLiteHelperSkills.COLUMN_CHAR_ID + " = " + charID, null, null, null, null);
		if (cursor2.moveToFirst()) {
			while (!cursor2.isAfterLast()) { 
				// Columns: COLUMN_CHAR_ID, COLUMN_REF_S_ID, COLUMN_TITLE, COLUMN_RANKS, COLUMN_MISC_MOD
				int skillID = cursor2.getInt(1);
				Skill skill = new Skill(charID, skillID);
				skill.title = cursor2.getString(2);
				skill.rank = cursor2.getInt(3);
				skill.miscMod = cursor2.getInt(4);
				skill.abMod = skillToAbMod.get(skillID);
				
				addSkill(skill);
			}
		}			
	}
	
	public void writeToDB() {
		for (int skillID : mostSkills.keySet()) {
			Skill skill = mostSkills.get(skillID);
			skill.writeToDB();
		}
		for (int skillID : nameSkills.keySet()) {
			Skill skill = nameSkills.get(skillID);
			skill.writeToDB();
		}
	}
}
