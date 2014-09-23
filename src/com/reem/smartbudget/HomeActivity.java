package com.reem.smartbudget;

import com.reem.smartbudget.R;
import com.reem.smartbudget.TabsPagerAdapter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class HomeActivity extends FragmentActivity implements
		ActionBar.TabListener {
	private ActionBar actionBar;

	private TabsPagerAdapter tabsPagerAdapter;
	private ViewPager viewPager;
	public static String[] tabs = { "Budget", "Reminder", "Reports",
			"Transfer", "Shopping List", "Settings" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);

		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(tabsPagerAdapter);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				actionBar.setSelectedNavigationItem(index);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		for (String tab_name : tabs) {
			Tab newTab = actionBar.newTab();
			newTab.setText(tab_name);
			newTab.setTabListener(this);

			actionBar.addTab(newTab);
		}

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}
}
