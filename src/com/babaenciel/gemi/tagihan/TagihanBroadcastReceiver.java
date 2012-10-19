package com.babaenciel.gemi.tagihan;

import com.babaenciel.gemi.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TagihanBroadcastReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String nama = intent.getExtras().getString("nama");
		
		NotificationManager nm;
        nm = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);        
        Notification notification = new Notification(R.drawable.ic_launcher, "Test Alarm", System.currentTimeMillis());        
        Intent i = new Intent(context, TagihanActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | intent.FLAG_ACTIVITY_NEW_TASK);        
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);        
        notification.setLatestEventInfo(context, "tes", "Deadline tagihan \""+ nama +"\"! ", contentIntent);        
        nm.notify(1, notification);
		
	}
}
