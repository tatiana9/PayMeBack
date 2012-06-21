package com.project.easycounts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BalanceActivity extends Activity {
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance);
        
        final Button returnButton = (Button) findViewById(R.id.goBack);
        final Button transactionsButton = (Button) findViewById(R.id.transactions);

        returnButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), GroupActivity.class);
				startActivity(intent);
			}
		});
        
        transactionsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), TransactionsActivity.class);
				startActivity(intent);
			}
		});
    }
}
