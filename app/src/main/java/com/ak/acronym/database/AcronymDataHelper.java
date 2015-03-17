package com.ak.acronym.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AcronymDataHelper extends SQLiteOpenHelper {

	public static final String TABLE_ACRONYMS = "acronymtable";
	public static final String ACRONYM_ID = "_id";
	public static final String ACRONYM = "acronym";
	public static final String ACRONYM_VALUE = "value";

	private static final String DATABASE_NAME = "acronymdatabase.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_ACRONYMS + "(" + ACRONYM_ID
			+ " integer primary key autoincrement, " + ACRONYM_VALUE
			+ " text not null, " + ACRONYM + " text not null );";

	public AcronymDataHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(AcronymDataHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACRONYMS);
		onCreate(db);
	}

}
