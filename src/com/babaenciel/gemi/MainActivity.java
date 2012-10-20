package com.babaenciel.gemi;

import com.babaenciel.gemi.anggaran.AnggaranActivity;
import com.babaenciel.gemi.hutang.HutangActivity;
import com.babaenciel.gemi.pemasukan.PemasukanActivity;
import com.babaenciel.gemi.tabungan.TabunganActivity;
import com.babaenciel.gemi.tagihan.TagihanActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String[] isi = new String[] {"pemasukan", "anggaran", "tagihan", "hutang", "tabungan"};
        ListView list = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, isi);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(((TextView)arg1).getText().equals("pemasukan")) {
					Intent i = new Intent(getApplicationContext(), PemasukanActivity.class);
					startActivity(i);
				}else if(((TextView)arg1).getText().equals("anggaran")) {
					Intent i = new Intent(getApplicationContext(), AnggaranActivity.class);
					startActivity(i);
				}else if(((TextView)arg1).getText().equals("tagihan")) {
					Intent i = new Intent(getApplicationContext(), TagihanActivity.class);
					startActivity(i);
				}else if(((TextView)arg1).getText().equals("hutang")){
					Intent i = new Intent(getApplicationContext(), HutangActivity.class);
					startActivity(i);
				}else {
					Intent i = new Intent(getApplicationContext(), TabunganActivity.class);
					startActivity(i);
				}	
				
			}        	
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
