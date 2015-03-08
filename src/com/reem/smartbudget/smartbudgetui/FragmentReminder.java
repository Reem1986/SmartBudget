package com.reem.smartbudget.smartbudgetui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.reem.smartbudget.AdapterReminder;
import com.reem.smartbudget.BudgetPreferences;
import com.reem.smartbudget.R;

public class FragmentReminder extends Fragment {

	ArrayList<String> alarmsList;

	@Override
	public void onResume() {
		super.onResume();

		// get all the alarms from the shared preferences
		alarmsList = BudgetPreferences.getAlarms(getActivity());

		// create the adapter
		adapterReminder = new AdapterReminder(getActivity(), alarmsList);

		// set the listview according to the adapter
		listViewReminder.setAdapter(adapterReminder);

		listViewReminder
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int position, long arg3) {

						String alarm = alarmsList.get(position);
						String key = alarm.split(",")[0];

						BudgetPreferences.deleteString(getActivity(),
								BudgetPreferences.KEY_REMINDER + "," + key);

						alarmsList = BudgetPreferences.getAlarms(getActivity());
						adapterReminder = new AdapterReminder(getActivity(),
								alarmsList);
						listViewReminder.setAdapter(adapterReminder);
						adapterReminder.notifyDataSetChanged();

						return false;
					}
				});
	}

	FragmentReminder base;
	Button buttonReminderAdd;
	ListView listViewReminder;
	AdapterReminder adapterReminder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_reminder, container,
				false);
		base = this;

		buttonReminderAdd = (Button) rootView
				.findViewById(R.id.buttonReminderAdd);
		listViewReminder = (ListView) rootView
				.findViewById(R.id.listViewReminder);

		buttonReminderAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(base.getActivity()
						.getApplicationContext(), ReminderAddActivity.class);
				startActivity(intent);
			}
		});

		return rootView;
	}

}
