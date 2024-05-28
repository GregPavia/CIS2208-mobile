package com.example.gymapp.receivers;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Calendar;

public class NotificationScheduler {

    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_PREFS = "notification_prefs";
    private static final String NOTIFICATION_SCHEDULED = "notification_scheduled";
    private static final String CHANNEL_ID = "channel_id";

    public static void scheduleNotification(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(NOTIFICATION_PREFS, Context.MODE_PRIVATE);

        if (!prefs.getBoolean(NOTIFICATION_SCHEDULED, false)) {
            createNotificationChannel(context); // Create notification channel if necessary

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, NotificationReceiver.class);
            intent.putExtra("notificationText", "Don't forget to exercise today."); // Notification text to display

            int flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, flags);

            // Set the alarm to trigger at 2:10 PM daily
            long triggerTime = getTwoTenPMMillis();
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, AlarmManager.INTERVAL_DAY, pendingIntent);

            // Mark notification as scheduled
            prefs.edit().putBoolean(NOTIFICATION_SCHEDULED, true).apply();
        }
    }

    private static long getTwoTenPMMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12); // 2 PM
        calendar.set(Calendar.MINUTE, 0);     // 10 minutes
        calendar.set(Calendar.SECOND, 0);      // 0 seconds

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1); // Set for tomorrow
        }

        return calendar.getTimeInMillis();
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            // Configure channel settings (e.g., description, importance, etc.)
            // channel.setDescription("Description of your notification channel");
            // channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
