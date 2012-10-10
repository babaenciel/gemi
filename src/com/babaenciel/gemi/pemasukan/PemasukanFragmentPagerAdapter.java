package com.babaenciel.gemi.pemasukan;

import com.babaenciel.gemi.utils.DBAdapter;
import com.babaenciel.gemi.utils.MyDate;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class PemasukanFragmentPagerAdapter extends FragmentStatePagerAdapter {

	private Context context;
	private int counter;
	private MyDate myDate;
	private DBAdapter dbAdapter;
	private int viewToggle;

	public PemasukanFragmentPagerAdapter(FragmentManager fm, Context context, int viewToggle) {
		super(fm);
		this.context = context;		
		this.counter = 0;
		this.viewToggle = viewToggle;
		myDate = new MyDate();
		myDate.setNow();		
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment f = new Fragment();
        //f = PageFragment.newInstance(context, Integer.toString(arg0), Integer.toString(arg0+1));
		counter = arg0 - (10000/2);
		myDate.geserBulan(counter);
		
		if(viewToggle == 1) {
			f = PemasukanFragment.newInstance(myDate);
			//Log.d("toggle", "1");
		}else {
			f = PemasukanFragment2.newInstance(myDate);
			//Log.d("toggle", "2");
		}
		
		//Log.d("fragment created", "created at position : " + arg0);
        return f;
	}

	@Override
	public int getCount() {
		return 10000;
	}

}
