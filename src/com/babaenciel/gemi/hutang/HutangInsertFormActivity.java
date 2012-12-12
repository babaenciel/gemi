package com.babaenciel.gemi.hutang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.lib.DateSlider;
import com.babaenciel.gemi.lib.DefaultDateSlider;
import com.babaenciel.gemi.pemasukan.PemasukanObject;
import com.babaenciel.gemi.utils.MyDate;

public class HutangInsertFormActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	private static final int DEFAULTDATESELECTOR_ID = 0;
	AutoCompleteTextView nama;
	EditText jumlah_hutang;
	EditText tanggal_deadline;	
	Context context = this;
	HutangInsertFormActivity activity = this;
	private MyDate myDate;
	private HutangDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		setTheme(THEME);
		setTitle("HUTANG");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hutang_insert_activity);
		
		myDate = new MyDate();
		db = new HutangDatabase(this);
		
		nama = (AutoCompleteTextView) findViewById(R.id.hutang_insert_edittext_nama);		
		jumlah_hutang = (EditText) findViewById(R.id.hutang_insert_edittext_nominal);
		tanggal_deadline = (EditText) findViewById(R.id.hutang_insert_edittext_tanggal);
			
		// set nama autocomplete
		ArrayList<HutangObject> listHutang = db.getHutangAll();
		HutangAutocompleteCustomAdapter adapter = new HutangAutocompleteCustomAdapter(
				this, listHutang, R.layout.autocomplete_rows);
		nama.setAdapter(adapter);
		nama.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HutangObject object = db.getHutangSingle((int) arg3);

				jumlah_hutang.setText(Integer.toString(object.jumlah_hutang));

				// ini untuk menutup keyboard setelah user memilih list
				// autocompletenya
				if (getCurrentFocus() != null
						&& getCurrentFocus() instanceof EditText) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(nama.getWindowToken(), 0);
				}
			}
		});
		
		//set tanggal
		myDate.setNow();
		tanggal_deadline.setText(myDate.dateFull1);
		tanggal_deadline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DEFAULTDATESELECTOR_ID);		
				
			}
		});
		
		Button submit = (Button) findViewById(R.id.hutang_insert_submit_button);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HutangDatabase db = new HutangDatabase(context);
				
				String namaString = nama.getText().toString();
								
				int jumlah_hutangInt = Integer.parseInt(jumlah_hutang.getText().toString());
								
				String tanggal_deadlineString = myDate.konversiTanggal1(tanggal_deadline.getText().toString());				
				db.insertHutang(namaString, 0, jumlah_hutangInt, tanggal_deadlineString);
				
				Intent i = new Intent(context, HutangActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				activity.finish();
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
					        		            
					tanggal_deadline.setText(date2);
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
