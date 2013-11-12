package com.example.smsquickform;

import java.util.ArrayList;
import java.util.List;

import com.example.smsquickform.model.Ibu;
import com.example.smsquickform.model.Sms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB {
	private MyDatabaseHelper dbHelper;  
	private SQLiteDatabase database;
	private static DB _this;
	public final static String IBU_TABLE="ibu";
	public final static String ANAK_TABLE="anak";
	public final static String PENIMBANGAN_TABLE="penimbangan";
	
	public DB(Context context){  
	    dbHelper = new MyDatabaseHelper(context);  
	    database = dbHelper.getWritableDatabase();
	}
	public long save(String tableName,ContentValues values){
		return database.insert(tableName, null, values);
	}
	public Cursor find(String tablename,String[] col,String condition){
		return database.query(true, tablename, col, condition, null, null, null, null, null);
	}
	public void open(){
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		database.close();
	}
	public void saveIbu(Ibu i){
	    ContentValues values = new ContentValues();
	    values.put("nik", i.getNik());
	    values.put("nama", i.getNama());
	    values.put("kk", i.getKk());
	    Cursor c = database.query(true, "ibu", Ibu.ATTRIBUTES,i.getWhereValue(),null,null, null, null, null);
	    if(c.getCount()==0){
	    	database.insert("ibu", null, values);
	    }
	    
	}
	public Ibu findIbu(int nik){
		Cursor c = database.query(true, "ibu", Ibu.ATTRIBUTES,"nik = "+nik,null,null, null, null, null);
		return Ibu.fromCursor(c);
	}
	public void saveSms(Sms i){
		ContentValues values = new ContentValues();
	    values.put("id", i.getId());
	    values.put("value", i.getValue());
	    Cursor c = database.query(true, "ibu", Ibu.ATTRIBUTES,i.getWhereValue(),null,null, null, null, null);
	    if(c.getCount()==0){
	    	database.insert("ibu", null, values);
	    }
	    else{
	    	database.update("sms", values, i.getValue(), null);
	    }
	}
	public List<Sms> findAllSMS(){
		List<Sms> comments = new ArrayList<Sms>();

	    Cursor cursor = database.query("sms",
	        Sms.ATTRIBUTES, null, null, null, null, null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Sms sms = Sms.fromCursor(cursor);
	      comments.add(sms);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return comments;
	}
}
