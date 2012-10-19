package com.babaenciel.gemi.tagihan;

public class TagihanObject {
	public int id_tagihan;
	public String nama;
	public int jumlah;
	public String tanggal_deadline;
	public int lunas;
	
	public TagihanObject(int id_tagihan, String nama, int jumlah, String tanggal_deadline, int lunas) {
		this.id_tagihan = id_tagihan;
		this.nama = nama;
		this.jumlah = jumlah;
		this.tanggal_deadline = tanggal_deadline;
		this.lunas = lunas;
	}
}
