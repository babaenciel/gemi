package com.babaenciel.gemi.anggaran;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.babaenciel.gemi.R;

public class AnggaranActivity extends SherlockFragmentActivity implements AnggaranInterface {
	private static final int THEME = R.style.Theme_Sherlock;

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
		
		ViewPager pager = (ViewPager) findViewById(R.id.anggaran_activity_pager);
		AnggaranFragmentPagerAdapter adapter = new AnggaranFragmentPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setCurrentItem(adapter.getCount() / 2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
		return super.onOptionsItemSelected(item);
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
}
