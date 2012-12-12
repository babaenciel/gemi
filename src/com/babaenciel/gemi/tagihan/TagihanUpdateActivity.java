package com.babaenciel.gemi.tagihan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.lib.DateSlider;
import com.babaenciel.gemi.lib.DefaultDateSlider;
import com.babaenciel.gemi.pemasukan.PemasukanObject;
import com.babaenciel.gemi.utils.MyDate;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TagihanUpdateActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	private static final int DEFAULTDATESELECTOR_ID = 0;
	//protected TextView tanggal_deadline;
	private int id_tagihan;
	private AutoCompleteTextView namaView;
	private EditText nominalView;	
	private EditText tanggalView;
	private Button submit;
	Context context = this;
	TagihanUpdateActivity activity = this;
	MyDate date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		setTheme(THEME);
		setTitle("TAGIHAN UPDATE");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tagihan_insert_activity);
		
		id_tagihan = getIntent().getExtras().getInt("id_tagihan");
		
		namaView = (AutoCompleteTextView) findViewById(R.id.tagihan_insert_edittext_nama);
		nominalView = (EditText) findViewById(R.id.tagihan_insert_edittext_nominal);
		tanggalView = (EditText) findViewById(R.id.tagihan_insert_edittext_tanggal);
		submit = (Button) findViewById(R.id.tagihan_insert_submit_button);
		
		final TagihanDatabase db = new TagihanDatabase(this);
		TagihanObject tagihanObject = db.getTagihanById(id_tagihan);
		
		date = new MyDate();
		
		namaView.setText(tagihanObject.nama);
		// set nama with autocomplete
		// set nama with autocomplete
		ArrayList<TagihanObject> listTagihan = db.getTagihanAll();
		// Log.d("valuesnama", valuesNama.get(0));
		TagihanAutocompleteCustomAdapter adapter = new TagihanAutocompleteCustomAdapter(
				this, listTagihan, R.layout.autocomplete_rows);
		namaView.setAdapter(adapter);
		namaView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TagihanObject object = db.getTagihanById((int) arg3);

				nominalView.setText(Integer.toString(object.jumlah));
				// tanggal_deadline.setText(date.konversiTanggal2(object.tanggal_deadline));

				// ini untuk menutup keyboard setelah user memilih list
				// autocompletenya
				if (getCurrentFocus() != null
						&& getCurrentFocus() instanceof EditText) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(namaView.getWindowToken(), 0);
				}
			}

		});

		nominalView.setText(Integer.toString(tagihanObject.jumlah));
		tanggalView.setText(date.konversiTanggal2(tagihanObject.tanggal_deadline));
				
		tanggalView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DEFAULTDATESELECTOR_ID);		
				
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(namaView.getText().toString().equals("") || nominalView.getText().toString().equals("") || tanggalView.getText().toString().equals("")) {
					Toast.makeText(context, "semua kolom harus terisi", Toast.LENGTH_LONG).show();
				}else {
					String nama = namaView.getText().toString();
					String jumlah = nominalView.getText().toString();
									
					String tanggalString = date.konversiTanggal1(tanggalView.getText().toString());					
									
					//db.insertTagihan(nama, Integer.parseInt(jumlah), tanggalString, 0);
					db.updateTagihan(id_tagihan, nama, Integer.parseInt(jumlah), tanggalString);
					
					setAlarm(tanggalString);
					
					//Toast.makeText(context, "insert tagihan : " + nama + " sukses", Toast.LENGTH_LONG).show();
					
					Intent i = new Intent(context, TagihanActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					activity.finish();
					startActivity(i);
				}

				
			}
		});
	}
	
	public void setAlarm(String tanggal) {		
		String delims = "[-]";
		String[] tokens = tanggal.split(delims);
		int tahun = Integer.parseInt(tokens[0]);
		int bulan = Integer.parseInt(tokens[1]) - 1;
		int hari = date.konversiTanggalDihilangkan0(tokens[2]);		
		Log.d("tahun bulan hari", ""+tahun+"-"+bulan+"-"+hari);
		//tokens[0]=tahun, token[1]=bulan, token[2]=hari
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, tahun);
        calendar.set(Calendar.MONTH, bulan);
        calendar.set(Calendar.DAY_OF_MONTH, hari);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
		
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, TagihanBroadcastReceiver.class);		
		intent.putExtra("nama", namaView.getText().toString());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		//am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (5 * 1000), pendingIntent);
		Log.d("calendar", ""+calendar.HOUR_OF_DAY+":"+calendar.MINUTE+":"+calendar.SECOND+" "+calendar.DAY_OF_MONTH+"-"+calendar.MONTH+"-"+calendar.YEAR);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
				
		/*Toast.makeText(this, "Alarm set at " + Integer.toString(calendar.HOUR_OF_DAY) + ":" +
		Integer.toString(calendar.MINUTE) + ":" +
				Integer.toString(calendar.SECOND) + " " +
				Integer.toString(calendar.DAY_OF_MONTH) + "-" +
				Integer.toString(calendar.MONTH) + "-" +
				Integer.toString(calendar.YEAR), Toast.LENGTH_LONG).show();*/
		//am.cancel(pendingIntent);
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
				        		            
				tanggalView.setText(date2);
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
