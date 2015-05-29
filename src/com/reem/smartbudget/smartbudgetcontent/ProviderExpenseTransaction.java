package com.reem.smartbudget.smartbudgetcontent;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class ProviderExpenseTransaction extends ProviderSuper {
	
	static private ProviderExpenseTransaction base;

	public final static String TABLE_NAME = "expense_transaction";
	public final static String AUTHORITY_NAME = "com.reem.smartbudget.smartbudgetcontent.ProviderExpenseTransaction";
	public final static Uri CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY_NAME + "/" + TABLE_NAME);

	public static final String KEY_ID = "id";
	public static final String KEY_ID_PARENT = "id_parent";
	public static final String KEY_NAME = "name";
	public static final String KEY_AMOUNT = "amount";
	public static final String KEY_FROM_INCOME = "from_income";
	public static final String KEY_DATE = "date";
	public static final String KEY_NOTE = "note";

	private final static String SELECTION = KEY_ID + " = ? ";

	
	public static int addTransaction(Context context,
			ClassExpenseTransaction transaction) {

		if (transaction.id.equals(""))
			return -1;

		final String[] selectionArgs = new String[] { transaction.id };

		
		ContentValues updateValues = new ContentValues(0);

		
		if (transaction.id != null)
			updateValues.put(KEY_ID, transaction.id);

		if (transaction.id_parent != null)
			updateValues.put(KEY_ID_PARENT, transaction.id_parent);

		if (transaction.note != null)
			updateValues.put(KEY_NAME, transaction.name);

		updateValues.put(KEY_AMOUNT, transaction.amount);

		if (transaction.from_income != null)
			updateValues.put(KEY_FROM_INCOME, transaction.from_income);
		//
		if (transaction.date != null)
			updateValues.put(KEY_DATE, transaction.date);

		if (transaction.note != null)
			updateValues.put(KEY_NOTE, transaction.note);

		
		if (updateValues.size() <= 0)
			return 0;

		
		int updateRowCount = ProviderSuper.update(context, CONTENT_URI,
				updateValues, SELECTION, selectionArgs);

		
		if (updateRowCount <= 0) {
			
			ContentValues addValues = getContentValues(transaction);

			ProviderSuper.insert(context, CONTENT_URI, addValues);
		}

		return updateRowCount;
	}

	
	public static int deleteTransaction(Context context,
			ClassExpenseTransaction transaction) {
		String selection = KEY_ID + " = ?";
		String[] selectionArgs = new String[] { transaction.id };

		return ProviderSuper.delete(context, CONTENT_URI, selection,
				selectionArgs);

	}

	@Override
	public boolean onCreate() {
		
		base = this;

		tableName = TABLE_NAME;
		contentUri = CONTENT_URI;
		querySortOrder = null;

		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY_NAME, tableName, URI_TYPE_DIR);
		uriMatcher.addURI(AUTHORITY_NAME, tableName + "/#", URI_TYPE_ITEM);

		String createQuery = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME
				+ " ( " + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ID
				+ " TEXT," + KEY_ID_PARENT + " TEXT," + KEY_NAME + " TEXT,"
				+ KEY_AMOUNT + " INTEGER," + KEY_FROM_INCOME + " TEXT,"
				+ KEY_DATE + " TEXT, " + KEY_NOTE + " TEXT ) ";
		DatabaseHelper dbHelper = new DatabaseHelper(getContext(), createQuery,
				TABLE_NAME);

		db = dbHelper.getWritableDatabase();
		db.execSQL(createQuery);
		return (db == null) ? false : true;
	}

	public static String[] getColumns() {
		return new String[] { _ID, KEY_ID, KEY_ID_PARENT, KEY_NAME, KEY_AMOUNT,
				KEY_FROM_INCOME, KEY_DATE, KEY_NOTE };
	}

	
	public static ContentValues getContentValues(
			ClassExpenseTransaction transaction) {
		ContentValues values = new ContentValues();

		values.put(KEY_ID, transaction.id);
		values.put(KEY_ID_PARENT, transaction.id_parent);
		values.put(KEY_NAME, transaction.name);
		values.put(KEY_AMOUNT, transaction.amount);
		values.put(KEY_FROM_INCOME, transaction.from_income);
		values.put(KEY_DATE, transaction.date);
		values.put(KEY_NOTE, transaction.note);

		return values;
	}

}
