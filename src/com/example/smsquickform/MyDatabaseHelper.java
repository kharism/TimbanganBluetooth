package com.example.smsquickform;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME="PENIMBANGAN";
	private static final int DATABASE_VERSION=2;
	private static final String DATABASE_CREATE="CREATE TABLE \"sms\" ("+
    "\"id\" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    "\"value\" TEXT"+
");"+
"CREATE TABLE sqlite_sequence(name,seq);";
	
	public MyDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		database.execSQL("DROP TABLE IF EXISTS ibu;DROP TABLE IF EXISTS anak;DROP TABLE IF EXISTS penimbangan");
        onCreate(database);
	}

}
