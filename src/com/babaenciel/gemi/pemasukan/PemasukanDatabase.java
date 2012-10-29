package com.babaenciel.gemi.pemasukan;

import java.util.ArrayList;

import com.babaenciel.gemi.kategori.KategoriDatabase;
import com.babaenciel.gemi.utils.DBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PemasukanDatabase {
	DBAdapter dbAdapter;
	//private KategoriDatabase kategoriDb;
	
	public PemasukanDatabase(Context context) {
		dbAdapter = new DBAdapter(context);		
	}
	
	public void insertPemasukan(String nama, int nominal, String tanggal, int id_kategori) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();		
		cv.put("nama", nama);
		cv.put("nominal", nominal);
		cv.put("tanggal", tanggal);
		cv.put("id_kategori", id_kategori);
		db.insert("Pemasukan", null, cv);		
	}
	
	//untuk view by date
	public Cursor getPemasukanFromMonthYear(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "SELECT * " +
				"FROM Pemasukan " +
				"WHERE strftime('%m', tanggal) = '"+month+"' and " +
				"strftime('%Y', tanggal) = '"+year+"' " +
				"order by strftime('%d', tanggal) desc";
		//Log.d("query", query);
		Cursor cursor = db.rawQuery(query, null);			
		return cursor;
	}
	
	public PemasukanObject getPemasukanSingle(int id_pemasukan) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Pemasukan, Kategori where id_pemasukan = " + id_pemasukan + " and " +
				"Pemasukan.id_kategori = Kategori.id_kategori";
		Cursor cursor = db.rawQuery(query, null);		
		PemasukanObject object = null;
		
		if(cursor.moveToFirst()) {
			object = new PemasukanObject(cursor.getInt(0), cursor.getString(1), cursor.getString(3), cursor.getInt(2), cursor.getInt(4));
			object.setNamaKategori(cursor.getString(6));
		}
		
		cursor.close();
		db.close();
		
		return object;
		
	}
	
	//untuk view by categori
	public Cursor getPemasukanFromMonthYear2(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "SELECT * " +
				"FROM Pemasukan, Kategori " +
				"WHERE strftime('%m', tanggal) = '"+month+"' and " +
				"strftime('%Y', tanggal) = '"+year+"' and " +
				"Pemasukan.id_kategori = Kategori.id_kategori " +
				"order by Kategori.nama";
		//Log.d("query", query);
		Cursor cursor = db.rawQuery(query, null);			
		return cursor;
	}
	
	public Cursor getPemasukanAll() {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "SELECT * from Pemasukan, Kategori where Pemasukan.id_kategori = Kategori.id_kategori";
		Cursor cursor = db.rawQuery(query, null);
		return cursor;
	}
	
	public int getPemasukanTotalSum(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "SELECT sum(nominal) " +
				"FROM Pemasukan, Kategori " +
				"WHERE strftime('%m', tanggal) = '"+month+"' and " +
				"strftime('%Y', tanggal) = '"+year+"' and " +
				"Pemasukan.id_kategori = Kategori.id_kategori " +
				"order by strftime('%d', tanggal) desc";
		Cursor cursor = db.rawQuery(query, null);
		int total;
		if(cursor.moveToFirst()) {
			total = cursor.getInt(0);
		}else {
			total = 0;
		}
				
		cursor.close();		
		return total;
	}
	
	//digunakan untuk mengambil kategori yang hanya muncul pada tabel pemasukan.
	//hal ini dilakukan untuk mengurangi loop ke semua kategori yang ada.
	public ArrayList<String> getPemasukanKategori(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();		
		String query = "SELECT distinct Kategori.nama from Pemasukan, Kategori where " +
				"strftime('%m', tanggal) = '"+month+"' and strftime('%Y', tanggal) = '"+year+"' and " +
				"Pemasukan.id_kategori = Kategori.id_kategori " +
				"order by Kategori.nama";
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<String> kategori = new ArrayList<String>();
		
		if(cursor.moveToFirst()) {
			do {
				kategori.add(cursor.getString(0));
			}while(cursor.moveToNext());			
		}
		
		cursor.close();
		db.close();
		return kategori;		
	}
	
	public ArrayList<String> getKategori() {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select nama from Kategori";
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<String> kategori = new ArrayList<String>();
		
		if(cursor.moveToFirst()) {
			do {
				kategori.add(cursor.getString(0));
			}while(cursor.moveToNext());			
		}
		
		cursor.close();
		db.close();
		return kategori;
	}
	
	public int getIdKategori(String nama) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select id_kategori from Kategori where nama = '"+nama+"'";
		Cursor cursor = db.rawQuery(query, null);
		int id = 0;
		if(cursor.moveToFirst()) {			
			id = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		return id;
	}
	
	public void updatePemasukan(int id, String nama, int nominal, String tanggal, int id_kategori) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();		
		cv.put("nama", nama);
		cv.put("nominal", nominal);
		cv.put("tanggal", tanggal);
		cv.put("id_kategori", id_kategori);
		db.update("Pemasukan", cv, "id_pemasukan = "+id, null);
	}
	
	public void deletePemasukan(int id) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		Log.d("id", ""+id);
		db.beginTransaction();
		db.delete("Pemasukan", "id_pemasukan = " + id, null);
		db.setTransactionSuccessful();
		db.endTransaction();
		//db.delete("Pemasukan", "id_pemasukan = " + id, null);
		db.close();
		Log.d("delete","sukses");
	}
	
	public void dbClose() {
		dbAdapter.close();
	}
}
