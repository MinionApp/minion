package uw.cse403.minion;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public abstract class SQLiteHelperInterface extends SQLiteOpenHelper {

	public SQLiteHelperInterface(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
//	private String[] ALL_COLUMNS = null;
//	private String TABLE_NAME = null;
//	private SQLiteDatabase db = null;

	public abstract String getTableName();
	public abstract String[] getAllColumns();
	public abstract SQLiteDatabase getDB();
//	public void checkTableExists() {
//		try {
//			System.out.println("testing table");
//			System.out.println(getTableName());
//			String[] columns = getAllColumns();
//			for (int j = 0; j < columns.length; j ++) {
//				System.out.println(columns[j]);
//			}
//			// attempt to query table to see if it exists
//			getDB().query(getTableName(),
//					columns, null, null, null, null, null);
//		} catch (Exception e) { // table doesn't exist yet, create
//			System.out.println("no table found, creating...");
//			onCreate(getDB());
//		}
//	}
	public abstract void printContents(SQLiteDatabase db);

}
