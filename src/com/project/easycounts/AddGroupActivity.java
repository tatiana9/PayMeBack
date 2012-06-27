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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AddGroupActivity extends Activity {
    /** Called when the activity is first created. */
	private PayMeBackBDD bdd;
	
	private List<Member> members = null;
	private List<String> names = null;
	
	private View list;
	private List<View> viewsList;
	private List<ImageButton> buttonsList;
	
	private Group newGroup;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addgroup);
        
        bdd = new PayMeBackBDD(this);
        
        final Button cancelButton = (Button) findViewById(R.id.cancelCreateGroup);
        final Button applyButton = (Button) findViewById(R.id.applyCreateGroup);
        final ImageButton addMemberButton = (ImageButton) findViewById(R.id.addMember);

        //instantiate a new group
        newGroup = new Group();
        
        list = findViewById(R.id.listMembers);
        viewsList = new ArrayList<View>();
        buttonsList = new ArrayList<ImageButton>();
        
        members = new ArrayList<Member>();
        names = new ArrayList<String>();

        updateList();
        
        
        addMemberButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				builder.setTitle("Member's name");
				builder.setCancelable(true);
				final EditText input = new EditText(v.getContext());
				input.setSingleLine(true);
				builder.setView(input);
				builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id) {
						Editable value = input.getText();
						String name = value.toString();
						
						if ((name.length()>=1)){
							if (!newGroup.getMembersNames().contains(name)){
								newGroup.addMember(name);
								//updateListView();
								updateList();
							}
							else {
								Toast.makeText(getApplicationContext(), "This member already exists", Toast.LENGTH_LONG).show();
							}
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
					if (GroupContainer.getInstance().getGroupsNames().contains(groupName)){
						Toast.makeText(v.getContext(), "Group name not available", Toast.LENGTH_LONG).show();
						return;
					}
					newGroup.setName(groupName);
				
					//verify that members have been entered
					if (!newGroup.getMembers().isEmpty()){
						//add the group to GroupContainer
						GroupContainer.getInstance().addGroupToContainer(newGroup);
						GroupContainer.getInstance().setCursorAtEnd();
						writeInBDD();
						
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
    
    
    private void updateList(){
    	LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	names = newGroup.getMembersNames();
    	((LinearLayout)list).removeAllViews();
    	viewsList.clear();
    	buttonsList.clear();
    	for (String name: names){
			View view = inflater.inflate(R.layout.list_member_item, null);
			TextView t = (TextView) view.findViewById(R.id.memberName);
			t.setText(name);
			ImageButton b = (ImageButton) view.findViewById(R.id.deleteButton);
			viewsList.add(t);
			buttonsList.add(b);
			((LinearLayout) list).addView(view);
		}
    	
        for (View view: viewsList){
			TextView t = (TextView) view.findViewById(R.id.memberName);
        	t.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//int position = GroupContainer.getInstance().getCurrentGroup().getMemberPosition(((TextView)v).getText().toString());
					int position = names.indexOf(((TextView)v).getText().toString());
					System.out.println("expense position : "+position);
					
					AlertDialog.Builder builder = new AlertDialog.Builder(AddGroupActivity.this);
					builder.setTitle("Edit member's name");
					builder.setCancelable(true);
					final EditText input = new EditText(AddGroupActivity.this);
					input.setSingleLine(true);
					final int pos = position;
					String n = ((TextView)v).getText().toString();
					input.setText(n);
					builder.setView(input);
					builder.setPositiveButton("Edit", new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id) {
							Editable value = input.getText();
							String name = value.toString();
							
							if ((name.length() >= 1)){
								if (!newGroup.getMembersNames().contains(name)){
									newGroup.getMembers().set(pos, new Member(name));
									//updateListView();
									updateList();
								}
								else {
									Toast.makeText(getApplicationContext(), "This member already exists", Toast.LENGTH_LONG).show();
								}
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
        }
        
        //for (int i=0; i<buttonsList.size(); i++){
        for (ImageButton button: buttonsList){
        	int pos = buttonsList.indexOf(button);
        	final String associatedName = newGroup.getMembersNames().get(pos);
        	button.setOnClickListener(new View.OnClickListener() {
    			
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				AlertDialog.Builder builder = new AlertDialog.Builder(AddGroupActivity.this);
    				builder.setTitle("Do you really want to delete "+associatedName+"?");
    				builder.setCancelable(true);
    				builder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
    					public void onClick(DialogInterface dialog, int id) {						
    						newGroup.deleteMember(associatedName);
    						updateList();
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
        }
        
    }
    
    
    
    public void writeInBDD(){
    	bdd.open();
    	long groupID = bdd.insertGroup(newGroup.getName());
    	int id = GroupContainer.getInstance().getCursor();
    	System.out.println("Test: id dans table = "+groupID+" et id dans groupContainer = "+id);
    	for (Member m: newGroup.getMembers()){
    		bdd.insertMember(m.getName(), groupID);
    	}
    	bdd.close();
    }
    
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
			startActivity(intent);
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
