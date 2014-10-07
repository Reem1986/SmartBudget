package com.reem.smartbudget;

import com.reem.smartbudget.FragmentBudgetAdd;
import com.reem.smartbudget.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FragmentBudget extends Fragment {

	@Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_budget, container,
				false);
		   Button buttonIncomeAdd = (Button) rootView.findViewById(R.id.buttonIncomeAdd);
	        Button buttonExpenseAdd = (Button) rootView.findViewById(R.id.buttonExpenseAdd);

	        buttonIncomeAdd.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v)
	            {
	                Intent i = new Intent(inflater.getContext(), FragmentBudgetAdd.class);
	                startActivity(i);
	            }
	        });

	        buttonExpenseAdd.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v)
	            {
	                Intent i = new Intent(inflater.getContext(), FragmentBudgetAdd.class);
	                startActivity(i);
	            }
	        });
		return rootView;
	}

}
