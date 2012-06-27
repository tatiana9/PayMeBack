package com.project.easycounts;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class HomeScreenActivity extends Activity{
	private PayMeBackBDD bdd;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //load data from database into model GroupContainer
        bdd = new PayMeBackBDD(this);
        bdd.open();
        bdd.loadBDD();
        printAllGroups();
        bdd.close();
        
        final Button addGroupButton = (Button) findViewById(R.id.addGroup);
        final Button loadGroupButton = (Button) findViewById(R.id.loadGroup);
   
        
        addGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AddGroupActivity.class);
				startActivity(intent);
			}
		});
        
        loadGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AllGroupsActivity.class);
				startActivity(intent);
			}
		});
		
    }
    
    public void printAllGroups(){
    	List<Group> allGroups = GroupContainer.getInstance().getAllGroups();
    	for (Group g: allGroups){
    		System.out.println(g.toString());
    	}
    }

}