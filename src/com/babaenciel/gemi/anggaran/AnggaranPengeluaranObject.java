package com.babaenciel.gemi.anggaran;

public class AnggaranPengeluaranObject {
	public int id_anggaran_pengeluaran;
	public String nama;
	public int nominal;
	public String tanggal;
	public int id_anggaran;
	
	public AnggaranPengeluaranObject(int id_anggaran_pengeluaran, String nama, int nominal, String tanggal, int id_anggaran) {
		this.id_anggaran_pengeluaran = id_anggaran_pengeluaran;
		this.nama = nama;
		this.nominal = nominal;
		this.tanggal = tanggal;
		this.id_anggaran = id_anggaran;
	}
}
