package com.babaenciel.gemi.anggaran;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
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

public class AnggaranPengeluaranInsertFormActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	private static final int DEFAULTDATESELECTOR_ID = 0;
	AnggaranPengeluaranDatabase db;
	EditText tanggal;
	Context context = this;
	AnggaranPengeluaranInsertFormActivity activity = this;
	int id_baru;
	int id_anggaran;
	int id_anggaran_spinner;
	MyDate date = new MyDate();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setTitle("ANGGARAN PENGELUARAN");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anggaran_pengeluaran_insert_form_activity);
		
		id_anggaran = getIntent().getExtras().getInt("id_anggaran");
				
		db = new AnggaranPengeluaranDatabase(this);
		String tanggalAnggaran = db.getAnggaranTanggal(id_anggaran);
		Cursor cursor = db.getAnggaranNamaAll(tanggalAnggaran);
		
		String[] from = new String[] {"nama"};
	    int[] to = new int[] {android.R.id.text1};
	    
	    SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to);	    
	    sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);	    
	    
	    Spinner spinner = (Spinner) findViewById(R.id.anggaran_pengeluaran_form_spinner);
	    spinner.setAdapter(sca);
	    
	    for(int i = 0; i < sca.getCount(); i++) {
			if(sca.getItemId(i) == id_anggaran) {
				spinner.setSelection(i);
			}
		}
	    
	    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				id_anggaran_spinner = (int) arg3;				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    tanggal = (EditText) findViewById(R.id.anggaran_pengeluaran_form_edittext_tanggal);
	    tanggal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DEFAULTDATESELECTOR_ID);						
			}
		});
	    
	    Button button = (Button) findViewById(R.id.anggaran_pengeluaran_form_submit_button);
	    button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText nama = (EditText) findViewById(R.id.anggaran_pengeluaran_form_edittext_nama);
				EditText nominal = (EditText) findViewById(R.id.anggaran_pengeluaran_form_edittext_nominal);				
				String tanggalKonversi = date.konversiTanggal1(tanggal.getText().toString());
				id_baru = db.insertAnggaranPengeluaran(nama.getText().toString(), Integer.parseInt(nominal.getText().toString()), tanggalKonversi, id_anggaran_spinner);								
				cekStatusAnggaran();
				/*activity.finish();	
				Intent i = new Intent(context, AnggaranPengeluaranActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);				
				i.putExtra("id_anggaran", id_anggaran);
				startActivity(i);*/
			}
		});
	    	    
	}
	
	public void showCustomToast() {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.anggaran_insert_pengeluaran_custom_toast, null);
			
		//initialize format
		DecimalFormatSymbols simbol = new DecimalFormatSymbols(); //diformat dulu hasilnya biar ada titiknya		
		simbol.setGroupingSeparator('.');	
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		
		//menghitung sisa anggaran
		AnggaranObject object = db.getAnggaranJumlahAnggaranDanPengeluaran(id_anggaran_spinner); 				
		int selisih = object.nominalBawah - object.nominalAtas;
		String selisihFormatted = customFormat.format(selisih);
		
		//menghitung sisa anggaran per hari
		date.setNow();	//set waktu sekarang
		String tanggal = db.getAnggaranTanggal(id_anggaran_spinner);		
		String delims = "[-]";
		String[] tokens = tanggal.split(delims);
		String bulan = tokens[1];		
		int jumlahHari = date.getHariDalamSuatuBulan(bulan);		
		int selisihHari = jumlahHari - date.date;	
		Log.d("selisih hari", "" + selisihHari);
		Log.d("jumlah hari", "" + jumlahHari);
		Log.d("date date", "" + date.date);

		//hitung sisa anggaran per hari
		String sisaAnggaranPerHari = customFormat.format(selisih / selisihHari);
		
		//get nama anggaran
		String namaAnggaran = db.getAnggaranNamaSingle(id_anggaran_spinner);
		
		TextView text = (TextView) layout.findViewById(R.id.anggaran_insert_pengeluaran_custom_toast_text);
		text.setText("Insert Sukses \n Anggaran \""+ namaAnggaran +"\" yang tersisa = " + selisihFormatted + "\n" + 
				"Kamu masih bisa mengeluarkan \n" + sisaAnggaranPerHari + " / hari");
		
		// Toast...
		final Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
		
		toast.show();
		
		new CountDownTimer(4000, 100) {
		    public void onTick(long millisUntilFinished) {toast.show();}
		    public void onFinish() {toast.show();}
		}.start();
		
	}
	
	public void showDialogWhenOverAnggaran() {				
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.anggaran_pengeluaran_dialog_warning, null);
		TextView text = (TextView)view.findViewById(R.id.anggaran_pengeluaran_dialog_warning_text);
		text.setText("Pengeluaran kamu sudah melebihi anggaran!");
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(view);
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				callActivity();
				dialog.cancel();
			}
		});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void callActivity() {
		activity.finish();	
		Intent i = new Intent(context, AnggaranPengeluaranActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);				
		i.putExtra("id_anggaran", id_anggaran);
		startActivity(i);		
	}
	
	public void cekStatusAnggaran() {
		AnggaranObject object = db.getAnggaranJumlahAnggaranDanPengeluaran(id_anggaran); 						
		if(object.nominalAtas > object.nominalBawah) {
			showDialogWhenOverAnggaran();
		}else {
			showCustomToast();
			callActivity();
		}
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
