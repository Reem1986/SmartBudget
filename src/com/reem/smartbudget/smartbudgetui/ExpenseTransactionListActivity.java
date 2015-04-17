package com.reem.smartbudget.smartbudgetui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.reem.smartbudget.AdapterReminder;
import com.reem.smartbudget.R;
import com.reem.smartbudget.smartbudgetcontent.ProviderBudget;
import com.reem.smartbudget.smartbudgetcontent.ProviderExpenseTransaction;

public class ExpenseTransactionListActivity extends FragmentActivity {

	String parentKeyId;

	ExpenseTransactionListActivity base;
	Button buttonExpenseTransactionAdd;
	ListView listViewExpenseTransaction;

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.expense_transaction_list);

		base = this;

		parentKeyId = getIntent().getStringExtra(ProviderBudget.KEY_ID);

		Toast.makeText(getApplicationContext(), parentKeyId, Toast.LENGTH_SHORT)
				.show();

		buttonExpenseTransactionAdd = (Button) findViewById(R.id.buttonExpenseTransactionAdd);
		listViewExpenseTransaction = (ListView) findViewById(R.id.listViewExpenseTransaction);

		buttonExpenseTransactionAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(base.getApplicationContext(),
						ExpenseTransactionAddActivity.class);
				intent.putExtra(ProviderExpenseTransaction.KEY_ID_PARENT,
						parentKeyId);

				startActivity(intent);
			}
		});

	}
}
