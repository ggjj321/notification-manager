package com.example.notification_manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotificationMangerService extends NotificationListenerService {
    @Override
    public IBinder onBind(Intent intent) {

        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        Bundle extras = sbn.getNotification().extras;
        String packageName = sbn.getPackageName();
        String title = extras.getString(Notification.EXTRA_TITLE);
        String text = extras.getString(Notification.EXTRA_TEXT);
        Drawable smallIcon = null;
        try {
            int iconId = extras.getInt(Notification.EXTRA_SMALL_ICON);
            PackageManager manager = getPackageManager();
            Resources resources = manager.getResourcesForApplication(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        final Bitmap largeIcon = sbn.getNotification().largeIcon;

        ScrollingActivity.show(packageName, title);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){

    }
}
