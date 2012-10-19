package com.babaenciel.gemi.tabungan;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.babaenciel.gemi.R;

public class TabunganActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {			
		setTheme(THEME);
		setTitle("TAGIHAN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hutang_activity);      
	}
}
