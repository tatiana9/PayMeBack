package com.project.easycounts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddGroupActivity extends Activity {
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addgroup);
        
        final Button cancelButton = (Button) findViewById(R.id.cancelCreateGroup);
        final Button applyButton = (Button) findViewById(R.id.applyCreateGroup);

        cancelButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(), HomeScreenActivity.class);
				startActivity(intent);
			}
		});
        
        applyButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(), GroupActivity.class);
				startActivity(intent);
			}
		});
        
    }
}
