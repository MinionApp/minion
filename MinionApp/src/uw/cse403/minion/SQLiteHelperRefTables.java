package uw.cse403.minion;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * 
 * @author Kevin Dong (kevinxd3)
 *
 */
public class SQLiteHelperRefTables extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "characters.db";
	private static final int DATABASE_VERSION = 1;
	 static SQLiteDatabase db;

	// REF ABILITY SCORES columns
	public static final String TABLE_REF_ABILITY_SCORES = "ref_ability_scores";
	public static final String COLUMN_AS_ID 	= "_id";
	public static final String COLUMN_AS_ABBREV = "abbrev";
	public static final String COLUMN_AS_NAME 	= "name";
	public static final String[] ALL_COLUMNS_AS = 
		{ COLUMN_AS_ID, COLUMN_AS_ABBREV, COLUMN_AS_NAME };

	// REF ABILITY SCORES table creation SQL statement
	private static final String CREATE_TABLE_REF_ABILITY_SCORES = "CREATE TABLE "
		+ TABLE_REF_ABILITY_SCORES 	+ "(" 
		+ COLUMN_AS_ID 		+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
		+ COLUMN_AS_ABBREV 	+ " TEXT, " 
		+ COLUMN_AS_NAME 	+ " TEXT)";

	/*****************************************/
	// REF SKILLS columns
	public static final String TABLE_REF_SKILLS 	= "ref_skills";
	public static final String COLUMN_S_ID 			= "_id";
	public static final String COLUMN_S_NAME 		= "name";
	public static final String COLUMN_S_REF_AS_ID 	= "ref_as_id";
	public static final String[] ALL_COLUMNS_S = 
		{ COLUMN_S_ID, COLUMN_S_NAME, COLUMN_S_REF_AS_ID };

	// REF SKILLS table creation SQL statement
	private static final String CREATE_TABLE_REF_SKILLS = "CREATE TABLE "
		+ TABLE_REF_SKILLS 	+ "(" 
		+ COLUMN_S_ID 		+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
		+ COLUMN_S_NAME 	+ " TEXT, " 
		+ COLUMN_S_REF_AS_ID 	+ " INTEGER, " 
		+ " FOREIGN KEY(" + COLUMN_S_REF_AS_ID + ") REFERENCES " 
		+ TABLE_REF_ABILITY_SCORES + "(" + COLUMN_AS_ID + ")) ";

	/*****************************************/
	// REF SAVING THROWS columns
	public static final String TABLE_REF_SAVING_THROWS = "ref_saving_throws";
	public static final String COLUMN_ST_ID 		= "_id";
	public static final String COLUMN_ST_NAME 		= "name";
	public static final String COLUMN_ST_REF_AS_ID 	= "ref_as_id";
	public static final String[] ALL_COLUMNS_ST = 
		{ COLUMN_ST_ID, COLUMN_ST_NAME, COLUMN_ST_REF_AS_ID };

	// REF SAVING THROWS table creation SQL statement
	private static final String CREATE_TABLE_REF_SAVING_THROWS = "CREATE TABLE "
		+ TABLE_REF_SAVING_THROWS 	+ "(" 
		+ COLUMN_ST_ID 		+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
		+ COLUMN_ST_NAME 	+ " TEXT, "
		+ COLUMN_ST_REF_AS_ID 	+ " INTEGER, "
		+ " FOREIGN KEY(" + COLUMN_ST_REF_AS_ID + ") REFERENCES " 
		+ TABLE_REF_ABILITY_SCORES + "(" + COLUMN_AS_ID + ")) ";

	/*****************************************/
	public SQLiteHelperRefTables(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.db = this.getWritableDatabase();
		System.out.println("SQLiteHelperRefTables constructor");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println(CREATE_TABLE_REF_ABILITY_SCORES);
		System.out.println(CREATE_TABLE_REF_SKILLS);
		System.out.println(CREATE_TABLE_REF_SAVING_THROWS);

		System.out.println("SQLiteHelperRefTables onCreate");
		
		System.out.println("SQLiteHelperRefTables creating table AS");
		db.execSQL(CREATE_TABLE_REF_ABILITY_SCORES);
		System.out.println("SQLiteHelperRefTables populating table AS");
		populateAbilityScores(db);

		System.out.println("SQLiteHelperRefTables creating table S");
		db.execSQL(CREATE_TABLE_REF_SKILLS);
		populateSkills(db);

		System.out.println("SQLiteHelperRefTables creating table ST");
		db.execSQL(CREATE_TABLE_REF_SAVING_THROWS);
		populateSavingThrows(db);
	}

	private static final int STR = 0;
	private static final int DEX = 1;
	private static final int CON = 2;
	private static final int INT = 3;
	private static final int WIS = 4;
	private static final int CHA = 5;
	private void populateAbilityScores(SQLiteDatabase db) {
		System.out.println("SQLiteHelperRefTables populateAbilityScores");
		String[][] abilities = {
			{"STR", "Strength"},
			{"DEX", "Dexterity"},
			{"CON", "Constitution"},
			{"INT", "Intelligence"},
			{"WIS", "Wisdom"},
			{"CHA", "Charisma"}
		};
		for (int i = 0; i < abilities.length; i++) {
			String insertStatement = "INSERT INTO " + TABLE_REF_ABILITY_SCORES + " ("
				+ COLUMN_AS_ID + ", "
				+ COLUMN_AS_ABBREV + ", "
				+ COLUMN_AS_NAME + ") VALUES ("
				+ i + ", '"
				+ abilities[i][0] + "', '"
				+ abilities[i][1] + "')";
			System.out.println(insertStatement);
			db.execSQL(insertStatement);
		}
	}
	// helper class for storing an int and string together
	// mostly for making the following code readable
	private class IntStr {
		int i;
		String s;
		public IntStr(int i, String s) {
			this.i = i;
			this.s = s;
		}
	}
	private void populateSkills(SQLiteDatabase db) {
		System.out.println("SQLiteHelperRefTables populateSkills");
		ArrayList<IntStr> list = new ArrayList<IntStr>();
			list.add(new IntStr(DEX, "Acrobatics"));
			list.add(new IntStr(INT, "Appraise"));
			list.add(new IntStr(CHA, "Bluff"));
			list.add(new IntStr(STR, "Climb"));
			list.add(new IntStr(INT, "Craft"));
			list.add(new IntStr(CHA, "Diplomacy"));
			list.add(new IntStr(DEX, "Disable Device"));
			list.add(new IntStr(CHA, "Disguise"));
			list.add(new IntStr(DEX, "Escape Artist"));
			list.add(new IntStr(DEX, "Fly"));
			list.add(new IntStr(CHA, "Handle Animal"));
			list.add(new IntStr(WIS, "Heal"));
			list.add(new IntStr(CHA, "Intimidate"));
			list.add(new IntStr(INT, "Knowledge (Arcana)"));
			list.add(new IntStr(INT, "Knowledge (Dungeoneering)"));
			list.add(new IntStr(INT, "Knowledge (Engineering)"));
			list.add(new IntStr(INT, "Knowledge (Geography)"));
			list.add(new IntStr(INT, "Knowledge (History)"));
			list.add(new IntStr(INT, "Knowledge (Local)"));
			list.add(new IntStr(INT, "Knowledge (Nature)"));
			list.add(new IntStr(INT, "Knowledge (Nobility)"));
			list.add(new IntStr(INT, "Knowledge (Planes)"));
			list.add(new IntStr(INT, "Knowledge (Religion)"));
			list.add(new IntStr(INT, "Linguistics"));
			list.add(new IntStr(WIS, "Perception"));
			list.add(new IntStr(CHA, "Perform"));
			list.add(new IntStr(WIS, "Profession"));
			list.add(new IntStr(DEX, "Ride"));
			list.add(new IntStr(WIS, "Sense Motive"));
			list.add(new IntStr(DEX, "Sleight of Hand"));
			list.add(new IntStr(INT, "Spellcraft"));
			list.add(new IntStr(DEX, "Stealth"));
			list.add(new IntStr(WIS, "Survival"));
			list.add(new IntStr(STR, "Swim"));
			list.add(new IntStr(CHA, "Use Magic Device"));
		for (int i = 0; i < list.size(); i++) {
			IntStr asdf = list.get(i);
			String insertStatement = "INSERT INTO " + TABLE_REF_SKILLS + " ("
				+ COLUMN_S_NAME + ", "
				+ COLUMN_S_REF_AS_ID + ") VALUES ('"
				+ asdf.s + "', '"
				+ asdf.i + "')";
			System.out.println(insertStatement);
			db.execSQL(insertStatement);
		}
	}
	private void populateSavingThrows(SQLiteDatabase db) {
		ArrayList<IntStr> list = new ArrayList<IntStr>();
			list.add(new IntStr(CON, "Fortitude"));
			list.add(new IntStr(DEX, "Reflex"));
			list.add(new IntStr(WIS, "Will"));
		for (int i = 0; i < list.size(); i++) {
			IntStr asdf = list.get(i);
			String insertStatement = "INSERT INTO " + TABLE_REF_SAVING_THROWS + " ("
				+ COLUMN_S_NAME + ", "
				+ COLUMN_S_REF_AS_ID + ") VALUES ('"
				+ asdf.s + "', '"
				+ asdf.i + "')";
			System.out.println(insertStatement);
			db.execSQL(insertStatement);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteDatabase.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REF_SAVING_THROWS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REF_SKILLS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REF_ABILITY_SCORES);
		onCreate(db);
	}

	public void printContents(SQLiteDatabase db) {
		System.out.println("CONTENTS OF ref_ability_scores");
		Cursor cursor = db.query(TABLE_REF_ABILITY_SCORES,
			ALL_COLUMNS_AS, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(0);
			String abbrev = cursor.getString(1);
			String name = cursor.getString(2);
			System.out.println(id + "    " + abbrev + "    " + name);
			cursor.moveToNext();
	 	}
		cursor.close();

		System.out.println("CONTENTS OF ref_skills");
		cursor = db.query(TABLE_REF_SKILLS,
			ALL_COLUMNS_S, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(0);
			String name = cursor.getString(1);
			int as_id = cursor.getInt(2);
			System.out.println(id + "    " + name + "    " + as_id);
			cursor.moveToNext();
	 	}
		cursor.close();

		System.out.println("CONTENTS OF ref_saving_throws");
		cursor = db.query(TABLE_REF_SAVING_THROWS,
			ALL_COLUMNS_ST, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(0);
			String name = cursor.getString(1);
			int as_id = cursor.getInt(2);
			System.out.println(id + "    " + name + "    " + as_id);
			cursor.moveToNext();
	 	}
		cursor.close();
	}

}
