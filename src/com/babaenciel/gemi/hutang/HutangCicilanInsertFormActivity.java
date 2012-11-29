package com.babaenciel.gemi.hutang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.R.layout;
import com.babaenciel.gemi.R.menu;
import com.babaenciel.gemi.anggaran.AnggaranPengeluaranActivity;
import com.babaenciel.gemi.lib.DateSlider;
import com.babaenciel.gemi.lib.DefaultDateSlider;
import com.babaenciel.gemi.utils.MyDate;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class HutangCicilanInsertFormActivity extends SherlockActivity {

    private static final int DEFAULTDATESELECTOR_ID = 0;
	private static final int THEME = R.style.Theme_Sherlock;
	private AutoCompleteTextView nama;
	private EditText nominal;
	private EditText tanggal;
	private MyDate myDate;
	private Button submit;
	private HutangCicilanDatabase db;
	private Spinner spinner;
	private int id_hutang;
	private TextView namaHutang;
	protected Activity activity = this;
	protected Context context = this;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setTitle("HUTANG CICILAN INSERT");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hutang_cicilan_insert_form_activity);
        
        id_hutang = getIntent().getExtras().getInt("id_hutang");
        
        myDate = new MyDate();
        db = new HutangCicilanDatabase(this);
                
        namaHutang = (TextView) findViewById(R.id.hutang_cicilan_nama_hutang);
        nama = (AutoCompleteTextView) findViewById(R.id.hutang_cicilan_insert_edittext_nama);
        nominal = (EditText) findViewById(R.id.hutang_cicilan_insert_edittext_nominal);
        tanggal = (EditText) findViewById(R.id.hutang_cicilan_insert_edittext_tanggal); 
        submit = (Button) findViewById(R.id.hutang_cicilan_insert_submit_button);
                       
        String namaHutangString = db.getHutangNama(id_hutang);
        namaHutang.setText(namaHutangString);
        
        myDate.setNow();
        tanggal.setText(myDate.dateFull1);
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
				
				db.insertHutangCicilan(namaString, nominalInt, tanggalString, id_hutang);
				
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
