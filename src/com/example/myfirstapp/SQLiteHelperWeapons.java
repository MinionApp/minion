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
public class SQLiteHelperWeapons extends SQLiteOpenHelper 
		implements SQLiteHelperMinion {
	private static final String DATABASE_NAME = "characters.db";
	private static final int DATABASE_VERSION = 1;
	
	// columns (AS = Ability Scores)
	public static final String TABLE_NAME 	= "weapons";
	public static final String COLUMN_CHAR_ID 		= "char_id";
	public static final String COLUMN_NAME 			= "name";
	public static final String COLUMN_ATTACK_BONUS 	= "attack_bonus";
	public static final String COLUMN_CRIT 			= "crit";
	public static final String COLUMN_TYPE 		= "type";
	public static final String COLUMN_RANGE 	= "range";
	public static final String COLUMN_AMMO 		= "ammo";
	public static final String COLUMN_DAMAGE 	= "damage";
	public static final String[] ALL_COLUMNS = 
		{ COLUMN_CHAR_ID, COLUMN_NAME, COLUMN_ATTACK_BONUS, COLUMN_CRIT, COLUMN_TYPE, 
			COLUMN_RANGE, COLUMN_AMMO, COLUMN_DAMAGE };

	// table creation SQL statement
	private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE "
	    + TABLE_NAME + "(" 
		+ COLUMN_CHAR_ID 		+ " INTEGER, "
		+ COLUMN_NAME			+ " TEXT, "
		+ COLUMN_ATTACK_BONUS 	+ " INTEGER, "
		+ COLUMN_CRIT			+ " INTEGER, "
		+ COLUMN_TYPE			+ " TEXT, "
		+ COLUMN_RANGE			+ " INTEGER, "
		+ COLUMN_AMMO			+ " INTEGER, "
		+ COLUMN_DAMAGE			+ " INTEGER, "
		// references Basic Info _id
		+ " FOREIGN KEY(" + COLUMN_CHAR_ID + ") REFERENCES " 
			+ SQLiteHelperBasicInfo.TABLE_NAME + "(" 
			+ SQLiteHelperBasicInfo.COLUMN_ID + ")) ";

	public SQLiteHelperWeapons(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println("SQLiteHelperWeapons onCreate");
		database.execSQL(CREATE_TABLE_STATEMENT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		Log.w(SQLiteHelperWeapons.class.getName(),
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
		// TODO Auto-generated method stub
		return ALL_COLUMNS;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}
}
