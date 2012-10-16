package com.babaenciel.gemi.tagihan;

import com.babaenciel.gemi.utils.MyDate;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TagihanPagerAdapter extends FragmentStatePagerAdapter {

	private Context context;
	private int counter;
	private MyDate myDate;

	public TagihanPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
		this.counter = 0;		
		myDate = new MyDate();
		myDate.setNow();	
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment f = new Fragment();
		counter = arg0 - (10000/2);
		myDate.geserBulan(counter);
		
		f = TagihanFragment.newInstance(myDate);
		return f;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10000;
	}

}
