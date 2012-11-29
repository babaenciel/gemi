package com.babaenciel.gemi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.anggaran.AnggaranActivity;
import com.babaenciel.gemi.hutang.HutangActivity;
import com.babaenciel.gemi.kategori.KategoriDatabase;
import com.babaenciel.gemi.pemasukan.PemasukanActivity;
import com.babaenciel.gemi.tabungan.TabunganActivity;
import com.babaenciel.gemi.tabungan.TabunganDatabase;
import com.babaenciel.gemi.tagihan.TagihanActivity;

public class GemiDashboard extends SherlockActivity implements OnClickListener {
	private static final int THEME = R.style.Theme_Sherlock;
	Context context = this;
	View pemasukan;
	View anggaran;	
	View tagihan;
	View hutang;
	View tabungan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setTitle("DASHBOARD");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		/*KategoriDatabase db = new KategoriDatabase(this);
		db.insertKategori("Gaji");
		db.insertKategori("Hadiah");
		db.insertKategori("Penjualan");*/
		
		/*TabunganDatabase db = new TabunganDatabase(this);
		db.insertTabungan("babaenciel", 150000, "2012-11-29");
		db.insertTabungan("babaenciel2", 200000, "2012-11-29");
		db.insertTabungan("babaenciel3", 100000, "2012-11-29");
		db.dbClose();*/
		
		pemasukan = (View) findViewById(R.id.dashboard_pemasukan_block);
        pemasukan.setOnClickListener(this);
        anggaran = (View) findViewById(R.id.dashboard_anggaran_block);
        anggaran.setOnClickListener(this);
        tagihan = (View) findViewById(R.id.dashboard_tagihan_block);
        tagihan.setOnClickListener(this);
        hutang = (View) findViewById(R.id.dashboard_hutang_block);
        hutang.setOnClickListener(this);
        tabungan = (View) findViewById(R.id.dashboard_tabungan_block);
        tabungan.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		if(v == pemasukan) {
			Intent i = new Intent(context, PemasukanActivity.class);
			startActivity(i);
		}else if(v == anggaran) {
			Intent i = new Intent(context, AnggaranActivity.class);
			startActivity(i);
		}else if(v == tagihan) {
			Intent i = new Intent(context, TagihanActivity.class);
			startActivity(i);
		}else if(v == hutang) {
			Intent i = new Intent(context, HutangActivity.class);
			startActivity(i);
		}else {
			Intent i = new Intent(context, TabunganActivity.class);
			startActivity(i);
		}
		
		
	}   
}
