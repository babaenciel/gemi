package com.babaenciel.gemi.tagihan;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.utils.MyDate;

public class TagihanFragment extends SherlockFragment {
	
	public static SherlockFragment newInstance(MyDate myDate) {
		TagihanFragment f = new TagihanFragment();
		
		String month = myDate.konversiBulanDitambahi0(myDate.monthCounter);
		
		Bundle b = new Bundle();				
		b.putInt("year", myDate.yearCounter);
		b.putString("month", month);		
		b.putString("monthText", myDate.monthText);
		b.putString("yearText", myDate.yearText);
		
		f.setArguments(b);
		
		return f;				
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.tagihan_fragment, null);
		TextView text = (TextView) view.findViewById(R.id.tagihan_fragment_bulan);
		text.setText(getArguments().getString("monthText"));
		
		ArrayList<TagihanObject> values = new ArrayList<TagihanObject>();
		for(int i = 0; i < 10; i++) {
			values.add(new TagihanObject(1, "asdf", 123000, "2012-10-10"));
		}
		
		ListView list = (ListView) view.findViewById(R.id.tagihan_fragment_list);
		TagihanFragmentListAdapter adapter = new TagihanFragmentListAdapter(getActivity(), values);
		list.setAdapter(adapter);
		return view;
	}

}
