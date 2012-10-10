package com.babaenciel.gemi.pemasukan;

import com.babaenciel.gemi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class PemasukanView extends RelativeLayout {

	private Context context;
	private View view;

	public PemasukanView(Context context) {
		super(context);		
		this.context = context;
		view = LayoutInflater.from(context).inflate(R.layout.activity_pemasukan, this);
	}

}
