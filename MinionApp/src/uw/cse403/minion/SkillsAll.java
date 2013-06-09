package uw.cse403.minion;

import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;

public class SkillsAll {
	private long charID;
	/** Stores whether or not this combat information has been stored previously or not **/
	public boolean isNew; 
	
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
	
	public Skill getSkill(int skillID) {
		return getSkill(skillID, 0);
	}
	
	public Skill getSkill(int skillID, int num) {
		if (Skill.isTitledSkillID(skillID)) {
			return nameSkills.get(skillID * 10 + num);
		} else {
			return mostSkills.get(skillID);
		}
	}
	
//	int crafts = 0; // number of Crafts loaded
//	int performs = 0; // number of Performs loaded
//	int professions = 0; // number of Professions loaded
	
	public void addSkill(Skill skill) {
		addSkill(skill, 0);
	}
	
	public void addSkill(Skill skill, int num) {
		int skillID = skill.skillID;
		if (Skill.isTitledSkillID(skillID)) {
			nameSkills.put(skillID * 10 + num, skill);
		} else {
			mostSkills.put(skillID, skill);
		}
	}
	
	public void clear() {
		mostSkills.clear();
		nameSkills.clear();
	}
	
	private void loadFromDB() {
		isNew = true;
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
			isNew = false;
			while (!cursor2.isAfterLast()) { 
				// Columns: COLUMN_CHAR_ID, COLUMN_REF_S_ID, COLUMN_TITLE, COLUMN_RANKS, COLUMN_MISC_MOD
				int skillID = cursor2.getInt(1);
				Skill skill = new Skill(charID, skillID);
				skill.title = cursor2.getString(2);
				skill.rank = cursor2.getInt(3);
				skill.miscMod = cursor2.getInt(4);
				skill.abMod = skillToAbMod.get(skillID);
				
				addSkill(skill);
				cursor2.moveToNext();
			}
		}		
		cursor2.close();
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
