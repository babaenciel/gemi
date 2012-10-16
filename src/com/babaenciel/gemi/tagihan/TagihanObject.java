package com.babaenciel.gemi.tagihan;

public class TagihanObject {
	public int id_tagihan;
	public String nama;
	public int jumlah;
	public String tanggal;
	
	public TagihanObject(int id_tagihan, String nama, int jumlah, String tanggal) {
		this.id_tagihan = id_tagihan;
		this.nama = nama;
		this.jumlah = jumlah;
		this.tanggal = tanggal;
	}
}
