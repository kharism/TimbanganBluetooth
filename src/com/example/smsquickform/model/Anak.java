package com.example.smsquickform.model;

public class Anak {
	int nik;
	String nama;
	String kk;
	int nikIbu;
	String kelamin;
	String tanggalLahir;
	public static String[] ATTRIBUTES = {"nik","nama","kk","nikIbu","kelamin","tanggalLahir"};
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
	public int getNikIbu() {
		return nikIbu;
	}
	public void setNikIbu(int nikIbu) {
		this.nikIbu = nikIbu;
	}
	public String getKelamin() {
		return kelamin;
	}
	public void setKelamin(String kelamin) {
		this.kelamin = kelamin;
	}
	public String getTanggalLahir() {
		return tanggalLahir;
	}
	public void setTanggalLahir(String tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}
	
}
