package com.babaenciel.gemi.pemasukan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.babaenciel.gemi.R;
import com.babaenciel.gemi.quickaction.ActionItem;
import com.babaenciel.gemi.quickaction.QuickAction;
import com.babaenciel.gemi.utils.DBAdapter;

public class PemasukanListAdapter extends BaseAdapter {

	private Context context;
	private int layoutId;
	private ArrayList<PemasukanObject> values;
	protected int mSelectedRow;
	private PemasukanActivity pemasukan;
	private PemasukanDatabase db;
	private static final CharSequence[] pemasukanItem = {"Edit", "Delete"};

	public PemasukanListAdapter(Context context, int layoutResourceId, ArrayList<PemasukanObject> values, PemasukanDatabase db) {
		super();
		this.pemasukan = pemasukan;
		this.context = context;
		this.layoutId = layoutResourceId;
		this.values = values;
		this.db = db;
	}
	
	public PemasukanListAdapter(Context context, int layoutResourceId) {
		super();
		this.context = context;
		this.layoutId = layoutResourceId;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		PemasukanHolder holder = null;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null) {			
			convertView = inflater.inflate(layoutId, parent, false);
			holder = new PemasukanHolder();
			holder.nama = (TextView) convertView.findViewById(R.id.judul_pemasukan);
			holder.tanggal = (TextView) convertView.findViewById(R.id.tanggal_pemasukan);
			holder.nominal = (TextView) convertView.findViewById(R.id.nominal_pemasukan);
			convertView.setTag(holder);
		}else {
			holder = (PemasukanHolder) convertView.getTag();
		}
		
		final PemasukanObject pemasukanObject = values.get(position);		
		final int id_pemasukan = pemasukanObject.id_pemasukan;
		
		holder.nama.setText(pemasukanObject.nama);
		holder.tanggal.setText(pemasukanObject.tanggal);
		
		//nominal format		
		DecimalFormatSymbols simbol = new DecimalFormatSymbols();		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		holder.nominal.setText(customFormat.format(pemasukanObject.nominal));				
				
		/*convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {			
				//Toast.makeText(context, "okey", Toast.LENGTH_SHORT).show();
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("sub Menu");
				builder.setItems(pemasukanItem, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	if(pemasukanItem[item].equals("Delete")) {
				    		db.deletePemasukan(pemasukanObject.id_pemasukan);
				    		
				    	}
				        Toast.makeText(context, pemasukanItem[item], Toast.LENGTH_SHORT).show();
				    }
				});
				AlertDialog alert = builder.create();				
				alert.show();
			}
		});*/
		
		//membuat warna zebra di rows
		if (position % 2 == 0){
		    convertView.setBackgroundResource(R.drawable.listview_rows_color_1);
		} else {
		    convertView.setBackgroundResource(R.drawable.listview_rows_color_2);
		}
		return convertView;
	}
	
	static class PemasukanHolder {
		TextView nama;
		TextView tanggal;		
		TextView nominal;
	}

	@Override
	public int getCount() {		
		return values.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return values.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return values.get(position).id_pemasukan;
	}
		

}