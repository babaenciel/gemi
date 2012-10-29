package com.babaenciel.gemi.hutang;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.babaenciel.gemi.utils.DBAdapter;

public class HutangCicilanDatabase {
	
	DBAdapter dbAdapter;
	
	public HutangCicilanDatabase(Context context) {
		dbAdapter = new DBAdapter(context);
	}
	
	public void insertHutangCicilan(String nama, int nominal, String tanggal, int id_hutang) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("nama", nama);
		cv.put("nominal", nominal);
		cv.put("tanggal", tanggal);
		cv.put("id_hutang", id_hutang);
		db.insert("Hutang_Cicilan", null, cv);
		db.close();
		
		db = dbAdapter.getReadableDatabase();
		String query = "select sum(nominal) from Hutang_Cicilan where id_hutang = " + id_hutang;
		int jumlah_cicilan = 0;
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()) {
			jumlah_cicilan = cursor.getInt(0);
		}		
		cursor.close();
		db.close();
		
		db = dbAdapter.getWritableDatabase();
		ContentValues cv2 = new ContentValues();
		cv2.put("jumlah_cicilan", jumlah_cicilan);
		db.update("Hutang", cv2, "id_hutang = " + id_hutang, null);
		db.close();
	}
	
	public ArrayList<HutangCicilanObject> getAllHutangCicilan(int id_hutang) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Hutang_Cicilan where id_hutang = " + id_hutang;
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<HutangCicilanObject> object = new ArrayList<HutangCicilanObject>();
		if(cursor.moveToFirst()) {
			do {
				object.add(new HutangCicilanObject(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4)));
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return object;
	}
	
	public void dbClose() {
		dbAdapter.close();
	}
}
