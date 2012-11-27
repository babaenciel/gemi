package com.babaenciel.gemi.hutang;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.babaenciel.gemi.R;

public class HutangCicilanActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {				
		setTheme(THEME);
		setTitle("HUTANG CICILAN");		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.hutang_cicilan_activity);
		
		int id_hutang = getIntent().getExtras().getInt("id_hutang");				
		
		HutangCicilanDatabase db = new HutangCicilanDatabase(this);
		ArrayList<HutangCicilanObject> values = new ArrayList<HutangCicilanObject>();
		values = db.getAllHutangCicilan(id_hutang);
		
		ListView list = (ListView) findViewById(R.id.hutang_cicilan_list);
		HutangCicilanListAdapter adapter = new HutangCicilanListAdapter(this, values);
		list.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu1 = menu.addSubMenu("Action Item");
        subMenu1.add("Insert Cicilan");             

        MenuItem subMenu1Item = subMenu1.getItem();
        subMenu1Item.setIcon(R.drawable.ic_launcher);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Insert Cicilan")) {
			//Intent i = new Intent(this, HutangInsertFormActivity.class);
			//startActivity(i);			
		}
		return true;
	}
}
