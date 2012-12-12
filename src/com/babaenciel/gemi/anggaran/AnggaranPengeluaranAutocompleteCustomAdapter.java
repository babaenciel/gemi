package com.babaenciel.gemi.anggaran;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import com.babaenciel.gemi.R;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class AnggaranPengeluaranAutocompleteCustomAdapter extends BaseAdapter implements Filterable {

	private Context context;
	ArrayList<AnggaranPengeluaranObject> list;
	ArrayList<AnggaranPengeluaranObject> listTemp;
	AnggaranPengeluaranFilter filter;
	private int layoutId;
	
	public AnggaranPengeluaranAutocompleteCustomAdapter(Context context, ArrayList<AnggaranPengeluaranObject> list, int layoutId) {
		super();
		this.context = context;
		this.list = list;
		this.listTemp = list;
		this.filter = new AnggaranPengeluaranFilter();
		this.layoutId = layoutId;
		
	}
	
	@Override
	public int getCount() {		
		if(list == null) {
			//Log.d("listsize", ""+list.size());
			return 0;
		}else {
			Log.d("listsize", ""+list.size());
			return list.size();
		}
		
	}
	
	@Override
	public Object getItem(int position) { 
		return list.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return list.get(position).id_anggaran_pengeluaran;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layoutId, null);		
		TextView nama = (TextView) view.findViewById(R.id.autocomplete_nama);
		TextView nominal = (TextView) view.findViewById(R.id.autocomplete_nominal);
		
		DecimalFormatSymbols simbol = new DecimalFormatSymbols(); //diformat dulu hasilnya biar ada titiknya		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		
		nama.setText(list.get(position).nama);
		nominal.setText(customFormat.format(list.get(position).nominal));
		
		return view;
	}
	
	@Override
	public Filter getFilter() {		
		return filter;
	}
		

	private class AnggaranPengeluaranFilter extends Filter {
		
		@Override
		public CharSequence convertResultToString(Object resultValue) {			
			String str = ((AnggaranPengeluaranObject)(resultValue)).nama; 
            return str;
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults filterResults = new FilterResults();
			ArrayList<AnggaranPengeluaranObject> listResult = new ArrayList<AnggaranPengeluaranObject>();											
			//Log.d("masuk perform filter", "masuk");
			if(listTemp == null) {
				listTemp = list;
			}
			
			if (constraint != null) {
				//Log.d("contraint", ""+constraint);
				if (listTemp != null && listTemp.size() > 0) {
					//Log.d("listTemp", listTemp.get(0).nama);
					for (AnggaranPengeluaranObject p : listTemp) {
						if (p.nama.contains(constraint)) {
							//Log.d("value added", "added");							
							listResult.add(p);
						}
														
					}
				}
				
				filterResults.values = listResult;
			}
			return filterResults;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			list = (ArrayList<AnggaranPengeluaranObject>)results.values;
			notifyDataSetChanged();
			
		}
		
	}

	



}
