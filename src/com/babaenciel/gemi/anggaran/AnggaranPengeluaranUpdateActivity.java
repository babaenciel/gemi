package com.babaenciel.gemi.anggaran;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.R;

public class AnggaranPengeluaranUpdateActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;	
	private AutoCompleteTextView nama;
	private EditText nominal;
	private Spinner spinner;
	private EditText tanggal;
	private AnggaranPengeluaranDatabase db;
	private int id_anggaran_pengeluaran;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setTitle("ANGGARAN PENGELUARAN UPDATE");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anggaran_pengeluaran_insert_form_activity);
		
		id_anggaran_pengeluaran = getIntent().getExtras().getInt("id_anggaran_pengeluaran");
		
		nama = (AutoCompleteTextView) findViewById(R.id.anggaran_pengeluaran_form_edittext_nama);
		nominal = (EditText) findViewById(R.id.anggaran_pengeluaran_form_edittext_nominal);
		spinner = (Spinner) findViewById(R.id.anggaran_pengeluaran_form_spinner);
		tanggal = (EditText) findViewById(R.id.anggaran_pengeluaran_form_edittext_tanggal);
		Button button = (Button) findViewById(R.id.anggaran_pengeluaran_form_submit_button);
		
		db = new AnggaranPengeluaranDatabase(this);
		AnggaranPengeluaranObject object = db.getAnggaranPengeluaranSingle(id_anggaran_pengeluaran);
	}
}
