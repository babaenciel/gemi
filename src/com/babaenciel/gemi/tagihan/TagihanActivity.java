package com.babaenciel.gemi.tagihan;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.babaenciel.gemi.R;
import com.babaenciel.gemi.pemasukan.PemasukanFragmentPagerAdapter;
import com.babaenciel.gemi.pemasukan.PemasukanInsert;

public class TagihanActivity extends SherlockFragmentActivity implements TagihanInterface {
	private static final int THEME = R.style.Theme_Sherlock;
	ViewPager pager;
	TagihanPagerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		setTheme(THEME);
		setTitle("TAGIHAN");
		super.onCreate(arg0);		
		setContentView(R.layout.tagihan_activity);
		
		/*TagihanDatabase db = new TagihanDatabase(this);
		db.insertTagihan("listrik", 10000, "2012-10-10", 0);
		db.insertTagihan("air", 20000, "2012-10-10", 0);
		db.insertTagihan("internet", 10000, "2012-10-10", 0);
		db.insertTagihan("pulsa", 10000, "2012-10-10", 0);
		db.dbClose();*/
		
		/*Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2012);
        calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.DAY_OF_MONTH, 18);
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 18);
        calendar.set(Calendar.SECOND, 0);
		
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, TagihanBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		//am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (5 * 1000), pendingIntent);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
		//am.cancel(pendingIntent);
*/		  
		pager = (ViewPager) findViewById(R.id.tagihan_activity_pager);
		adapter = new TagihanPagerAdapter(getSupportFragmentManager(), this);
		pager.setAdapter(adapter);
		pager.setCurrentItem(adapter.getCount() / 2);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu1 = menu.addSubMenu("Action Item");
        subMenu1.add("Insert Tagihan");             

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
		if(item.getTitle().equals("Insert Tagihan")) {
			Intent i = new Intent(this, TagihanInsertActivity.class);			
			startActivity(i);
		}
		return true;
	}

	@Override
	public void onLunas() {
		/*adapter = new TagihanPagerAdapter(getSupportFragmentManager(), this);
		pager.setAdapter(adapter);
		pager.setCurrentItem(adapter.getCount() / 2);		*/
		/*Intent i = new Intent(this, TagihanActivity.class);
		finish();
		startActivity(i);*/
        adapter = new TagihanPagerAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount() / 2);
	}

    @Override
    public void onDelete() {
        //Intent i = new Intent(this, TagihanActivity.class);
        adapter = new TagihanPagerAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount() / 2);
    }


}
