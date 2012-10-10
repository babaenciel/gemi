package com.babaenciel.gemi.pemasukan;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.kategori.KategoriDatabase;
import com.babaenciel.gemi.utils.DBAdapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PemasukanActivity extends SherlockFragmentActivity implements PemasukanListInterface {
	public static int THEME = R.style.Theme_Sherlock;
	protected int focusedPage = 0;
	ArrayList<Integer> bulan;
	Context context = this;
	//public static DBAdapter dbAdapter;
	public PemasukanFragmentPagerAdapter adapter;
	ViewPager pager;
	int viewToggle = 1;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		setTitle("PEMASUKAN");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pemasukan_pager);
		
		//initialize database
		/*KategoriDatabase db2 = new KategoriDatabase(this);
		db2.insertKategori("Gaji");
		db2.insertKategori("Penjualan");
		db2.insertKategori("Hadiah");
		
		PemasukanDatabase db = new PemasukanDatabase(this);
		db.insertPemasukan("gajian", 200000, "2012-10-12", 1);
		db.insertPemasukan("gajian kedua", 200000, "2012-10-12", 2);
		db.insertPemasukan("gajian kemarin", 200000, "2012-10-20", 3);
		db.insertPemasukan("gajian bulan depan", 200000,"2012-10-23", 1);
		db.insertPemasukan("gajian aja", 200000, "2012-10-01", 2);
		db.insertPemasukan("gajian kemarin", 200000, "2012-10-20", 3);
		db.insertPemasukan("gajian bulan depan", 200000,"2012-10-23", 1);
		db.insertPemasukan("gajian aja", 200000, "2012-10-01", 2);
		db.insertPemasukan("gajian kemarin", 200000, "2012-10-20", 1);
		db.insertPemasukan("gajian bulan depan", 200000,"2012-10-23", 3);
		db.insertPemasukan("gajian aja", 200000, "2012-10-01", 2);
		db.dbClose();*/
		
		//create pager
		adapter = new PemasukanFragmentPagerAdapter(getSupportFragmentManager(), this, viewToggle);
		pager = (ViewPager) findViewById(R.id.pemasukan_pager);
		pager.setAdapter(adapter);
		pager.setCurrentItem(adapter.getCount() / 2);
		
		PemasukanListInterface childListener;
		//childListener.onListSelected()
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu1 = menu.addSubMenu("Action Item");
        subMenu1.add("Insert Pemasukan");     
        subMenu1.add("change menu");

        MenuItem subMenu1Item = subMenu1.getItem();
        subMenu1Item.setIcon(R.drawable.ic_launcher);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        /*SubMenu subMenu2 = menu.addSubMenu("Overflow Item");
        subMenu2.add("These");
        subMenu2.add("Are");
        subMenu2.add("Sample");
        subMenu2.add("Items");

        MenuItem subMenu2Item = subMenu2.getItem();
        subMenu2Item.setIcon(R.drawable.ic_action_search);*/

        return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Insert Pemasukan")) {
			Toast.makeText(getApplicationContext(), "Insert", Toast.LENGTH_SHORT).show();
		}else if(item.getTitle().equals("change menu")) {
			item.setTitle("change menu 2");
			viewToggle = 2;
			adapter = new PemasukanFragmentPagerAdapter(getSupportFragmentManager(), this, viewToggle);
			pager.setAdapter(adapter);
			pager.setCurrentItem(adapter.getCount() / 2);
		}else if(item.getTitle().equals("change menu 2")) {
			item.setTitle("change menu");
			viewToggle = 1;
			adapter = new PemasukanFragmentPagerAdapter(getSupportFragmentManager(), this, viewToggle);
			pager.setAdapter(adapter);
			pager.setCurrentItem(adapter.getCount() / 2);
		}
		return true;
	}
	
	//ini digunakan hanya untuk merefresh view nya.
	//onDeleteChild dipanggil ketika user menekan dialog delete yang dimunculkan dari dalam fragment(PemasukanFragment2).
	@Override
	public void onDeleteChild(int idChild, int viewToggle) {
		//Toast.makeText(this, "toast from activity : "+idChild, Toast.LENGTH_SHORT).show();		
		adapter = new PemasukanFragmentPagerAdapter(getSupportFragmentManager(), this, viewToggle);
		pager.setAdapter(adapter);
		pager.setCurrentItem(adapter.getCount() / 2);
		
		
	}
	
	//onUpdateChild dipanggil ketika user menekan dialog update yang dimunculkan dari dalam fragment(PemasukanFragment2).
	//ini digunakan untuk memanggil activity baru untuk menampilkan form update pemasukan.
	@Override
	public void onUpdateChild(int idChild, int viewToggle) {	
		
	}

	
}
