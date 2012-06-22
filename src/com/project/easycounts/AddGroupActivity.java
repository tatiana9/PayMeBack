package com.project.easycounts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class AddGroupActivity extends Activity {
    /** Called when the activity is first created. */
	private List<Member> members = null;
	private List<String> names = null;
	
	private ListView list;
	//private LinearLayout list;
	
	private ArrayAdapter<String> adapter;
	private Group newGroup;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addgroup);
        
        final Button cancelButton = (Button) findViewById(R.id.cancelCreateGroup);
        final Button applyButton = (Button) findViewById(R.id.applyCreateGroup);
        final ImageButton addMemberButton = (ImageButton) findViewById(R.id.addMember);

        //instantiate a new group
        newGroup = new Group();
        
        list = (ListView) findViewById(R.id.listMembers);
        members = new ArrayList<Member>();
        names = new ArrayList<String>();

        updateListView();
        
        addMemberButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				builder.setTitle("Member's name");
				builder.setCancelable(true);
				final EditText input = new EditText(v.getContext());
				builder.setView(input);
				builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id) {
						Editable value = input.getText();
						String name = value.toString();
						
						if (name!=null){
							newGroup.addMember(name);
							updateListView();
						}
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						dialog.cancel();
					}
				});
				
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
        
        cancelButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), HomeScreenActivity.class);
				startActivity(intent);
			}
		});
        
        applyButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				
				//get the groupName
				EditText editText = (EditText) findViewById(R.id.groupName);
				String groupName = editText.getText().toString();
								
				//first verify group name is not empty
				if (groupName.length() >= 1){
					newGroup.setName(groupName);
				
					//verify that members have been entered
					if (!newGroup.getMembers().isEmpty()){
						//add the group to GroupContainer
						GroupContainer.getInstance().addGroupToContainer(newGroup);
						GroupContainer.getInstance().setCursorAtEnd();
						
						Intent intent = new Intent(v.getContext(), GroupActivity.class);
						startActivity(intent);
					}
					else{
				   		Toast.makeText(v.getContext(), "No member registered", Toast.LENGTH_LONG).show();
					}
				}
				else {
			   		Toast.makeText(v.getContext(), "Please enter a group name", Toast.LENGTH_LONG).show();
				}
			}
		});  
    }
    
    
    public void updateNames(){
    	List<String> n = new ArrayList<String>();
    	for (Member m : members){
    		if (m != null){
   				n.add(m.getName());
   			}
   		}
       	names = n;
    }
    
    public void updateListView(){
    	members = newGroup.getMembers();
    	if (members != null){
    		updateNames();
	    	adapter = new ArrayAdapter<String>(this, R.layout.list_member_item, names);
	    	list.setAdapter(adapter);
			list.setTextFilterEnabled(true);
    	}
    }
    
    /*
    public void updateListView(){
    	members = newGroup.getMembers();
    	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	if (members != null){
    		updateNames();
    		for (int i=0; i<names.size(); i++){
    			View view = inflater.inflate(R.layout.list_member_item, null);
    			
    			//initialize the view
    			view.setOnClickListener(new View.OnClickListener(){
    				public void onClick(View v){
    					//TODO
    				}
    			});
    			
    			list.addView(view);
    			if (i < names.size()-1){
    				inflater.inflate(R.layout.line,list);
    			}
    		}
    	}
    }
    
    */
    public void printAllGroupNames(){
		int size = GroupContainer.getInstance().getSize();
		List<Group> listGroups = GroupContainer.getInstance().getAllGroups();
    	if (size>0){
    		for (Group g : listGroups){
    			System.out.println(g.getName());
    		}
    	}
    	System.out.println(size);
    }
}
