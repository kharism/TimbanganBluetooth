package com.example.smsquickform.model;

public class Penimbangan {
	int id;
	int nikAnak;
	String tanggalAmbil;
	double berat;
	public static String[] ATTRIBUTES = {"id","nikAnak","tanggalAmbil","berat"};
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNikAnak() {
		return nikAnak;
	}
	public void setNikAnak(int nikAnak) {
		this.nikAnak = nikAnak;
	}
	public String getTanggalAmbil() {
		return tanggalAmbil;
	}
	public void setTanggalAmbil(String tanggalAmbil) {
		this.tanggalAmbil = tanggalAmbil;
	}
	public double getBerat() {
		return berat;
	}
	public void setBerat(double berat) {
		this.berat = berat;
	}
	
}
