package com.babaenciel.gemi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.babaenciel.gemi.utils.DBAdapter;

public class DashboardDatabase {

	private DBAdapter dbAdapter;

	public DashboardDatabase(Context context) {
		this.dbAdapter = new DBAdapter(context);
	}
	
//	public int getSaldo() {
//		SQLiteDatabase db = dbAdapter.getReadableDatabase();
//		String query = "select sum(nominal) from Pemasukan";
//		Cursor cursor = db.rawQuery(query, null);
//		int totalPemasukan = 0;
//		
//		if(cursor.moveToFirst()) {
//			totalPemasukan = cursor.getInt(0);
//		}
//		
//		//String query = "select * from Anggaran_Pengeluaran"
//	}
	
	
}
