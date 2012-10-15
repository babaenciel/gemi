package com.babaenciel.gemi.anggaran;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.lib.DateSlider;
import com.babaenciel.gemi.lib.DefaultDateSlider;
import com.babaenciel.gemi.lib.MonthYearDateSlider;
import com.babaenciel.gemi.utils.MyDate;

public class AnggaranInsertFormActivity extends SherlockActivity {
	protected static final int DEFAULTDATESELECTOR_ID = 0;
	public static int THEME = R.style.Theme_Sherlock;
	private EditText tanggal;
	private Button submit;
	private EditText nama;
	private EditText nominal;
	Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		setTheme(THEME);
		setTitle("PEMASUKAN");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anggaran_form_activity);
		
		tanggal = (EditText) findViewById(R.id.anggaran_form_edittext_tanggal);
		tanggal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DEFAULTDATESELECTOR_ID);		
				
			}
		});
		
		submit = (Button) findViewById(R.id.anggaran_form_submit_button);
		submit.setOnClickListener(new OnClickListener() {
			
			

			@Override
			public void onClick(View v) {
				nama = (EditText) findViewById(R.id.anggaran_form_edittext_nama);
				nominal = (EditText) findViewById(R.id.anggaran_form_edittext_nominal);
				
				MyDate myDate = new MyDate();
				String tanggalComplete = myDate.konversiBulanTahunKeCompleteTanggal(tanggal.getText().toString());
				AnggaranDatabase db = new AnggaranDatabase(context);
				db.insertAnggaran(nama.getText().toString(), Integer.parseInt(nominal.getText().toString()), 0, tanggalComplete);
				db.dbClose();
				Toast.makeText(context, "insert : " + nama.getText().toString() + " sukses", Toast.LENGTH_LONG).show();
				
				Intent i = new Intent(context, AnggaranActivity.class);
				finish();
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
