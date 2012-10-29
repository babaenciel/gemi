package com.babaenciel.gemi.pemasukan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.lib.DateSlider;
import com.babaenciel.gemi.lib.DefaultDateSlider;
import com.babaenciel.gemi.utils.MyDate;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PemasukanInsertActivity extends SherlockActivity {
	protected static final int DEFAULTDATESELECTOR_ID = 0;
	public static int THEME = R.style.Theme_Sherlock;
	Context context = this;
	PemasukanInsertActivity pemasukanInsert = this;
	AutoCompleteTextView nama;
	EditText tanggal;
	EditText nominal;
	Spinner spinnerKategori;
	MyDate myDate;
	PemasukanDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setTitle("PEMASUKAN Insert");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pemasukan_insert_form);
		
		db = new PemasukanDatabase(this);
		
		//ArrayList<String> valuesNama = new ArrayList<String>();
		Cursor cursor = db.getPemasukanNamaAll();
		nama = (AutoCompleteTextView) findViewById(R.id.pemasukan_form_edittext_nama);
		String[] from = new String[] {"nama"};
	    int[] to = new int[] {android.R.id.text1};
		//ArrayAdapter<String> adapterNama = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, valuesNama);		
	    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line, cursor, from, to, SimpleCursorAdapter.NO_SELECTION);
		nama.setAdapter(sca);
		nama.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//arg1.get
				Toast.makeText(context, ""+arg3, Toast.LENGTH_SHORT).show();				
				
			}
		});
		
		ArrayList<String> kategori = db.getKategori();
		//Log.d("kategori", kategori.get(0));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kategori);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerKategori = (Spinner) findViewById(R.id.pemasukan_form_spinner_kategori);
		spinnerKategori.setAdapter(adapter);
		
		myDate = new MyDate();
		myDate.setNow();
		tanggal = (EditText) findViewById(R.id.pemasukan_form_edittext_tanggal);
		tanggal.setText(myDate.dateFull1);
		tanggal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DEFAULTDATESELECTOR_ID);		
				
			}
		});
		
		nominal = (EditText) findViewById(R.id.pemasukan_form_edittext_nominal);
		
		Button submit = (Button) findViewById(R.id.pemasukan_form_submit_button);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String namaText = nama.getText().toString();
				String spinnerText = (String) spinnerKategori.getSelectedItem();
				Log.d("spinnertext", spinnerText);
				int nominalAngka = Integer.parseInt(nominal.getText().toString());
				String tanggalText = tanggal.getText().toString();
				String konversiTanggalText = myDate.konversiTanggal1(tanggalText);
				int id_kategori = db.getIdKategori(spinnerText);
				
				db.insertPemasukan(namaText, nominalAngka, konversiTanggalText, id_kategori);
				
				Toast.makeText(context, "insert : "+namaText+" sukses", Toast.LENGTH_SHORT).show();
				pemasukanInsert.finish();				
				Intent i = new Intent(context, PemasukanActivity.class);				
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
			}
		});
	}
	
	//ini fungsi untuk dateslider. hasil kopasan
	DateSlider.OnDateSetListener mDateSetListener =
		new DateSlider.OnDateSetListener() {
        	public void onDateSet(DateSlider view, Calendar selectedDate) {
	            
				// update the dateText view with the corresponding date
        			        		
        		SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
        		SimpleDateFormat simple2 = new SimpleDateFormat("yyyy-MM-dd");
        		Date date1;
        		String date2 = null;
				try {
					date1 = simple2.parse(String.format("%tF", selectedDate, selectedDate, selectedDate));
					date2 = simple.format(date1);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}        		
				        		            
				tanggal.setText(date2);
        	}
		};
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case DEFAULTDATESELECTOR_ID:
			final Calendar c = Calendar.getInstance();
            return new DefaultDateSlider(this,mDateSetListener,c);
		}
		return null;
	}
		
}
