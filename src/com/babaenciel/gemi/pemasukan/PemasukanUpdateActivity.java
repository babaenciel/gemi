package com.babaenciel.gemi.pemasukan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.lib.DateSlider;
import com.babaenciel.gemi.lib.DefaultDateSlider;
import com.babaenciel.gemi.utils.MyDate;

public class PemasukanUpdateActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	private static final int DEFAULTDATESELECTOR_ID = 0;
	int id_pemasukan;
	EditText nama;
	EditText tanggal;
	EditText nominal;
	private PemasukanDatabase db;
	private Spinner spinnerKategori;
	private MyDate myDate;
	Context context = this;
	PemasukanUpdateActivity activity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setTitle("PEMASUKAN Insert");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pemasukan_insert_form);
		
		id_pemasukan = getIntent().getExtras().getInt("id_pemasukan");
		
		db = new PemasukanDatabase(this);
		PemasukanObject object = db.getPemasukanSingle(id_pemasukan);
		
		nama = (EditText) findViewById(R.id.pemasukan_form_edittext_nama);
		nama.setText(object.nama);
		
		ArrayList<String> kategori = db.getKategori();
		//Log.d("kategori", kategori.get(0));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kategori);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerKategori = (Spinner) findViewById(R.id.pemasukan_form_spinner_kategori);
		spinnerKategori.setAdapter(adapter);
		int spinnerPosition = adapter.getPosition(object.nama_kategori);
		spinnerKategori.setSelection(spinnerPosition);
		
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
		nominal.setText(Integer.toString(object.nominal));
		
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
				
				//db.insertPemasukan(namaText, nominalAngka, konversiTanggalText, id_kategori);
				db.updatePemasukan(id_pemasukan, namaText, nominalAngka, konversiTanggalText, id_kategori);
				
				Toast.makeText(context, "update : "+namaText+" sukses", Toast.LENGTH_SHORT).show();
				activity.finish();				
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
