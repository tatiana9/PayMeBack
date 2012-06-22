package com.project.easycounts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
	    
	    list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//String selectedItem = ((TextView) view).getText().toString();
				GroupContainer.getInstance().setCursor(position);
				System.out.println("position : "+position);
				Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
				startActivity(intent);
			}
	    	
	    });
	    
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
				list.setTextFilterEnabled(true);
			    list.setAdapter(adapter);
			}
		}
	}
	
	/*
    private void test(){
    	//add new groups for test purpose
    	Group group1 = new Group();
    	group1.setName("mecs");
    	group1.addMember("Tatiana");
    	group1.addMember("Daniel");
    	group1.addMember("Adrien");
    	
    	Group group2 = new Group();
    	group1.setName("filles");
    	group1.addMember("Tatiana");
    	group1.addMember("Aurore");
    	group1.addMember("Emilie");
    	
    	GroupContainer.getInstance().addGroupToContainer(group1);
    	GroupContainer.getInstance().addGroupToContainer(group2);
    }
	*/
}


