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
import android.widget.ProgressBar;
import android.widget.TextView;

public class HutangFragmentListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HutangObject> values;

	public HutangFragmentListAdapter(Context context, ArrayList<HutangObject> values) {
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
		return values.get(position).id_hutang;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		HutangHolder holder = null;
		
		if(convertView == null) {
			holder = new HutangHolder();
			convertView = inflater.inflate(R.layout.hutang_fragment_list_rows, null);
			holder.nama = (TextView) convertView.findViewById(R.id.hutang_fragment_list_rows_judul);
			holder.nominalAtas = (TextView) convertView.findViewById(R.id.hutang_fragment_list_rows_nominal_atas);
			holder.nominalBawah = (TextView) convertView.findViewById(R.id.hutang_fragment_list_rows_nominal_bawah);
			holder.bar = (ProgressBar) convertView.findViewById(R.id.hutang_fragment_list_rows_bar);
			convertView.setTag(holder);
		}else {
			holder = (HutangHolder) convertView.getTag();
		}
		
		HutangObject hutangObject = values.get(position);
		
		//set nama
		holder.nama.setText(hutangObject.nama);
		
		////nominal format		
		DecimalFormatSymbols simbol = new DecimalFormatSymbols();		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		
		//set nominal atas dengan jumlah pengeluaran yang telah dikeluarkan
		holder.nominalAtas.setText(customFormat.format(hutangObject.jumlah_cicilan));
		
		//set nominal bawah dengan jumlah anggaran
		holder.nominalBawah.setText(customFormat.format(hutangObject.jumlah_hutang));
		
		//set seekbar
		holder.bar.setMax(hutangObject.jumlah_hutang);
		holder.bar.setProgress(hutangObject.jumlah_cicilan);
		
		return convertView;
	}
	
	public class HutangHolder {
		TextView nama;
		TextView nominalAtas;
		TextView nominalBawah;
		TextView tanggal_deadline;
		ProgressBar bar;
	}

}
