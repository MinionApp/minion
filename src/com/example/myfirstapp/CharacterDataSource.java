package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Thomas Eberlein (uwte)
 * @author Kevin Dong (kevinxd3)
 *
 */

public class CharacterDataSource {
	// Database fields
	//private SQLiteDatabase database;
	//private MinionSQLiteHelper dbHelper;
	
	private SQLiteHelperRefTables helperRef;
	private SQLiteHelperBasicInfo helperBasicInfo;
	private SQLiteHelperAbilityScores helperAbilityScores;
	private SQLiteHelperASTempMods helperASTempMods;
	private SQLiteHelperSkills helperSkills;
	private SQLiteHelperCombat helperCombat;
	private SQLiteHelperArmor helperArmor;
	private SQLiteHelperSavingThrows helperSavingThrows;
	private SQLiteHelperWeapons helperWeapons;
	private SQLiteHelperInterface[] helpers = {helperBasicInfo, helperAbilityScores, 
		helperASTempMods, helperSkills, helperCombat, helperArmor, 
		helperSavingThrows, helperWeapons};
	
	private SQLiteDatabase dbRef;
	private SQLiteDatabase dbBasicInfo;
	private SQLiteDatabase dbAbilityScores;
	private SQLiteDatabase dbASTempMods;
	private SQLiteDatabase dbSkills;
	private SQLiteDatabase dbCombat;
	private SQLiteDatabase dbArmor;
	private SQLiteDatabase dbSavingThrows;
	private SQLiteDatabase dbWeapons;
	
	//SQLiteDatabase[] data = {dbBasicInfo, dbAbilityScores, dbASTempMods, 
	//		dbSkills, dbCombat, dbArmor, dbSavingThrows, dbWeapons};
	

	public CharacterDataSource(Context context) {
		//dbHelper = new MinionSQLiteHelper(context);

		helperRef			= new SQLiteHelperRefTables(context);
		helperBasicInfo 	= new SQLiteHelperBasicInfo(context);
		helperAbilityScores = new SQLiteHelperAbilityScores(context);
		helperASTempMods 	= new SQLiteHelperASTempMods(context);
		helperSkills 		= new SQLiteHelperSkills(context);
		helperCombat 		= new SQLiteHelperCombat(context);
		helperArmor 		= new SQLiteHelperArmor(context);
		helperSavingThrows 	= new SQLiteHelperSavingThrows(context);
		helperWeapons 		= new SQLiteHelperWeapons(context);

	}

	public void open() throws SQLException {
		//database = dbHelper.getWritableDatabase();
		
		dbRef = helperRef.getReadableDatabase();
		
		// test ref databases; create them if they don't exist
		try {
			String[] columns = { SQLiteHelperRefTables.COLUMN_AS_ID };
			dbRef.query(SQLiteHelperRefTables.TABLE_REF_SKILLS,
			       columns, null, null, null, null, null);
		} catch (Exception e) { // ref tables doesn't exist yet, create
			helperRef.onCreate(dbRef);
		}
		
		for (int i = 0; i < helpers.length; i++) {			
			// test database; create if it doesn't exist
			SQLiteHelperInterface h = helpers[i];
			try {
				
				System.out.println("testing table");
				System.out.println(h.getTableName());
				String[] columns = h.getColumns();
				//String[] columns = { helpers[i].getColumns()[0] };
				for (int j = 0; j < columns.length; j ++) {
					System.out.println(columns[j]);
				}
				h.db.query(h.getTableName(),
				       columns, null, null, null, null, null);
			} catch (Exception e) { // table doesn't exist yet, create
				System.out.println(e.getCause());
				e.printStackTrace();
				
				System.out.println("no table found, creating...");
;				((SQLiteOpenHelper) h).onCreate(h.getDB());
			}
		}
		
		
	}

	public void close() {
		//dbHelper.close();

		helperBasicInfo.close();
		helperAbilityScores.close();
		helperASTempMods.close();
		helperSkills.close();
		helperCombat.close();
		helperArmor.close();
		helperSavingThrows.close();
		helperWeapons.close();
	}

//	public Character createCharacter(String character) {
//		ContentValues values = new ContentValues();
//		values.put(MinionSQLiteHelper.COLUMN_NAME, character);
//		long insertId = database.insert(MinionSQLiteHelper.TABLE_BASIC_INFO, null, values);
//		Cursor cursor = database.query(MinionSQLiteHelper.TABLE_BASIC_INFO,
//			allColumns, MinionSQLiteHelper.COLUMN_ID + " = " + insertId, null,
//			null, null, null);
//		cursor.moveToFirst();
//		Character newCharacter = cursorToCharacter(cursor);
//		cursor.close();
//		return newCharacter;
//	}
	
	/**
	 * Adds character to local SQLite database. Each component of the Character knows 
	 * how to write itself to the database.
	 * @param character character to add to database
	 */
	public void addCharacter(Character character) {
		character.writeToDB(dbBasicInfo, dbAbilityScores, dbASTempMods, dbSkills, 
			dbCombat, dbArmor, dbSavingThrows, dbWeapons);
		character.writeToDB(helperBasicInfo.getDB(), helperAbilityScores.getDB(), helperASTempMods.getDB(), helperSkills.getDB(), 
			helperCombat.getDB(), helperArmor.getDB(), helperSavingThrows.getDB(), helperWeapons.getDB());
	}
	
	/**
	 * Remove character from local database.
	 * @param character character to remove
	 */
	public void deleteCharacter(Character character) {
		long id = character.getId();
		System.out.println("Comment deleted with id: " + id);
		//database.delete(MinionSQLiteHelper.TABLE_BASIC_INFO, MinionSQLiteHelper.COLUMN_ID
		//	+ " = " + id, null);
	}

// NOTE: these methods won't be used, but are being kept for reference until actual add/delete methods are implemented
//	public void deleteCharacter(Character character) {
//		long id = character.getId();
//		System.out.println("Comment deleted with id: " + id);
//		database.delete(MinionSQLiteHelper.TABLE_BASIC_INFO, MinionSQLiteHelper.COLUMN_ID
//			+ " = " + id, null);
//	}

//	public List<Character> getAllCharacters() {
//		List<Character> characters = new ArrayList<Character>();
//		Cursor cursor = database.query(MinionSQLiteHelper.TABLE_BASIC_INFO,
//			allColumns, null, null, null, null, null);
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//			Character character = cursorToCharacter(cursor);
//			characters.add(character);
//			cursor.moveToNext();
//	 	}
//		// Make sure to close the cursor
//		cursor.close();
//		return characters;
//	}
	
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
		Character character = new Character();
		character.setId(cursor.getLong(0));
		character.setName(cursor.getString(1));
		return character;
	}
	
	public void printTables() {
		helperRef.printContents(dbRef);
	}
}
