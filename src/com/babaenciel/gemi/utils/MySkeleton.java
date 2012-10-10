package com.babaenciel.gemi.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MySkeleton {
	private static DecimalFormatSymbols simbolClass;
	private DecimalFormat myDecimalFormat;
	
	public MySkeleton() {
		this.simbolClass = new DecimalFormatSymbols();
		simbolClass.setGroupingSeparator('.');
		this.myDecimalFormat = new DecimalFormat("###,###,###", simbolClass);
	}
		
}
