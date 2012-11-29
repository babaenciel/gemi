package com.babaenciel.gemi.tabungan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.pemasukan.PemasukanInsertActivity;

public class TabunganActivity extends SherlockFragmentActivity implements TabunganInterface {
	private static final int THEME = R.style.Theme_Sherlock;
	private ViewPager pager;
	TabunganFragmentPagerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {			
		setTheme(THEME);
		setTitle("TABUNGAN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabungan_activity);    
        
        adapter = new TabunganFragmentPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.tabungan_activity_pager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount() / 2);        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu = menu.addSubMenu("Menu");
		subMenu.add("Insert Tabungan");
		MenuItem subMenuItem = subMenu.getItem();		
        subMenuItem.setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_dark);
        subMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Insert Tabungan")) {
			Intent i = new Intent(this, TabunganInsertActivity.class);				
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDelete() {
		adapter = new TabunganFragmentPagerAdapter(getSupportFragmentManager());        
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount() / 2);  		
	}

	@Override
	public void onUpdate(int id_tabungan) {
		Intent i = new Intent(this, TabunganUpdateActivity.class);
		i.putExtra("id_tabungan", id_tabungan);
		startActivity(i);
		
	}
}
