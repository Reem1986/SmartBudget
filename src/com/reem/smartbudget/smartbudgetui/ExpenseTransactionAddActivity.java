package com.reem.smartbudget.smartbudgetui;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.reem.smartbudget.R;
import com.reem.smartbudget.smartbudgetcontent.ClassTransaction;
import com.reem.smartbudget.smartbudgetcontent.ProviderBudget;
import com.reem.smartbudget.smartbudgetcontent.ProviderExpenseTransaction;


public class ExpenseTransactionAddActivity extends FragmentActivity
{

    boolean isAdd = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.expense_transaction_add);

        final Button buttonDelete = (Button) findViewById(R.id.buttonDelete);

        //check if there is an id being sent
        final String editId = getIntent().getStringExtra(ProviderExpenseTransaction.KEY_ID);

        //if it is an edit then do this
        if (editId != null)
        {
            getActionBar().setTitle("Edit");
            isAdd = false;
        }
        //otherwise this is an add call
        else
        {
            getActionBar().setTitle("Add");
            buttonDelete.setVisibility(View.GONE);
        }

        final Button buttonSave = (Button) findViewById(R.id.buttonSave);
        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        final EditText editTextFromIncome = (EditText) findViewById(R.id.editTextFromIncome);
        final EditText editTextNote = (EditText) findViewById(R.id.editTextNote);


        buttonDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
            }
        });

        buttonSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
            }
        });

    }
}
