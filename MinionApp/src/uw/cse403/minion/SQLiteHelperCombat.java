package uw.cse403.minion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * 
 * @author Kevin Dong (kevinxd3)
 *
 */
public class SQLiteHelperCombat extends SQLiteOpenHelper 
implements SQLiteHelperInterface {
	private static final String DATABASE_NAME = "characters.db";
	private static final int DATABASE_VERSION = 1;
	static SQLiteDatabase db;

	// columns (AS = Ability Scores)
	public static final String TABLE_NAME 	= "combat";
	public static final String COLUMN_CHAR_ID 	= "char_id";
	public static final String COLUMN_HP_TOTAL 	= "hp_total";
	public static final String COLUMN_HP_DR 	= "hp_dr";
	public static final String COLUMN_SPEED_BASE 	= "speed_base";
	public static final String COLUMN_SPEED_ARMOR 	= "speed_armor";
	public static final String COLUMN_INIT_MISC_MOD = "init_misc_mod";
	public static final String COLUMN_ARMOR 		= "armor";
	public static final String COLUMN_ARMOR_SHIELD 	= "armor_shield";
	public static final String COLUMN_ARMOR_NATURAL = "armor_natural";
	public static final String COLUMN_ARMOR_DEFLEC 	= "armor_deflec";
	public static final String COLUMN_ARMOR_MISC 	= "armor_misc";
	public static final String COLUMN_BASE_ATTACK_BONUS = "base_attack_bonus";
	public static final String[] ALL_COLUMNS = 
		{ COLUMN_CHAR_ID, COLUMN_HP_TOTAL, COLUMN_HP_DR, COLUMN_SPEED_BASE, COLUMN_SPEED_ARMOR,
		COLUMN_INIT_MISC_MOD, COLUMN_ARMOR, COLUMN_ARMOR_SHIELD, COLUMN_ARMOR_NATURAL,
		COLUMN_ARMOR_DEFLEC, COLUMN_ARMOR_MISC, COLUMN_BASE_ATTACK_BONUS };

	// table creation SQL statement
	private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE "
			+ TABLE_NAME 			+ "(" 
			+ COLUMN_CHAR_ID 		+ " INTEGER, "
			+ COLUMN_HP_TOTAL		+ " INTEGER, "
			+ COLUMN_HP_DR 			+ " INTEGER, "
			+ COLUMN_SPEED_BASE		+ " INTEGER, "
			+ COLUMN_SPEED_ARMOR	+ " INTEGER, "
			+ COLUMN_INIT_MISC_MOD	+ " INTEGER, "
			+ COLUMN_ARMOR 			+ " INTEGER, "
			+ COLUMN_ARMOR_SHIELD 	+ " INTEGER, "
			+ COLUMN_ARMOR_NATURAL 	+ " INTEGER, "
			+ COLUMN_ARMOR_DEFLEC 	+ " INTEGER, "
			+ COLUMN_ARMOR_MISC 	+ " INTEGER, "
			+ COLUMN_BASE_ATTACK_BONUS	+ " INTEGER, "
			// references Basic Info _id
			+ " FOREIGN KEY(" + COLUMN_CHAR_ID + ") REFERENCES " 
			+ SQLiteHelperBasicInfo.TABLE_NAME + "(" 
			+ SQLiteHelperBasicInfo.COLUMN_ID + ")) ";

	public SQLiteHelperCombat(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println("SQLiteHelperCombat onCreate");
		database.execSQL(CREATE_TABLE_STATEMENT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		Log.w(SQLiteHelperCombat.class.getName(),
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
