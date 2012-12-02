package com.babaenciel.gemi;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.anggaran.AnggaranActivity;
import com.babaenciel.gemi.anggaran.AnggaranDatabase;
import com.babaenciel.gemi.anggaran.AnggaranPengeluaranDatabase;
import com.babaenciel.gemi.hutang.HutangActivity;
import com.babaenciel.gemi.hutang.HutangCicilanDatabase;
import com.babaenciel.gemi.hutang.HutangDatabase;
import com.babaenciel.gemi.hutang.HutangObject;
import com.babaenciel.gemi.kategori.KategoriDatabase;
import com.babaenciel.gemi.pemasukan.PemasukanActivity;
import com.babaenciel.gemi.pemasukan.PemasukanDatabase;
import com.babaenciel.gemi.tabungan.TabunganActivity;
import com.babaenciel.gemi.tabungan.TabunganDatabase;
import com.babaenciel.gemi.tagihan.TagihanActivity;
import com.babaenciel.gemi.tagihan.TagihanDatabase;
import com.babaenciel.gemi.tagihan.TagihanObject;
import com.babaenciel.gemi.utils.MyDate;

public class GemiDashboard extends SherlockActivity implements OnClickListener {
	private static final int THEME = R.style.Theme_Sherlock;
	Context context = this;
	View pemasukan;
	View anggaran;	
	View tagihan;
	View hutang;
	View tabungan;
	PemasukanDatabase dbPemasukan;
	MyDate myDate;
	private String month;
	private String year;
	DecimalFormat customFormat;
	private AnggaranPengeluaranDatabase dbAnggaranPengeluaran;
	private TagihanDatabase dbTagihan;
	private HutangCicilanDatabase dbHutangCicilan;
	private AnggaranDatabase dbAnggaran;
	private HutangDatabase dbHutang;
	private TabunganDatabase dbTabungan;
	
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
		
		myDate = new MyDate();
		myDate.setNow();
		month = Integer.toString(myDate.month);
		year = Integer.toString(myDate.year);
		
		dbPemasukan = new PemasukanDatabase(this);	
		dbAnggaranPengeluaran = new AnggaranPengeluaranDatabase(this);
		dbTagihan = new TagihanDatabase(this);
		dbHutangCicilan = new HutangCicilanDatabase(this);
		dbAnggaran = new AnggaranDatabase(this);
		dbHutang = new HutangDatabase(this);
		dbTabungan = new TabunganDatabase(this);		
		
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
        
		DecimalFormatSymbols simbol = new DecimalFormatSymbols(); //diformat dulu hasilnya biar ada titiknya		
		simbol.setGroupingSeparator('.');
		customFormat = new DecimalFormat("###,###,###",simbol);	
        
	}
	
	@Override
	protected void onResume() {		
		super.onResume();
		Log.d("resumed", "resumed");
		setAllValue();		
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
	
	public void setAllValue() {
		//set total tabungan
		TextView tabunganTotalView = (TextView) findViewById(R.id.dashboard_tabungan_header_nominal);
		int totalTabungan = dbTabungan.getTabunganTotal();
		tabunganTotalView.setText(customFormat.format(totalTabungan));
		
		//set total pemasukan
        TextView pemasukanNominalView = (TextView) findViewById(R.id.dashboard_pemasukan_nominal);
        int totalPemasukanBulan = dbPemasukan.getPemasukanTotalSum(month, year);
        pemasukanNominalView.setText(customFormat.format(totalPemasukanBulan));
        
        //set total pengeluaran
        int totalAnggaranPengeluaran = dbAnggaranPengeluaran.getAnggaranPengeluaranTotal(month, year);
        int totalTagihan = dbTagihan.getTagihanLunasTotalFromMonthYear(month, year);
        int totalHutangCicilan = dbHutangCicilan.getHutangCicilanTotal(month, year);
        int pengeluaran = totalAnggaranPengeluaran + totalTagihan + totalHutangCicilan;
        TextView pengeluaranNominalView = (TextView) findViewById(R.id.dashboard_pengeluaran_nominal);
        pengeluaranNominalView.setText(customFormat.format(pengeluaran));
        
        //set anggaran
        int anggaranTotal = dbAnggaran.getAnggaranTotal(month, year);
        int anggaranPengeluaranTotal = dbAnggaran.getAnggaranPengeluaranTotal(month, year);
        ProgressBar anggaranBar = (ProgressBar) findViewById(R.id.dashboard_anggaran_progressbar);
        TextView anggaranNominalAtas = (TextView) findViewById(R.id.dashboard_anggaran_nominal_atas);
        TextView anggaranNominalBawah = (TextView) findViewById(R.id.dashboard_anggaran_nominal_bawah);
        anggaranNominalAtas.setText(customFormat.format(anggaranPengeluaranTotal));
        anggaranNominalBawah.setText(customFormat.format(anggaranTotal));
        anggaranBar.setMax(anggaranTotal);
        anggaranBar.setProgress(anggaranPengeluaranTotal);
        
        //set tagihan
        TextView tagihanNamaView = (TextView) findViewById(R.id.dashboard_tagihan_terdekat_judul);
        TextView tagihanTanggalView = (TextView) findViewById(R.id.dashboard_tagihan_terdekat_tanggal);
        TextView tagihanJumlahView = (TextView) findViewById(R.id.dashboard_tagihan_terdekat_nominal);
        TextView tagihanOther = (TextView) findViewById(R.id.dashboard_tagihan_terdekat_other_tagihan);
        ArrayList<TagihanObject> tagihanObject = dbTagihan.getTagihanBelumLunasFromMonthYear(month, year);
        int tanggal = 0;
        int tanggalTemp = 0;
        int selisih = 99;
        int selisihTemp = 0;
        int otherTagihan = 0;
        TagihanObject objTagihanTerdekat = null;
        for(TagihanObject obj : tagihanObject) {
    		String delims = "[-]";
    		String[] tokens = obj.tanggal_deadline.split(delims);
    		tanggalTemp = Integer.parseInt(tokens[2]);	//tokens[2] merujuk ke hari
    		Log.d("tanggalTemp", ""+tanggalTemp);
    		selisihTemp = tanggalTemp - myDate.date;
    		if(selisihTemp >= 0 && selisihTemp < selisih) {    			
    			selisih = selisihTemp;
    			tanggal = tanggalTemp;
    			objTagihanTerdekat = obj;
    			
    		}else if(selisihTemp == selisih) {
    			//ini jika ada tagihan yang bertanggal sama.
    			Log.d("other tagihan naik", "naik");
				otherTagihan += 1;
    		}    		        
        }
        Log.d("selisih", "" + selisih);
        Log.d("tanggal", "" + tanggal);
        Log.d("otherTagihan", "" + otherTagihan);
        
        if(objTagihanTerdekat == null) {
        	tagihanNamaView.setText("tidak ada tagihan terdekat untuk bulan ini");
        	tagihanTanggalView.setVisibility(View.GONE);
        	tagihanJumlahView.setVisibility(View.GONE);
        	tagihanOther.setVisibility(View.GONE);
        }else if(otherTagihan > 0){
        	Log.d("masuk yg other tagihan > 0", "oke");
        	tagihanNamaView.setText(objTagihanTerdekat.nama);
            tagihanTanggalView.setText(objTagihanTerdekat.tanggal_deadline);
            tagihanJumlahView.setText(customFormat.format(objTagihanTerdekat.jumlah));	
            tagihanOther.setText("dan ada " + Integer.toString(otherTagihan) + " tagihan lagi bertanggal sama");
            tagihanTanggalView.setVisibility(View.VISIBLE);
        	tagihanJumlahView.setVisibility(View.VISIBLE);
            tagihanOther.setVisibility(View.VISIBLE);
        }else {
        	Log.d("masuk yg else doank", "oke");
        	tagihanNamaView.setText(objTagihanTerdekat.nama);
            tagihanTanggalView.setText(objTagihanTerdekat.tanggal_deadline);
            tagihanJumlahView.setText(customFormat.format(objTagihanTerdekat.jumlah));	
            tagihanTanggalView.setVisibility(View.VISIBLE);
        	tagihanJumlahView.setVisibility(View.VISIBLE);
            tagihanOther.setVisibility(View.GONE);
        }
        
        //set hutang
        TextView hutangNamaView = (TextView) findViewById(R.id.dashboard_hutang_terdekat_judul);
        TextView hutangTanggalView = (TextView) findViewById(R.id.dashboard_hutang_terdekat_tanggal);
        TextView hutangJumlahView = (TextView) findViewById(R.id.dashboard_hutang_terdekat_nominal);
        TextView hutangOther = (TextView) findViewById(R.id.dashboard_hutang_terdekat_other_hutang);
        ArrayList<HutangObject> hutangObject = dbHutang.getHutangBelumLunasFromMonthYear(month, year);
        tanggal = 0;
        tanggalTemp = 0;
        selisih = 99;
        selisihTemp = 0;
        int otherHutang = 0;
        HutangObject objHutangTerdekat = null;
        for(HutangObject obj : hutangObject) {
    		String delims = "[-]";
    		String[] tokens = obj.tanggal_deadline.split(delims);
    		tanggalTemp = Integer.parseInt(tokens[2]);	//tokens[2] merujuk ke hari
    		Log.d("tanggalTemp", ""+tanggalTemp);
    		selisihTemp = tanggalTemp - myDate.date;
    		if(selisihTemp >= 0 && selisihTemp < selisih) {    			
    			selisih = selisihTemp;
    			tanggal = tanggalTemp;
    			objHutangTerdekat = obj;
    			
    		}else if(selisihTemp == selisih) {
    			//ini jika ada hutang yang bertanggal sama.
    			Log.d("other hutang naik", "naik");
				otherHutang += 1;
    		}    		        
        }
        Log.d("selisih", "" + selisih);
        Log.d("tanggal", "" + tanggal);
        Log.d("otherHutang", "" + otherHutang);
        
        if(objHutangTerdekat == null) {        	
        	hutangNamaView.setText("tidak ada hutang terdekat untuk bulan ini");
        	hutangJumlahView.setVisibility(View.GONE);
        	hutangTanggalView.setVisibility(View.GONE);
        	hutangOther.setVisibility(View.GONE);
        }else if(otherHutang > 0){
        	Log.d("masuk yg other hutang > 0", "oke");
        	hutangNamaView.setText(objHutangTerdekat.nama);
            hutangTanggalView.setText(objHutangTerdekat.tanggal_deadline);
            hutangJumlahView.setText(customFormat.format(objHutangTerdekat.jumlah_hutang));	
            hutangOther.setText("dan ada " + Integer.toString(otherHutang) + " hutang lagi bertanggal sama");
            hutangJumlahView.setVisibility(View.VISIBLE);
        	hutangTanggalView.setVisibility(View.VISIBLE);
            hutangOther.setVisibility(View.VISIBLE);
        }else {
        	Log.d("masuk yg else doank", "oke");
        	hutangNamaView.setText(objHutangTerdekat.nama);
            hutangTanggalView.setText(objHutangTerdekat.tanggal_deadline);
            hutangJumlahView.setText(customFormat.format(objHutangTerdekat.jumlah_hutang));
            hutangJumlahView.setVisibility(View.VISIBLE);
        	hutangTanggalView.setVisibility(View.VISIBLE);
            hutangOther.setVisibility(View.GONE);
        }
        
        
        
	}
}
