package com.example.notification_manager;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationMangerService extends NotificationListenerService {


    @Override
    public IBinder onBind(Intent intent) {

        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        Bundle extras = sbn.getNotification().extras;
        String  packageName= sbn.getPackageName();
        String title = extras.getString(Notification.EXTRA_TITLE);
        String text = extras.getString(Notification.EXTRA_TEXT);
        long timestamp = sbn.getPostTime();

        ScrollingActivity.insertNotification(packageName, title, text, timestamp);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){

    }
}
