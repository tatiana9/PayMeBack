package com.project.easycounts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GroupActivity  extends Activity {
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouppage);
        
        final Button addExpenseButton = (Button) findViewById(R.id.addExpense);
        final Button returnButton = (Button) findViewById(R.id.goBack);
        final Button balanceButton = (Button) findViewById(R.id.balance);
        
        addExpenseButton.setOnClickListener(new View.OnClickListener() {		
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), NewExpenseActivity.class);
				startActivity(intent);
			}
		});
        
        returnButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AllGroupsActivity.class);
				startActivity(intent);
			}
		});
        
        balanceButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), BalanceActivity.class);
				startActivity(intent);
			}
		});
    }
}