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


public class FragmentBudgetAdd extends FragmentActivity
{

    boolean isAdd = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_budget_add);

        final Button buttonDelete = (Button) findViewById(R.id.buttonDelete);

        //check if there is an id being sent
        final String editId = getIntent().getStringExtra(ProviderBudget.KEY_ID);

        final String typeOfTransaction = getIntent().getStringExtra("TYPE");

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


        //if it was an edit
        if (!isAdd)
        {
            //add intent extras data to your views as this is an edit call
            //populate all your views with the intent extra values that you are getting
            editTextName.setText(getIntent().getStringExtra(ProviderBudget.KEY_NAME));

            if (typeOfTransaction.equals("INCOME"))
            	editTextAmount.setText(getIntent().getStringExtra(ProviderBudget.KEY_AMOUNT));
            else
            	editTextAmount.setText(getIntent().getStringExtra(ProviderBudget.KEY_AMOUNT).replace("-", ""));

        }

        //button icon
        final Button buttonIcon = (Button) findViewById(R.id.buttonIcon);
        buttonIcon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Log.v("clicked", "clicked");
                final Drawable icon = buttonIcon.getBackground();

                final Drawable iconApple = getResources().getDrawable(R.drawable.apple);
                final Drawable iconRobot = getResources().getDrawable(R.drawable.ic_launcher);

                if (icon == iconApple)
                {
                    buttonIcon.setBackgroundResource(R.drawable.ic_launcher);
                }
                else if (icon == iconRobot)
                {
                    buttonIcon.setBackgroundResource(R.drawable.apple);
                }
            }
        });

        buttonDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                ClassTransaction newTransaction = new ClassTransaction();
                newTransaction.id = editId;

                ProviderBudget.deleteTransaction(getApplicationContext(), newTransaction);

                Toast.makeText(getApplicationContext(), "Delete success", Toast.LENGTH_LONG).show();

                finish();
            }
        });

        buttonSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                // extract all user input from those views compile the insert
                // data into a new Transaction
                ClassTransaction newTransaction = new ClassTransaction();

                if (isAdd)
                {
                    //if this is an add call, then generate a unique id for the record
                    newTransaction.id = System.currentTimeMillis() + "";
                }
                else
                {
                    //if this was an edit call, then use the id that you're getting in intent extras
                    newTransaction.id = editId;
                }

                //populate the transaction object with all the values in our views
                newTransaction.name = editTextName.getText().toString();

                if (typeOfTransaction.equals("INCOME"))
                	newTransaction.amount = Float.parseFloat(editTextAmount.getText().toString());
                else
                	newTransaction.amount = - Float.parseFloat(editTextAmount.getText().toString());

                //using the id, our addTransaction function can know if it needs to edit or add
                ProviderBudget.addTransaction(getApplicationContext(), newTransaction);

                Toast.makeText(getApplicationContext(), "Transaction success", Toast.LENGTH_LONG).show();

                finish();

            }
        });

    }
}
