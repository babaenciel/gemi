package com.babaenciel.gemi.pemasukan;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.lib.DateSlider;
import com.babaenciel.gemi.lib.DefaultDateSlider;
import com.babaenciel.gemi.utils.MyDate;

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
	int id_pemasukan_autocomplete;
	private int viewToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setTitle("PEMASUKAN INSERT");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pemasukan_insert_form);
		
		//get intent
		if(getIntent().hasExtra("view_toggle")) {
			viewToggle = getIntent().getExtras().getInt("view_toggle");
		}
		nama = (AutoCompleteTextView) findViewById(R.id.pemasukan_form_edittext_nama);
		spinnerKategori = (Spinner) findViewById(R.id.pemasukan_form_spinner_kategori);
		tanggal = (EditText) findViewById(R.id.pemasukan_form_edittext_tanggal);
		nominal = (EditText) findViewById(R.id.pemasukan_form_edittext_nominal);
		
		db = new PemasukanDatabase(this);						

		//set spinner for kategori
		String[] from = new String[] {"nama"};
	    int[] to = new int[] {android.R.id.text1};
		Cursor cursorKategori = db.getKategori();				
		final SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursorKategori, from, to);	    	    
		sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spinnerKategori.setAdapter(sca);
		
		//set nama with autocomplete	
		ArrayList<PemasukanObject> listPemasukan = db.getPemasukanAllAutocomplete();		
		PemasukanAutocompleteCustomAdapter adapter = new PemasukanAutocompleteCustomAdapter(this, listPemasukan, R.layout.autocomplete_rows);
		nama.setAdapter(adapter);
		nama.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				PemasukanObject object = db.getPemasukanSingle((int)arg3);											
				
				nominal.setText(Integer.toString(object.nominal));
				
				for(int i = 0; i < sca.getCount(); i++) {
					if(sca.getItemId(i) == object.id_kategori) {
						spinnerKategori.setSelection(i);
					}
				}
								
				//ini untuk menutup keyboard setelah user memilih list autocompletenya
				if(getCurrentFocus()!=null && getCurrentFocus() instanceof AutoCompleteTextView){
			        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			        imm.hideSoftInputFromWindow(nama.getWindowToken(), 0);
				}
								
			}
			
		});
//		ArrayList<String> valuesNama = db.getPemasukanNamaAll();
//		ArrayAdapter<String> adapterNama = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, valuesNama);
//		nama.setAdapter(adapterNama);
//		nama.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				String namaString = ((TextView)arg1).getText().toString();
//				int id_pemasukan = db.getPemasukanIdFromNama(namaString);
//				PemasukanObject object = db.getPemasukanSingle(id_pemasukan);
//				
//				for(int i = 0; i < sca.getCount(); i++) {
//					if(sca.getItemId(i) == object.id_kategori) {
//						spinnerKategori.setSelection(i);
//					}
//				}
//				
//				nominal.setText(Integer.toString(object.nominal));
//				
//				//ini untuk menutup keyboard setelah user memilih list autocompletenya
//				if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
//			        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//			        imm.hideSoftInputFromWindow(nama.getWindowToken(), 0);
//			    }
//			}
//			
//		});		
//		
				
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
		Button submit = (Button) findViewById(R.id.pemasukan_form_submit_button);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(nama.getText().toString().equals("") || nominal.getText().toString().equals("") || tanggal.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "semua kolom harus terisi", Toast.LENGTH_SHORT).show();							
				}else {
					String namaText = nama.getText().toString();
					String spinnerText = ((TextView) spinnerKategori.getSelectedView()).getText().toString();
					Log.d("spinnertext", spinnerText);
					int nominalAngka = Integer.parseInt(nominal.getText().toString());
					String tanggalText = tanggal.getText().toString();
					String konversiTanggalText = myDate.konversiTanggal1(tanggalText);
					int id_kategori = db.getIdKategori(spinnerText);
					db.insertPemasukan(namaText, nominalAngka, konversiTanggalText, id_kategori);
					
					Toast.makeText(context, "insert : "+namaText+" sukses", Toast.LENGTH_SHORT).show();
					pemasukanInsert.finish();				
					Intent i = new Intent(context, PemasukanActivity.class);		
					i.putExtra("view_toggle", viewToggle);
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
