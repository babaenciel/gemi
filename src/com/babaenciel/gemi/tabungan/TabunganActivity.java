package com.babaenciel.gemi.tabungan;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.babaenciel.gemi.R;

public class TabunganActivity extends SherlockFragmentActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	private ViewPager pager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {			
		setTheme(THEME);
		setTitle("TABUNGAN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabungan_activity);    
        
        TabunganFragmentPagerAdapter adapter = new TabunganFragmentPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.tabungan_activity_pager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount() / 2);        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		return super.onCreateOptionsMenu(menu);
	}
}
