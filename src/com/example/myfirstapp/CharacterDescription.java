package com.example.myfirstapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * A wrapper class around the basic descriptions of a character. 
 * Which can be found on the top of most character sheets as the name,
 * age, alignment, ect...
 * 
 * @author Loki White (lokiw)
 */
public class CharacterDescription {
	public long charID;
	
	public String name;
	public String player;
	public String alignment;
	public String size;
	public Alignment firstAlign;
	public Alignment secondAlign;
	public String diety;
	public String homeLand;
	public String gender;
	public String race;
	public int age;
	//consider storing in inches and displaying in feet inches
	public int height;
	public int weight;
	public String hair;
	public String eyes;
	
	public CharacterDescription(long id) {
		charID = id;
		// default values
		name = "";
		player = "";
		alignment = "";
		size = "";
		firstAlign = null;
		secondAlign = null;
		diety = "";
		homeLand = "";
		gender = "";
		race = "";
		age = 0;
		height = 0;
		weight = 0;
		hair = "";
		eyes = "";
	}
	
	/** 
	 * Writes character description / basic info to database. SHOULD ONLY BE CALLED BY CHARACTER
	 * @param id id of character
	 * @param db database to write into
	 */
	public void writeToDB() {
		long id = charID;
		SQLiteDatabase db = SQLiteHelperBasicInfo.db;
		// TODO implement
		ContentValues values = new ContentValues();
		values.put(SQLiteHelperBasicInfo.COLUMN_NAME, name);
		values.put(SQLiteHelperBasicInfo.COLUMN_ID, charID);
		values.put(SQLiteHelperBasicInfo.COLUMN_ALIGNMENT, alignment);
		values.put(SQLiteHelperBasicInfo.COLUMN_SIZE, size.toString());
		// TODO figure out what to do with alignments
		//values.put(SQLiteHelperBasicInfo.COLUMN_NAME, name); // alignment1
		//values.put(SQLiteHelperBasicInfo.COLUMN_NAME, name); // alignment2
		values.put(SQLiteHelperBasicInfo.COLUMN_DIETY, diety);
		values.put(SQLiteHelperBasicInfo.COLUMN_HOMELAND, homeLand);
		values.put(SQLiteHelperBasicInfo.COLUMN_GENDER, gender);
		values.put(SQLiteHelperBasicInfo.COLUMN_RACE, race);
		values.put(SQLiteHelperBasicInfo.COLUMN_AGE, age);
		values.put(SQLiteHelperBasicInfo.COLUMN_HEIGHT, height);
		values.put(SQLiteHelperBasicInfo.COLUMN_WEIGHT, weight);
		values.put(SQLiteHelperBasicInfo.COLUMN_HAIR, hair);
		values.put(SQLiteHelperBasicInfo.COLUMN_EYES, eyes);
		
		long insertId = db.insert(SQLiteHelperBasicInfo.TABLE_NAME, null, values);
		//String[] columns = {SQLiteHelperBasicInfo.COLUMN_ID};
		//Cursor c = db.query(SQLiteHelperBasicInfo.TABLE_NAME, columns, columns[0] + " = " + name, null, null, null, null);
		//return c.getInt(0);
		
	}
}
