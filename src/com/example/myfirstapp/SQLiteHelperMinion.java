package com.example.myfirstapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public interface SQLiteHelperMinion {
	
	public static final String[] ALL_COLUMNS = null;
	public static final String TABLE_NAME = null;
	public String[] getColumns();
	public String getTableName();
	public abstract void printContents(SQLiteDatabase db);

}
