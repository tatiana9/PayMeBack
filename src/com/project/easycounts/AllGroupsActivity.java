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
	private List<Group> groups = null;
	private List<String> names = null;
	//private ListView list;
	private View list;
	
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
        
	    //list = (ListView) findViewById(R.id.listGroups);
	    groups = new ArrayList<Group>();
	    names = new ArrayList<String>();
	    
	    list = findViewById(R.id.listGroups);
		//TextView essai = new TextView(this);
		//essai.setText("essai");
		//essai.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		//list.setBackgroundColor(Color.TRANSPARENT);
		//((LinearLayout)list).addView(essai);
	    
	    updateList();
	    //essai a la main
	    /*
	    names = GroupContainer.getInstance().getGroupsNames();
	    for (String name: names){
	    	TextView t = new TextView(this);
	    	t.setText(name);
	    	t.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	    	((LinearLayout) list).addView(t);
	    }
	    */
	    
	    
	    
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
	
	public void updateList(){
		System.out.println("update0");
		LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		int size = GroupContainer.getInstance().getSize();
		if (size > 0){
			System.out.println("update1");
			names = GroupContainer.getInstance().getGroupsNames();
			for (int i =0; i<names.size(); i++){
				String name = names.get(i);
				System.out.println("update2");
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
				System.out.println("update3");

			}
		}

	}
	
}


