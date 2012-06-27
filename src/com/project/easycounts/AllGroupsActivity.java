package com.project.easycounts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class AllGroupsActivity extends Activity {
	private List<String> names = null;
	//private ListView list;
	private View list;
	private List<View> viewsList;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.allgroups);
	    
        //final Button returnButton = (Button) findViewById(R.id.goBack);
	    /*
        returnButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), HomeScreenActivity.class);
				startActivity(intent);
			}
		});
		*/
        
	    names = new ArrayList<String>();
	    viewsList = new ArrayList<View>();
	    
	    list = findViewById(R.id.listGroups);

	    LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    
		int size = GroupContainer.getInstance().getSize();
		if (size > 0){
			names = GroupContainer.getInstance().getGroupsNames();
			for (int i =0; i<names.size(); i++){
				String name = names.get(i);
				View view = inflater.inflate(R.layout.list_group_item, null);
				((TextView)view.findViewById(R.id.groupItemName)).setText(name);
				viewsList.add(view);
				((LinearLayout) list).addView(view);
			}
		}
	    
		for (View view: viewsList){
		    view.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int position = GroupContainer.getInstance().getGroupPosition(((TextView)v).getText().toString());
					System.out.println("group position: "+position);
					GroupContainer.getInstance().setCursor(position);
					Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
					startActivity(intent);
				}
			});
		}

		
	    //list = (ListView) findViewById(R.id.listGroups);

	    //updateListView();
	    /*
	    list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				GroupContainer.getInstance().setCursor(position);
				Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
				startActivity(intent);
			}
	    	
	    });
	    
	    */
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
			startActivity(intent);
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	/*   
	public void updateListView(){
		int size = GroupContainer.getInstance().getSize();
		if (size>0){
			names = GroupContainer.getInstance().getGroupsNames();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_member_item, names);
			list.setTextFilterEnabled(true);
			list.setAdapter(adapter);
		}
	}
	*/
	/*
	public void updateList(){
		LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		int size = GroupContainer.getInstance().getSize();
		if (size > 0){
			names = GroupContainer.getInstance().getGroupsNames();
			for (int i =0; i<names.size(); i++){
				String name = names.get(i);
				View v = inflater.inflate(R.layout.list_group_item, null);
				((TextView)v.findViewById(R.id.groupItemName)).setText(name);
				
				v.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int position = GroupContainer.getInstance().getGroupPosition(((TextView)v).getText().toString());
						GroupContainer.getInstance().setCursor(position);
						Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
						startActivity(intent);
					}
				});
				
				((LinearLayout) list).addView(v);

			}
		}

	}
	*/
}


