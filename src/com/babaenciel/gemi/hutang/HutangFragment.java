package com.babaenciel.gemi.hutang;

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

public class HutangFragment extends SherlockFragment {
	private static final CharSequence[] hutangMenuItem = {"Cicilan", "Edit", "Delete"};
	private HutangInterface hutangInterface;
	
	public static HutangFragment newInstance(MyDate myDate) {
		HutangFragment f = new HutangFragment();
		
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
		String month = getArguments().getString("month");
		String year = Integer.toString(getArguments().getInt("year"));
		
		//inflate view
		View view = inflater.inflate(R.layout.hutang_fragment, null);
		
		//db
		final HutangDatabase db = new HutangDatabase(getActivity());
		
		//set bulan text
		TextView bulanView = (TextView) view.findViewById(R.id.hutang_fragment_bulan);
		bulanView.setText(getArguments().getString("monthText"));
		
		//initialize format
		DecimalFormatSymbols simbol = new DecimalFormatSymbols(); //diformat dulu hasilnya biar ada titiknya		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		
		//set total cicilan
		int jumlah_cicilan = db.getJumlahCicilan(month, year);
		TextView nominal_atas = (TextView) view.findViewById(R.id.hutang_fragment_total_nominal_atas);
		nominal_atas.setText(customFormat.format(jumlah_cicilan));
		
		//set total hutang
		int jumlah_hutang = db.getJumlahHutang(month, year);
		TextView nominal_bawah = (TextView) view.findViewById(R.id.hutang_fragment_total_nominal_bawah);
		nominal_bawah.setText(customFormat.format(jumlah_hutang));
		
		ArrayList<HutangObject> values = db.getHutangFromMonthYear(month, year);
		
		ListView list = (ListView) view.findViewById(R.id.hutang_fragment_list);
		final HutangFragmentListAdapter adapter = new HutangFragmentListAdapter(getActivity(), values);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final int id_hutang = (int) arg3;
				final HutangObject hutangObject = (HutangObject) adapter.getItem(arg2);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("sub Menu");
				builder.setItems(hutangMenuItem, new DialogInterface.OnClickListener() {				    

					public void onClick(DialogInterface dialog, int item) {
				    	if(hutangMenuItem[item].equals("Delete")) {
				    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				    		alertDialogBuilder.setTitle("Konfirmasi Delete");
				    		alertDialogBuilder.setMessage("Yakin mau menghapus : "+ hutangObject.nama + " ?")
				    			.setCancelable(false)
				    			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				    				public void onClick(DialogInterface dialog,int id) {
				    					db.deleteHutang(hutangObject.id_hutang);
								    	hutangInterface.onDelete();				    					
				    				}
				    			})
				    			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				    				public void onClick(DialogInterface dialog,int id) {				    						
				    					dialog.cancel();
				    				}
				    			});				     
				    		AlertDialog alertDialog = alertDialogBuilder.create();				 
				    		alertDialog.show();
				    		
				    	}else if(hutangMenuItem[item].equals("Edit")) {
				    		hutangInterface.onUpdate(hutangObject.id_hutang);
				    	}else {
				    		hutangInterface.onCicilan(id_hutang);				    	
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
		if(hutangInterface == null) {
			hutangInterface = (HutangInterface) activity;
		}
				
	}
}
