package com.reem.smartbudget.smartbudgetui;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.reem.smartbudget.AlarmReceiver;
import com.reem.smartbudget.BudgetPreferences;
import com.reem.smartbudget.R;

public class ReminderAddActivity extends FragmentActivity {
	TimePicker timePickerReminder;
	DatePicker datePickerReminder;
	CheckBox checkBoxReminder;
	RadioGroup reminderRadioGroup;

	long repeatMillis = 1000 * 60 * 60 * 24;
	String repeatText = "Daily";

	boolean isAdd = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.reminder_add);

		timePickerReminder = (TimePicker) findViewById(R.id.timePickerReminder);
		datePickerReminder = (DatePicker) findViewById(R.id.datePickerReminder);
		checkBoxReminder = (CheckBox) findViewById(R.id.checkBoxRepeat);
		reminderRadioGroup = (RadioGroup) findViewById(R.id.reminderRadioGroup);

		// by default 
		reminderRadioGroup.check(R.id.radioDaily);

		
		
		if (checkBoxReminder.isChecked()) {
			datePickerReminder.setVisibility(View.GONE);
			reminderRadioGroup.setVisibility(View.VISIBLE);

		} else {
			datePickerReminder.setVisibility(View.VISIBLE);
			reminderRadioGroup.setVisibility(View.GONE);
		}

		
		checkBoxReminder
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							datePickerReminder.setVisibility(View.GONE);
							reminderRadioGroup.setVisibility(View.VISIBLE);
						} else {
							datePickerReminder.setVisibility(View.VISIBLE);
							reminderRadioGroup.setVisibility(View.GONE);
						}
					}
				});

		
		reminderRadioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.radioDaily) {
							// 24 hours in milliseconds
							repeatMillis = 1000 * 60 * 60 * 24;
							repeatText = "Daily";
						} else if (checkedId == R.id.radioWeekly) {
							// 7 days into milliseconds
							repeatMillis = 1000 * 60 * 60 * 24 * 7;
							repeatText = "Weekly";
						} else if (checkedId == R.id.radioMonthly) {
							repeatMillis = 1000 * 60 * 60 * 24 * 30;
							repeatText = "Monthly";
						} else if (checkedId == R.id.radioYearly) {
							repeatMillis = 1000 * 60 * 60 * 24 * 365;
							repeatText = "Yearly";
						}
					}
				});

		getActionBar().setTitle("Add Reminder");

		final Button buttonSave = (Button) findViewById(R.id.buttonSaveReminder);
		final EditText editTextName = (EditText) findViewById(R.id.editTextReminderName);
		final EditText editTextDescription = (EditText) findViewById(R.id.editTextReminderDescription);

		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				long id = System.currentTimeMillis();

				
				String title = editTextName.getText().toString();
				String text = editTextDescription.getText().toString();

				
				Intent intent = new Intent(getApplicationContext(),
						AlarmReceiver.class);
				intent.putExtra("title", title);
				intent.putExtra("text", text);
				intent.putExtra("id", (int) id);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(
						getApplicationContext(), (int) id, intent,
						PendingIntent.FLAG_CANCEL_CURRENT);

				// get the alarm manager from the OS
				AlarmManager alarmManager = (AlarmManager) getApplicationContext()
						.getSystemService(Context.ALARM_SERVICE);

				// repeat
				if (checkBoxReminder.isChecked()) {
					// get the hour and minute from the time picker
					int hour = timePickerReminder.getCurrentHour();
					int minute = timePickerReminder.getCurrentMinute();

					// set the calendar object with the selected time
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY, hour);
					calendar.set(Calendar.MINUTE, minute);

					String date = calendar.get(Calendar.DATE) + "/"
							+ (1 + calendar.get(Calendar.MONTH)) + "/"
							+ calendar.get(Calendar.YEAR);

					// create a repeating alarm
					alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
							calendar.getTimeInMillis(), repeatMillis,
							pendingIntent);

					String time = hour + ":" + minute + " - " + date + " - "
							+ repeatText;

					String alarmString = id + "," + title + "," + text + ","
							+ time;

					BudgetPreferences.saveString(getApplicationContext(),
							BudgetPreferences.KEY_REMINDER + "," + id,
							alarmString);
				}
				// single
				else {
					// get the hour..
					int hour = timePickerReminder.getCurrentHour(); // 24-hour
																	// clock
					int minute = timePickerReminder.getCurrentMinute();

					// get the day..
					int day = datePickerReminder.getDayOfMonth();
					int month = datePickerReminder.getMonth();
					int year = datePickerReminder.getYear();

				
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month, day, hour, minute, 0);

					
					long alarmTime = calendar.getTimeInMillis();

				
					
					alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime,
							pendingIntent);

					

					String time = hour + ":" + minute + " - " + day + "/"
							+ (month + 1) + "/" + year;
					String alarmString = id + "," + title + "," + text + ","
							+ time;

					BudgetPreferences.saveString(getApplicationContext(),
							BudgetPreferences.KEY_REMINDER + "," + id,
							alarmString);
				}

				finish();

			}
		});

	}
}
