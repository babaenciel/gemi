package com.babaenciel.gemi.anggaran;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.babaenciel.gemi.R;

public class AnggaranPengeluaranActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	private static final CharSequence[] anggaranPengeluaranMenuItem = {"Edit", "Delete"};
	public static int buttonPressed;	//digunakan untuk mengecek apakah dialog telah diedit atau didelete.
	Context context = this;
	AnggaranPengeluaranDatabase db;
	AnggaranPengeluaranActivity activity = this;
	AnggaranInterface anggaranInterface;
	int id_anggaran;

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		setTheme(THEME);
		setTitle("ANGGARAN PENGELUARAN DETAIL");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anggaran_pengeluaran_activity);
		
		buttonPressed = 0;
		id_anggaran = getIntent().getExtras().getInt("id_anggaran");
		Log.d("id_anggaran", ""+id_anggaran);
		
		//initialize database
		db = new AnggaranPengeluaranDatabase(this);
		
		//nominal format		
		DecimalFormatSymbols simbol = new DecimalFormatSymbols();		
		simbol.setGroupingSeparator('.');
		DecimalFormat customFormat = new DecimalFormat("###,###,###",simbol);
		
		//set total
		int jumlah = db.getAnggaranPengeluaranTotal(id_anggaran);
		TextView totalText = (TextView) findViewById(R.id.anggaran_pengeluaran_nominal);
		totalText.setText(customFormat.format(jumlah));
		
		//set list		
		ArrayList<AnggaranPengeluaranObject> values = db.getAnggaranPengeluaranById(id_anggaran);		
		ListView list = (ListView) findViewById(R.id.anggaran_pengeluaran_list);
		final AnggaranPengeluaranListAdapter adapter = new AnggaranPengeluaranListAdapter(this, values);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final int id_anggaran_pengeluaran = (int) arg3;
				final AnggaranPengeluaranObject anggaranPengeluaranObject = (AnggaranPengeluaranObject) adapter.getItem(arg2);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Submenu");
				builder.setItems(anggaranPengeluaranMenuItem, new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						if(anggaranPengeluaranMenuItem[which].equals("Edit")) {
							Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
						}else {
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				    		alertDialogBuilder.setTitle("Konfirmasi Delete");
				    		alertDialogBuilder.setMessage("Yakin mau menghapus : "+ anggaranPengeluaranObject.nama + " ?")
				    			.setCancelable(false)
				    			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				    				public void onClick(DialogInterface dialog,int id) {
				    					db.deleteAnggaranPengeluaran(id_anggaran_pengeluaran, id_anggaran);				    					
				    					activity.finish();
				    					activity.startActivity(getIntent());
				    					buttonPressed = 1;
				    				}
				    			})
				    			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				    				public void onClick(DialogInterface dialog,int id) {				    						
				    					dialog.cancel();
				    				}
				    			});				     
				    		AlertDialog alertDialog = alertDialogBuilder.create();				 
				    		alertDialog.show();
						}
						
					}
				});
				
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu1 = menu.addSubMenu("Action Item");
        subMenu1.add("Insert Pengeluaran");             

        MenuItem subMenu1Item = subMenu1.getItem();
        subMenu1Item.setIcon(R.drawable.ic_launcher);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Insert Pengeluaran")) {
			Intent i = new Intent(this, AnggaranPengeluaranInsertFormActivity.class);
			i.putExtra("id_anggaran", id_anggaran);
			startActivity(i);
		}
		return true;
	}
	
	/*@Override
	public void onBackPressed() {		
		super.onBackPressed();
		
		if(buttonPressed == 1) {
			Intent i = new Intent(context, AnggaranActivity.class);
			startActivity(i);
		}else {
			
		}
	}*/
	
	/*@Override
	protected void onResume() {		
		super.onResume();
		Intent i = new Intent(context, AnggaranPengeluaranActivity.class);
		finish();
		startActivity(i);
	}*/
		
}
