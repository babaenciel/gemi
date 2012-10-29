package com.babaenciel.gemi.hutang;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.babaenciel.gemi.utils.DBAdapter;

public class HutangDatabase {
	
	private DBAdapter dbAdapter;

	public HutangDatabase(Context context) {
		dbAdapter = new DBAdapter(context);
	}
	
	public void insertHutang(String nama, int jumlah_cicilan, int jumlah_hutang, String tanggal_deadline) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("nama", nama);
		cv.put("jumlah_cicilan", jumlah_cicilan);
		cv.put("jumlah_hutang", jumlah_hutang);
		cv.put("tanggal_deadline", tanggal_deadline);
		db.insert("Hutang", null, cv);		
		db.close();
	}
	
	public ArrayList<HutangObject> getHutangFromMonthYear(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Hutang where " +
				"strftime('%m', tanggal_deadline) = '" + month + "' and " +
				"strftime('%Y', tanggal_deadline) = '" + year + "'";		
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<HutangObject> object = new ArrayList<HutangObject>();
		
		if(cursor.moveToFirst()) {
			do {
				object.add(new HutangObject(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		
		return object;		
	}
	
	public int getJumlahCicilan(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select sum(jumlah_cicilan) from Hutang where " +
				"strftime('%m', tanggal_deadline) = '" + month + "' and " +
				"strftime('%Y', tanggal_deadline) = '" + year + "'";
		Cursor cursor = db.rawQuery(query, null);
		int jumlah_cicilan = 0;
		if(cursor.moveToFirst()) {
			jumlah_cicilan = cursor.getInt(0);
		}
		
		cursor.close();
		db.close();
		
		return jumlah_cicilan;
		
	}
	
	public int getJumlahHutang(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select sum(jumlah_hutang) from Hutang where " +
				"strftime('%m', tanggal_deadline) = '" + month + "' and " +
				"strftime('%Y', tanggal_deadline) = '" + year + "'";
		Cursor cursor = db.rawQuery(query, null);
		int jumlah_hutang = 0;
		if(cursor.moveToFirst()) {
			jumlah_hutang = cursor.getInt(0);
		}
		
		cursor.close();
		db.close();
		
		return jumlah_hutang;
		
	}
	
	public void dbClose() {
		dbAdapter.close();
	}
}
