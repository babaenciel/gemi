package com.babaenciel.gemi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.babaenciel.gemi.utils.DBAdapter;

public class DashboardDatabase {

	private DBAdapter dbAdapter;

	public DashboardDatabase(Context context) {
		this.dbAdapter = new DBAdapter(context);
	}
	
	
}
