package com.babaenciel.gemi.pemasukan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.utils.MyDate;

public class PemasukanFragment2 extends SherlockFragment {	
	private String month, year;
	private String monthText, yearText;
	private static final CharSequence[] pemasukanItem = {"Edit", "Delete"};
	private PemasukanDatabase db;
	ExpandableListView list;
	PemasukanListInterface childListener;
	//Cursor cursor;
	
	public static SherlockFragment newInstance(MyDate myDate) {
		PemasukanFragment2 f = new PemasukanFragment2();
				
		Bundle b = new Bundle();		
		b.putInt("month", myDate.monthCounter);
		b.putInt("year", myDate.yearCounter);
		b.putString("monthText", myDate.monthText);
		//b.putString("yearText", myDate.yearText);
		
		Log.d("month", ""+myDate.monthCounter);
		Log.d("year", ""+myDate.yearCounter);
		f.setArguments(b);
		return f;		
	}
		
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
       
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//get from arguments
		month = Integer.toString(getArguments().getInt("month"));
		year = Integer.toString(getArguments().getInt("year"));
		monthText = getArguments().getString("monthText");
		//yearText = getArguments().getString("yearText");
		
		//inflate view
		final View view = inflater.inflate(com.babaenciel.gemi.R.layout.activity_pemasukan2, null);
		
		//inisiasi database
		db = new PemasukanDatabase(getActivity());
		
		//nama bulan
		TextView bulanText = (TextView) view.findViewById(R.id.bulan_pemasukan);
		bulanText.setText(monthText);
		
		//jumlah pemasukan
		TextView jumlahPemasukan = (TextView) view.findViewById(R.id.pemasukan_total_nominal);
		int jum = db.getPemasukanTotalSum(month, year);		
		DecimalFormatSymbols simbol = new DecimalFormatSymbols(); //diformat dulu hasilnya biar ada titiknya		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);		
		jumlahPemasukan.setText(customFormat.format(jum));
		
		//set data list
		list = (ExpandableListView) view.findViewById(R.id.pemasukan_expandablelist);
		int[] colors = {0, R.color.black_soft, 0};					
		int[] colors2 = {0, R.color.blue_modern, 0};				
		list.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors2));
		list.setChildDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors2));
		list.setDividerHeight(1);
		
		//set data list - set groups
		ArrayList<String> groups = new ArrayList<String>();
		groups = db.getPemasukanKategori(month, year);		
		
		//set data list - set children
		ArrayList<ArrayList<PemasukanObject>> children = new ArrayList<ArrayList<PemasukanObject>>();
		for(String item : groups) {
			children.add(new ArrayList<PemasukanObject>());
		}		
		
		Cursor cursor = db.getPemasukanFromMonthYear2(month, year);
			
		for(int i = 0; i < groups.size(); i++) {
			if(cursor.moveToFirst()) {
				do {
					//membandingkan groups dengan index(i) dengan hasil cursor berupa nama kategori.
					//jika groups dengan index(i) sama dengan nama kategori dari hasil query dari cursor,
					//maka masukkan ke arraylist children sesuai dengan groups(children.get(i)).
					//cursor.getString(6) itu ngambil kolom Kategori.nama
					if(groups.get(i).equals(cursor.getString(6))) {						
						children.get(i).add(new PemasukanObject(cursor.getInt(0), cursor.getString(1), cursor.getString(3), cursor.getInt(2), cursor.getInt(4)));
					}
						
				}while(cursor.moveToNext());
			}
		}
		
		//Log.d("children", children.get(0).get(0).nama);
		cursor.close();
		db.dbClose();
		
		final PemasukanExpandableListAdapter adapter = new PemasukanExpandableListAdapter(getActivity(), groups, children);
		list.setAdapter(adapter);		
		
		list.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				//Toast.makeText(getActivity(), ""+id, Toast.LENGTH_SHORT).show();
				final int idChildren = (int) id;
				final PemasukanObject pemasukanObject = (PemasukanObject) adapter.getChild(groupPosition, childPosition);
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("sub Menu");
				builder.setItems(pemasukanItem, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	if(pemasukanItem[item].equals("Delete")) {
				    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				    		alertDialogBuilder.setTitle("Konfirmasi Delete");
				    		alertDialogBuilder.setMessage("Yakin mau menghapus : "+ pemasukanObject.nama +"?")
				    			.setCancelable(false)
				    			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				    				public void onClick(DialogInterface dialog,int id) {
				    					db.deletePemasukan(idChildren);
							    		//Toast.makeText(getActivity(), ""+idChildren+" deleted", Toast.LENGTH_SHORT).show();				    						    				
							    		childListener.onDeleteChild(idChildren, 2);
				    				}
				    			})
				    			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				    				public void onClick(DialogInterface dialog,int id) {				    						
				    					dialog.cancel();
				    				}
				    			});				     
				    		AlertDialog alertDialog = alertDialogBuilder.create();				 
				    		alertDialog.show();
				    		
				    	}else {
				    		//Toast.makeText(getActivity(), "update", Toast.LENGTH_SHORT).show();
				    		childListener.onUpdateChild(idChildren, 2);
				    	}
				    	
				    }
				});
				AlertDialog alert = builder.create();				
				alert.show();
				return true;
			}
		});
		
		return view;
		
	}
	
	@Override
	public void onAttach(Activity activity) {	
		super.onAttach(activity);
		childListener = (PemasukanListInterface) activity;
	}
		
}
