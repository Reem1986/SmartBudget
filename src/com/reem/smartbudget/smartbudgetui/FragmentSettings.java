package com.reem.smartbudget.smartbudgetui;

import com.reem.smartbudget.BudgetPreferences;
import com.reem.smartbudget.R;
import com.reem.smartbudget.R.layout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.PorterDuff; 

public class FragmentSettings extends Fragment {

	public Dialog pinDialog(Bundle savedInstanceState) {
		final View layout = View.inflate(getActivity(), R.layout.changepin_dialog, null);
		final EditText pin = (EditText) layout.findViewById(R.id.pin);
		
		final EditText confirmpin = (EditText) layout.findViewById(R.id.confirmpin);
	    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    builder.setView(layout);
	    
	    // Add action buttons
	           builder.setPositiveButton("Change PIN", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   if(pin.getText().toString().equals(confirmpin.getText().toString())) {
	            		   BudgetPreferences.savePinToFile(getActivity().getApplicationContext(), pin.getText().toString());
	            		   Toast.makeText(getActivity().getApplicationContext(), "PIN Updated Successfully", Toast.LENGTH_LONG).show();
	            	   }
	            	   else
	            		   Toast.makeText(getActivity().getApplicationContext(), "PIN Does not match", Toast.LENGTH_LONG).show();
	               }
	           });
	           builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   dialog.dismiss();
	               }
	           });
	           
	    return builder.create();
	}
	public Dialog currencyDialog(Bundle savedInstanceState) {
		final View layout = View.inflate(getActivity(), R.layout.currency_dialog, null);

		
	    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    builder.setView(layout);
	    
	           builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   RadioGroup currency = (RadioGroup) layout.findViewById(R.id.radioGroup1);
	                   int selectedId = currency.getCheckedRadioButtonId();

	                       RadioButton radioButton = (RadioButton) layout.findViewById(selectedId);
	                       String Text = (String) radioButton.getText();
	                       BudgetPreferences.saveCurrencyToFile(getActivity().getApplicationContext(), Text.toString());
	               }
	           });
	           builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   dialog.dismiss();
	               }
	           });
	           
	    return builder.create();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			final Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings, container,
				false);
        ImageButton bt_rate = (ImageButton) rootView.findViewById(R.id.rateapp);
        bt_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getApplicationContext().getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "unable to find market app", Toast.LENGTH_LONG).show();
                }


            }
        });

        ImageButton bt_share = (ImageButton) rootView.findViewById(R.id.shareapp);
        bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Want to control your income and expense. Download budget app now --- https://play.google.com/store/apps/details?id=" + getActivity().getApplicationContext().getPackageName();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Keep Track Of Your Budget");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        
        ImageButton bt_email = (ImageButton) rootView.findViewById(R.id.emaildev);
        bt_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         	   Intent i = new Intent(Intent.ACTION_SEND);
         	   i.setType("message/rfc822");
         	   i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"info@devsite.com"});
         	   try {
         	       startActivity(Intent.createChooser(i, "Send mail..."));
         	   } catch (android.content.ActivityNotFoundException ex) {
         	       Toast.makeText(getActivity().getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
         	   }
            }
        });
        ImageButton changepin = (ImageButton) rootView.findViewById(R.id.changepin);
        changepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Dialog dialog = pinDialog(savedInstanceState);
            	dialog.setTitle("Change PIN");
                dialog.show();
            }
        });
        
        ImageButton changecurrency = (ImageButton) rootView.findViewById(R.id.changecurrency);
        changecurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Dialog dialog = currencyDialog(savedInstanceState);
            	dialog.setTitle("Change Currency");
                dialog.show();
            }
        });
		return rootView;
	}

}
