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
import android.widget.ProgressBar;
import android.widget.TextView;

public class AnggaranFragmentByDateListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<AnggaranObject> values;

	public AnggaranFragmentByDateListAdapter(ArrayList<AnggaranObject> values, Context context) {
		this.context = context;
		this.values = values;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return values.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return values.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return values.get(arg0).id_anggaran;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		AnggaranHolder holder = null;
		
		if(convertView == null) {
			holder = new AnggaranHolder();
			convertView = inflater.inflate(R.layout.anggaran_fragment_by_date_list_rows, null);
			holder.nama = (TextView) convertView.findViewById(R.id.anggaran_fragment_by_date_list_rows_judul);
			holder.nominalAtas = (TextView) convertView.findViewById(R.id.anggaran_fragment_by_date_list_rows_nominal_atas);
			holder.nominalBawah = (TextView) convertView.findViewById(R.id.anggaran_fragment_by_date_list_rows_nominal_bawah);
			holder.seekbar = (ProgressBar) convertView.findViewById(R.id.anggaran_fragment_by_date_list_rows_bar);
			convertView.setTag(holder);
		}else {
			holder = (AnggaranHolder) convertView.getTag();
		}
		
		AnggaranObject anggaranObject = values.get(position);
		
		//set nama
		holder.nama.setText(anggaranObject.nama);
		
		////nominal format		
		DecimalFormatSymbols simbol = new DecimalFormatSymbols();		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		
		//set nominal atas dengan jumlah pengeluaran yang telah dikeluarkan
		holder.nominalAtas.setText(customFormat.format(anggaranObject.nominalAtas));
		
		//set nominal bawah dengan jumlah anggaran
		holder.nominalBawah.setText(customFormat.format(anggaranObject.nominalBawah));
		
		//set seekbar
		holder.seekbar.setMax(anggaranObject.nominalBawah);
		holder.seekbar.setProgress(anggaranObject.nominalAtas);
		
		return convertView;
	}
	
	static class AnggaranHolder {
		TextView nama;		
		TextView nominalAtas;
		TextView nominalBawah;
		ProgressBar seekbar;
	}

}
