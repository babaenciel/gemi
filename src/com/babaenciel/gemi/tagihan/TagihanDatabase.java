package com.babaenciel.gemi.tagihan;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.babaenciel.gemi.utils.DBAdapter;

public class TagihanDatabase {	
	DBAdapter dbAdapter;
	
	public TagihanDatabase(Context context) {
		dbAdapter = new DBAdapter(context);
	}
	
	public void insertTagihan(String nama, int jumlah, String tanggal_deadline, int lunas) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("nama", nama);
		cv.put("jumlah", jumlah);
		cv.put("tanggal_deadline", tanggal_deadline);
		cv.put("lunas", lunas);
		db.insert("Tagihan", null, cv);
		db.close();
	}
	
	public void lunas(int id_tagihan, int lunasBoolean) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("lunas", lunasBoolean);
		db.update("Tagihan", cv, "id_tagihan = " + id_tagihan, null);
		db.close();
	}
	
	public ArrayList<TagihanObject> getTagihanFromMonthYear(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Tagihan where " +
				"strftime('%m', tanggal_deadline) = '" + month + "' and " +
				"strftime('%Y', tanggal_deadline) = '" + year + "'";
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<TagihanObject> object = new ArrayList<TagihanObject>();
		
		if(cursor.moveToFirst()) {
			do {
				object.add(new TagihanObject(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4)));
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		
		return object;
	}

    public void deleteTagihan(int id_tagihan) {
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        db.delete("Tagihan", "id_tagihan = " + id_tagihan, null);
        db.close();
    }
	
	public void dbClose() {
		dbAdapter.close();
	}
} 
