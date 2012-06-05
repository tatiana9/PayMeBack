package com.project.easycounts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AddGroupActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addgroup);
        
        View addCancelView = findViewById(R.id.cancelCreateGroup);
        if (addCancelView != null) {
        	addCancelView.setOnClickListener(this);
        }
    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.cancelCreateGroup: {
			Intent intent = new Intent(this, HomeScreenActivity.class);
			startActivity(intent);
			break;
		}
	}
	}
}
