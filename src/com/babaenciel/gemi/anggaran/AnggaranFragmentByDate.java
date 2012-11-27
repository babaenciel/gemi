package com.babaenciel.gemi.anggaran;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.utils.MyDate;

public class AnggaranFragmentByDate extends SherlockFragment {
	
	private static final CharSequence[] anggaranMenuItem = {"Detail", "Edit", "Delete"};
	private String month;
	private String year;
	AnggaranDatabase db;
	AnggaranInterface anggaranInterface;	//to communicate with the activity parent
	
	public static AnggaranFragmentByDate newInstance(MyDate myDate) {
		AnggaranFragmentByDate f = new AnggaranFragmentByDate();
		
		String month = myDate.konversiBulanDitambahi0(myDate.monthCounter);
		Bundle bundle = new Bundle();
		//bundle.putInt("month", myDate.monthCounter);
		bundle.putString("month", month);
		bundle.putInt("year", myDate.yearCounter);
		bundle.putString("monthText", myDate.monthText);
		bundle.putString("yearText", myDate.yearText);
		f.setArguments(bundle);
		return f;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//month = Integer.toString(getArguments().getInt("month"));
		month = getArguments().getString("month");
		year = Integer.toString(getArguments().getInt("year"));
		
		//inflate the xml layout
		View view = inflater.inflate(R.layout.anggaran_fragment_by_date, null);
		
		//initialize database
		db = new AnggaranDatabase(getActivity());
		
		//set bulan yang bergeser
		TextView bulanText = (TextView) view.findViewById(R.id.anggaran_fragment_by_date_bulan);
		bulanText.setText(getArguments().getString("monthText"));
		
		//initialize format
		DecimalFormatSymbols simbol = new DecimalFormatSymbols(); //diformat dulu hasilnya biar ada titiknya		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		
		//set total nominal atas (jumlah pengeluarannya)
		int nominalAtas = db.getAnggaranPengeluaranTotal(month, year);
		TextView nominalAtasText = (TextView) view.findViewById(R.id.anggaran_fragment_by_date_total_nominal_atas);
		nominalAtasText.setText(customFormat.format(nominalAtas));
		
		//set total nominal bawah (jumlah anggarannya)
		int nominalBawah = db.getAnggaranTotal(month, year);
		TextView nominalBawahText = (TextView) view.findViewById(R.id.anggaran_fragment_by_date_total_nominal_bawah);
		nominalBawahText.setText(customFormat.format(nominalBawah));
				
		//set list
		ArrayList<AnggaranObject> values = new ArrayList<AnggaranObject>();
		values = db.getAnggaranFromMonthYear(month, year);
	
		ListView list = (ListView) view.findViewById(R.id.anggaran_fragment_by_date_list);
		final AnggaranFragmentByDateListAdapter adapter = new AnggaranFragmentByDateListAdapter(values, getActivity());
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final int idRow = (int) arg3;	//id_anggaran yang ada di database
				final AnggaranObject anggaranObject = (AnggaranObject) adapter.getItem(arg2);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("sub Menu");
				builder.setItems(anggaranMenuItem, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	if(anggaranMenuItem[item].equals("Delete")) {
				    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				    		alertDialogBuilder.setTitle("Konfirmasi Delete");
				    		alertDialogBuilder.setMessage("Yakin mau menghapus : "+ anggaranObject.nama + " ?")
				    			.setCancelable(false)
				    			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				    				public void onClick(DialogInterface dialog,int id) {
				    					db.deleteAnggaran(idRow);
								    	anggaranInterface.onDelete(idRow);				    					
				    				}
				    			})
				    			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				    				public void onClick(DialogInterface dialog,int id) {				    						
				    					dialog.cancel();
				    				}
				    			});				     
				    		AlertDialog alertDialog = alertDialogBuilder.create();				 
				    		alertDialog.show();
				    		
				    	}else if(anggaranMenuItem[item].equals("Edit")) {
				    		anggaranInterface.onUpdate(idRow);				    		
				    	}else {
				    		anggaranInterface.onDetail(idRow);
				    		Toast.makeText(getActivity(), "Detail", Toast.LENGTH_SHORT).show();
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
		anggaranInterface = (AnggaranInterface) activity;
	}
}
