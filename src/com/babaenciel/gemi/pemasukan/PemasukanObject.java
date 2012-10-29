package com.babaenciel.gemi.pemasukan;

import android.os.Bundle;

public class PemasukanObject {
	public int id_pemasukan;
	public String nama;
	public String tanggal;
	public int nominal;
	public int id_kategori;
	public String nama_kategori;
	
	public PemasukanObject(int id, String nama, String tanggal, int nominal, int id_kategori) {
		this.id_pemasukan = id;
		this.nama = nama;
		this.tanggal = tanggal;
		this.nominal = nominal;
		this.id_kategori = id_kategori;
	}
	
	public PemasukanObject() {
		
	}
	
	public void setNamaKategori(String nama_kategori) {
		this.nama_kategori = nama_kategori;
	}
	
	//digunakan untuk transfer object antar activity
	//karena dibutuhkan untuk mengisi form update dengan data sebelumnya
	public Bundle createBundle(int id, String nama, int nominal, String tanggal, int id_kategori) {
		Bundle b = new Bundle();
		b.putInt("id", id);
		b.putString("nama", nama);
		b.putInt("nominal", nominal);
		b.putString("tanggal", tanggal);
		b.putInt("id_kategori", id_kategori);
		return b;
	}
	
	public PemasukanObject UnBundle(Bundle bundle) {							
		PemasukanObject po = new PemasukanObject(bundle.getInt("id"), 
				bundle.getString("nama"),
				bundle.getString("tanggal"),
				bundle.getInt("nominal"),
				bundle.getInt("id_kategori"));
		return po;
	}
		
}

