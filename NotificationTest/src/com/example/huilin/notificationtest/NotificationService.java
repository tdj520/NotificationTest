package com.example.huilin.notificationtest;

import android.app.Notification; 
import android.app.NotificationManager; 
import android.app.PendingIntent; 
import android.app.RemoteInput; 
import android.app.Service; 
import android.content.ComponentName; 
import android.content.Intent; 
import android.graphics.drawable.Icon; 
import android.os.Build; 
import android.os.Bundle; 
import android.os.IBinder; 
import android.util.Log; 
import android.view.View; 
import android.widget.Toast; 

public class NotificationService extends Service {

	private String TAG = "linhui";

	@Override 
	public IBinder onBind(Intent intent) { 
		return null; 
	} 

	@Override 
	public int onStartCommand(Intent intent, int flags, int startId) { 
		Bundle data = RemoteInput.getResultsFromIntent(intent); 
		Log.i(TAG,"message content = "+data.getString("key_text_reply")); 
		Toast.makeText(this,"Content : "+data.getString("key_text_reply"),Toast.LENGTH_LONG).show(); 
		showReplyStyleNotification(intent); 
		return super.onStartCommand(intent, flags, startId); 
	} 

	private void showReplyStyleNotification(Intent intent) { 
		Log.i(TAG, "showCustomStyleNotification"); 
		try { 
			Thread.sleep(2000); 
		} catch (InterruptedException e) { 
			e.printStackTrace(); 
		} 
		NotificationManager mNotificationManager = ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)); 
		mNotificationManager.cancel(MainActivity.notificationIDReply); 
	} 
}
