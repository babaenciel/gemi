package com.babaenciel.gemi.hutang;

public class HutangObject {
	public int id_hutang;
	public String nama;
	public int nominalAtas;		//jumlah cicilan
	public int nominalBawah;	//jumlah hutang
	public String tanggal_deadline;
	
	public HutangObject(int id_hutang, String nama, int nominalAtas, int nominalBawah, String tanggal_deadline) {
		this.id_hutang = id_hutang;
		this.nama = nama;
		this.nominalAtas = nominalAtas;
		this.nominalBawah = nominalBawah;
		this.tanggal_deadline = tanggal_deadline;
	}
}
