package com.example.myfirstapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public interface SQLiteHelperInterface {
	
	public static final String[] ALL_COLUMNS = null;
	public static final String TABLE_NAME = null;
	public static SQLiteDatabase db = null;
	public SQLiteDatabase getDB();
	
	public String[] getColumns();
	public String getTableName();
	public abstract void printContents(SQLiteDatabase db);

}
