package com.example.myfirstapp;

import java.util.*;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Ability is a class to represent a single ability on a character sheet 
 * which has a base value and temporary modifiers. This class handles both
 * the raw stat and the modifier from that stat.
 * 
 * @author Loki White (lokiw)
 */
public class Ability {
	private AbilityName name;
	private int base;
	private Map<String,Integer> tempModifiers;
	
	/**
	 * Initializes an ability with the given name and defaults the base stat to
	 * 10. The average stat for most pathfinder rolling systems is 10 because it 
	 * has no possitive or negative ability modifiers. This average changes
	 * with the fantasy level of a campaign but since the standard is no bonuses or
	 * detriments, this value was chosen.
	 * <p>
	 * No temporary modifiers are initialized.
	 * 
	 * @param name	the name of the ability being stored as the AbilityName enum,
	 * 				such as STRENGTH, DEXTERITY, ect...
	 */
	public Ability(AbilityName name){
		this.name = name;
		base = 10;
		tempModifiers = new HashMap<String,Integer>();
	}
	
	/**
	 * Initializes an ability with the given name and given default score.
	 * 
	 * @param name	the name of the ability being stored as the AbilityName enum,
	 * 				such as STRENGTH, DEXTERITY, ect...
	 * @param score	the value to initialize the base stat
	 * 
	 */
	public Ability(AbilityName name, int score){
		//TODO: Consider ability values < 0
		this.name = name;
		this.base = score;
	}
	
	/**
	 * Get the name of this ability
	 * 
	 * @return a AbilityName enum representing the name of the ability
	 */
	public AbilityName getName(){
		return name;
	}
	
	/**
	 * Get the current base stat for the ability, not including any temporary
	 * modifiers to the base stat
	 * 
	 * @return an int ability score with no temp modifiers
	 */
	public int getBase(){
		return base;
	}
	
	/**
	 * Add the given value to the base permanently 
	 * 
	 * @param modifier	Adds given number to the base stat
	 * @modifies base
	 */
	public void addToBase(int modifier){
		//TODO: Consider ability values < 0
		base += modifier;
	}
	
	/**
	 * Sets base stat to the given value permanently
	 * 
	 * @param newBase	an int whose value set over the old base stat
	 * @modifies this
	 */
	public void setBase(int newBase){
		//TODO: Consider ability values < 0
		base = newBase;
	}
	
	/**
	 * Returns the modifier under the given name. Can return both negative
	 * and positive modifiers. These modifiers represent values that will be
	 * either added or subtracted from the base stat.
	 * 
	 * @param tempName the name of the temporary modifier whose value is retrieved
	 * @return 	the value associated with the given String, may be either negative
	 * 			or positive. Returns 0 if no temporary modifier of the given name
	 * 			was found
	 */
	public int getTempModifier(String tempName){
		if (tempModifiers.containsKey(tempName)) {
			return tempModifiers.get(tempName);
		}
		
		return 0;
	}
	
	/**
	 * Removes the modifier under the given name as well as the record of that name.
	 * 
	 * @param tempName	the name of the temporary modifier to remove
	 * @modifies this
	 */
	public void removeTempModifier(String tempName){
		if (tempModifiers.containsKey(tempName)) {
			tempModifiers.remove(tempName);
		}
	}
	
	/**
	 * Adds a new Temporary Modifier with the given name and value
	 * 
	 * @param tempName	the name of the temporary modifier
	 * @param tempValue	the value of the modifier
	 * @modifies this
	 */
	public void addTempModifier(String tempName, int tempValue){
		//TODO: Consider already existing values
		tempModifiers.put(tempName, tempValue);
	}
	
	/**
	 * Gets the total ability score including the base stat value and all
	 * temporary modifiers currently stored.
	 * <p>
	 * The current ability score is the base score with additional temporary 
	 * modifiers. Modifiers are added or suptracted depending on their sign and
	 * they affect the score directly. Thus a base score of 10 with a +2 modifier 
	 * from an amulet will return a score of 12.
	 * 
	 * @return an int score representing the total ability score
	 */
	public int getScore(){
		//TODO: consider capping return at 0 instead of giving negative scores
		int score = base;
		Collection<Integer> temps = tempModifiers.values();
		Iterator<Integer> it = temps.iterator();
		while (it.hasNext()) {
			score += it.next();
		}
		return score;
	}
	
	/**
	 * Gets the modifier of the ability score with all base and temporary values
	 * considered.
	 * <p>
	 * The ability modifier is the information most used from a character's stats,
	 * and is calculated from their ability score. This score includes the base
	 * and all temporary modifiers. The modifier is always smaller in magnitude then
	 * the raw score as it is added to a dice roll and is generally calculated and 
	 * recorded. Every two ability scores has a single modifier associated with it.
	 * The ability score 10 and 11 have a modifier of 0, every even number thereafter
	 * adds one modifier, so 12 and 13 are 1, 14 and 15 are 2 and so on. Once a score
	 * is below 10, the modifiers are negative. A score of 8 and 9 have a -1 modifier,
	 * every two bellow that get another -1, so 7 and 6 have a -2 and so on.
	 * 
	 * @return an int representing the ability modifier 
	 * 
	 */
	public int getMod(){
		//TODO: Consider ability values < 0
		int mod;
		int score = getScore();
		if (score >= 10) {
			mod = (score - 10) / 2;
		} else {
			mod = (11 - score) / 2;
		}
		
		return mod;
	}
	/** 
	 * Writes Ability to database. SHOULD ONLY BE CALLED BY CHARACTER
	 * @param id id of character
	 * @param db database to write into (except temporary mods)
	 * @param dbTempMods database to write temporary mods into
	 */
	public void writeToDB(long id, SQLiteDatabase db, SQLiteDatabase dbTempMods) {
		// TODO implement
		int abilityID = 0; // get ability ID from ref db
		
		ContentValues values = new ContentValues();
		values.put(SQLiteHelperAbilityScores.COLUMN_CHAR_ID, id);
		values.put(SQLiteHelperAbilityScores.COLUMN_REF_AS_ID, abilityID);
		values.put(SQLiteHelperAbilityScores.COLUMN_SCORE, base);
		db.insert(SQLiteHelperAbilityScores.TABLE_NAME, null, values);
		
		for (String s : tempModifiers.keySet()) {
			int i = tempModifiers.get(s);
			// write to dbTempMods
			values = new ContentValues();
			values.put(SQLiteHelperASTempMods.COLUMN_CHAR_ID, id);
			values.put(SQLiteHelperASTempMods.COLUMN_REF_AS_ID, abilityID);
			values.put(SQLiteHelperASTempMods.COLUMN_NAME, s);
			values.put(SQLiteHelperASTempMods.COLUMN_MOD, i);		
			dbTempMods.insert(SQLiteHelperASTempMods.TABLE_NAME, null, values);
		}
	}
	
}
