package uw.cse403.minion;

import java.util.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;

/**
 * A wrapper class around the saving throws of a character.
 * @author lokiw
 */
public class SavingThrow {

	/** Class constants for string representations **/
	public static final String MAGIC_MOD_STRING = "magic";
	public static final String MISC_MOD_STRING = "misc";
	public static final String TEMP_MOD_STRING = "temp";

	/** The unique id for a character **/
	public long charID;

	/** Used to determine whether to populate UI with stored values **/
	public boolean isNew;

	/** Various components the make up the saving throw information about a character **/
	private AbilityName assocAbility;
	private int baseSave;
	private Map<String,Integer> modifiers;
	public int abMod;

	/**
	 * Initialize a saving throw.
	 * @param attribute	the associated attribute for the saving throw,
	 * 			throws IllegalArgumentException if not WISDOM, DEXTERITY or
	 * 			CONSTITUTION
	 */
	public SavingThrow(AbilityName attribute){
		if (attribute != AbilityName.WISDOM && attribute != AbilityName.DEXTERITY
				&& attribute != AbilityName.CONSTITUTION) {
			throw new IllegalArgumentException();
		}
		assocAbility = attribute;
		baseSave = 0;
		modifiers = new HashMap<String,Integer>();
		
		if (TraceControl.TRACE)
			Debug.startMethodTracing("SavingThrow_database");
		
		loadFromDB();
		
		if (TraceControl.TRACE)
			Debug.stopMethodTracing();
	}

	/**
	 * Return base save
	 * @return int base save
	 */
	public int getBaseSave(){
		return baseSave;
	}

	/**
	 * Set base save to given value
	 * @param save	new base save
	 */
	public void setBaseSave(int save){
		if (save < 0) {
			throw new IllegalArgumentException();
		}
		baseSave = save;
	}

	/**
	 * Returns the modifier under the given name. Can return both negative
	 * and positive modifiers. These modifiers represent values that will be
	 * either added or subtracted from the skill.
	 * @param name the name of the modifier whose value is retrieved
	 * @return 	the value associated with the given String, may be either negative
	 * 			or positive. Returns 0 if no modifier of the given name
	 * 			was found
	 */
	public int getModifier(String name){
		Integer retVal = modifiers.get(name);
		if (retVal == null) {
			return 0;
		}
		return retVal;
	}

	/**
	 * Removes the modifier under the given name as well as the record of that name.
	 * @param name	the name of the modifier to remove
	 * @modifies this
	 */
	public void removeModifier(String name){
		// remove checks if modifiers contains name
		modifiers.remove(name);
	}

	/**
	 * Adds a new modifier with the given name and value
	 * @param name	the name of the modifier
	 * @param value	the value of the modifier
	 * @throws IllegalArgumentException, if name is null or value equals zero
	 * @modifies this
	 * @return the previous modifier associated with name
	 * 		   or 0 if no modifier was associated
	 */
	public int addModifier(String name, int value){
		if (name == null) {
			throw new IllegalArgumentException("no null modifier names allowed");
		} else if (value == 0) {
			// We don't want the program to break. Let's try just not saving it
			// throw new IllegalArgumentException("no zero modifiers allowed");
		}
		if (value != 0) {
			Integer prev = modifiers.put(name, value);
			if (prev == null) {
				return 0;
			}
			return prev;
		}
		return 1;
	}

	/**
	 * Returns the total save for the saving throw.
	 * @return The total save for the saving throw
	 */
	public int getTotal() {
		int total = baseSave + abMod;
		Collection<Integer> mods = modifiers.values();
		Iterator<Integer> it = mods.iterator();
		while (it.hasNext()) {
			total += it.next();
		}
		return total;
	}

	/**
	 * Populate fields with values from DB
	 */
	private void loadFromDB() {
		isNew = true;
		// attempt to load from DB
		int stID = 0;
		if (assocAbility == AbilityName.CONSTITUTION)
			stID = 1;
		if (assocAbility == AbilityName.DEXTERITY)
			stID = 2;
		if (assocAbility == AbilityName.WISDOM)
			stID = 3;

		Cursor cursor = SQLiteHelperSavingThrows.db.query(SQLiteHelperSavingThrows.TABLE_NAME, 
				SQLiteHelperSavingThrows.ALL_COLUMNS, SQLiteHelperSavingThrows.COLUMN_CHAR_ID 
				+ " = " + charID + " AND " + SQLiteHelperSavingThrows.COLUMN_REF_ST_ID
				+ " = " + stID, null, null, null, null);

		if (cursor.moveToFirst()) {
			isNew = false;
			// Columns: COLUMN_CHAR_ID, COLUMN_REF_ST_ID, COLUMN_BASE_SAVE, COLUMN_MAGIC_MOD,
			// COLUMN_MISC_MOD, COLUMN_TEMP_MOD
			baseSave = cursor.getInt(2);
			modifiers.put(MAGIC_MOD_STRING, cursor.getInt(3));
			modifiers.put(MISC_MOD_STRING, cursor.getInt(4));
			modifiers.put(TEMP_MOD_STRING, cursor.getInt(5));
		}
		cursor.close();
	}

	/** 
	 * Writes Saving Throw to database. SHOULD ONLY BE CALLED BY CHARACTER
	 * @param id id of character
	 * @param db database to write into
	 */
	public void writeToDB(long charID) {
		int stID = 0; //saving throw ID 
		if (assocAbility == AbilityName.CONSTITUTION)
			stID = 1;
		if (assocAbility == AbilityName.DEXTERITY)
			stID = 2;
		if (assocAbility == AbilityName.WISDOM)
			stID = 3;
		// remove old data
		SQLiteHelperSavingThrows.db.delete(SQLiteHelperSavingThrows.TABLE_NAME, 
				SQLiteHelperSavingThrows.COLUMN_CHAR_ID + " = " + charID + " AND " 
						+ SQLiteHelperSavingThrows.COLUMN_REF_ST_ID + " = " + stID, null);
		// prepare new insert
		ContentValues values = new ContentValues();
		values.put(SQLiteHelperSavingThrows.COLUMN_CHAR_ID, charID);
		values.put(SQLiteHelperSavingThrows.COLUMN_REF_ST_ID, stID);
		values.put(SQLiteHelperSavingThrows.COLUMN_BASE_SAVE, baseSave);
		values.put(SQLiteHelperSavingThrows.COLUMN_MAGIC_MOD, modifiers.get(MAGIC_MOD_STRING));
		values.put(SQLiteHelperSavingThrows.COLUMN_MISC_MOD, modifiers.get(MISC_MOD_STRING));
		values.put(SQLiteHelperSavingThrows.COLUMN_TEMP_MOD, modifiers.get(TEMP_MOD_STRING));

		SQLiteHelperSavingThrows.db.insert(SQLiteHelperSavingThrows.TABLE_NAME, null, values);
	}
}
