package uw.cse403.minion;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;

/**
 * 
 * @author Thomas Eberlein (uwte)
 * @author Kevin Dong (kevinxd3)
 */
public class CharacterDataSource {

	/** Database helper fields **/
	private SQLiteHelperRefTables helperRef;
	private static SQLiteHelperBasicInfo helperBasicInfo;
	private SQLiteHelperAbilityScores helperAbilityScores;
	private SQLiteHelperASTempMods helperASTempMods;
	private SQLiteHelperSkills helperSkills;
	private SQLiteHelperCombat helperCombat;
	private SQLiteHelperArmor helperArmor;
	private SQLiteHelperSavingThrows helperSavingThrows;
	private SQLiteHelperWeapons helperWeapons;
	// array of all SQLiteHelpers, EXCEPT dbRef (it's special)
	private SQLiteHelperInterface[] helpers; 

	private SQLiteDatabase dbRef;

	/*
	 * Testing Results:
	 * This class deals heavily with SQLite, and hence many of the expensive operations occur in the
	 * SQL caller. As before, these are unavoidable operations, and there is no sense in
	 * reimplementing SQLite.
	 */
	/**
	 * Creates a CharacterDataSource object that encapsulates all of the different
	 * components and helper classes that make up a character data entry in the
	 * local database.
	 * @param context The given Activity's context
	 */
	public CharacterDataSource(Context context) {
		if (TraceControl.TRACE)
			Debug.startMethodTracing("CharacterDataSource_constructor");
		
		helperRef			= new SQLiteHelperRefTables(context);

		helperBasicInfo 	= new SQLiteHelperBasicInfo(context);
		helperAbilityScores = new SQLiteHelperAbilityScores(context);
		helperASTempMods 	= new SQLiteHelperASTempMods(context);
		helperSkills 		= new SQLiteHelperSkills(context);
		helperCombat 		= new SQLiteHelperCombat(context);
		helperArmor 		= new SQLiteHelperArmor(context);
		helperSavingThrows 	= new SQLiteHelperSavingThrows(context);
		helperWeapons 		= new SQLiteHelperWeapons(context);
		
		helpers = new SQLiteHelperInterface[8];

		helpers[0] = helperBasicInfo;
		helpers[1] = helperAbilityScores;
		helpers[2] = helperASTempMods;
		helpers[3] = helperSkills;
		helpers[4] = helperCombat;
		helpers[5] = helperArmor;
		helpers[6] = helperSavingThrows;
		helpers[7] = helperWeapons;

		if (TraceControl.TRACE)
			Debug.stopMethodTracing();
	}

	/**
	 * Opens the connection to the local database.
	 */
	public void open() throws SQLException {
		dbRef = helperRef.getReadableDatabase();

		// test ref databases; create them if they don't exist
		try {
			String[] columns = { SQLiteHelperRefTables.COLUMN_AS_ID };
			// attempt to query table to see if it exists
			dbRef.query(SQLiteHelperRefTables.TABLE_REF_SKILLS,
					columns, null, null, null, null, null);
		} catch (Exception e) { // ref tables don't exist yet, create
			helperRef.onCreate(dbRef);
		}
		System.out.println("TESTING TABLES");
		for (int i = 0; i < helpers.length; i++) {			
			// test database; create if it doesn't exist
			SQLiteHelperInterface helper = helpers[i];
			try {
				System.out.println("testing table");
				System.out.println(helper.getTableName());
				String[] columns = helper.getColumns();
				for (int j = 0; j < columns.length; j ++) {
					System.out.println(columns[j]);
				}
				// attempt to query table to see if it exists
				helper.getDB().query(helper.getTableName(),
						columns, null, null, null, null, null);
			} catch (Exception e) { // table doesn't exist yet, create
				System.out.println("no table found, creating...");
				((SQLiteOpenHelper) helper).onCreate(helper.getDB());
			}
		}
	}

	/**
	 * Closes the connection to the local database.
	 */
	public void close() {
		helperBasicInfo.close();
		helperAbilityScores.close();
		helperASTempMods.close();
		helperSkills.close();
		helperCombat.close();
		helperArmor.close();
		helperSavingThrows.close();
		helperWeapons.close();
	}

	/**
	 * Gets a list of all characters in local database
	 * @return list of all characters
	 */
	public List<Character> getAllCharacters() {
		List<Character> characters = new ArrayList<Character>();
		// TODO actually get the characters
		return characters;
	}

	// helper method for building Character from current cursor
	private Character cursorToCharacter(Cursor cursor) {
		//Character character = new Character();
		//character.setId(cursor.getLong(0));
		//character.setName(cursor.getString(1));
		//return character;
		return null;
	}

	public void printTables() {
		helperRef.printContents();
		helperBasicInfo.printContents(helperBasicInfo.db);
	}

	/**
	 * Returns a new character ID#, not yet used
	 * @return an unused ID# of type long
	 */
	public static long getNewID() {
		List<Long> list = new ArrayList<Long>();
		String[] columns = { SQLiteHelperBasicInfo.COLUMN_ID };
		// get all used IDs
		Cursor cursor = SQLiteHelperBasicInfo.db.query(SQLiteHelperBasicInfo.TABLE_NAME, columns, null, null, null, null, null);
		// make a list of the used IDs
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			long id = cursor.getLong(0);
			list.add(id);
			cursor.moveToNext();
		}
		// find an unused ID
		long id = 0;
		while (list.contains(id)) {
			id++;
		}
		return id;
	}
	
	/**
	 * Deletes a character from local database with charID
	 * @param charID id of character to be deleted
	 */
	public static void deleteCharacter(long charID) {
		SQLiteHelperBasicInfo.db.delete(SQLiteHelperBasicInfo.TABLE_NAME, 
				SQLiteHelperBasicInfo.COLUMN_ID + " = " + charID, null);
		SQLiteHelperAbilityScores.db.delete(SQLiteHelperAbilityScores.TABLE_NAME, 
				SQLiteHelperAbilityScores.COLUMN_CHAR_ID + " = " + charID, null);
		SQLiteHelperSkills.db.delete(SQLiteHelperSkills.TABLE_NAME, 
				SQLiteHelperSkills.COLUMN_CHAR_ID + " = " + charID, null);
		SQLiteHelperCombat.db.delete(SQLiteHelperCombat.TABLE_NAME, 
				SQLiteHelperCombat.COLUMN_CHAR_ID + " = " + charID, null);
		SQLiteHelperSavingThrows.db.delete(SQLiteHelperSavingThrows.TABLE_NAME, 
				SQLiteHelperSavingThrows.COLUMN_CHAR_ID + " = " + charID, null);
	}
	
	/**
	 * Deletes all characters from database
	 */
	public static void deleteAllCharacters() {
		SQLiteHelperBasicInfo.db.delete(SQLiteHelperBasicInfo.TABLE_NAME, null, null);
		SQLiteHelperAbilityScores.db.delete(SQLiteHelperAbilityScores.TABLE_NAME, null, null);
		SQLiteHelperSkills.db.delete(SQLiteHelperSkills.TABLE_NAME, null, null);
		SQLiteHelperCombat.db.delete(SQLiteHelperCombat.TABLE_NAME, null, null);
		SQLiteHelperSavingThrows.db.delete(SQLiteHelperSavingThrows.TABLE_NAME, null, null);
	}
}
