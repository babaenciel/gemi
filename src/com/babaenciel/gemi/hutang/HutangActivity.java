package com.babaenciel.gemi.hutang;

import com.babaenciel.gemi.R;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;


public class HutangActivity extends SherlockFragmentActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setTheme(THEME);
		setTitle("TAGIHAN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hutang_activity);
        
        ViewPager pager = (ViewPager) findViewById(R.id.hutang_activity_pager);
        HutangFragmentPagerAdapter adapter = new HutangFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount() / 2);
        
    }
}
