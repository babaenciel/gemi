package com.babaenciel.gemi.hutang;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.babaenciel.gemi.R;

public class HutangCicilanActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	int id_hutang;
	Context context = this;
	HutangCicilanActivity activity = this;
	protected String[] items = {"Edit", "Delete"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {				
		setTheme(THEME);
		setTitle("HUTANG CICILAN");		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.hutang_cicilan_activity);
		
		id_hutang = getIntent().getExtras().getInt("id_hutang");				
		
		final HutangCicilanDatabase db = new HutangCicilanDatabase(this);
		
		//nominal format		
		DecimalFormatSymbols simbol = new DecimalFormatSymbols();		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		
		//set total cicilan
		TextView total = (TextView) findViewById(R.id.hutang_cicilan_nominal);
		int totalCicilan = db.getHutangCicilanTotal(id_hutang);
		total.setText(customFormat.format(totalCicilan));
		
		ArrayList<HutangCicilanObject> values = new ArrayList<HutangCicilanObject>();
		values = db.getAllHutangCicilan(id_hutang);
		
		ListView list = (ListView) findViewById(R.id.hutang_cicilan_list);
		HutangCicilanListAdapter adapter = new HutangCicilanListAdapter(this, values);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final int id_hutang_cicilan = (int)arg3;
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("sub menu");
				builder.setItems(items , new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(items[which].equals("Edit")) {
							Intent i = new Intent(context, HutangCicilanUpdateActivity.class);
							i.putExtra("id_hutang", id_hutang);
							i.putExtra("id_hutang_cicilan", id_hutang_cicilan);
							startActivity(i);
						}else {
							AlertDialog.Builder builder = new AlertDialog.Builder(context);
							builder.setTitle("Konfirmasi Delete");
							builder.setMessage("yakin?");
							builder.setPositiveButton("yes", new OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									db.deleteHutangCicilan(id_hutang_cicilan, id_hutang);
									activity.finish();
									Intent i = new Intent(context, HutangCicilanActivity.class);
									i.putExtra("id_hutang", id_hutang);									
									startActivity(i);
								}
							});
							builder.setNegativeButton("no", new OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();									
								}
							});
							
							AlertDialog alertDialog = builder.create();
							alertDialog.show();
						}
						
					}
										
				});
				
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
				
			}
		});
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
			Intent i = new Intent(this, HutangCicilanInsertFormActivity.class);
			i.putExtra("id_hutang", id_hutang);
			startActivity(i);			
		}
		return true;
	}
}
