package com.babaenciel.gemi.hutang;

import com.babaenciel.gemi.R;
import com.babaenciel.gemi.anggaran.AnggaranInsertFormActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;


public class HutangActivity extends SherlockFragmentActivity implements HutangInterface {
	private static final int THEME = R.style.Theme_Sherlock;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setTheme(THEME);
		setTitle("TAGIHAN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hutang_activity);
        
       /* HutangDatabase db = new HutangDatabase(this);
        db.insertHutang("hutang pada budi", 10000, 1000000, "2012-10-30");
        db.insertHutang("hutang pada ani", 0, 1000000, "2012-10-30");
        db.insertHutang("hutang pada tejo", 0, 1000000, "2012-10-30");
        db.insertHutang("hutang pada dengkul", 0, 1000000, "2012-10-30");
        db.insertHutang("hutang pada tedi", 0, 1000000, "2012-10-30");*/
        
        /*HutangCicilanDatabase db = new HutangCicilanDatabase(this);
		db.insertHutangCicilan("cicilan 1 nich", 100000, "2012-10-10", 1);
		db.insertHutangCicilan("cicilan 2 nich", 200000, "2012-10-10", 1);
		db.insertHutangCicilan("cicilan 3 nich", 300000, "2012-10-10", 1);
        db.dbClose();*/
        
        ViewPager pager = (ViewPager) findViewById(R.id.hutang_activity_pager);
        HutangFragmentPagerAdapter adapter = new HutangFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount() / 2);
        
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu1 = menu.addSubMenu("Action Item");
        subMenu1.add("Set Hutang Baru");             

        MenuItem subMenu1Item = subMenu1.getItem();
        subMenu1Item.setIcon(R.drawable.ic_launcher);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Set Hutang Baru")) {
			Intent i = new Intent(this, HutangInsertFormActivity.class);
			startActivity(i);			
		}
		return true;
	}

	@Override
	public void onCicilan(int id_hutang) {
		Intent i = new Intent(this, HutangCicilanActivity.class);
		i.putExtra("id_hutang", id_hutang);
		startActivity(i);
		
	}
}
