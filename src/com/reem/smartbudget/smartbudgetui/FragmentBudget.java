package com.reem.smartbudget.smartbudgetui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.reem.smartbudget.BudgetPreferences;
import com.reem.smartbudget.R;
import com.reem.smartbudget.smartbudgetcontent.ProviderBudget;

public class FragmentBudget extends Fragment {

	SimpleCursorAdapter simpleCursorAdapterIncome;
	SimpleCursorAdapter simpleCursorAdapterExpense;

	TextView textViewIncomeTotal; 
	TextView textViewIncomeRemaining; 
	TextView textViewIncomeSpending; 

	TextView textViewExpenseTotal; 
	TextView textViewExpenseRemaining; 
	TextView textViewExpenseSpending; 

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_budget, container,
				false);
		

		Button buttonIncomeAdd = (Button) rootView
				.findViewById(R.id.buttonIncomeAdd);
		Button buttonExpenseAdd = (Button) rootView
				.findViewById(R.id.buttonExpenseAdd);


		textViewIncomeTotal = (TextView) rootView
				.findViewById(R.id.textViewIncomeTotal);
		textViewIncomeRemaining = (TextView) rootView
				.findViewById(R.id.textViewIncomeRemaining);
		textViewIncomeSpending = (TextView) rootView
				.findViewById(R.id.textViewIncomeSpending);
		textViewExpenseTotal = (TextView) rootView
				.findViewById(R.id.textViewExpensesTotal);
		textViewExpenseRemaining = (TextView) rootView
				.findViewById(R.id.textViewExpensesRemaining);
		textViewExpenseSpending = (TextView) rootView
				.findViewById(R.id.textViewExpensesSpending);

		String[] from = new String[] { ProviderBudget.KEY_ID,
				ProviderBudget.KEY_NAME, ProviderBudget.KEY_AMOUNT,
				ProviderBudget.KEY_NOTE };

		int[] to = new int[] { R.id.textViewId, R.id.textViewName,
				R.id.textViewAmount, R.id.textViewNote };

		simpleCursorAdapterIncome = new SimpleCursorAdapter(
				inflater.getContext(), R.layout.item_income, null, from, to, 0);

		simpleCursorAdapterExpense = new SimpleCursorAdapter(
				inflater.getContext(), R.layout.item_expense, null, from, to, 0);

		ListView listViewIncome = (ListView) rootView
				.findViewById(R.id.listViewIncome);
		listViewIncome.setAdapter(simpleCursorAdapterIncome);

		ListView listViewExpense = (ListView) rootView
				.findViewById(R.id.listViewExpense);
		
		listViewExpense.setAdapter(simpleCursorAdapterExpense);

		
		listViewIncome
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int index, long arg3) {

						Cursor cursor = (Cursor) simpleCursorAdapterIncome
								.getItem(index);

						// get values 
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

						// start the add activity
						Intent intent = new Intent(getActivity(),
								BudgetAddActivity.class);

						// add all the remaining values
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

		listViewExpense.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {


				Cursor cursor = (Cursor) simpleCursorAdapterExpense
						.getItem(index);

				String transactionId = cursor.getString(cursor
						.getColumnIndex(ProviderBudget.KEY_ID));

				Intent intent = new Intent(getActivity(),
						ExpenseTransactionListActivity.class);

				intent.putExtra(ProviderBudget.KEY_ID, transactionId);


				startActivity(intent);

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

					
						Intent intent = new Intent(getActivity(),
								BudgetAddActivity.class);

						
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
						BudgetAddActivity.class);
				i.putExtra("TYPE", "INCOME");
				startActivity(i);
			}
		});

		buttonExpenseAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(inflater.getContext(),
						BudgetAddActivity.class);
				i.putExtra("TYPE", "EXPENSE");
				startActivity(i);
			}
		});

		// loader manager manages data loading from the table
		// it provides us with some callback functions by using LoaderCallbacks
		// so that we can update our views with the latest data

		getLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>() {

			
			@Override
			public android.support.v4.content.Loader<Cursor> onCreateLoader(
					int arg0, Bundle arg1) {
				
				String[] projection = ProviderBudget.getColumns();

			
				String selection = "amount >= ?";

				
				String[] selectionArgs = new String[] { "0" };

				
				return new android.support.v4.content.CursorLoader(
						getActivity(), ProviderBudget.CONTENT_URI, projection,
						selection, selectionArgs, null);
			}

			
			@Override
			public void onLoadFinished(
					android.support.v4.content.Loader<Cursor> arg0,
					Cursor cursor) {
				// update the adapter with new database values
				simpleCursorAdapterIncome.swapCursor(cursor);

				refreshTextViews();

			}

			// on loader reset gets called when the loader is reset
			@Override
			public void onLoaderReset(
					android.support.v4.content.Loader<Cursor> arg0) {
				simpleCursorAdapterIncome.swapCursor(null);
			}
		});

		// this is the loader for the expense list view
		getLoaderManager().initLoader(1, null, new LoaderCallbacks<Cursor>() {

			// on create loader gets called whenever the fragment is loaded for
			// the first time
			@Override
			public android.support.v4.content.Loader<Cursor> onCreateLoader(
					int arg0, Bundle arg1) {
				
				String[] projection = ProviderBudget.getColumns();

			
				String selection = "amount < ?";

				String[] selectionArgs = new String[] { "0" };

				
				return new android.support.v4.content.CursorLoader(
						getActivity(), ProviderBudget.CONTENT_URI, projection,
						selection, selectionArgs, null);
			}

			
			@Override
			public void onLoadFinished(
					android.support.v4.content.Loader<Cursor> arg0,
					Cursor cursor) {
				// update the adapter with new database values
				simpleCursorAdapterExpense.swapCursor(cursor);

				refreshTextViews();
			}

			@Override
			public void onLoaderReset(
					android.support.v4.content.Loader<Cursor> arg0) {
				simpleCursorAdapterExpense.swapCursor(null);
			}
		});

		return rootView;

	}
	public String getCurrency() {
		String symbol = BudgetPreferences.loadCurrencyFromFile(getActivity().getApplicationContext());
		if(symbol.equals("USD"))
			return "$";
		else if(symbol.equals("EURO"))
			return "€";
		else if(symbol.equals("POUND"))
			return "£";
		else if(symbol.equals("AUD"))
			return "AU$";
		else
			return "¥";	
	}
	private void refreshTextViews() {
	
		String symbol = getCurrency();
		// manually update all other views with updated database values
		String totalIncome = ProviderBudget.getTotalIncome(getActivity());
		String totalExpense = ProviderBudget.getTotalExpense(getActivity())
				.replace("-", "");

		String remainingIncome = ""
				+ (Integer.parseInt(totalIncome) - Integer
						.parseInt(totalExpense));
		String spendingIncome = totalExpense;

		String remainingExpense = "";
		String spendingExpense = "";

		textViewIncomeTotal.setText("Total: " + symbol + totalIncome);
		textViewIncomeRemaining.setText("Savings: " + symbol + remainingIncome);
		textViewIncomeSpending.setText("Spending: " + symbol + spendingIncome);

		textViewExpenseTotal.setText("Total: " + symbol + totalExpense);

		textViewExpenseRemaining.setText("Remaining: " + symbol + remainingExpense);
		textViewExpenseSpending.setText("Spending: " + symbol + spendingExpense);
	}
}
