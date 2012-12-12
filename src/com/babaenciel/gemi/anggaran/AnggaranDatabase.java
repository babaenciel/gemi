package com.babaenciel.gemi.anggaran;

import java.util.ArrayList;

import com.babaenciel.gemi.utils.DBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AnggaranDatabase {
	
	private Context context;
	private DBAdapter dbAdapter;
	
	public AnggaranDatabase(Context context) {
		this.context = context;
		dbAdapter = new DBAdapter(context);
	}
	
	public void insertAnggaran(String nama, int jumlah_anggaran, int jumlah_pengeluaran, String tanggal) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("nama", nama);
		cv.put("jumlah_anggaran", jumlah_anggaran);
		cv.put("jumlah_pengeluaran", jumlah_pengeluaran);
		cv.put("tanggal", tanggal);
		db.insert("Anggaran", null, cv);
	}
		
	public ArrayList<AnggaranObject> getAnggaranFromMonthYear(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Anggaran where " +
				"strftime('%m', tanggal) = '"+month+"' and " +
				"strftime('%Y', tanggal) = '"+year+"'";
		Cursor cursor = db.rawQuery(query, null);
		
		ArrayList<AnggaranObject> values = new ArrayList<AnggaranObject>();
		if(cursor.moveToFirst()) {
			do {
				//0: id_anggaran, 1: nama, 2: jumlah_anggaran, 3: jumlah_pengeluaran, 4: tanggal
				values.add(new AnggaranObject(cursor.getInt(0), cursor.getString(1), cursor.getInt(3), cursor.getInt(2), cursor.getString(4)));
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		return values;
	}
	
	public int getAnggaranTotal(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select sum(jumlah_anggaran) from Anggaran where " +
				"strftime('%m', tanggal) = '"+month+"' and " +
				"strftime('%Y', tanggal) = '"+year+"'";
		Cursor cursor = db.rawQuery(query, null);
		
		int jumlah_anggaran = 0;
		
		if(cursor.moveToFirst()) {			
			jumlah_anggaran = cursor.getInt(0);		
		}	
		
		cursor.close();
		db.close();
		return jumlah_anggaran; 
	}	
	
	public int getAnggaranPengeluaranTotal(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select sum(jumlah_pengeluaran) from Anggaran where " +
				"strftime('%m', tanggal) = '"+month+"' and " +
				"strftime('%Y', tanggal) = '"+year+"'";
		Cursor cursor = db.rawQuery(query, null);
		
		int jumlah_pengeluaran = 0;
		
		if(cursor.moveToFirst()) {			
			jumlah_pengeluaran = cursor.getInt(0);		
		}	
		
		cursor.close();
		db.close();
		return jumlah_pengeluaran;
	}
	
	public AnggaranObject getAnggaranSingle(int id_anggaran) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Anggaran where id_anggaran = " + id_anggaran;
		Cursor cursor = db.rawQuery(query, null);
		AnggaranObject object = null;
		
		if(cursor.moveToFirst()) {
			object = new AnggaranObject(cursor.getInt(0), cursor.getString(1), cursor.getInt(3), cursor.getInt(2), cursor.getString(4));
		}
		
		cursor.close();
		db.close();
		
		return object;
	}
	
	public ArrayList<String> getAnggaranNamaAll() {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select nama from Anggaran";
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<String> nama = new ArrayList<String>();
		
		if(cursor.moveToFirst()) {
			do {
				nama.add(cursor.getString(0));
			}while(cursor.moveToNext());
			
		}
		
		cursor.close();
		db.close();
		
		return nama;
	}
	
	//biasanya digunakan setelah getAnggaranNamaAll.
	//digunakan pada autocomplete insert, update	
	public int getAnggaranIdFromNama(String nama) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select id_anggaran from Anggaran where nama = '" + nama + "'";
		Cursor cursor = db.rawQuery(query, null);
		int id_anggaran = 0;
		
		if(cursor.moveToFirst()) {
			id_anggaran = cursor.getInt(0);
		}
		
		cursor.close();
		db.close();
		
		return id_anggaran;		
	}
	
	public ArrayList<AnggaranObject> getAnggaranAll() {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Anggaran";
		Cursor cursor = db.rawQuery(query, null);
		
		ArrayList<AnggaranObject> values = new ArrayList<AnggaranObject>();
		if(cursor.moveToFirst()) {
			do {
				//0: id_anggaran, 1: nama, 2: jumlah_anggaran, 3: jumlah_pengeluaran, 4: tanggal
				values.add(new AnggaranObject(cursor.getInt(0), cursor.getString(1), cursor.getInt(3), cursor.getInt(2), cursor.getString(4)));
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		return values;
	}
	
	public void updateAnggaran(int id_anggaran, String nama, int jumlah_anggaran, String tanggal) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("nama", nama);
		cv.put("jumlah_anggaran", jumlah_anggaran);
		cv.put("tanggal", tanggal);
		db.update("Anggaran", cv, "id_anggaran = " + id_anggaran, null);
		db.close();
	}
	
	public void deleteAnggaran(int id_anggaran) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();		
		db.beginTransaction();
		db.delete("Anggaran", "id_anggaran = " + id_anggaran, null);
		db.setTransactionSuccessful();
		db.endTransaction();
		//db.delete("Pemasukan", "id_pemasukan = " + id, null);
		db.close();		
	}
	
	public void dbClose() {
		dbAdapter.close();
	}
	
	
}
