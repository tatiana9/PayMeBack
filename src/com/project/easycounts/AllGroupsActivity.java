package com.project.easycounts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AllGroupsActivity extends Activity {
	private List<Group> groups = null;
	private List<String> names = null;
	private ListView list;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.allgroups);
	    
        final Button returnButton = (Button) findViewById(R.id.goBack);

        returnButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), HomeScreenActivity.class);
				startActivity(intent);
			}
		});
        
	    list = (ListView) findViewById(R.id.listGroups);
	    groups = new ArrayList<Group>();
	    names = new ArrayList<String>();
	        
	    updateListView();
	}

	public void updateNames(){
		List<String> n = new ArrayList<String>();
	    for (Group m : groups){
	    	if (m != null){
	   			n.add(m.getName());
	   		}
	   	}
	    names = n;
	}
	    
	public void updateListView(){
		int size = GroupContainer.getInstance().getSize();
		if (size>0){
			//groups = HomeScreenActivity.groupContainer.allGroups;
			groups = GroupContainer.getInstance().getAllGroups();
			if (groups != null){
				updateNames();
			    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_member_item, names);
			    list.setAdapter(adapter);
				list.setTextFilterEnabled(true);
			}
		}
	}

}


