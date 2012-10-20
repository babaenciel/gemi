package com.babaenciel.gemi.hutang;

import com.babaenciel.gemi.utils.MyDate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HutangFragmentPagerAdapter extends FragmentStatePagerAdapter {

	private MyDate myDate;
	private int counter;

	public HutangFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		myDate = new MyDate();
		myDate.setNow();
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment f = new Fragment();
		
		counter = arg0 - (10000/2);
		myDate.geserBulan(counter);
		
		f = HutangFragment.newInstance(myDate);
		
		return f;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10000;
	}

}
