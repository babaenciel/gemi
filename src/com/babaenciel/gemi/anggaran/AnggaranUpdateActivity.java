package com.babaenciel.gemi.anggaran;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.lib.DateSlider;
import com.babaenciel.gemi.lib.MonthYearDateSlider;
import com.babaenciel.gemi.pemasukan.PemasukanActivity;
import com.babaenciel.gemi.utils.MyDate;

public class AnggaranUpdateActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	private static final int DEFAULTDATESELECTOR_ID = 0;
	private EditText tanggal;
	private Button submit;
	private AutoCompleteTextView nama;
	private EditText nominal;
	Context context = this;	
	int id_anggaran;
	MyDate myDate;
	AnggaranDatabase db;
	AnggaranUpdateActivity activity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setTitle("ANGGARAN PENGELUARAN");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anggaran_form_activity);
		
		//get extras
		id_anggaran = getIntent().getExtras().getInt("id_anggaran");
		
		//db
		db = new AnggaranDatabase(this);
		AnggaranObject object = db.getAnggaranSingle(id_anggaran);
		
		nama = (AutoCompleteTextView) findViewById(R.id.anggaran_form_edittext_nama);
		nominal = (EditText) findViewById(R.id.anggaran_form_edittext_nominal);
		tanggal = (EditText) findViewById(R.id.anggaran_form_edittext_tanggal);
		submit = (Button) findViewById(R.id.anggaran_form_submit_button);
		
		// set nama
		nama.setText(object.nama);
		// set nama autocomplete
		ArrayList<AnggaranObject> listAnggaran = db.getAnggaranAll();
		AnggaranAutocompleteCustomAdapter adapterAutocomplete = new AnggaranAutocompleteCustomAdapter(
				this, listAnggaran, R.layout.autocomplete_rows);
		nama.setAdapter(adapterAutocomplete);
		nama.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				AnggaranObject object = db.getAnggaranSingle((int) arg3);

				nominal.setText(Integer.toString(object.nominalBawah));

				// ini untuk menutup keyboard setelah user memilih list
				// autocompletenya
				if (getCurrentFocus() != null
						&& getCurrentFocus() instanceof EditText) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(nama.getWindowToken(), 0);
				}
			}
		});	
		
		//set nominal
		nominal.setText(Integer.toString(object.nominalBawah));
		
		//set tanggal
		myDate = new MyDate();		
		tanggal.setText(myDate.konversiTanggalCompleteDihilangiTanggalnya(object.tanggal));
		tanggal.setOnClickListener(new OnClickListener() {						

			@Override
			public void onClick(View v) {
				showDialog(DEFAULTDATESELECTOR_ID );		
				
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {								
				String tanggalText = myDate.konversiBulanTahunKeCompleteTanggal(tanggal.getText().toString());
				Log.d("tanggal anggaran", tanggalText);
				db.updateAnggaran(id_anggaran, nama.getText().toString(), Integer.parseInt(nominal.getText().toString()), tanggalText);
				
				Toast.makeText(context, "update sukses", Toast.LENGTH_SHORT).show();
				activity.finish();				
				Intent i = new Intent(context, AnggaranActivity.class);					
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
        		SimpleDateFormat simple = new SimpleDateFormat("MM-yyyy");
        		//SimpleDateFormat simple2 = new SimpleDateFormat("MM-yyyy");
        		Date date1;
        		String date2 = null;
				try {
					date1 = simple.parse(String.format("%tm-%tY", selectedDate, selectedDate));
					date2 = simple.format(date1);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}        		
				Log.d("date2", date2);        		            
				tanggal.setText(date2);
        	}
		};
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case DEFAULTDATESELECTOR_ID:
			final Calendar c = Calendar.getInstance();
            return new MonthYearDateSlider(this,mDateSetListener,c);
		}
		return null;
	}
}
