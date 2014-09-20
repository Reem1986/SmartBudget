package com.reem.smartbudget;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	    private Button buttonSubmit;
	    private EditText editTextPassword;
	    private boolean isPinFirstTime = true;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        String loadedPinValue = BudgetPreferences.loadPinFromFile(getApplicationContext());

        if (loadedPinValue.equals(""))
        {
            buttonSubmit.setText("Save pin for first time");
            isPinFirstTime = true;
        }
        else
        {
            buttonSubmit.setText("Enter pin");
            isPinFirstTime = false;
        }

        buttonSubmit.setOnClickListener(new OnClickListener() {
        	@Override
            public void onClick(View v)
            {

                if (isPinFirstTime)
                {
                    BudgetPreferences.savePinToFile(getApplicationContext(), editTextPassword.getText().toString());
                    
                    Toast.makeText(getApplicationContext(), "Your pin has been saved", Toast.LENGTH_LONG).show();
                }
                else
                {

                    final String loadedPin = BudgetPreferences.loadPinFromFile(getApplicationContext());
                    final String enteredPin = editTextPassword.getText().toString();

                    if (enteredPin.equals(loadedPin))
                    {
                        Toast.makeText(getApplicationContext(), "Pin " + enteredPin + " is correct", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else

                    {
                        Toast.makeText(getApplicationContext(), "Pin " + enteredPin + " is wrong!!!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
      
	 @Override
	    protected void onResume()
	    {
	        super.onResume();
	    }

	    @Override
	    protected void onPause()
	    {
	        super.onPause();
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
