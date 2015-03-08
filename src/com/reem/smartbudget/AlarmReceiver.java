
package com.reem.smartbudget;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        int id = intent.getIntExtra("id", (int) System.currentTimeMillis());

        ManagerNotifications.showNotification(context, title, text, id);
    }

}
