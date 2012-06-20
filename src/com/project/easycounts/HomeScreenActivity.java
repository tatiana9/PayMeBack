package com.project.easycounts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreenActivity extends Activity{

	//public static GroupContainer groupContainer;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Button addGroupButton = (Button) findViewById(R.id.addGroup);
        final Button loadGroupButton = (Button) findViewById(R.id.loadGroup);

        
        
        addGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(), AddGroupActivity.class);
				startActivity(intent);
			}
		});
        
        loadGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(), AllGroupsActivity.class);
				startActivity(intent);
			}
		});
		
    }
}