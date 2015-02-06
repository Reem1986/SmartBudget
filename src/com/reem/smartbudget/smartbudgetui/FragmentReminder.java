
package com.reem.smartbudget.smartbudgetui;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.reem.smartbudget.AlarmReceiver;
import com.reem.smartbudget.R;


public class FragmentReminder extends Fragment
{
    FragmentReminder base;
    Button buttonReminderExact;
    Button buttonReminderRepeating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_reminder, container,
                false);
        base = this;

        buttonReminderExact = (Button) rootView.findViewById(R.id.buttonReminderExact);
        buttonReminderRepeating = (Button) rootView.findViewById(R.id.buttonReminderRepeating);

        buttonReminderExact.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(base.getActivity().getApplicationContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(base.getActivity().getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager = (AlarmManager) base.getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 2000, pendingIntent);
            }
        });

        buttonReminderRepeating.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(base.getActivity().getApplicationContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(base.getActivity().getApplicationContext(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager = (AlarmManager) base.getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 2000, 5000, pendingIntent);

                alarmManager.cancel(pendingIntent);
            }
        });



        return rootView;
    }

}
