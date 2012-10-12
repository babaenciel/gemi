package com.babaenciel.gemi.anggaran;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import com.babaenciel.gemi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AnggaranPengeluaranListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<AnggaranPengeluaranObject> values;

	public AnggaranPengeluaranListAdapter(Context context, ArrayList<AnggaranPengeluaranObject> values) {
		this.context = context;
		this.values = values;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
		return values.get(position).id_anggaran_pengeluaran;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		AnggaranPengeluaranHolder holder = null;
		
		if(convertView == null) {
			holder = new AnggaranPengeluaranHolder();
			convertView = inflater.inflate(R.layout.anggaran_pengeluaran_list_rows, null);
			holder.nama = (TextView) convertView.findViewById(R.id.anggaran_pengeluaran_list_rows_judul);
			holder.nominal = (TextView) convertView.findViewById(R.id.anggaran_pengeluaran_list_rows_nominal);			
			holder.tanggal = (TextView) convertView.findViewById(R.id.anggaran_pengeluaran_list_rows_tanggal);
			convertView.setTag(holder);
		}else {
			holder = (AnggaranPengeluaranHolder) convertView.getTag();
		}
		
		AnggaranPengeluaranObject anggaranPengeluaranObject = values.get(position);
		
		//set nama
		holder.nama.setText(anggaranPengeluaranObject.nama);
		
		//nominal format		
		DecimalFormatSymbols simbol = new DecimalFormatSymbols();		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		
		//set nominal
		holder.nominal.setText(customFormat.format(anggaranPengeluaranObject.nominal));
		
		//set tanggal
		holder.tanggal.setText(anggaranPengeluaranObject.tanggal);
		
		return convertView;
	}
	
	public static class AnggaranPengeluaranHolder {
		TextView nama;
		TextView nominal;
		TextView tanggal;
	}

}
