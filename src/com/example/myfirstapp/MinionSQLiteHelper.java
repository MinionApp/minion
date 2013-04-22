package com.example.myfirstapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MinionSQLiteHelper extends SQLiteOpenHelper {
	public static final String TABLE_CHARACTERS = "characters";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";

	private static final String DATABASE_NAME = "characters.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
	    + TABLE_CHARACTERS + "(" + COLUMN_ID
	    + " integer primary key autoincrement, " + COLUMN_NAME
	    + " text not null);";

	public MinionSQLiteHelper(Context context) {
	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		// TODO Auto-generated method stub
		Log.w(MinionSQLiteHelper.class.getName(),
		       "Upgrading database from version " + oldVer + " to "
		           + newVer + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHARACTERS);
		onCreate(db);
	}
}
