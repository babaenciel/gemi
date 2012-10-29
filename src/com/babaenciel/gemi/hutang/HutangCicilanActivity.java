package com.babaenciel.gemi.hutang;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
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
}
