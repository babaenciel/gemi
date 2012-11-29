package com.babaenciel.gemi.hutang;

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

public class HutangCicilanListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HutangCicilanObject> values;

	public HutangCicilanListAdapter(Context context, ArrayList<HutangCicilanObject> values) {
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
		return values.get(position).id_hutang_cicilan;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		HutangCicilanHolder holder = null;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null) {			
			convertView = inflater.inflate(R.layout.hutang_cicilan_list_rows, null);
			holder = new HutangCicilanHolder();
			holder.nama = (TextView) convertView.findViewById(R.id.hutang_cicilan_list_rows_judul);
			holder.nominal = (TextView) convertView.findViewById(R.id.hutang_cicilan_list_rows_nominal);
			holder.tanggal = (TextView) convertView.findViewById(R.id.hutang_cicilan_list_rows_tanggal);
			convertView.setTag(holder);
		}else {
			holder = (HutangCicilanHolder) convertView.getTag();
		}
		
		final HutangCicilanObject hutangCicilanObject = values.get(position);		
		
		holder.nama.setText(hutangCicilanObject.nama);
		holder.tanggal.setText(hutangCicilanObject.tanggal);
		
		//nominal format		
		DecimalFormatSymbols simbol = new DecimalFormatSymbols();		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		holder.nominal.setText(customFormat.format(hutangCicilanObject.nominal));		
		
		//membuat warna zebra di rows
		if (position % 2 == 0){
			convertView.setBackgroundResource(R.drawable.listview_rows_color_1);
		} else {
			convertView.setBackgroundResource(R.drawable.listview_rows_color_2);
		}			
				
		return convertView;
	}
	
	public class HutangCicilanHolder {
		TextView nama;
		TextView nominal;
		TextView tanggal;
	}

}
