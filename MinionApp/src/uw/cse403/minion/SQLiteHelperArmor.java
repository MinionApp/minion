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
public class SQLiteHelperArmor extends SQLiteOpenHelper 
implements SQLiteHelperInterface {
	private static final String DATABASE_NAME = "characters.db";
	private static final int DATABASE_VERSION = 1;
	static SQLiteDatabase db;

	// columns (AS = Ability Scores)
	public static final String TABLE_NAME 			= "armor";
	public static final String COLUMN_CHAR_ID 		= "char_id";
	public static final String COLUMN_ARMOR_BONUS 	= "armor_bonus";
	public static final String COLUMN_SHIELD_BONUS 	= "shield_bonus";
	public static final String COLUMN_NAT_ARMOR 		= "nat_armor";
	public static final String COLUMN_DEFLECTION_MOD 	= "deflection_mod";
	public static final String COLUMN_MISC_MOD 			= "misc_mod";
	public static final String[] ALL_COLUMNS = 
		{ COLUMN_CHAR_ID, COLUMN_ARMOR_BONUS, COLUMN_SHIELD_BONUS, COLUMN_NAT_ARMOR, 
		COLUMN_DEFLECTION_MOD, COLUMN_MISC_MOD };

	// table creation SQL statement
	private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE "
			+ TABLE_NAME 			+ "(" 
			+ COLUMN_CHAR_ID 		+ " INTEGER, "
			+ COLUMN_ARMOR_BONUS	+ " INTEGER, "
			+ COLUMN_SHIELD_BONUS 	+ " INTEGER, "
			+ COLUMN_NAT_ARMOR		+ " INTEGER, "
			+ COLUMN_DEFLECTION_MOD	+ " INTEGER, "
			+ COLUMN_MISC_MOD		+ " INTEGER, "
			// references Basic Info _id
			+ " FOREIGN KEY(" + COLUMN_CHAR_ID + ") REFERENCES " 
			+ SQLiteHelperBasicInfo.TABLE_NAME + "(" 
			+ SQLiteHelperBasicInfo.COLUMN_ID + ")) ";

	public SQLiteHelperArmor(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println("SQLiteHelperArmor onCreate");
		database.execSQL(CREATE_TABLE_STATEMENT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		Log.w(SQLiteHelperArmor.class.getName(),
				"Upgrading database from version " + oldVer + " to "
						+ newVer + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	@Override
	public void printContents(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}@Override
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
