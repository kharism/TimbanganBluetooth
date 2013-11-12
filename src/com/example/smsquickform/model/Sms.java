package com.example.smsquickform.model;

import android.database.Cursor;

public class Sms {
	int id;
	String value;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public static String[] ATTRIBUTES = {"id","value"};
	public static Sms fromCursor(Cursor c){
		Sms n = new Sms();
		n.id = c.getInt(0);
		n.value = c.getString(1);
		return n;
	}
	public String getWhereValue(){
		return "id="+this.getId()+" and value="+this.getValue();
	}
}
