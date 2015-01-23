
package com.reem.smartbudget.smartbudgetcontent;


import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;


public class ProviderBudget extends ProviderSuper
{
    //base object to access provider budget object
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
        //implementing SINGLETON PATTERN by using a base object in order to access private variables from a static function
        
        //equivalent sql query: "select sum(amount) - sum(expenses) from budget" 
        Cursor cursor = base.db.rawQuery("SELECT SUM(" + KEY_AMOUNT + ") FROM " + TABLE_NAME, null);

        int remaining = 0;
        
        //check if there was any error or not
        if (cursor != null)
        {
            try
            {
                //move the cursor to the first column index
                if (cursor.moveToFirst())
                {
                    //the first index will have the query result
                    remaining = cursor.getInt(0);
                }
            }
            finally
            {
                //close the cursor so that the db connection gets closed
                cursor.close();
            }
        }

        return remaining + "";
    }
    
    public static String getTotal(Context context)
    {
        //implementing SINGLETON PATTERN by using a base object in order to access private variables from a static function
        
        //equivalent sql query: "select sum(amount) from budget" 
        Cursor cursor = base.db.rawQuery("SELECT SUM(" + KEY_AMOUNT + ") FROM " + TABLE_NAME, null);

        int sum = 0;
        
        //check if there was any error or not
        if (cursor != null)
        {
            try
            {
                //then move the cursor to the first column index
                if (cursor.moveToFirst())
                {
                    //the first index will have the query result
                    sum = cursor.getInt(0);
                }
            }
            finally
            {
                //close the cursor so that the db connection gets closed
                cursor.close();
            }
        }

        return sum + "";
    }

    //to insert or update a value we call this function
    //if the record already exists, then it is updated
    //if the record does not exist, then a new record is inserted
    //the return value tells us if a record was inserted, or how many records were updated
    public static int addTransaction(Context context, ClassTransaction transaction)
    {
        if (transaction.id.equals(""))
            return -1;

        final String[] selectionArgs = new String[] { transaction.id };

        //contentvalues contains the value which we will insert/update in our table
        ContentValues updateValues = new ContentValues(0);

        //now we will construct our contentvalues with the data that we want to insert/update
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

        //we have added all data into the contentvalues

        //if there was no data to write to the table, then simply exit with the value 0
        if (updateValues.size() <= 0)
            return 0;

        //call the update function onto the table
        //and save the number of records that were updated
        int updateRowCount = ProviderSuper.update(context, CONTENT_URI, updateValues, SELECTION, selectionArgs);

        //if there were 0 records updated, then this means that the record does not exist
        //so now we should insert this record into the table
        if (updateRowCount <= 0)
        {
            //since there is no record for this transaction, add a new record
            ContentValues addValues = getContentValues(transaction);

            ProviderSuper.insert(context, CONTENT_URI, addValues);
        }

        return updateRowCount;
    }

    //this function deletes records from our table
    public static int deleteTransaction(Context context, ClassTransaction transaction)
    {
        String selection = KEY_ID + " = ?";
        String[] selectionArgs = new String[] { transaction.id };

        return ProviderSuper.delete(context, CONTENT_URI, selection, selectionArgs);

    }

    @Override
    public boolean onCreate()
    {
        //the initialized object gets saved in my base object
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

    //this is a helper function
    //this function simply converts all my transaction data into contentvalue data inorder to insert new values for the first time
    //if any data is null or not initialized, it will insert blank data
    //we need to use this function to add a new record for the first time even if we don't have all the data for the record
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
