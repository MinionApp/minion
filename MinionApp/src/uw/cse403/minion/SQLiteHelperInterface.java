package uw.cse403.minion;
import android.database.sqlite.SQLiteDatabase;


public interface SQLiteHelperInterface {

	public static final String[] ALL_COLUMNS = null;
	public static final String TABLE_NAME = null;
	public static SQLiteDatabase db = null;
	public SQLiteDatabase getDB();

	public String[] getColumns();
	public String getTableName();
	public abstract void printContents(SQLiteDatabase db);

}
