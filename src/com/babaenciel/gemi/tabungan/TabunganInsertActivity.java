package com.babaenciel.gemi.tabungan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.lib.DateSlider;
import com.babaenciel.gemi.lib.DefaultDateSlider;
import com.babaenciel.gemi.pemasukan.PemasukanActivity;
import com.babaenciel.gemi.pemasukan.PemasukanDatabase;
import com.babaenciel.gemi.pemasukan.PemasukanInsertActivity;
import com.babaenciel.gemi.pemasukan.PemasukanObject;
import com.babaenciel.gemi.utils.MyDate;

public class TabunganInsertActivity extends SherlockActivity {
	protected static final int DEFAULTDATESELECTOR_ID = 0;
	public static int THEME = R.style.Theme_Sherlock;
	Context context = this;
	TabunganInsertActivity tabunganInsert = this;
	AutoCompleteTextView nama;
	EditText tanggal;
	EditText nominal;
	Spinner spinnerKategori;
	MyDate myDate;
	TabunganDatabase db;
	int id_pemasukan_autocomplete;
	private int viewToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setTitle("TABUNGAN INSERT");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabungan_insert_activity);
	
		
		nama = (AutoCompleteTextView) findViewById(R.id.tabungan_insert_activity_edittext_nama);		
		tanggal = (EditText) findViewById(R.id.tabungan_insert_activity_edittext_tanggal);
		nominal = (EditText) findViewById(R.id.tabungan_insert_activity_edittext_nominal);		
		
		db = new TabunganDatabase(this);								
						
		//set tanggal. default langsung terisi now.
		myDate = new MyDate();
		myDate.setNow();
		
		tanggal.setText(myDate.dateFull1);
		tanggal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DEFAULTDATESELECTOR_ID);		
				
			}
		});
		
		
		//submit
		Button submit = (Button) findViewById(R.id.tabungan_insert_activity_submit_button);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(nama.getText().toString().equals("") || nominal.getText().toString().equals("") || tanggal.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "semua kolom harus terisi", Toast.LENGTH_SHORT).show();							
				}else {
					String namaText = nama.getText().toString();					
					int nominalAngka = Integer.parseInt(nominal.getText().toString());
					String tanggalText = tanggal.getText().toString();
					String konversiTanggalText = myDate.konversiTanggal1(tanggalText);
					
					db.insertTabungan(namaText, nominalAngka, konversiTanggalText);
					
					Toast.makeText(context, "insert : "+namaText+" sukses", Toast.LENGTH_SHORT).show();
					tabunganInsert.finish();				
					Intent i = new Intent(context, TabunganActivity.class);							
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
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
