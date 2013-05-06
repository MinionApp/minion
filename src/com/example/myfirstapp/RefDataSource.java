package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RefDataSource {
	// Database fields
		
	private SQLiteDatabase refTablesDB;
	private SQLiteHelperRefTables refTables;
	private String[] allColumns = { MinionSQLiteHelper.COLUMN_ID,
	    MinionSQLiteHelper.COLUMN_NAME };
	
	public RefDataSource(Context context) {
		refTables = new SQLiteHelperRefTables(context);
		
	}
	
	public void open() throws SQLException {
		refTablesDB = refTables.getWritableDatabase();
		try {
			String[] columns = { SQLiteHelperRefTables.COLUMN_S_NAME };
			refTablesDB.query(SQLiteHelperRefTables.TABLE_REF_SKILLS,
			       columns, null, null, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			// tables don't exist yet, create
			refTables.onCreate(refTablesDB);
		}
		
	}
	
	public void close() {
		refTables.close();
	}
	
	public Character createCharacter(String character) {
	  ContentValues values = new ContentValues();
	  values.put(MinionSQLiteHelper.COLUMN_NAME, character);
	  long insertId = refTablesDB.insert(MinionSQLiteHelper.TABLE_BASIC_INFO, null,
	      values);
	  Cursor cursor = refTablesDB.query(MinionSQLiteHelper.TABLE_BASIC_INFO,
	      allColumns, MinionSQLiteHelper.COLUMN_ID + " = " + insertId, null,
	      null, null, null);
	  cursor.moveToFirst();
	  Character newCharacter = cursorToCharacter(cursor);
	  cursor.close();
	  return newCharacter;
	}
	
	public void deleteCharacter(Character character) {
	  long id = character.getId();
	  System.out.println("Comment deleted with id: " + id);
	  refTablesDB.delete(MinionSQLiteHelper.TABLE_BASIC_INFO, MinionSQLiteHelper.COLUMN_ID
	      + " = " + id, null);
	}
	
	public List<Character> getAllCharacters() {
		System.out.println("RefDataSource getAllCharacters");
		List<Character> characters = new ArrayList<Character>();
		String[] columns = { SQLiteHelperRefTables.COLUMN_S_NAME };
		System.out.println(columns[0]);
		Cursor cursor = refTablesDB.query(SQLiteHelperRefTables.TABLE_REF_SKILLS,
		       columns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			System.out.println(cursor.getString(0));
			cursor.moveToNext();
	  }
	  // Make sure to close the cursor
	  cursor.close();
	  return characters;
	 }
	
	private Character cursorToCharacter(Cursor cursor) {
	  Character character = new Character();
	  character.setId(cursor.getLong(0));
	  character.setName(cursor.getString(1));
	  return character;
	}
}
