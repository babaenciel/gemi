package com.babaenciel.gemi.kategori;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.babaenciel.gemi.utils.DBAdapter;

public class KategoriDatabase {

	private DBAdapter dbAdapter;

	public KategoriDatabase(Context context) {
		dbAdapter = new DBAdapter(context);
	}
	
	public void insertKategori(String nama) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("nama", nama);
		db.insert("Kategori", null, cv);
		db.close();
	}
}
