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

public class GroupActivity  extends Activity {
	private ListView list;
	private List<String> expenses;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouppage);
        
        final Button addExpenseButton = (Button) findViewById(R.id.addExpense);
        final Button returnButton = (Button) findViewById(R.id.goBack);
        final Button balanceButton = (Button) findViewById(R.id.balance);
        list = (ListView) findViewById(R.id.listExpenses);
        
        expenses = new ArrayList<String>();
        
        updateExpenses();
        
        addExpenseButton.setOnClickListener(new View.OnClickListener() {		
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), NewExpenseActivity.class);
				startActivity(intent);
			}
		});
        
        returnButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AllGroupsActivity.class);
				startActivity(intent);
			}
		});
        
        balanceButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), BalanceActivity.class);
				startActivity(intent);
			}
		});
    }
    
    private void updateExpenses(){
    	//TODO do it correctly with cursor
    	//List<Expense> exp = GroupContainer.getInstance().getLastGroup().getExpenses();
    	List<Expense> exp = GroupContainer.getInstance().getCurrentGroup().getExpenses();

    	for (Expense e : exp){
    		expenses.add(e.getName()+"\n"+e.getMonth()+"-"+e.getDay()+"-"+e.getYear()+", "+e.getAmount()+" euros");
    	}
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_member_item, expenses);
    	list.setAdapter(adapter);
		list.setTextFilterEnabled(true);
    }
    
}