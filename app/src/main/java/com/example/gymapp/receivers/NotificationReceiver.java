package com.example.gymapp.receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.gymapp.NotificationActivity;
import com.example.gymapp.R;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    private void showNotification(Context context) {
        // Create a notification channel (required for API 26+)
        createNotificationChannel(context);

        // Create the notification
        Notification.Builder builder = new Notification.Builder(context, "default")
                .setContentTitle("Time to Workout!")
                .setContentText("Don't forget to exercise today.")
                .setSmallIcon(R.drawable.notification_bell_icon);

        // Open NotificationActivity when notification is clicked
        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Display the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Workout Reminder", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

