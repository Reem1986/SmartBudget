
package com.reem.smartbudget;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;


public class ManagerPreferences
{
    public static final String NO_VALUE = "_NO_VALUE_";

    public static boolean loadBoolean(Context context, String key, boolean defaultReturnValue)
    {
        return getSharedPreferences(context).getBoolean(key, defaultReturnValue);
    }

    public static void saveBoolean(Context context, String key, boolean value)
    {
        apply(getSharedPreferences(context).edit().putBoolean(key, value));
    }

    public static int loadInteger(Context context, String key, int defaultValue)
    {
        return getSharedPreferences(context).getInt(key, defaultValue);
    }

    public static void saveInteger(Context context, String key, int value)
    {
        apply(getSharedPreferences(context).edit().putInt(key, value));
    }

    public static String loadString(Context context, String key)
    {
        return getSharedPreferences(context).getString(key, NO_VALUE);
    }

    public static void saveString(Context context, String key, String value)
    {
        apply(getSharedPreferences(context).edit().putString(key, value));
    }

    private static SharedPreferences getSharedPreferences(Context context)
    {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private static void apply(Editor editor)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
        {
            editor.apply();
        }
        else
        {
            editor.commit();
        }

    }

}
