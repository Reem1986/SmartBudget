package com.reem.smartbudget;

import android.content.Context;
import android.content.SharedPreferences;

public class BudgetPreferences {
	public static void savePinToFile(Context context, String pinValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);

		sharedPreferences.edit().putString("pin", pinValue).commit();
	}

	public static String loadPinFromFile(Context context) {
		String pinValue = "";

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);

		pinValue = sharedPreferences.getString("pin", "");

		return pinValue;

	}
}
