package com.reem.smartbudget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		Log.v("index", index + "");

		if (index == 0) {
			return new FragmentBudget();
		} else if (index == 1) {
			return new FragmentReminder();
		} else if (index == 2) {
			return new FragmentReports();
		} else if (index == 3) {
			return new FragmentTransfer();
		} else if (index == 4) {
			return new FragmentShoppinglist();
		} else {
			return new FragmentSettings();
		}
	}

	@Override
	public int getCount() {
		return HomeActivity.tabs.length;
	}

}