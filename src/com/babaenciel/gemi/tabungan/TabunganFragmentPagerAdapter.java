package com.babaenciel.gemi.tabungan;

import com.babaenciel.gemi.pemasukan.PemasukanFragment;
import com.babaenciel.gemi.pemasukan.PemasukanFragment2;
import com.babaenciel.gemi.utils.MyDate;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabunganFragmentPagerAdapter extends FragmentStatePagerAdapter {

	private Context context;
	private int counter;
	private int viewToggle;
	private MyDate myDate;

	public TabunganFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		this.context = context;		
		this.counter = 0;		
		myDate = new MyDate();
		myDate.setNow();	
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment f = new Fragment();
        //f = PageFragment.newInstance(context, Integer.toString(arg0), Integer.toString(arg0+1));
		counter = arg0 - (10000/2);
		myDate.geserBulan(counter);
		
		f = TabunganFragment.newInstance(myDate);
		
		//Log.d("fragment created", "created at position : " + arg0);
        return f;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10000;
	}

}
