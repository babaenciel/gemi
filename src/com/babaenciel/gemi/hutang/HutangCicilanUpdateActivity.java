package com.babaenciel.gemi.hutang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.lib.DateSlider;
import com.babaenciel.gemi.lib.DefaultDateSlider;
import com.babaenciel.gemi.utils.MyDate;

public class HutangCicilanUpdateActivity extends SherlockActivity {
	
	private static final int THEME = R.style.Theme_Sherlock;
	protected static final int DEFAULTDATESELECTOR_ID = 0;
	private int id_hutang;
	private MyDate myDate;
	private HutangCicilanDatabase db;
	private TextView namaHutang;
	private AutoCompleteTextView nama;
	private EditText nominal;
	private EditText tanggal;
	private Button submit;
	protected Activity activity = this;
	protected Context context = this;
	private int id_hutang_cicilan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		setTheme(THEME);
		setTitle("HUTANG CICILAN UPDATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hutang_cicilan_insert_form_activity);
        
        id_hutang = getIntent().getExtras().getInt("id_hutang");
        id_hutang_cicilan = getIntent().getExtras().getInt("id_hutang_cicilan");
        
        myDate = new MyDate();
        db = new HutangCicilanDatabase(this);
        
        HutangCicilanObject object = db.getHutangCicilanSingle(id_hutang_cicilan);
                
        namaHutang = (TextView) findViewById(R.id.hutang_cicilan_nama_hutang);
        nama = (AutoCompleteTextView) findViewById(R.id.hutang_cicilan_insert_edittext_nama);
        nominal = (EditText) findViewById(R.id.hutang_cicilan_insert_edittext_nominal);
        tanggal = (EditText) findViewById(R.id.hutang_cicilan_insert_edittext_tanggal); 
        submit = (Button) findViewById(R.id.hutang_cicilan_insert_submit_button);
                       
        String namaHutangString = db.getHutangNama(id_hutang);
        namaHutang.setText(namaHutangString);
        
		nama.setText(object.nama);
		
		// set nama autocomplete
		ArrayList<HutangCicilanObject> listHutangCicilan = db
				.getHutangCicilanAll();
		HutangCicilanAutocompleteCustomAdapter adapter = new HutangCicilanAutocompleteCustomAdapter(
				this, listHutangCicilan, R.layout.autocomplete_rows);
		nama.setAdapter(adapter);
		nama.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HutangCicilanObject object = db
						.getHutangCicilanSingle((int) arg3);

				nominal.setText(Integer.toString(object.nominal));

				// ini untuk menutup keyboard setelah user memilih list
				// autocompletenya
				if (getCurrentFocus() != null
						&& getCurrentFocus() instanceof EditText) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(nama.getWindowToken(), 0);
				}
			}
		});
		
		nominal.setText(Integer.toString(object.nominal));                
        tanggal.setText(myDate.konversiTanggal2(object.tanggal));
        tanggal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DEFAULTDATESELECTOR_ID);
				
			}
		});
        
        submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String namaString = nama.getText().toString();
				int nominalInt = Integer.parseInt(nominal.getText().toString());
				String tanggalString = myDate.konversiTanggal1(tanggal.getText().toString());
				
				//db.insertHutangCicilan(namaString, nominalInt, tanggalString, id_hutang);
				db.updateHutangCicilan(id_hutang_cicilan, namaString, nominalInt, tanggalString, id_hutang);
				
				activity.finish();	
				Intent i = new Intent(context, HutangCicilanActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);				
				i.putExtra("id_hutang", id_hutang);
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
