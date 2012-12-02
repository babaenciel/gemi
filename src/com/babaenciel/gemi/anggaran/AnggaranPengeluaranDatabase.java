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
	
	public int insertAnggaranPengeluaran(String nama, int nominal, String tanggal, int id_anggaran) {
		SQLiteDatabase db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("nama", nama);
		cv.put("nominal", nominal);
		cv.put("tanggal", tanggal);
		cv.put("id_anggaran", id_anggaran);
		long id = db.insert("Anggaran_Pengeluaran", null, cv);
		
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
		
		return (int) id;
	}
		
	
	public ArrayList<AnggaranPengeluaranObject> getAnggaranPengeluaranById(int id_anggaran) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Anggaran_Pengeluaran where id_anggaran = " + id_anggaran + 
				" order by strftime('%d', tanggal) desc";
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
	
	public AnggaranPengeluaranObject getAnggaranPengeluaranSingle(int id_anggaran_pengeluaran) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select * from Anggaran_Pengeluaran where id_anggaran_pengeluaran = " + id_anggaran_pengeluaran;
		Cursor cursor = db.rawQuery(query, null);
		AnggaranPengeluaranObject object = null;
		
		if(cursor.moveToFirst()) {
			object = new AnggaranPengeluaranObject(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4));
		}
		
		cursor.close();
		db.close();
		return object;
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
	
	public int getAnggaranPengeluaranTotal(String month, String year) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select sum(nominal) from Anggaran_Pengeluaran where " +
				"strftime('%m', tanggal) = '" + month + "' and " +
				"strftime('%Y', tanggal) = '" + year + "'";
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
	
	//method ini digunakan pada spinner di form anggaran pengeluaran,
	//method ini berguna untuk mengambil tanggal dari suatu id.
	//setelah mendapatkan tanggal, kemudian berlanjut ke method getAnggaranNamaAll.
	public String getAnggaranTanggal(int id_anggaran) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select tanggal from Anggaran where id_anggaran = " + id_anggaran;
		Cursor cursor = db.rawQuery(query, null);
		
		String tanggal = null;
		
		if(cursor.moveToFirst()) {
			tanggal = cursor.getString(0);
		}
		
		db.close();
		cursor.close();
		return tanggal;
	}
	
	//untuk mengambil anggaran-anggaran yang memiliki tanggal sama dengan suatu id.
	public Cursor getAnggaranNamaAll(String tanggal) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select id_anggaran as _id, nama from Anggaran where tanggal = '" + tanggal + "'";
		Cursor cursor = db.rawQuery(query, null);
		return cursor;
	}
	
	//digunakan pada autocomplete insert form. untuk mengambil semua nama anggaran pengeluaran yg ada.
	public ArrayList<String> getAnggaranPengeluaranNamaAll() {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select nama from Anggaran_Pengeluaran";
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
	
	//digunakan pada autocomplete insert form.
	//biasanya digunakan setelah getAnggaranPengeluaranNamaAll
	public int getAnggaranPengeluaranIdFromNama(String nama) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select id_anggaran_pengeluaran from Anggaran_Pengeluaran where " +
				"nama = '" + nama + "'";
		Cursor cursor = db.rawQuery(query, null);
		int id = 0;
		
		if(cursor.moveToFirst()) {
			id = cursor.getInt(0);
		}
		
		return id;
	}
		
	
	public String getAnggaranNamaSingle(int id_anggaran) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select nama from Anggaran where id_anggaran = " + id_anggaran;
		Cursor cursor = db.rawQuery(query, null);
		String nama = null;
		if(cursor.moveToFirst()) {
			nama = cursor.getString(0);
		}
		db.close();
		cursor.close();
		return nama;
	}
	
	public AnggaranObject getAnggaranJumlahAnggaranDanPengeluaran(int id_anggaran) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select jumlah_anggaran, jumlah_pengeluaran from Anggaran where id_anggaran = " + id_anggaran;
		Cursor cursor = db.rawQuery(query, null);
		AnggaranObject object = null;		
		if(cursor.moveToFirst()) {
			object = new AnggaranObject(0, null, cursor.getInt(1), cursor.getInt(0), null);
		}
		cursor.close();
		db.close();
		return object;
	}
	
	public void deleteAnggaranPengeluaran(int id_anggaran_pengeluaran, int id_anggaran) {
		SQLiteDatabase db = dbAdapter.getReadableDatabase();
		String query = "select nominal from Anggaran_Pengeluaran where " +
				"id_anggaran_pengeluaran = " + id_anggaran_pengeluaran;
		Cursor cursor = db.rawQuery(query, null);
		int nominal = 0;
		if(cursor.moveToFirst()) {
			nominal = cursor.getInt(0);
		}
		cursor.close();
		
		db = dbAdapter.getReadableDatabase();
		db.delete("Anggaran_Pengeluaran", "id_anggaran_pengeluaran = "+id_anggaran_pengeluaran, null);
		
		query = "select jumlah_pengeluaran from Anggaran where " +
				"id_anggaran = "+ id_anggaran;
		cursor = db.rawQuery(query, null);
		int jumlah_pengeluaran = 0;
		if(cursor.moveToFirst()) {
			jumlah_pengeluaran = cursor.getInt(0);
		}
		cursor.close();
		jumlah_pengeluaran = jumlah_pengeluaran - nominal;
		
		db = dbAdapter.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("jumlah_pengeluaran", jumlah_pengeluaran);
		db.update("Anggaran", cv, "id_anggaran = "+id_anggaran, null);
		db.close();
	}
	
	public void dbClose() {
		dbAdapter.close();
	}
	
}
