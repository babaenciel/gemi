package com.babaenciel.gemi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.Time;
import android.util.Log;

public class MyDate {

	Time time;
	public int date;
	public int month;
	public int year;
	public String dateFull1;	//dd-mm-yyyy
	public String dateFull2;	//yyyy-mm-dd
	int monthJumlah;
	public int monthCounter;	
	public int yearCounter;	
	public String monthText;
	public String yearText;
	
	
	
	public MyDate() {
		time = new Time();
		monthCounter = 0;
		yearCounter = 0;
		monthJumlah = 0;
	}
	
	public void setNow() {
		time.setToNow();
		date = time.monthDay;
		month = time.month+1;	//karena month dimulai dari 0
		year = time.year;
		dateFull1 = date+"-"+month+"-"+year;
		dateFull2 = year+"-"+month+"-"+date;
	}
	
	/*algoritma agar rumit.
	jadi intinya adalah menambahkan bulan dengan posisi ketika diswipe.
	jika swipe kekanan maka counter akan berincrement, dsb.
		if(monthJumlah < 1) menangani swipe kebelakang.
		ketika swipe kebelakang itu dibutuhkan algoritma konversi
		karena monthJumlah bernilai -1 di awal dan kita butuh memulai dari bulan terakhir (bulan 12).
		konversi yang dilakukan adalah mengkonversi dari -1 ke 12, -2 ke 11 dst.
		
	
	*/
	public void geserBulan(int counter) {
		monthJumlah = month + counter;
		
		//menangani swipe kebelakang yang bernilai negatif.
		//mulainya dari -1 -2 -3 -4 .... -12.
		//perlu dikonversi dari -1 ke 12 dulu pake method konversiBulanMinus.		
		if(monthJumlah < 1) {	
			//di -1 diawal pada monthJumlah untuk melewati nilai 0	
			monthJumlah = (monthJumlah - 1);		
			
			//ini if untuk menangani monthJumlah bernilai -12 yang jika di mod 12 hasilnya 0.
			//padahal harusnya masih -1.
			if(monthJumlah % 12 == 0) {
				monthCounter = -12;
				
				/*maksud dari -1 adalah karena ketika sudah masuk ke if(monthJumlah < 1) yang diatas,
				maka tahun sudah berkurang 1. jadi yearCounter harus selalu dikurangi -1 terlebih dahulu.
				maksud dari monthJumlah+1 adalah untuk menangani monthJumlah yg nilainya -12 div 12 yang hasilnya 1.
				padahal hasil yang dibutuhkan adalah 0 karena -12 masih dalam tahun yg sama, belum pindah tahun. 
				Jadi karena monthJumlah yg nilainya 12 di div 12 hasilnya 1,
				maka perlu ditambahkan 1 dulu pada monthJumlah (jadi -11) agar ketika di mod 12 hasilnya masih 0.*/
				yearCounter = ((monthJumlah+1) / 12)-1;		
				
				monthCounter = konversiBulanMinus(monthCounter);
				Log.d("monthCounter", ""+monthCounter);				
				monthText = konversiBulanKeText(monthCounter);
				yearText = Integer.toString(year + yearCounter);
			}else {				
				monthCounter = monthJumlah % 12;
				yearCounter = (monthJumlah / 12)-1;
				
				monthCounter = konversiBulanMinus(monthCounter);
				Log.d("monthCounter", ""+monthCounter);				
				monthText = konversiBulanKeText(monthCounter);
				yearText = Integer.toString(year + yearCounter);
			}
			
		//ini untuk menangani monthJumlah yang nilainya positif.
		}else if(monthJumlah % 12 == 0) {
			monthCounter = 12;
			yearCounter = year + ((monthJumlah-1) / 12);
			
			monthText = konversiBulanKeText(monthCounter);
			yearText = Integer.toString(year + yearCounter);
		}else {
			monthCounter = monthJumlah % 12;
			yearCounter = year + (monthJumlah / 12);
			
			monthText = konversiBulanKeText(monthCounter);
			yearText = Integer.toString(year + yearCounter);
		} 
		
	}
	
	//from dd-mm-yyyy to yyyy-mm-dd
	public String konversiTanggal1(String tanggaldd) {
		SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat simple2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1;
		String date2 = null;
		try {
			date1 = simple.parse(tanggaldd);
			date2 = simple2.format(date1);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}        		
		
		return date2;
	}
	
	//from yyyy-mm-dd to dd-mm-yyyy
	public String konversiTanggal2(String tanggalyyyy) {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simple2 = new SimpleDateFormat("dd-MM-yyyy");
		Date date1;
		String date2 = null;
		try {
			date1 = simple.parse(tanggalyyyy);
			date2 = simple2.format(date1);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}        		
		
		return date2;
	}
	
	public String konversiBulanKeText(int monthDigit) {
		if(monthDigit == 1) {
			return "Januari";
		}else if(monthDigit == 2) {
			return "Februari";
		}else if(monthDigit == 3) {
			return "Maret";
		}else if(monthDigit == 4) {
			return "April";
		}else if(monthDigit == 5) {
			return "Mei";
		}else if(monthDigit == 6) {
			return "Juni";
		}else if(monthDigit == 7) {
			return "Juli";
		}else if(monthDigit == 8) {
			return "Agustus";
		}else if(monthDigit == 9) {
			return "September";
		}else if(monthDigit == 10) {
			return "Oktober";
		}else if(monthDigit == 11) {
			return "November";
		}else {
			return "Desember";
		}
		
	}
	
	public int konversiBulanMinus(int monthDigitMinus) {
		if(monthDigitMinus == -1) {
			return 12;
		}else if(monthDigitMinus == -2) {
			return 11;
		}else if(monthDigitMinus == -3) {
			return 10;
		} else if(monthDigitMinus == -4) {
			return 9;
		} else if(monthDigitMinus == -5) {
			return 8;
		} else if(monthDigitMinus == -6) {
			return 7;
		} else if(monthDigitMinus == -7) {
			return 6;
		} else if(monthDigitMinus == -8) {
			return 5;
		} else if(monthDigitMinus == -9) {
			return 4;
		} else if(monthDigitMinus == -10) {
			return 3;
		} else if(monthDigitMinus == -11) {
			return 2;
		} else {
			return 1;
		} 
		
	}
		
	
}
