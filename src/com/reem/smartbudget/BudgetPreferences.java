package com.reem.smartbudget;

import java.util.ArrayList;
import java.util.Map;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

public class BudgetPreferences {
	public static final String NO_VALUE = "_NO_VALUE_";
	public static final String KEY_REMINDER = "_REMINDER_";

	public static ArrayList<String> getAlarms(Context context) {
		ArrayList<String> alarmList = new ArrayList<String>();

		Map<String, ?> keys = getSharedPreferences(context).getAll(); // returns
																		// me
																		// all
																		// values

		for (Map.Entry<String, ?> entry : keys.entrySet()) {

			if (entry.getKey().contains(KEY_REMINDER)) {
				alarmList.add(entry.getValue().toString());
			}
		}

		return alarmList;
	}

	public static boolean loadBoolean(Context context, String key,
			boolean defaultReturnValue) {
		return getSharedPreferences(context)
				.getBoolean(key, defaultReturnValue);
	}

	public static void saveBoolean(Context context, String key, boolean value) {
		apply(getSharedPreferences(context).edit().putBoolean(key, value));
	}

	public static int loadInteger(Context context, String key, int defaultValue) {
		return getSharedPreferences(context).getInt(key, defaultValue);
	}

	public static void saveInteger(Context context, String key, int value) {
		apply(getSharedPreferences(context).edit().putInt(key, value));
	}

	public static String loadString(Context context, String key) {
		return getSharedPreferences(context).getString(key, NO_VALUE);
	}

	public static void saveString(Context context, String key, String value) {
		apply(getSharedPreferences(context).edit().putString(key, value));
	}

	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(context.getPackageName(),
				Context.MODE_PRIVATE);
	}

	public static void deleteString(Context context, String key) {
		apply(getSharedPreferences(context).edit().remove(key));

	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private static void apply(Editor editor) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			editor.apply();
		} else {
			editor.commit();
		}

	}

	public static void savePinToFile(Context context, String pinValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);

		sharedPreferences.edit().putString("pin", pinValue).commit();
	}
	
	public static void saveCurrencyToFile(Context context, String currency) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);

		sharedPreferences.edit().putString("Currency", currency).commit();
	}

	public static String loadPinFromFile(Context context) {
		String pinValue = "";

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);

		pinValue = sharedPreferences.getString("pin", "");

		return pinValue;

	}
	public static String loadCurrencyFromFile(Context context) {
		String currency = "";

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);

		currency = sharedPreferences.getString("Currency", "");

		return currency;

	}
}
