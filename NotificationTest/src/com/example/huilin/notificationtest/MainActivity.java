package com.example.huilin.notificationtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.app.NotificationChannel;
import android.widget.Button;
import android.util.Log;
import android.content.ComponentName;
import android.app.RemoteInput;


public class MainActivity extends Activity {

    private NotificationManager mNotificationManager;
    Button button,message;
    private String TAG = "linhui";
    public String mId, mDescription;
    public int mImportance;
    NotificationChannel mChannel;
    private static final String mPackageName = "com.example.huilin.notificationtest";
    private static final String mMainActivity = "com.example.huilin.notificationtest.MainActivity";
    private static final String mLongText = "如果,所有的伤痕都能够痊愈;"
                                            +"\n如果,所有的真心都能够换来真意;"
                                            +"\n如果,所有的相信都能够坚持;"
                                            +"\n如果,所有的情感都能够完美;"
                                            +"\n如果,依然能相遇在某座城;"
                                            +"\n单纯的微笑,微微的幸福,肆意的拥抱,该多好;"
                                            +"\n可是真的只是如果。。。";

    int notificationIDBigPicture = 100;
    int notificationIDMessage = 101;
    int notificationIDReply = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createNotificationChannel();
        button = (Button)findViewById(R.id.sendNoti);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.i(TAG,"Post big picture stype notification.");
                postBigPictureNotification();
            }
        });
    }

    private void createNotificationChannel() {
        mId ="channel_1";//channel的id 
        mDescription = "123";//channel的描述信息 
        mImportance = NotificationManager.IMPORTANCE_HIGH;//channel的重要性 
        mChannel = new NotificationChannel(mId, "123", mImportance);//生成channel
        mNotificationManager.createNotificationChannel(mChannel);
    }

    public void postBigPictureNotification() { 
        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle();
        //R.drawable.back
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_launcher));

        Notification.Builder builder = new Notification.Builder(getApplicationContext(),mId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.dog))
        .setTicker("showBigView_Picture")
        .setContentInfo("contentInfo")
        .setStyle(bigPictureStyle)
        .setAutoCancel(true)
        .setContentTitle("Test")
        .setContentText("BigPictureStyle notification")
        .setPriority(Notification.PRIORITY_MAX);

        mNotificationManager.notify(notificationIDBigPicture, builder.build());
    }


    public void postMessagingNotification(View v) {
        Log.i(TAG, "sendMessagingNotification");
        Intent mIntent = new Intent();
        ComponentName name = new ComponentName(mPackageName, mMainActivity);
        mIntent.setComponent(name);
        PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, mIntent, 0);

        Notification.MessagingStyle mMessagingStyle = new Notification.MessagingStyle(TAG);
        mMessagingStyle.addMessage("Message Content", 10*1000, "sender");
        mMessagingStyle.setConversationTitle("10086");

        Notification.Builder mBuilder = new Notification.Builder(MainActivity.this, mId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("余额提醒")
        .setContentText("尊敬的用户,您的余额不足，请及时充值。")
        .setContentIntent(mPendingIntent)
        .setPriority(Notification.PRIORITY_MAX)
        .setStyle(mMessagingStyle);

        mNotificationManager.notify(notificationIDMessage, mBuilder.build());
    }


    public void postReplyStyleNotification(View view) {
        Log.i(TAG, "showCustomStyleNotification");

        RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply").setLabel("reply label").build();
        Notification.Action action = new Notification.Action.Builder(R.mipmap.ic_launcher, "reply", 
                PendingIntent.getService(MainActivity.this, 0, 
                    new Intent("start.notification.service"),0))
                    .addRemoteInput(remoteInput)
                    .build();

        Intent mIntent = new Intent();
        ComponentName name = new ComponentName(mPackageName, mMainActivity);
        mIntent.setComponent(name);
        PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, mIntent, 0);

        Notification.Builder mBuilder = new Notification.Builder(MainActivity.this, mId)
        .setSmallIcon( R.mipmap.ic_launcher)
        .setContentTitle("Receive a new message")
        .setContentText("Sorry, you phone need to top-up.")
        .addAction(action)
        .setGroup("hello")
        .setContentIntent(mPendingIntent)
        .setPriority(Notification.PRIORITY_MAX);

        mNotificationManager.notify(notificationIDReply, mBuilder.build());
    }


    public void postBigTextNotification(View view) { 
        Log.i(TAG, "showBigTextNotification");
        Intent mIntent = new Intent(); 
        ComponentName name = new ComponentName(mPackageName, mMainActivity);
        mIntent.setComponent(name);
        PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, mIntent, 0);

        Notification.BigTextStyle mBigTextStyle = new Notification.BigTextStyle()
        .setBigContentTitle("This is BigContentTitle")
        .setSummaryText("This is BigSummaryText")
        .bigText(mLongText);

        Notification.Builder mBuilder = new Notification.Builder(MainActivity.this, mId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Notification")
        .setContentText("bigTextStyle Notification")
        .setContentIntent(mPendingIntent)
        .setPriority(Notification.PRIORITY_MAX)
        .setStyle(mBigTextStyle);

        mNotificationManager.notify(103, mBuilder.build());
    }

}
