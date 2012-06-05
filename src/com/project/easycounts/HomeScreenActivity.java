package com.project.easycounts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.Button;

public class HomeScreenActivity extends Activity implements OnClickListener{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View addGroupView = findViewById(R.id.addGroup);
        if (addGroupView != null) {
        	addGroupView.setOnClickListener(this);
        }
        
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()){
			case R.id.addGroup: {
				Intent intent = new Intent(this, AddGroupActivity.class);
				startActivity(intent);
				break;
			}
		}
		/*
		
		case R.id.loadGroup: {
			Intent intent = new Intent(this, LoadGroupActivity.class);
			//startActivityForResult(intent, );
			break;
		}
		
		}*/
	}
}