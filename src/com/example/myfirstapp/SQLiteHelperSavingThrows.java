package com.example.myfirstapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * 
 * @author Kevin Dong (kevinxd3)
 *
 */
public class SQLiteHelperSavingThrows extends SQLiteOpenHelper 
		implements SQLiteHelperInterface {
	private static final String DATABASE_NAME = "characters.db";
	private static final int DATABASE_VERSION = 1;
	 static SQLiteDatabase db;
	
	// columns (AS = Ability Scores)
	public static final String TABLE_NAME 	= "saving_throws";
	public static final String COLUMN_CHAR_ID 	= "char_id";
	public static final String COLUMN_REF_ST_ID = "ref_st_id";
	public static final String COLUMN_BASE_SAVE = "base_save";
	public static final String COLUMN_MAGIC_MOD = "magic_mod";
	public static final String COLUMN_MISC_MOD 	= "misc_mod";
	public static final String COLUMN_TEMP_MOD 	= "temp_mod";
	public static final String[] ALL_COLUMNS = 
		{ COLUMN_CHAR_ID, COLUMN_REF_ST_ID, COLUMN_BASE_SAVE, COLUMN_MAGIC_MOD,
			COLUMN_MISC_MOD, COLUMN_TEMP_MOD };

	// table creation SQL statement
	private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE "
	    + TABLE_NAME + "(" 
		+ COLUMN_CHAR_ID 	+ " INTEGER, "
		+ COLUMN_REF_ST_ID	+ " INTEGER, "
		+ COLUMN_BASE_SAVE 	+ " INTEGER, "
		+ COLUMN_MAGIC_MOD	+ " INTEGER, "
		+ COLUMN_MISC_MOD	+ " INTEGER, "
		+ COLUMN_TEMP_MOD	+ " INTEGER, "
		// references Basic Info _id
		+ " FOREIGN KEY(" + COLUMN_CHAR_ID + ") REFERENCES " 
			+ SQLiteHelperBasicInfo.TABLE_NAME + "(" 
			+ SQLiteHelperBasicInfo.COLUMN_ID + "), "
		// references Ref Skills _id
		+ " FOREIGN KEY(" + COLUMN_REF_ST_ID + ") REFERENCES " 
			+ SQLiteHelperRefTables.TABLE_REF_ABILITY_SCORES + "(" 
			+ SQLiteHelperRefTables.COLUMN_ST_ID + ")) ";

	public SQLiteHelperSavingThrows(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println("SQLiteHelperSavingThrows onCreate");
		database.execSQL(CREATE_TABLE_STATEMENT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		Log.w(SQLiteHelperSavingThrows.class.getName(),
			   "Upgrading database from version " + oldVer + " to "
		           + newVer + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	@Override
	public void printContents(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getColumns() {
		return ALL_COLUMNS;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	public SQLiteDatabase getDB() {
		return db;
	}

}

