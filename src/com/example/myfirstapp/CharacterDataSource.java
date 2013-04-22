package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class CharacterDataSource {
	// Database fields
	private SQLiteDatabase database;
	private MinionSQLiteHelper dbHelper;
	private String[] allColumns = { MinionSQLiteHelper.COLUMN_ID,
	    MinionSQLiteHelper.COLUMN_NAME };

	public CharacterDataSource(Context context) {
	  dbHelper = new MinionSQLiteHelper(context);
	}

	public void open() throws SQLException {
	  database = dbHelper.getWritableDatabase();
	}

	public void close() {
	  dbHelper.close();
	}

	public Character createCharacter(String character) {
	  ContentValues values = new ContentValues();
	  values.put(MinionSQLiteHelper.COLUMN_NAME, character);
	  long insertId = database.insert(MinionSQLiteHelper.TABLE_CHARACTERS, null,
	      values);
	  Cursor cursor = database.query(MinionSQLiteHelper.TABLE_CHARACTERS,
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
	  database.delete(MinionSQLiteHelper.TABLE_CHARACTERS, MinionSQLiteHelper.COLUMN_ID
	      + " = " + id, null);
	}

	public List<Character> getAllCharacters() {
	  List<Character> characters = new ArrayList<Character>();
      Cursor cursor = database.query(MinionSQLiteHelper.TABLE_CHARACTERS,
	       allColumns, null, null, null, null, null);
      cursor.moveToFirst();
	  while (!cursor.isAfterLast()) {
	    Character character = cursorToCharacter(cursor);
	    characters.add(character);
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
