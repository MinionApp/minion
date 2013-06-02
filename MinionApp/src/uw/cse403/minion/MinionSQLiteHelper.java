package uw.cse403.minion;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MinionSQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "characters.db";
	private static final int DATABASE_VERSION = 1;

	// BASIC INFO columns
	public static final String TABLE_BASIC_INFO = "basic_info";
	public static final String COLUMN_ID 		= "_id";
	public static final String COLUMN_NAME 		= "name";
	public static final String COLUMN_ALIGNMENT = "alignment";
	public static final String COLUMN_LEVEL 	= "level";
	public static final String COLUMN_DIETY 	= "diety";
	public static final String COLUMN_HOMELAND 	= "homeland";
	public static final String COLUMN_RACE 		= "race";
	public static final String COLUMN_SIZE 		= "size";
	public static final String COLUMN_GENDER 	= "gender";
	public static final String COLUMN_AGE 		= "age";
	public static final String COLUMN_HEIGHT 	= "height";
	public static final String COLUMN_WEIGHT 	= "weight";
	public static final String COLUMN_HAIR 		= "hair";
	public static final String COLUMN_EYES 		= "eyes";

	// BASIC INFO table creation SQL statement
	private static final String CREATE_TABLE_BASIC_INFO = "create table "
			+ TABLE_BASIC_INFO + "(" 
			+ COLUMN_ID 		+ " integer primary key autoincrement, " 
			+ COLUMN_NAME 		+ " text not null, "
			+ COLUMN_LEVEL 		+ " text, "
			+ COLUMN_DIETY 		+ " text, "
			+ COLUMN_HOMELAND 	+ " text, "
			+ COLUMN_RACE 		+ " text, "
			+ COLUMN_SIZE 		+ " text, "
			+ COLUMN_GENDER 	+ " text, "
			+ COLUMN_AGE 		+ " integer, "
			+ COLUMN_HEIGHT 	+ " integer, "
			+ COLUMN_WEIGHT 	+ " integer, "
			+ COLUMN_HAIR 		+ " text, "
			+ COLUMN_EYES 		+ " text) ";




	// SKILLS table creation SQL statement


	public MinionSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(CREATE_TABLE_BASIC_INFO);
		System.out.println("MinionSQLiteHelper onCreate");
		//database.execSQL(CREATE_TABLE_ABILITY_SCORES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		// TODO Auto-generated method stub
		Log.w(MinionSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVer + " to "
						+ newVer + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BASIC_INFO);
		onCreate(db);
	}
}
