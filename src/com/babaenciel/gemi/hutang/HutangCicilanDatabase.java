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

	public void insertHutangCicilan(String nama, int nominal, String tanggal,
			int id_hutang) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("nama", nama);
		cv.put("nominal", nominal);
		cv.put("tanggal", tanggal);
		cv.put("id_hutang", id_hutang);
		db.insert("Hutang_Cicilan", null, cv);
		db.close();

		db = dbAdapter.getReadableDatabase();
		String query = "select sum(nominal) from Hutang_Cicilan where id_hutang = "
				+ id_hutang;
		int jumlah_cicilan = 0;
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
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

	public int getHutangCicilanTotal(int id_hutang) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select sum(nominal) from Hutang_Cicilan where id_hutang = "
				+ id_hutang;
		Cursor cursor = db.rawQuery(query, null);
		int totalCicilan = 0;
		if (cursor.moveToFirst()) {
			totalCicilan = cursor.getInt(0);
		}

		cursor.close();
		db.close();

		return totalCicilan;
	}

	public ArrayList<HutangCicilanObject> getAllHutangCicilan(int id_hutang) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Hutang_Cicilan where id_hutang = "
				+ id_hutang;
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<HutangCicilanObject> object = new ArrayList<HutangCicilanObject>();
		if (cursor.moveToFirst()) {
			do {
				object.add(new HutangCicilanObject(cursor.getInt(0), cursor
						.getString(1), cursor.getInt(2), cursor.getString(3),
						cursor.getInt(4)));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return object;
	}

	public HutangCicilanObject getHutangCicilanSingle(int id_hutang_cicilan) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Hutang_Cicilan where id_hutang_cicilan = "
				+ id_hutang_cicilan;
		Cursor cursor = db.rawQuery(query, null);
		HutangCicilanObject object = null;

		if (cursor.moveToFirst()) {
			object = new HutangCicilanObject(cursor.getInt(0),
					cursor.getString(1), cursor.getInt(2), cursor.getString(3),
					cursor.getInt(4));
		}

		cursor.close();
		db.close();

		return object;
	}

	public String getHutangNama(int id_hutang) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select nama from Hutang where id_hutang = " + id_hutang;
		Cursor cursor = db.rawQuery(query, null);
		String nama = null;
		if (cursor.moveToFirst()) {
			nama = cursor.getString(0);
		}

		cursor.close();
		db.close();

		return nama;
	}

	public Cursor getHutangForSpinner(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select id_hutang as _id, nama from Hutang where "
				+ "strftime('%m', tanggal) = '" + month + "' and "
				+ "strftime('%Y', tanggal) = '" + year + "'";
		return db.rawQuery(query, null);

	}

	public void updateHutangCicilan(int id_hutang_cicilan, String nama, int nominal, String tanggal, int id_hutang) {
		//ngambil nominal cicilan lama dari hutang cicilan yang akan diupdate
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select nominal from Hutang_Cicilan where id_hutang_cicilan = " + id_hutang_cicilan;
		Cursor cursor = db.rawQuery(query, null);
		
		int cicilan = 0;
		
		if(cursor.moveToFirst()) {
			cicilan = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		
		//ngambil jumlah cicilan di hutang yang hutang cicilannya akan di update
		db = dbAdapter.getReadableDatabase();
		query = "select jumlah_cicilan from Hutang where id_hutang = " + id_hutang;
		cursor = db.rawQuery(query, null);
		
		int jumlah_cicilan = 0;
		
		if(cursor.moveToFirst()) {
			jumlah_cicilan = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		
		//kurangi jumlah cicilan di hutang dengan cicilan yang akan di update
		jumlah_cicilan = jumlah_cicilan - cicilan;
		
		
		//update hutang dengan nilai cicilan yang baru
		jumlah_cicilan = jumlah_cicilan + nominal;
		
		db = dbAdapter.getWritableDatabase();
		ContentValues cv3 = new ContentValues();
		cv3.put("jumlah_cicilan", jumlah_cicilan);
		db.update("Hutang", cv3, "id_hutang = " + id_hutang, null);
		db.close();
				
		//update cicilan
		db = dbAdapter.getWritableDatabase();
		ContentValues cv2 = new ContentValues();
		cv2.put("nama", nama);
		cv2.put("nominal", nominal);
		cv2.put("tanggal", tanggal);
		cv2.put("id_hutang", id_hutang);
		db.update("Hutang_Cicilan", cv2, "id_hutang_cicilan = " + id_hutang_cicilan, null);
		db.close();
		
		
	}

	public void deleteHutangCicilan(int id_hutang_cicilan, int id_hutang) {
		// ngambil nominal cicilan dari hutang cicilan yang akan didelete
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select nominal from Hutang_Cicilan where id_hutang_cicilan = "
				+ id_hutang_cicilan;
		Cursor cursor = db.rawQuery(query, null);

		int cicilan = 0;

		if (cursor.moveToFirst()) {
			cicilan = cursor.getInt(0);
		}
		cursor.close();
		db.close();

		// ngambil jumlah cicilan di hutang yang hutang cicilannya akan didelete
		db = dbAdapter.getReadableDatabase();
		query = "select jumlah_cicilan from Hutang where id_hutang = "
				+ id_hutang;
		cursor = db.rawQuery(query, null);

		int jumlah_cicilan = 0;

		if (cursor.moveToFirst()) {
			jumlah_cicilan = cursor.getInt(0);
		}
		cursor.close();
		db.close();

		// jumlah cicilan hutangnya dikurangi dengan nominal cicilan yang akan
		// didelete
		jumlah_cicilan = jumlah_cicilan - cicilan;

		// update jumlah cicilan hutang
		db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("jumlah_cicilan", jumlah_cicilan);
		db.update("Hutang", cv, "id_hutang = " + id_hutang, null);
		db.close();

		// delete cicilan
		db = dbAdapter.getWritableDatabase();
		db.delete("Hutang_Cicilan", "id_hutang_cicilan = " + id_hutang_cicilan,
				null);
		db.close();
	}

	public void dbClose() {
		dbAdapter.close();
	}
}
