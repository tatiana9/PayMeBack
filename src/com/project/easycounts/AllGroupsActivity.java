package com.project.easycounts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AllGroupsActivity extends Activity {
	private List<String> names = null;
	private View list;
	private List<View> viewsList;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.allgroups);
        
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
				((TextView)view.findViewById(R.id.groupItemInfos)).setText("Total spent: "+GroupContainer.getInstance().getTotal(name)+ " Û");

				viewsList.add(view);
				((LinearLayout) list).addView(view);
			}
		}
	    
		for (View view: viewsList){
		    view.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					TextView t = (TextView) v.findViewById(R.id.groupItemName);
					try {
					int position = GroupContainer.getInstance().getGroupPosition(t.getText().toString());
					System.out.println("group position: "+position);
					GroupContainer.getInstance().setCursor(position);
					Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
					startActivity(intent);
					} catch (Exception e){
						System.err.println(e);
					}
				}
			});
		}

	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
			startActivity(intent);
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}


