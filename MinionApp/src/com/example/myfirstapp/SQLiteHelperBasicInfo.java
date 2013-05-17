package com.example.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * 
 * @author Kevin Dong (kevinxd3)
 *
 */
public class SQLiteHelperBasicInfo extends SQLiteOpenHelper implements SQLiteHelperInterface {
	private static final String DATABASE_NAME = "characters.db";
	private static final int DATABASE_VERSION = 1;
	 static SQLiteDatabase db;
	
	// columns
	public static final String TABLE_NAME		= "basic_info";
	public static final String COLUMN_ID 		= "_id";
	public static final String COLUMN_NAME 		= "name";
	public static final String COLUMN_ALIGNMENT = "alignment";
	public static final String COLUMN_LEVEL 	= "level";
	public static final String COLUMN_DEITY 	= "diety";
	public static final String COLUMN_HOMELAND 	= "homeland";
	public static final String COLUMN_RACE 		= "race";
	public static final String COLUMN_SIZE 		= "size";
	public static final String COLUMN_GENDER 	= "gender";
	public static final String COLUMN_AGE 		= "age";
	public static final String COLUMN_HEIGHT 	= "height";
	public static final String COLUMN_WEIGHT 	= "weight";
	public static final String COLUMN_HAIR 		= "hair";
	public static final String COLUMN_EYES 		= "eyes";
	public static final String[] ALL_COLUMNS = 
		{ COLUMN_ID, COLUMN_NAME, COLUMN_ALIGNMENT, COLUMN_LEVEL, COLUMN_DEITY, 
			COLUMN_HOMELAND, COLUMN_RACE, COLUMN_SIZE, COLUMN_GENDER, COLUMN_AGE, 
			COLUMN_HEIGHT, COLUMN_WEIGHT, COLUMN_HAIR, COLUMN_EYES };

	// table creation SQL statement
	private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE "
	    + TABLE_NAME 	+ "(" 
		+ COLUMN_ID 		+ " integer primary key autoincrement, " 
		+ COLUMN_NAME 		+ " text unique not null, "
		+ COLUMN_ALIGNMENT 	+ " text, "
		+ COLUMN_LEVEL 		+ " text, "
		+ COLUMN_DEITY 		+ " text, "
		+ COLUMN_HOMELAND 	+ " text, "
		+ COLUMN_RACE 		+ " text, "
		+ COLUMN_SIZE 		+ " text, "
		+ COLUMN_GENDER 	+ " text, "
		+ COLUMN_AGE 		+ " integer, "
		+ COLUMN_HEIGHT 	+ " integer, "
		+ COLUMN_WEIGHT 	+ " integer, "
		+ COLUMN_HAIR 		+ " text, "
		+ COLUMN_EYES 		+ " text) ";

	public SQLiteHelperBasicInfo(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println("SQLiteHelperBasicInfo onCreate");
		System.out.println(CREATE_TABLE_STATEMENT);
		database.execSQL(CREATE_TABLE_STATEMENT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		Log.w(SQLiteHelperBasicInfo.class.getName(),
			   "Upgrading database from version " + oldVer + " to "
		           + newVer + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	public void printContents(SQLiteDatabase db) {
		System.out.println("CONTENTS OF basic_info");
		Cursor cursor = db.query(TABLE_NAME,
			ALL_COLUMNS, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(0);
			String name = cursor.getString(1);
			String alignment = cursor.getString(2);
			int level = cursor.getInt(3);
			String diety = cursor.getString(4);
			String homeland = cursor.getString(5);
			String race = cursor.getString(6);
			int size = cursor.getInt(7);
			String gender = cursor.getString(8);
			int age = cursor.getInt(9);
			int height = cursor.getInt(10);
			int weight = cursor.getInt(11);
			String hair = cursor.getString(12);
			String eyes = cursor.getString(13);

			System.out.println(id 
				+ "    " + name
				+ "    " + alignment
				+ "    " + level
				+ "    " + diety
				+ "    " + homeland
				+ "    " + race
				+ "    " + size
				+ "    " + gender
				+ "    " + age
				+ "    " + height
				+ "    " + weight
				+ "    " + hair
				+ "    " + eyes
				+ "    "); 
			cursor.moveToNext();
	 	}
		cursor.close();
	}

	@Override
	public String[] getColumns() {
		return ALL_COLUMNS;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	public void insert() {
		//asdf
	}

	public SQLiteDatabase getDB() {
		return db;
	}


}