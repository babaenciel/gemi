package com.babaenciel.gemi.anggaran;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.babaenciel.gemi.R;

public class AnggaranActivity extends SherlockFragmentActivity implements AnggaranInterface {
	private static final int THEME = R.style.Theme_Sherlock;
	ViewPager pager;
	AnggaranFragmentPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		setTheme(THEME);
		setTitle("ANGGARAN");
		
		super.onCreate(arg0);
		setContentView(R.layout.anggaran_activity);
		
		/*AnggaranDatabase db = new AnggaranDatabase(this);
		db.insertAnggaran("makan", 250000, 0, "2012-10-01");
		db.insertAnggaran("ngedate", 300000, 0, "2012-10-01");
		db.insertAnggaran("main", 500000, 0, "2012-10-01");
		db.insertAnggaran("rokok", 250000, 0, "2012-10-01");
		db.insertAnggaran("jajan", 400000, 0, "2012-10-01");*/
		
		/*AnggaranPengeluaranDatabase db = new AnggaranPengeluaranDatabase(this);
		db.insertAnggaranPengeluaran("makan siang", 10000, "2012-10-10", 1);
		db.insertAnggaranPengeluaran("makan pagi", 20000, "2012-10-10", 1);
		db.insertAnggaranPengeluaran("makan malem", 5000, "2012-10-10", 1);
		db.insertAnggaranPengeluaran("makan sore", 20000, "2012-10-10", 1);
		db.insertAnggaranPengeluaran("makan malem banget", 50000, "2012-10-10", 1);*/
		
		pager = (ViewPager) findViewById(R.id.anggaran_activity_pager);
		adapter = new AnggaranFragmentPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setCurrentItem(adapter.getCount() / 2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu1 = menu.addSubMenu("Action Item");
        subMenu1.add("Set Anggaran Baru");             

        MenuItem subMenu1Item = subMenu1.getItem();
        subMenu1Item.setIcon(R.drawable.ic_launcher);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Set Anggaran Baru")) {
			Intent i = new Intent(this, AnggaranInsertFormActivity.class);
			startActivity(i);			
		}
		return true;
	}

	//onResume() digunakan agar ketika user kembali dari AnggaranPengeluaranActivity,
	//nilai total pengeluarannya ikut berubah.
	//Disini dilakukan pengresetan adapter.
	@Override
	protected void onResume() {		
		super.onResume();
		Log.d("resumed", "anggaran resumed");
		adapter = new AnggaranFragmentPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setCurrentItem(adapter.getCount() / 2);
	}
	
	@Override
	public void onDetail(int id_anggaran) {
		Intent i = new Intent(this, AnggaranPengeluaranActivity.class);
		i.putExtra("id_anggaran", id_anggaran);		
		startActivity(i);
		
	}

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restartActivity() {
		// TODO Auto-generated method stub
		
	}
}
