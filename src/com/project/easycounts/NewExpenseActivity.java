package com.project.easycounts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewExpenseActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newexpense);
        
        final Button addExpenseButton = (Button) findViewById(R.id.addNewExpense);
        final Button cancelButton = (Button) findViewById(R.id.cancelAddExpense);

        addExpenseButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(), GroupActivity.class);
				startActivity(intent);
			}
		});
        
        cancelButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(), GroupActivity.class);
				startActivity(intent);
			}
		});
		
    }
}
