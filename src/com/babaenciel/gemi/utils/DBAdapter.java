package com.babaenciel.gemi.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper {

	private static String dbName = "FinancialPlanning.sqlite";

	public DBAdapter(Context context) {
		super(context, dbName, null, 1);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String tabelPemasukan = 
			"create table Pemasukan (" +
			"id_pemasukan integer primary key autoincrement," +
			"nama text," +
			"nominal integer," +
			"tanggal text," +
			"id_kategori integer)";
		db.execSQL(tabelPemasukan);
		
		String tabelAnggaran = 
			"create table Anggaran (" +
			"id_anggaran integer primary key autoincrement," +
			"nama text," +
			"jumlah_anggaran integer," +
			"jumlah_pengeluaran integer," +
			"tanggal text)";						
		db.execSQL(tabelAnggaran);
		
		String anggaranPengeluaran = 
			"create table Anggaran_Pengeluaran (" +
			"id_anggaran_pengeluaran integer primary key autoincrement," +
			"nama text," +
			"nominal integer," +
			"tanggal text," +
			"id_anggaran integer)";
		db.execSQL(anggaranPengeluaran);
		
		String tabelTagihan = 
			"create table Tagihan (" +
			"id_tagihan integer primary key autoincrement," +
			"nama text," +
			"jumlah integer," +
			"tanggal_deadline text," +
			"lunas int)";
		db.execSQL(tabelTagihan);
		
		String tabelHutang = 
			"create table Hutang (" +
			"id_hutang integer primary key autoincrement," +
			"nama text," +
			"jumlah_cicilan integer," +
			"jumlah_hutang integer," +
			"tanggal_deadline text)";
		db.execSQL(tabelHutang);	
		
		String tabelHutangCicilan = 
			"create table Hutang_Cicilan (" +
			"id_hutang_cicilan integer primary key autoincrement," +
			"nama text," +
			"nominal integer," +
			"tanggal text," +
			"id_hutang int)";
		db.execSQL(tabelHutangCicilan);
		
		String kategori = 
				"create table Kategori (" +
				"id_kategori integer primary key autoincrement, " +
				"nama text)";
		db.execSQL(kategori);
		
		String trigger_delete_pengeluaran_anggaran = 
			"create trigger delete_pengeluaran_anggaran after delete on Anggaran for each row " +
			"begin " +
			"delete from Anggaran_Pengeluaran where id_anggaran = old.id_anggaran;" +
			"end;";
		db.execSQL(trigger_delete_pengeluaran_anggaran);
		 		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*db.execSQL("drop table if exists Kategori");
		db.execSQL("drop table if exists Pemasukan");
		db.execSQL("drop table if exists Anggaran");
		db.execSQL("drop table if exists Anggaran_Pengeluaran");	
		db.execSQL("drop trigger if exists update_id_pemasukan");
		
		String tabelPemasukan = 
				"create table Pemasukan (" +
				"id_pemasukan integer primary key autoincrement," +
				"nama text," +
				"nominal integer," +
				"tanggal text," +
				"id_kategori integer)";
			db.execSQL(tabelPemasukan);
			
			String tabelAnggaran = 
				"create table Anggaran (" +
				"id_anggaran integer primary key autoincrement," +
				"nama text," +
				"jumlah_anggaran integer," +
				"jumlah_pengeluaran integer," +
				"tanggal text)";						
			db.execSQL(tabelAnggaran);
			
			String anggaranPengeluaran = 
				"create table Anggaran_Pengeluaran (" +
				"id_anggaran_pengeluaran integer primary key autoincrement," +
				"nama text," +
				"nominal integer," +
				"tanggal text," +
				"id_anggaran integer)";
			db.execSQL(anggaranPengeluaran);
			
			String kategori = 
					"create table Kategori (" +
					"id_kategori integer primary key autoincrement, " +
					"nama text)";
			db.execSQL(kategori);
			
			String trigger_delete_pengeluaran_anggaran = 
				"create trigger delete_pengeluaran_anggaran after delete on Anggaran for each row " +
				"begin " +
				"delete from Anggaran_Pengeluaran where id_anggaran = old.id_anggaran;" +
				"end;";
			db.execSQL(trigger_delete_pengeluaran_anggaran);
		
			String tabelTagihan = 
				"create table Tagihan (" +
				"id_tagihan integer primary key autoincrement," +
				"nama text," +
				"jumlah integer," +
				"tanggal_deadline text," +
				"lunas int)";
			db.execSQL(tabelTagihan);*/
		
		/*String tabelHutang = 
				"create table Hutang (" +
				"id_hutang integer primary key autoincrement," +
				"nama text," +
				"jumlah_cicilan integer," +
				"jumlah_hutang integer," +
				"tanggal_deadline text)";
			db.execSQL(tabelHutang);*/	
			
			String tabelHutangCicilan = 
					"create table Hutang_Cicilan (" +
					"id_hutang_cicilan integer primary key autoincrement," +
					"nama text," +
					"nominal integer," +
					"tanggal text," +
					"id_hutang int)";
				db.execSQL(tabelHutangCicilan);
	}
	
	

}
