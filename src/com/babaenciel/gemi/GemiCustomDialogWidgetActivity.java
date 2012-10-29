package com.babaenciel.gemi;

import com.babaenciel.gemi.anggaran.AnggaranPengeluaranInsertFormActivity;
import com.babaenciel.gemi.anggaran.AnggaranPengeluaranInsertFormFromWidgetActivity;
import com.babaenciel.gemi.hutang.HutangInsertFormActivity;
import com.babaenciel.gemi.pemasukan.PemasukanInsertActivity;
import com.babaenciel.gemi.tabungan.TabunganActivity;
import com.babaenciel.gemi.tagihan.TagihanInsertActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class GemiCustomDialogWidgetActivity extends Activity {
	private static final int CUSTOM_DIALOG = 0;
	Dialog listDialog;
	Context context = this;
	String[] values = {"Pemasukan", "Anggaran", "Tagihan", "Hutang", "Tabungan"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.custom_dialog_widget, null);			
		ListView list = (ListView) view.findViewById(R.id.custom_dialog_widget_list);		
		listDialog = new Dialog(context);
		listDialog.setTitle("Pilih Menu Untuk Menginput");
		listDialog.setContentView(view);
		listDialog.setCancelable(true);			
		list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, values));
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String title = ((TextView)arg1).getText().toString();
				if(title.equals("Pemasukan")) {
					Intent i = new Intent(context, PemasukanInsertActivity.class);
					//dismissDialog(CUSTOM_DIALOG);
					finish();
					startActivity(i);
				}else if(title.equals("Anggaran")) {
					Intent i = new Intent(context, AnggaranPengeluaranInsertFormFromWidgetActivity.class);
					//dismissDialog(CUSTOM_DIALOG);
					finish();
					startActivity(i);
				}else if(title.equals("Tagihan")) {
					Intent i = new Intent(context, TagihanInsertActivity.class);
					//dismissDialog(CUSTOM_DIALOG);
					finish();
					startActivity(i);
				}else if(title.equals("Hutang")) {
					Intent i = new Intent(context, HutangInsertFormActivity.class);
					//dismissDialog(CUSTOM_DIALOG);
					finish();
					startActivity(i);
				}else  {
					Intent i = new Intent(context, TabunganActivity.class);
					//dismissDialog(CUSTOM_DIALOG);
					finish();
					startActivity(i);
				}
				
			}
		});
		
		listDialog.show();
		//showDialog(CUSTOM_DIALOG);
	}
	
	/*@Override
	protected Dialog onCreateDialog(int id) {
		if(id == CUSTOM_DIALOG) {
			LayoutInflater inflater = getLayoutInflater();
			View view = inflater.inflate(R.layout.custom_dialog_widget, null);			
			ListView list = (ListView) view.findViewById(R.id.custom_dialog_widget_list);
			
			listDialog = new Dialog(context);
			listDialog.setTitle("Pilih Menu Untuk Menginput");
			listDialog.setContentView(view);
			listDialog.setCancelable(true);			
			list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, values));
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					String title = ((TextView)arg1).getText().toString();
					if(title.equals("Pemasukan")) {
						Intent i = new Intent(context, PemasukanInsert.class);
						dismissDialog(CUSTOM_DIALOG);
						finish();
						startActivity(i);
					}else if(title.equals("Anggaran")) {
						Intent i = new Intent(context, AnggaranPengeluaranInsertFormFromWidgetActivity.class);
						dismissDialog(CUSTOM_DIALOG);
						finish();
						startActivity(i);
					}else if(title.equals("Tagihan")) {
						Intent i = new Intent(context, TagihanInsertActivity.class);
						dismissDialog(CUSTOM_DIALOG);
						finish();
						startActivity(i);
					}else if(title.equals("Hutang")) {
						Intent i = new Intent(context, HutangInsertFormActivity.class);
						dismissDialog(CUSTOM_DIALOG);
						finish();
						startActivity(i);
					}else  {
						Intent i = new Intent(context, TabunganActivity.class);
						dismissDialog(CUSTOM_DIALOG);
						finish();
						startActivity(i);
					}
					
				}
			});
			
			listDialog.show();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(view);
			builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(context, MainActivity.class);
					startActivity(intent);					
					finish();
				}
			});
			
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		
        return null;
	}*/
	
}
