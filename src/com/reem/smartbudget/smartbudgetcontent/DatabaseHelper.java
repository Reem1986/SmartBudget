
package com.reem.smartbudget.smartbudgetcontent;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper
{
    private final static String DATABASE_NAME = "budgetapp";
    private final static int DATABASE_VERSION = 1;

    private String createQuery;
    private String tableName;

    DatabaseHelper(Context context, String createQuery, String tableName)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.createQuery = createQuery;
        this.tableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }
}
