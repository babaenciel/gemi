package com.babaenciel.gemi.anggaran;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.babaenciel.gemi.utils.DBAdapter;

public class AnggaranPengeluaranDatabase {
	private DBAdapter dbAdapter;

	public AnggaranPengeluaranDatabase(Context context) {
		this.dbAdapter = new DBAdapter(context);
	}
	
	public void insertAnggaranPengeluaran(String nama, int nominal, String tanggal, int id_anggaran) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("nama", nama);
		cv.put("nominal", nominal);
		cv.put("tanggal", tanggal);
		cv.put("id_anggaran", id_anggaran);
		db.insert("Anggaran_Pengeluaran", null, cv);
		
		db = dbAdapter.getReadableDatabase();
		String query = "select sum(nominal) from Anggaran_Pengeluaran where id_anggaran = "+id_anggaran;
		Cursor cursor = db.rawQuery(query, null);
		int jumlah_pengeluaran = 0;
		
		if(cursor.moveToFirst()) {			
			jumlah_pengeluaran = cursor.getInt(0);			
		}		
		db.close();
		cursor.close();
		
		db = dbAdapter.getWritableDatabase();
		ContentValues cv2 = new ContentValues();
		cv2.put("jumlah_pengeluaran", jumlah_pengeluaran);
		db.update("Anggaran", cv2, "id_anggaran = " + id_anggaran, null);		
		db.close();
	}
	
	public ArrayList<AnggaranPengeluaranObject> getAnggaranPengeluaranById(int id_anggaran) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Anggaran_Pengeluaran where id_anggaran = " + id_anggaran;
		Cursor cursor = db.rawQuery(query, null);
		
		ArrayList<AnggaranPengeluaranObject> values = new ArrayList<AnggaranPengeluaranObject>();
		if(cursor.moveToFirst()) {
			do {
				//0: id_anggaran_pengeluaran, 1: nama, 2: nominal, 3: tanggal, 4: id_anggaran
				values.add(new AnggaranPengeluaranObject(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4)));
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		
		return values;
	}
	
	public int getAnggaranPengeluaranTotal(int id_anggaran) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select sum(nominal) from Anggaran_Pengeluaran " +
				"where id_anggaran = " + id_anggaran;
		Cursor cursor = db.rawQuery(query, null);
		int jumlah = 0;
		
		if(cursor.moveToFirst()) {
			do {
				jumlah = cursor.getInt(0);
			}while(cursor.moveToNext());
		}
				
		cursor.close();
		db.close();
		
		return jumlah;
	}
	
}
