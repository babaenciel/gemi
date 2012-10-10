package com.babaenciel.gemi.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.util.Log;

public class DatabaseFile {
	
	private Context context;
	
	public DatabaseFile(Context context) {
		this.context = context;
	}
	
	public void StoreDatabase() {
		try {
			// Open your local db as the input stream
			InputStream myInput = context.getAssets().open("FinancialPlanning.sqlite");

			// Path to the just created empty db
			String outFileName = "/data/data/ciel.en.baba/databases/FinancialPlanning.sqlite";

			OutputStream myOutput = new FileOutputStream(outFileName);

			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) 
			 {
			     myOutput.write(buffer, 0, length);
			 }

			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
			} 
			catch (Exception e) 
			{
			Log.e("error", e.toString());
			}

	}
}
