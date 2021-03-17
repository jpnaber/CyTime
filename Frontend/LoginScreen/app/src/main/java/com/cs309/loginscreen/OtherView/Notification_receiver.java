package com.cs309.loginscreen.OtherView;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cs309.loginscreen.R;

/**
 * @author Josh
 */
public class Notification_receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyCyTime");
        builder.setContentTitle("CyTime");
        builder.setContentText("Is your task done?");
        builder.setSmallIcon(R.drawable.angrybirdsmall);
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }

    public void onReceive(Context context, Intent intent, String title, String description) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyCyTime");
        builder.setContentTitle(title);
        builder.setContentText(description);
        builder.setSmallIcon(R.drawable.angrybirdsmall);
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}