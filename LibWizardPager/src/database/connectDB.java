package database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class connectDB {
	public static final String _ID = "_id";
	public static final String ORDER_TYPE = "ordertype";
	public static final String FILLING = "Filling";
	public static final String RICES = "Rice";
	public static final String BEANS = "Beans";
	public static final String TOPPINGS = "Toppings";
	public static final String CHIPS_TYPE = "chipstype";
	public static final String SALSA = "salsa";
	public static final String DRINKS = "Drinks";
	public static final String DATABASE_NAME = "OrderingSystem";
	public static final String TABLE_NAME = "Sandwich";
	public static final int VERSION = 1;
	public static final String DATATABLE_CREATE = "create table " + TABLE_NAME
			+ "(" + _ID + " integer primary key autoincrement , " + ORDER_TYPE + " text not null," + FILLING
			+ " text not null," + RICES + " text not null," + BEANS + " text not null," + TOPPINGS + " text not null," + CHIPS_TYPE + " text not null," + SALSA + " text not null," + DRINKS + " text not null);";

	private SQLiteDatabase sqldb;
	private MySQLHelper helper;
	private Context ctx;
	private ContentValues values;
	
	public connectDB(Context context) {
		this.ctx = context;
	}

	class MySQLHelper extends SQLiteOpenHelper {

		public MySQLHelper() {
			super(ctx, DATABASE_NAME, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATATABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table if exists " + TABLE_NAME);
			onCreate(db);
		}
	}

	public void open() {
		helper = new MySQLHelper();
		sqldb = helper.getWritableDatabase();
	}

	public void close() {
		helper.close();
	}

	public long insert(String ordertype, String filling, String rices, String beans, String toppings, String chipstype, String salsa, String drinks) {
		values = new ContentValues();
		values.put(ORDER_TYPE, ordertype);
		values.put(FILLING, filling);
		values.put(RICES, rices);
		values.put(BEANS, beans);
		values.put(TOPPINGS, toppings);
		values.put(CHIPS_TYPE, chipstype);
		values.put(SALSA, salsa);
		values.put(DRINKS, drinks);
		return sqldb.insert(TABLE_NAME, "empty", values);
	}

	public int delete(int id) {
		String[] whereArgs = { String.valueOf(id) };
		return sqldb.delete(TABLE_NAME, "_id=?", whereArgs);
	}

	public boolean update(long rowID, String ordertype, String filling, String rices, String beans, String toppings, String chipstype, String salsa, String drinks) {
		values = new ContentValues();
		values.put(ORDER_TYPE, ordertype);
		values.put(FILLING, filling);
		values.put(RICES, rices);
		values.put(BEANS, beans);
		values.put(TOPPINGS, toppings);
		values.put(CHIPS_TYPE, chipstype);
		values.put(SALSA, salsa);
		values.put(DRINKS, drinks);
		return sqldb.update(TABLE_NAME, values, _ID + "=rowID",null)>0;
	}

	public Cursor query(long rowID) {
		return sqldb.query(TABLE_NAME, new String[]{_ID,ORDER_TYPE,FILLING,RICES,BEANS,TOPPINGS,CHIPS_TYPE,SALSA,DRINKS},_ID+"="+rowID, null, null, null, null, null);
	}
	public Cursor query() {
		return sqldb.query(TABLE_NAME, null, null, null, null, null, null, null);
	}
}
