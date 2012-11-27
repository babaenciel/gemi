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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
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

public class AnggaranPengeluaranInsertFormActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	private static final int DEFAULTDATESELECTOR_ID = 0;
	AnggaranPengeluaranDatabase db;
	AutoCompleteTextView nama;
	EditText tanggal;
	EditText nominal;
	Spinner spinner;
	Context context = this;
	AnggaranPengeluaranInsertFormActivity activity = this;
	int id_baru;
	int id_anggaran;
	int id_anggaran_spinner;
	MyDate myDate = new MyDate();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setTitle("ANGGARAN PENGELUARAN");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anggaran_pengeluaran_insert_form_activity);
		
		id_anggaran = getIntent().getExtras().getInt("id_anggaran");
		
		nama = (AutoCompleteTextView) findViewById(R.id.anggaran_pengeluaran_form_edittext_nama);
		nominal = (EditText) findViewById(R.id.anggaran_pengeluaran_form_edittext_nominal);
		spinner = (Spinner) findViewById(R.id.anggaran_pengeluaran_form_spinner);
		tanggal = (EditText) findViewById(R.id.anggaran_pengeluaran_form_edittext_tanggal);
		Button button = (Button) findViewById(R.id.anggaran_pengeluaran_form_submit_button);
		
		db = new AnggaranPengeluaranDatabase(this);

		//spinner set
		String tanggalAnggaran = db.getAnggaranTanggal(id_anggaran);	//get tanggal yang sama dari suatu anggaran
		Cursor cursor = db.getAnggaranNamaAll(tanggalAnggaran);			// lalu tanggal digunakan untuk mengambil anggaran yang lain
		
		String[] from = new String[] {"nama"};
	    int[] to = new int[] {android.R.id.text1};
	    
	    final SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to);	    
	    sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);	    

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
	    
		//set nama with autocomplete
		ArrayList<String> namaAll = db.getAnggaranPengeluaranNamaAll();
		ArrayAdapter<String> adapterNama = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, namaAll);
		nama.setAdapter(adapterNama);
		nama.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String namaString = ((TextView)arg1).getText().toString();
				int id_anggaran_pengeluaran = db.getAnggaranPengeluaranIdFromNama(namaString);
				AnggaranPengeluaranObject object = db.getAnggaranPengeluaranSingle(id_anggaran_pengeluaran);
				
				for(int i = 0; i < sca.getCount(); i++) {
					if(sca.getItemId(i) == object.id_anggaran) {
						spinner.setSelection(i);
					}
				}
				
				nominal.setText(Integer.toString(object.nominal));
				
				//ini untuk menutup keyboard setelah user memilih list autocompletenya
				if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
			        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			        imm.hideSoftInputFromWindow(nama.getWindowToken(), 0);
			    }					
			}
		});
		   
	    //set tanggal
	    myDate.setNow();
	    tanggal.setText(myDate.dateFull1);
	    tanggal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DEFAULTDATESELECTOR_ID);						
			}
		});
	    
	    //submit	    
	    button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(nama.getText().toString().equals("") || nominal.getText().toString().equals("") || tanggal.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "semua kolom harus terisi", Toast.LENGTH_SHORT).show();							
				}else {
					String tanggalKonversi = myDate.konversiTanggal1(tanggal.getText().toString());
					id_baru = db.insertAnggaranPengeluaran(nama.getText().toString(), Integer.parseInt(nominal.getText().toString()), tanggalKonversi, id_anggaran_spinner);								
					cekStatusAnggaran();
				}
				/*activity.finish();	
				Intent i = new Intent(context, AnggaranPengeluaranActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);				
				i.putExtra("id_anggaran", id_anggaran);
				startActivity(i);*/
			}
		});
	    	    
	}
	
	public void cekStatusAnggaran() {
		AnggaranObject object = db.getAnggaranJumlahAnggaranDanPengeluaran(id_anggaran_spinner); 						
		if(object.nominalAtas > object.nominalBawah) {
			showDialogWhenOverAnggaran();
		}else {
			showCustomToast();
			callActivity();
		}
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
		myDate.setNow();	//set waktu sekarang
		String tanggal = db.getAnggaranTanggal(id_anggaran_spinner);		
		String delims = "[-]";
		String[] tokens = tanggal.split(delims);
		String bulan = tokens[1];		
		int jumlahHari = myDate.getHariDalamSuatuBulan(bulan);		
		int selisihHari = (jumlahHari - myDate.date) + 1;	
		Log.d("selisih hari", "" + selisihHari);
		Log.d("jumlah hari", "" + jumlahHari);
		Log.d("date date", "" + myDate.date);

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
