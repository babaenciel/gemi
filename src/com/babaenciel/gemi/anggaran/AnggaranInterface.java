package com.babaenciel.gemi.anggaran;

public interface AnggaranInterface {
	public void onDetail(int id_anggaran);
	public void onDelete(int id_anggaran);
	public void onUpdate(int id_anggaran);
	public void restartActivity();
}
