package com.babaenciel.gemi.tabungan;

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

public class TabunganFragmentListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<TabunganObject> list;
	private int rowsLayout;

	public TabunganFragmentListAdapter(Context context, int rowsLayout, ArrayList<TabunganObject> list) {
		this.context = context;
		this.list = list;
		this.rowsLayout = rowsLayout;
	}
	
	@Override
	public int getCount() {		
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {		
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {		
		return list.get(arg0).id_tabungan;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TabunganHolder holder = null;
		
		if(arg1 == null) {
			arg1 = inflater.inflate(rowsLayout, null);
			holder = new TabunganHolder();
			holder.nama = (TextView) arg1.findViewById(R.id.tabungan_fragment_list_rows_judul);
			holder.nominal = (TextView) arg1.findViewById(R.id.tabungan_fragment_list_rows_nominal);
			holder.tanggal = (TextView) arg1.findViewById(R.id.tabungan_fragment_list_rows_tanggal);
			arg1.setTag(holder);
		}else {
			holder = (TabunganHolder) arg1.getTag();
		}
		
		// init format
		DecimalFormatSymbols simbol = new DecimalFormatSymbols(); 
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###", simbol);
		
		holder.nama.setText(list.get(arg0).nama);

		holder.nominal.setText(customFormat.format(list.get(arg0).nominal));

		holder.tanggal.setText(list.get(arg0).tanggal);
		
		//membuat warna zebra di rows
		if (arg0 % 2 == 0){
		    arg1.setBackgroundResource(R.drawable.listview_rows_color_1);
		} else {
		    arg1.setBackgroundResource(R.drawable.listview_rows_color_2);
		}
		
		return arg1;
	}
	
	public static class TabunganHolder {
		TextView nama;
		TextView nominal;
		TextView tanggal;
	}

}
