
package com.reem.smartbudget;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import com.reem.smartbudget.smartbudgetui.LoginActivity;


public class ManagerNotifications
{
    public static void showNotification(Context context, String title, String body, long notificationId)
    {
        if (title == null)
            title = "";

        if (body == null)
            body = "";

       
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String ticker = body;

    
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

     
        Intent i = new Intent(context, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, i, 0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context.getApplicationContext()).setContentTitle(title).setContentText(body).setTicker(ticker).setLargeIcon(largeIcon).setSmallIcon(R.drawable.ic_launcher).setDefaults(Notification.DEFAULT_ALL).setContentIntent(contentIntent).setAutoCancel(true).setStyle(new NotificationCompat.BigTextStyle().bigText(body));

        notificationManager.notify((int) (notificationId), notification.build());
    }
}
