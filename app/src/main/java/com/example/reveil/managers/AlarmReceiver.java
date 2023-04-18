package com.example.reveil.managers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.example.reveil.R;

public class AlarmReceiver extends BroadcastReceiver {
    public static Ringtone ringtone;
    private static NotificationManager notificationManager;
    private static int notificationId = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Créer une NotificationCompat.Builder et configurez les paramètres de notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Alarme en cours")
                .setContentText("Votre alarme est en cours d'execution. Appuyez sur le bouton ci-dessous pour l'arrêter")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Créer un intent pour arrêter le réveil
        Intent stopIntent = new Intent(context, StopAlarmReceiver.class);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(context, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(stopPendingIntent);
        builder.addAction(R.drawable.ic_delete, "Arrêter", stopPendingIntent);

        // Lancer une notification sonore
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        // Afficher la notification
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());
    }

    public static void stopAlarm() {
        ringtone.stop();
        notificationManager.cancel(notificationId);
    }
}
