package com.babaenciel.gemi.hutang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.utils.MyDate;

public class HutangFragment extends SherlockFragment {
	
	public static HutangFragment newInstance(MyDate myDate) {
		HutangFragment f = new HutangFragment();
		
		String month = myDate.konversiBulanDitambahi0(myDate.monthCounter);
		Bundle bundle = new Bundle();
		//bundle.putInt("month", myDate.monthCounter);
		bundle.putString("month", month);
		bundle.putInt("year", myDate.yearCounter);
		bundle.putString("monthText", myDate.monthText);
		bundle.putString("yearText", myDate.yearText);
		f.setArguments(bundle);
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.hutang_fragment, null);
		
		TextView bulanView = (TextView) view.findViewById(R.id.hutang_bulan);
		bulanView.setText(getArguments().getString("monthText"));
		
		ListView list = (ListView) view.findViewById(R.id.hutang_list);
		HutangFragmentListAdapter adapter = new HutangFragmentListAdapter()
		list.setAdapter(adapter)
		
		
		return view;
	}
}
