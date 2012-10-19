package com.babaenciel.gemi.hutang;

import com.babaenciel.gemi.R;
import android.os.Bundle;
import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.SherlockActivity;


public class HutangActivity extends SherlockActivity {
	private static final int THEME = R.style.Theme_Sherlock;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setTheme(THEME);
		setTitle("TAGIHAN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hutang_activity);        
    }
}
