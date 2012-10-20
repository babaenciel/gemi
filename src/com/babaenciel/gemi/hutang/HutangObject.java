package com.babaenciel.gemi.hutang;

public class HutangObject {
	public int id_hutang;
	public String nama;
	public int jumlah_cicilan;		
	public int jumlah_hutang;		
	public String tanggal_deadline;
	
	public HutangObject(int id_hutang, String nama, int jumlah_cicilan, int jumlah_hutang, String tanggal_deadline) {
		this.id_hutang = id_hutang;
		this.nama = nama;
		this.jumlah_cicilan = jumlah_cicilan;
		this.jumlah_hutang = jumlah_hutang;
		this.tanggal_deadline = tanggal_deadline;
	}
}
