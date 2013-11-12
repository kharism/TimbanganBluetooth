package com.example.smsquickform.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;

public class Ibu {
	int nik;
	String nama;
	String kk;
	public static String[] ATTRIBUTES = {"nik","nama","kk"};
	public int getNik() {
		return nik;
	}
	public void setNik(int nik) {
		this.nik = nik;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getKk() {
		return kk;
	}
	public void setKk(String kk) {
		this.kk = kk;
	}
	public String getWhereValue(){
		return "nik="+nik+" and nama="+nama+" and kk="+kk;
	}
	public static Ibu fromCursor(Cursor c){
		Ibu i = new Ibu();
		i.setNik(c.getInt(0));
		i.setNama(c.getString(1));
		i.setKk(c.getString(2));
		return i;
		
	}
}
