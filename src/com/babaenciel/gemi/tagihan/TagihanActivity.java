package com.babaenciel.gemi.tagihan;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.babaenciel.gemi.R;

public class TagihanActivity extends SherlockFragmentActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	
	@Override
	protected void onCreate(Bundle arg0) {
		setTheme(THEME);
		setTitle("TAGIHAN");
		super.onCreate(arg0);
		setContentView(R.layout.tagihan_activity);
		
		ViewPager pager = (ViewPager) findViewById(R.id.tagihan_activity_pager);
		TagihanPagerAdapter adapter = new TagihanPagerAdapter(getSupportFragmentManager(), this);
		pager.setAdapter(adapter);
		pager.setCurrentItem(adapter.getCount() / 2);
		
	}
}
