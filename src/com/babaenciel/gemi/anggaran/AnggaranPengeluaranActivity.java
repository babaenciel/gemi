package com.babaenciel.gemi.anggaran;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.R;

public class AnggaranPengeluaranActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		setTheme(THEME);
		setTitle("ANGGARAN PENGELUARAN DETAIL");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anggaran_pengeluaran_activity);
		
		int id_anggaran = getIntent().getExtras().getInt("id_anggaran");
		Log.d("id_anggaran", ""+id_anggaran);
		
		//initialize database
		AnggaranPengeluaranDatabase db = new AnggaranPengeluaranDatabase(this);
		
		//nominal format		
		DecimalFormatSymbols simbol = new DecimalFormatSymbols();		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		
		//set total
		int jumlah = db.getAnggaranPengeluaranTotal(id_anggaran);
		TextView totalText = (TextView) findViewById(R.id.anggaran_pengeluaran_nominal);
		totalText.setText(customFormat.format(jumlah));
		
		//set list		
		ArrayList<AnggaranPengeluaranObject> values = db.getAnggaranPengeluaranById(id_anggaran);		
		ListView list = (ListView) findViewById(R.id.anggaran_pengeluaran_list);
		AnggaranPengeluaranListAdapter adapter = new AnggaranPengeluaranListAdapter(this, values);
		list.setAdapter(adapter);
	}
}
