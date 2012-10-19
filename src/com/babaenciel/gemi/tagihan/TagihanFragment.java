package com.babaenciel.gemi.tagihan;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.babaenciel.gemi.pemasukan.PemasukanObject;
import com.babaenciel.gemi.utils.MyDate;

public class TagihanFragment extends SherlockFragment {
	private static final CharSequence[] tagihanItem1 = {"Edit", "Delete", "Lunasi"};
	private static final CharSequence[] tagihanItem2 = {"Edit", "Delete", "Batal Lunas"};
	TagihanDatabase db;
	TagihanInterface tagihanInterface;
	
	public static SherlockFragment newInstance(MyDate myDate) {
		TagihanFragment f = new TagihanFragment();
		
		String month = myDate.konversiBulanDitambahi0(myDate.monthCounter);
		
		Bundle b = new Bundle();				
		b.putInt("year", myDate.yearCounter);
		b.putString("month", month);		
		b.putString("monthText", myDate.monthText);
		b.putString("yearText", myDate.yearText);
		
		f.setArguments(b);
		
		return f;				
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String month = getArguments().getString("month");
		String year = Integer.toString(getArguments().getInt("year"));
		
		View view = inflater.inflate(R.layout.tagihan_fragment, null);
		TextView text = (TextView) view.findViewById(R.id.tagihan_fragment_bulan);
		text.setText(getArguments().getString("monthText"));
				
		db = new TagihanDatabase(getActivity());		
		ArrayList<TagihanObject> values = db.getTagihanFromMonthYear(month, year);
						
		ListView list = (ListView) view.findViewById(R.id.tagihan_fragment_list);
		final TagihanFragmentListAdapter adapter = new TagihanFragmentListAdapter(getActivity(), values);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final int id_tagihan = (int) arg3;				
				final TagihanObject tagihanObject = (TagihanObject) adapter.getItem(arg2);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("sub Menu");
				
				if(tagihanObject.lunas == 1) {
					builder.setItems(tagihanItem2, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	if(tagihanItem2[item].equals("Delete")) {
					    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
					    		alertDialogBuilder.setTitle("Konfirmasi Delete");
					    		alertDialogBuilder.setMessage("Yakin mau menghapus : "+ tagihanObject.nama + "?")
					    			.setCancelable(false)
					    			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					    				public void onClick(DialogInterface dialog,int id) {
					    					db.deleteTagihan(id_tagihan);
									    	tagihanInterface.onDelete();
					    				}
					    			})
					    			.setNegativeButton("No",new DialogInterface.OnClickListener() {
					    				public void onClick(DialogInterface dialog,int id) {				    						
					    					dialog.cancel();
					    				}
					    			});				     
					    		AlertDialog alertDialog = alertDialogBuilder.create();				 
					    		alertDialog.show();
					    		
					    	}else if(tagihanItem2[item].equals("Batal Lunas")) {
					    		db.lunas(id_tagihan, 0);	//0 = belum lunas
						    	tagihanInterface.onLunas();
						    }					        
					    }
					});
					AlertDialog alert = builder.create();				
					alert.show();
				}else {
					builder.setItems(tagihanItem1, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	if(tagihanItem1[item].equals("Delete")) {
					    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
					    		alertDialogBuilder.setTitle("Konfirmasi Delete");
					    		alertDialogBuilder.setMessage("Yakin mau menghapus : "+ tagihanObject.nama + "?")
					    			.setCancelable(false)
					    			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					    				public void onClick(DialogInterface dialog,int id) {
                                            db.deleteTagihan(id_tagihan);
                                            tagihanInterface.onDelete();
					    				}
					    			})
					    			.setNegativeButton("No",new DialogInterface.OnClickListener() {
					    				public void onClick(DialogInterface dialog,int id) {				    						
					    					dialog.cancel();
					    				}
					    			});				     
					    		AlertDialog alertDialog = alertDialogBuilder.create();				 
					    		alertDialog.show();
					    		
					    	}else if(tagihanItem1[item].equals("Lunasi")) {
						    	db.lunas(id_tagihan, 1);	//1 = lunas
						    	tagihanInterface.onLunas();
						    }				        
					    }
					});
					AlertDialog alert = builder.create();				
					alert.show();
					
				}
							
			}
		});
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {		
		super.onAttach(activity);
		if(tagihanInterface == null) {
			tagihanInterface = (TagihanInterface) activity;
		}		
	}

}
