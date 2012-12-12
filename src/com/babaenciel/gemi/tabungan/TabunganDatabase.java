package com.babaenciel.gemi.tabungan;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.babaenciel.gemi.utils.DBAdapter;

public class TabunganDatabase {	
	DBAdapter dbAdapter;
	
	public TabunganDatabase(Context context) {
		dbAdapter = new DBAdapter(context);
	}
	
	public void insertTabungan(String nama, int nominal, String tanggal) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("nama", nama);
		cv.put("nominal", nominal);
		cv.put("tanggal", tanggal);
		db.insert("Tabungan", null, cv);		
	}
	
	public TabunganObject getTabunganSingle(int id_tabungan) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Tabungan where id_tabungan = " + id_tabungan;
				
		Cursor cursor = db.rawQuery(query, null);
		TabunganObject object = null;
		
		if(cursor.moveToFirst()) {
			object = new TabunganObject(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
		}
		
		cursor.close();
		db.close();
		
		return object;
	}	
	
	public ArrayList<TabunganObject> getTabunganAll(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Tabungan where " +
				"strftime('%m', tanggal) = '" + month + "' and " +
				"strftime('%Y', tanggal) = '" + year + "' " +
				"order by strftime('%d', tanggal) desc";
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<TabunganObject> list = new ArrayList<TabunganObject>();
		
		if(cursor.moveToFirst()) {
			do {
				list.add(new TabunganObject(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		
		return list;
	}
	
	public ArrayList<TabunganObject> getTabunganAll() {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Tabungan";
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<TabunganObject> list = new ArrayList<TabunganObject>();
		
		if(cursor.moveToFirst()) {
			do {
				list.add(new TabunganObject(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		
		return list;
	}
	
	public int getTabunganTotal() {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select sum(nominal) from Tabungan";
				
		Cursor cursor = db.rawQuery(query, null);
		int totalTabungan = 0;
		
		if(cursor.moveToFirst()) {
			totalTabungan = cursor.getInt(0);
		}
		
		cursor.close();
		db.close();
		
		return totalTabungan;
		
	}
	
	public int getTabunganTotal(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select sum(nominal) from Tabungan where " +
				"strftime('%m', tanggal) = '" + month + "' and " +
				"strftime('%Y', tanggal) = '" + year + "'";
		Cursor cursor = db.rawQuery(query, null);
		int totalTabungan = 0;
		
		if(cursor.moveToFirst()) {
			totalTabungan = cursor.getInt(0);
		}
		
		cursor.close();
		db.close();
		
		return totalTabungan;
		
	}
	
	public void updateTabungan(int id_tabungan, String nama, int nominal, String tanggal) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("nama", nama);
		cv.put("nominal", nominal);
		cv.put("tanggal", tanggal);
		db.update("Tabungan", cv, "id_tabungan = " + id_tabungan, null);
	}
	
	public void deleteTabungan(int id_tabungan) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		db.delete("Tabungan", "id_tabungan = " + id_tabungan, null);
	}
	
	public void deleteTabunganAll() {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		db.delete("Tabungan", null, null);
	}
	
	public void dbClose() {
		dbAdapter.close();
	}
}
