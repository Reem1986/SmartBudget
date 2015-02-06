package com.reem.smartbudget.smartbudgetui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.reem.smartbudget.R;
import com.reem.smartbudget.smartbudgetcontent.ProviderBudget;

public class FragmentBudget extends Fragment {

	SimpleCursorAdapter simpleCursorAdapterIncome;
	SimpleCursorAdapter simpleCursorAdapterExpense;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_budget, container,
				false);

		Button buttonIncomeAdd = (Button) rootView
				.findViewById(R.id.buttonIncomeAdd);
		Button buttonExpenseAdd = (Button) rootView
				.findViewById(R.id.buttonExpenseAdd);

		final TextView textViewTotal = (TextView) rootView
				.findViewById(R.id.textViewIncomeTotal);
		final TextView textViewRemaining = (TextView) rootView
				.findViewById(R.id.textViewIncomeRemaining);

		String[] from = new String[] { ProviderBudget.KEY_ID,
				ProviderBudget.KEY_NAME, ProviderBudget.KEY_AMOUNT,
				ProviderBudget.KEY_NOTE };

		int[] to = new int[] { R.id.textViewId, R.id.textViewName,
				R.id.textViewAmount, R.id.textViewNote };

		simpleCursorAdapterIncome = new SimpleCursorAdapter(inflater.getContext(),
				R.layout.item_income, null, from, to, 0);

		simpleCursorAdapterExpense = new SimpleCursorAdapter(inflater.getContext(),
				R.layout.item_expense, null, from, to, 0);



		ListView listViewIncome = (ListView) rootView
				.findViewById(R.id.listViewIncome);
		listViewIncome.setAdapter(simpleCursorAdapterIncome);

		ListView listViewExpense = (ListView) rootView
				.findViewById(R.id.listViewExpense);
		listViewExpense.setAdapter(simpleCursorAdapterExpense);

		// long click listener on list view
		listViewIncome
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int index, long arg3) {

						Cursor cursor = (Cursor) simpleCursorAdapterIncome
								.getItem(index);

						// get the values of the item where i long clicked
						// extract ALL values from the cursor which you need to
						// edit or show
						String transactionId = cursor.getString(cursor
								.getColumnIndex(ProviderBudget.KEY_ID));
						String transactionName = cursor.getString(cursor
								.getColumnIndex(ProviderBudget.KEY_NAME));
						String transactionAmount = cursor.getString(cursor
								.getColumnIndex(ProviderBudget.KEY_AMOUNT));
						String note = cursor.getString(cursor
								.getColumnIndex(ProviderBudget.KEY_NOTE));

						Toast.makeText(
								getActivity(),
								"item " + transactionId + ", "
										+ transactionName + ", "
										+ transactionAmount, Toast.LENGTH_LONG)
								.show();

						// start the add activity, but give it the values so
						// that it can edit the existing data
						Intent intent = new Intent(getActivity(),
								FragmentBudgetAdd.class);

						// add all the remaining values that you have extraceted
						// from the cursor
						intent.putExtra(ProviderBudget.KEY_ID, transactionId);
						intent.putExtra(ProviderBudget.KEY_NAME,
								transactionName);
						intent.putExtra(ProviderBudget.KEY_AMOUNT,
								transactionAmount);
						intent.putExtra(ProviderBudget.KEY_NOTE, note);

						intent.putExtra("TYPE", "INCOME");

						startActivity(intent);

						return true;
					}
				});

		listViewExpense
		.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0,
					View arg1, int index, long arg3) {

				Cursor cursor = (Cursor) simpleCursorAdapterExpense
						.getItem(index);

				// get the values of the item where i long clicked
				// extract ALL values from the cursor which you need to
				// edit or show
				String transactionId = cursor.getString(cursor
						.getColumnIndex(ProviderBudget.KEY_ID));
				String transactionName = cursor.getString(cursor
						.getColumnIndex(ProviderBudget.KEY_NAME));
				String transactionAmount = cursor.getString(cursor
						.getColumnIndex(ProviderBudget.KEY_AMOUNT));
				String note = cursor.getString(cursor
						.getColumnIndex(ProviderBudget.KEY_NOTE));

				Toast.makeText(
						getActivity(),
						"item " + transactionId + ", "
								+ transactionName + ", "
								+ transactionAmount, Toast.LENGTH_LONG)
						.show();

				// start the add activity, but give it the values so
				// that it can edit the existing data
				Intent intent = new Intent(getActivity(),
						FragmentBudgetAdd.class);

				// add all the remaining values that you have extraceted
				// from the cursor
				intent.putExtra(ProviderBudget.KEY_ID, transactionId);
				intent.putExtra(ProviderBudget.KEY_NAME,
						transactionName);
				intent.putExtra(ProviderBudget.KEY_AMOUNT,
						transactionAmount);
				intent.putExtra(ProviderBudget.KEY_NOTE, note);

				intent.putExtra("TYPE", "EXPENSE");

				startActivity(intent);

				return true;
			}
		});

		buttonIncomeAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(inflater.getContext(),
						FragmentBudgetAdd.class);
				i.putExtra("TYPE", "INCOME");
				startActivity(i);
			}
		});

		buttonExpenseAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(inflater.getContext(),
						FragmentBudgetAdd.class);
				i.putExtra("TYPE", "EXPENSE");
				startActivity(i);

			}
		});

		// loader manager manages data loading from the table
		// it provides us with some callback functions by using LoaderCallbacks
		// so that we can update our views with the latest data

		getLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>() {

			// on create loader gets called whenever the fragment is loaded for
			// the first time
			@Override
			public android.support.v4.content.Loader<Cursor> onCreateLoader(
					int arg0, Bundle arg1) {
				// projection are the database fields that will be returned
				String[] projection = ProviderBudget.getColumns();

				// selection are the database fields on which the query will be
				// run
				String selection = "amount >= ?";

				// selectionArgs are the criteria for the selection fields
				String[] selectionArgs = new String[] { "0" };

				// return me the cursor for the query that i just made by using
				// projection, selection and selectionArgs
				return new android.support.v4.content.CursorLoader(
						getActivity(), ProviderBudget.CONTENT_URI, projection,
						selection, selectionArgs, null);
			}

			// on load finished means that loading has finished and update your
			// views with new data
			@Override
			public void onLoadFinished(
					android.support.v4.content.Loader<Cursor> arg0,
					Cursor cursor) {
				// update the adapter with new database values
				simpleCursorAdapterIncome.swapCursor(cursor);

				// manually update all other views with updated database values
				String total = ProviderBudget.getTotal(getActivity());
				textViewTotal.setText("Total: " + total);

				textViewRemaining.setText("Remaining: " + total);

			}

			// on loader reset gets called when the loader is reset
			@Override
			public void onLoaderReset(
					android.support.v4.content.Loader<Cursor> arg0) {
				simpleCursorAdapterIncome.swapCursor(null);
			}
		});

		//this is the loader for the expense list view
		getLoaderManager().initLoader(1, null, new LoaderCallbacks<Cursor>() {

			// on create loader gets called whenever the fragment is loaded for
			// the first time
			@Override
			public android.support.v4.content.Loader<Cursor> onCreateLoader(
					int arg0, Bundle arg1) {
				// projection are the database fields that will be returned
				String[] projection = ProviderBudget.getColumns();

				// selection are the database fields on which the query will be
				// run
				String selection = "amount < ?";

				// selectionArgs are the criteria for the selection fields
				String[] selectionArgs = new String[] { "0" };

				// return me the cursor for the query that i just made by using
				// projection, selection and selectionArgs
				return new android.support.v4.content.CursorLoader(getActivity(), ProviderBudget.CONTENT_URI, projection,
						selection, selectionArgs, null);
			}

			// on load finished means that loading has finished and update your
			// views with new data
			@Override
			public void onLoadFinished(
					android.support.v4.content.Loader<Cursor> arg0,
					Cursor cursor) {
				// update the adapter with new database values
				simpleCursorAdapterExpense.swapCursor(cursor);



			}

			// on loader reset gets called when the loader is reset
			@Override
			public void onLoaderReset(
					android.support.v4.content.Loader<Cursor> arg0) {
				simpleCursorAdapterExpense.swapCursor(null);
			}
		});

		return rootView;

	}

}
