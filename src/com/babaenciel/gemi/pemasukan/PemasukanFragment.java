package com.babaenciel.gemi.pemasukan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
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
import com.babaenciel.gemi.utils.MyDate;

public class PemasukanFragment extends SherlockFragment {
	
	//private Context context;
	private static final CharSequence[] pemasukanItem = {"Edit", "Delete"};
	private PemasukanListInterface childListener;
	private String month;
	private String year;
	PemasukanDatabase db; 
	
	public static SherlockFragment newInstance(MyDate myDate) {
		PemasukanFragment f = new PemasukanFragment();
		
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
		//month = Integer.toString(getArguments().getInt("month"));
		month = getArguments().getString("month");
		year = Integer.toString(getArguments().getInt("year"));
		Log.d("month", month);
		Log.d("year", year);
		
		//inflate view
		View view = inflater.inflate(com.babaenciel.gemi.R.layout.pemasukan_fragment_1, null);
		
		//initialisasi database
		db = new PemasukanDatabase(getActivity());
		
		//set bulan
		TextView bulanText = (TextView) view.findViewById(R.id.bulan_pemasukan);
		bulanText.setText(getArguments().getString("monthText"));
		
		//set total pemasukan
		TextView jumlahPemasukan = (TextView) view.findViewById(R.id.pemasukan_total);
		int jumlah = db.getPemasukanTotalSum(month, year);
		DecimalFormatSymbols simbol = new DecimalFormatSymbols(); //diformat dulu hasilnya biar ada titiknya		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);	
		jumlahPemasukan.setText(customFormat.format(jumlah));
		
		ArrayList<PemasukanObject> values = new ArrayList<PemasukanObject>();
		Cursor cursor = db.getPemasukanFromMonthYear(month, year);		
		if(cursor.moveToFirst()) {
			do {
				//row index -> 0: id_pemasukan, 1: nama, 2: nominal, 3: tanggal, 4:id_kategori
				values.add(new PemasukanObject(cursor.getInt(0), cursor.getString(1), cursor.getString(3), cursor.getInt(2), cursor.getInt(4)));
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.dbClose();
		
		ListView list = (ListView) view.findViewById(R.id.pemasukan_list);
		/*int[] colors = {0, R.color.black_soft, 0};
		list.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		list.setDividerHeight(1);*/
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_rows_pemasukan, values);
		final PemasukanListAdapter adapter = new PemasukanListAdapter(getActivity(), R.layout.list_rows_pemasukan, values, db);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final int idRow = (int) arg3;				
				final PemasukanObject pemasukanObject = (PemasukanObject) adapter.getItem(arg2);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("sub Menu");
				builder.setItems(pemasukanItem, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	if(pemasukanItem[item].equals("Delete")) {
				    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				    		alertDialogBuilder.setTitle("Konfirmasi Delete");
				    		alertDialogBuilder.setMessage("Yakin mau menghapus : "+ pemasukanObject.nama + "?")
				    			.setCancelable(false)
				    			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				    				public void onClick(DialogInterface dialog,int id) {
				    					db.deletePemasukan(idRow);
								    	childListener.onDeleteChild(idRow, 1);
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
				    		childListener.onUpdateChild(idRow, 1);
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
		childListener = (PemasukanListInterface) activity;
	}
		
}
