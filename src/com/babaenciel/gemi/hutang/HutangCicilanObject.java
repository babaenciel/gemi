package com.babaenciel.gemi.hutang;

public class HutangCicilanObject {
	public int id_hutang_cicilan;
	public String nama;
	public int nominal;
	public String tanggal;
	public int id_hutang;
	
	public HutangCicilanObject(int id_hutang_cicilan, String nama, int nominal, String tanggal, int id_hutang) {
		this.id_hutang_cicilan = id_hutang_cicilan;
		this.nama = nama;
		this.nominal = nominal;
		this.tanggal = tanggal;
		this.id_hutang = id_hutang;
	}
}
