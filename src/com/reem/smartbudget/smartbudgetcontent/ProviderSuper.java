
package com.reem.smartbudget.smartbudgetcontent;


import java.util.HashMap;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;


public abstract class ProviderSuper extends ContentProvider
{
    public String tableName;
    public Uri contentUri;
    public String querySortOrder;

    protected SQLiteDatabase db;

    private static HashMap<String, String> PROJECTION_MAP;

    protected static final String _ID = "_id";

    public static final int URI_TYPE_DIR = 1;
    public static final int URI_TYPE_ITEM = 2;

    protected UriMatcher uriMatcher;

    @Override
    public abstract boolean onCreate();

    public static Uri insert(Context context, Uri uri, ContentValues values)
    {
        return context.getContentResolver().insert(uri, values);
    }

    public static int update(Context context, Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return context.getContentResolver().update(uri, values, selection, selectionArgs);
    }

    public static Cursor query(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        return context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
    }

    public static int delete(Context context, Uri uri, String selection, String[] selectionArgs)
    {
        return context.getContentResolver().delete(uri, selection, selectionArgs);
    }

    //uri = this is the location/address of the table where i want to insert values
    //values = these are the content values that need to be added into the table
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        //insert the values into the table name specified and return the row index where the value has been inserted in table
        long rowID = db.insert(tableName, "", values);

        //if value has been successfully entered in table
        if (rowID > 0)
        {
            //notify your listeners that the table has been updated with new content values
            Uri _uri = ContentUris.withAppendedId(contentUri, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        
        throw new SQLException("Failed to add a record into " + uri);
    }

    //sort order sorts the results in that particular order
    //projection defines which field values will be returned by the query (for eg. only return me the id and amount of the item)
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        
        //use query builder to create a new read query
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        
        //tell the query builder which table to execute the query on
        qb.setTables(tableName);

        int i = uriMatcher.match(uri);
        switch (i)
        {
            case URI_TYPE_DIR:
                //if it is a dir URI then simply map the projection to the query builder
                qb.setProjectionMap(PROJECTION_MAP);
                break;
            case URI_TYPE_ITEM:
                //if it is an item URI then only execute the query on that particular record
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        
        /*//if a sort order exists
        if (sortOrder == null || sortOrder == "")
        {
            sortOrder = querySortOrder;
        }*/

        //query the databse for the records
        //the cursor saves the results of the query, the advantage of a cursor is that the query is executed only once
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    //delete the entire item/record
    //uri = the uri of the table where we need to delete values
    //selection = the field names where you want to compare your selection arguments
    //selection arguments = the values within those selection fields (the criteria) on which you want to delete the record
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        int count = 0;
        
        //compare the type of the uri's and then delete the matching criteria records from the table
        switch (uriMatcher.match(uri))
        {
            //if directory URI then search for the records in the entire table and delete matching records (it may delete multiple records in that table)
            case URI_TYPE_DIR:
                count = db.delete(tableName, selection, selectionArgs);     
                break;
                
            //item URI points to a specific record in that table    
            //if it is an item URI then only match that specific record
            case URI_TYPE_ITEM:
                String id = uri.getPathSegments().get(1);
                count = db.delete(tableName, _ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        //notify listeners that a record has been deleted
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        int count = 0;

        switch (uriMatcher.match(uri))
        {
            case URI_TYPE_DIR:
                count = db.update(tableName, values, selection, selectionArgs);
                break;
            case URI_TYPE_ITEM:
                count = db.update(tableName, values, _ID + " = " + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri)
    {
        switch (uriMatcher.match(uri))
        {
            case URI_TYPE_DIR:
                return "dir";
            case URI_TYPE_ITEM:
                return "item";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

}
