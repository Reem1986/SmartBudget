package com.reem.smartbudget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterReminder extends BaseAdapter {
	private Activity base;

	private ArrayList<String> reminderList;

	public AdapterReminder(Activity context, ArrayList<String> reminderList) {
		this.base = context;
		this.reminderList = reminderList;
	}

	public static class ViewHolder {
		int index;
		TextView title;
		TextView text;
		TextView time;
	}

	//the position is the position of the item in the list view
	//convertview is the view of the item
	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		final ViewHolder viewHolder;

		if (convertView == null) {

			//inflating the item_reminder view
			//and initializing the convert view into the reminderview
			LayoutInflater vi = (LayoutInflater) base
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.item_reminder, parent, false);

			viewHolder = new ViewHolder();
			convertView.setTag(viewHolder);

			viewHolder.title = (TextView) convertView
					.findViewById(R.id.textViewReminderTitle);
			viewHolder.text = (TextView) convertView
					.findViewById(R.id.textViewReminderText);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.textViewReminderTime);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		//i get the value that I need to show in the list view at that position
		String s = reminderList.get(position);


		//split the values
		String[] tokens = s.split(",");

		viewHolder.index = position;
		viewHolder.title.setText(tokens[1]);
		viewHolder.text.setText(tokens[2]);
		viewHolder.time.setText(tokens[3]);

		return convertView;
	}

	@Override
	public int getCount() {
		return this.reminderList.size();

	}

	@Override
	public Object getItem(int position) {
		return this.reminderList.get(position);
	}

	//it takes a position of the list view,
	//and tells you the corresponding position of the item in the arraylist
	@Override
	public long getItemId(int position) {
		return position;
	}

}
