package com.babaenciel.gemi.anggaran;

public class AnggaranObject {
	
	public int id_anggaran;
	public String nama;
	public int nominalAtas;
	public int nominalBawah;
	public String tanggal;
	
	public AnggaranObject() {
		
	}
	
	public AnggaranObject(int id_anggaran, String nama, int nominalAtas, int nominalBawah, String tanggal) {
		this.id_anggaran = id_anggaran;
		this.nama = nama;
		this.nominalAtas = nominalAtas;
		this.nominalBawah = nominalBawah;
		this.tanggal = tanggal;
	}
}
