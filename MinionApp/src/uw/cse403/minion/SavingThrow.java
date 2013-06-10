package uw.cse403.minion;

import java.util.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Debug;

/**
 * A wrapper class around the saving throws of a character.
 * @author lokiw
 */
public class SavingThrow {
	public static final int FORTITUDE_ID = 1;
	public static final int REFLEX_ID = 2;
	public static final int WILL_ID = 3;

	/** Class constants for string representations **/
	public static final String MAGIC_MOD_STRING = "magic";
	public static final String MISC_MOD_STRING = "misc";
	public static final String TEMP_MOD_STRING = "temp";

	/** The unique id for a character **/
	public long charID;
	public int saveID;

	/** Used to determine whether to populate UI with stored values **/
	public boolean isNew;

	/** Various components the make up the saving throw information about a character **/
	public int baseSave;
	public Map<String,Integer> modifiers;
	public int abMod;

	/**
	 * Initialize a saving throw.
	 * @param charID	ID of character associated with object
	 * @param saveID	ID of specific saving throw where
	 * 							1 is Fortitude
	 * 							2 is Reflex
	 * 							3 is Will
	 */
	public SavingThrow(long charID, int saveID){
		baseSave = 0;
		modifiers = new HashMap<String,Integer>();
		this.charID = charID;
		this.saveID = saveID;
		
		if (TraceControl.TRACE)
			Debug.startMethodTracing("SavingThrow_database");
		
		if (charID >= 0) {
			loadFromDB();
		}
		
		if (TraceControl.TRACE)
			Debug.stopMethodTracing();
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

		Cursor cursor = SQLiteHelperSavingThrows.db.query(SQLiteHelperSavingThrows.TABLE_NAME, 
				SQLiteHelperSavingThrows.ALL_COLUMNS, SQLiteHelperSavingThrows.COLUMN_CHAR_ID 
				+ " = " + charID + " AND " + SQLiteHelperSavingThrows.COLUMN_REF_ST_ID
				+ " = " + saveID, null, null, null, null);

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
	public void writeToDB() {
		// remove old data
		SQLiteHelperSavingThrows.db.delete(SQLiteHelperSavingThrows.TABLE_NAME, 
				SQLiteHelperSavingThrows.COLUMN_CHAR_ID + " = " + charID + " AND " 
						+ SQLiteHelperSavingThrows.COLUMN_REF_ST_ID + " = " + saveID, null);
		// prepare new insert
		ContentValues values = new ContentValues();
		values.put(SQLiteHelperSavingThrows.COLUMN_CHAR_ID, charID);
		values.put(SQLiteHelperSavingThrows.COLUMN_REF_ST_ID, saveID);
		values.put(SQLiteHelperSavingThrows.COLUMN_BASE_SAVE, baseSave);
		values.put(SQLiteHelperSavingThrows.COLUMN_MAGIC_MOD, modifiers.get(MAGIC_MOD_STRING));
		values.put(SQLiteHelperSavingThrows.COLUMN_MISC_MOD, modifiers.get(MISC_MOD_STRING));
		values.put(SQLiteHelperSavingThrows.COLUMN_TEMP_MOD, modifiers.get(TEMP_MOD_STRING));

		SQLiteHelperSavingThrows.db.insert(SQLiteHelperSavingThrows.TABLE_NAME, null, values);
	}
}
