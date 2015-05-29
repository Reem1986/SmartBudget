
package com.reem.smartbudget.smartbudgetcontent;


import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;


public class ProviderBudget extends ProviderSuper
{
   
    static private ProviderBudget base;

    public final static String TABLE_NAME = "budget";
    public final static String AUTHORITY_NAME = "com.reem.smartbudget.smartbudgetcontent.ProviderBudget";
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY_NAME + "/" + TABLE_NAME);

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_CREDIT_CARD = "credit_card";
    public static final String KEY_CYCLE = "cycle";
    public static final String KEY_ICON = "icon";
    public static final String KEY_DATE = "date";
    public static final String KEY_NOTE = "note";

    private final static String SELECTION = KEY_ID + " = ? ";

    public static String getRemaining(Context context)
    {

        Cursor cursor = base.db.rawQuery("SELECT SUM(" + KEY_AMOUNT + ") FROM " + TABLE_NAME, null);

        int remaining = 0;

        if (cursor != null)
        {
            try
            {
                if (cursor.moveToFirst())
                {
                    remaining = cursor.getInt(0);
                }
            }
            finally
            {
                cursor.close();
            }
        }

        return remaining + "";
    }

    private static String getValue(Cursor cursor)
    {
    	int sum = 0;

        
        if (cursor != null)
        {
            try
            {
                
                if (cursor.moveToFirst())
                {
                    
                    sum = cursor.getInt(0);
                }
            }
            finally
            {
                
                cursor.close();
            }
        }

        return sum + "";
    }

    public static String getTotalIncome(Context context)
    {
        Cursor cursor = base.db.rawQuery("SELECT SUM(" + KEY_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + KEY_AMOUNT + " >= 0", null);

        return getValue(cursor);
    }

    public static String getTotalExpense(Context context)
    {
        
        Cursor cursor = base.db.rawQuery("SELECT SUM(" + KEY_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + KEY_AMOUNT + " < 0", null);

        return getValue(cursor);
    }

    public static int addTransaction(Context context, ClassTransaction transaction)
    {
        if (transaction.id.equals(""))
            return -1;

        final String[] selectionArgs = new String[] { transaction.id };

        ContentValues updateValues = new ContentValues(0);

        if (transaction.id != null)
            updateValues.put(KEY_ID, transaction.id);

        if (transaction.note != null)
            updateValues.put(KEY_NAME, transaction.name);

        updateValues.put(KEY_AMOUNT, transaction.amount);
        updateValues.put(KEY_CREDIT_CARD, transaction.creditCard);

        if (transaction.cycle != null)
            updateValues.put(KEY_CYCLE, transaction.cycle);

        if (transaction.icon > 0)
            updateValues.put(KEY_ICON, transaction.icon);

        if (transaction.date != null)
            updateValues.put(KEY_DATE, transaction.date);

        if (transaction.note != null)
            updateValues.put(KEY_NOTE, transaction.note);

        
        if (updateValues.size() <= 0)
            return 0;

        
        int updateRowCount = ProviderSuper.update(context, CONTENT_URI, updateValues, SELECTION, selectionArgs);

        
        if (updateRowCount <= 0)
        {
           
            ContentValues addValues = getContentValues(transaction);

            ProviderSuper.insert(context, CONTENT_URI, addValues);
        }

        return updateRowCount;
    }

    
    public static int deleteTransaction(Context context, ClassTransaction transaction)
    {
        String selection = KEY_ID + " = ?";
        String[] selectionArgs = new String[] { transaction.id };

        return ProviderSuper.delete(context, CONTENT_URI, selection, selectionArgs);

    }

    @Override
    public boolean onCreate()
    {
      
        base = this;

        tableName = TABLE_NAME;
        contentUri = CONTENT_URI;
        querySortOrder = null;

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY_NAME, tableName, URI_TYPE_DIR);
        uriMatcher.addURI(AUTHORITY_NAME, tableName + "/#", URI_TYPE_ITEM);

        String createQuery = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ID + " TEXT," + KEY_NAME + " TEXT," + KEY_AMOUNT + " INTEGER," + KEY_CREDIT_CARD + " BOOLEAN," + KEY_CYCLE + " TEXT," + KEY_ICON + " INTEGER, " + KEY_DATE + " TEXT, " + KEY_NOTE + " TEXT ) ";
        DatabaseHelper dbHelper = new DatabaseHelper(getContext(), createQuery, TABLE_NAME);

        db = dbHelper.getWritableDatabase();
        db.execSQL(createQuery);
        return (db == null) ? false : true;
    }


    public static String[] getColumns()
    {
        return new String[] { _ID, KEY_ID, KEY_NAME, KEY_AMOUNT, KEY_CREDIT_CARD, KEY_CYCLE, KEY_ICON, KEY_DATE, KEY_NOTE };
    }

    
    public static ContentValues getContentValues(ClassTransaction transaction)
    {
        ContentValues values = new ContentValues();

        values.put(KEY_ID, transaction.id);
        values.put(KEY_NAME, transaction.name);
        values.put(KEY_AMOUNT, transaction.amount);
        values.put(KEY_CREDIT_CARD, transaction.creditCard);
        values.put(KEY_CYCLE, transaction.cycle);
        values.put(KEY_ICON, transaction.icon);
        values.put(KEY_DATE, transaction.date);
        values.put(KEY_NOTE, transaction.note);

        return values;
    }

}
