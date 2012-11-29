package com.babaenciel.gemi.tabungan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.pemasukan.PemasukanFragment;
import com.babaenciel.gemi.utils.MyDate;

public class TabunganFragment extends SherlockFragment {
	private String month;
	private String year;	
	TabunganInterface tabunganInterface;
	CharSequence[] items = {"Edit", "Delete"};
	
	public static SherlockFragment newInstance(MyDate myDate) {
		TabunganFragment f = new TabunganFragment();
		
		String month = myDate.konversiBulanDitambahi0(myDate.monthCounter);
		Bundle b = new Bundle();		
		//b.putInt("month", myDate.monthCounter);
		b.putInt("year", myDate.yearCounter);
		b.putString("month", month);
		
		b.putString("monthText", myDate.monthText);
		b.putString("yearText", myDate.yearText);
		
		Log.d("month counter", ""+myDate.monthCounter);
		Log.d("year counter", ""+myDate.yearCounter);
		f.setArguments(b);
		return f;		
	}

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		month = getArguments().getString("month");
		year = Integer.toString(getArguments().getInt("year"));
		
		View view = inflater.inflate(R.layout.tabungan_fragment, null);
		
		final TabunganDatabase db = new TabunganDatabase(getActivity());
		
		TextView bulanView = (TextView) view.findViewById(R.id.tabungan_fragment_bulan);
		TextView totalTabunganView = (TextView) view.findViewById(R.id.tabungan_fragment_total);
		ListView listView = (ListView) view.findViewById(R.id.tabungan_fragment_list);
		
		//init format
		DecimalFormatSymbols simbol = new DecimalFormatSymbols(); //diformat dulu hasilnya biar ada titiknya		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		
		//set tulisan bulan
		bulanView.setText(getArguments().getString("monthText"));
		
		//set total tabungan
		int totalTabungan = db.getTabunganTotal(month, year);
		totalTabunganView.setText(customFormat.format(totalTabungan));
		
		//set list
		ArrayList<TabunganObject> listTabungan = db.getTabunganAll(month, year);
		TabunganFragmentListAdapter adapter = new TabunganFragmentListAdapter(getActivity(), R.layout.tabungan_fragment_list_rows, listTabungan);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final int id_tabungan = (int) arg3;
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Sub menu");
				builder.setItems(items, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(items[which].equals("Edit")) {
							tabunganInterface.onUpdate(id_tabungan);
						}else {
							AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
							builder.setTitle("Konfirmasi Delete");
							builder.setMessage("yakin?");
							builder.setPositiveButton("yes", new OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									db.deleteTabungan(id_tabungan);
									tabunganInterface.onDelete();									
								}
							});
							
							builder.setNegativeButton("no", new OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
									
								}
							});
							
							AlertDialog alert = builder.create();
							alert.show();
						}
						
					}
				});
				
				AlertDialog alert = builder.create();
				alert.show();
				
			}
		});
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {		
		super.onAttach(activity);
		tabunganInterface = (TabunganInterface) activity;
	}
	
}
