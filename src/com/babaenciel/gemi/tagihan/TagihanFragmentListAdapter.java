package com.babaenciel.gemi.tagihan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import com.babaenciel.gemi.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TagihanFragmentListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<TagihanObject> values;

	public TagihanFragmentListAdapter(Context context, ArrayList<TagihanObject> values) {
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
		return values.get(arg0).id_tagihan;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		final int pos = arg0;
		View convertView = arg1;
		TagihanHolder holder = null;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null) {			
			convertView = inflater.inflate(R.layout.tagihan_fragment_list_rows, null);
			holder = new TagihanHolder();
			holder.nama = (TextView) convertView.findViewById(R.id.tagihan_fragment_list_rows_judul);
			holder.jumlah = (TextView) convertView.findViewById(R.id.tagihan_fragment_list_rows_nominal);
			holder.tanggal_deadline = (TextView) convertView.findViewById(R.id.tagihan_fragment_list_rows_tanggal);
			convertView.setTag(holder);
		}else {
			holder = (TagihanHolder) convertView.getTag();
		}
		
		final TagihanObject tagihanObject = values.get(pos);		
		
		holder.nama.setText(tagihanObject.nama);
		holder.tanggal_deadline.setText(tagihanObject.tanggal_deadline);
		
		//nominal format		
		DecimalFormatSymbols simbol = new DecimalFormatSymbols();		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		holder.jumlah.setText(customFormat.format(tagihanObject.jumlah));	
			
		if(values.get(pos).lunas == 1) {			
			convertView.setBackgroundResource(R.color.black_soft);			
		}else {
			convertView.setBackgroundResource(R.color.sunday_2);
		}
			
		
		return convertView;
	}
	
	static class TagihanHolder {
		TextView nama;
		TextView jumlah;
		TextView tanggal_deadline;
	}

}
