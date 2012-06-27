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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GroupActivity  extends Activity {
	//private ListView list;
	private View list;
	private List<View> viewsList;

	private static int OLD_EXPENSE = 2;
	private static int NEW_EXPENSE = 1;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouppage);
        
        final Button addExpenseButton = (Button) findViewById(R.id.addExpense);
        //final Button returnButton = (Button) findViewById(R.id.goBack);
        final Button balanceButton = (Button) findViewById(R.id.balance);
        list = findViewById(R.id.listExpenses);
        viewsList = new ArrayList<View>();
       
        
        //updateExpenses();
        updateList();
        
		for (View view: viewsList){
		    view.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int position = GroupContainer.getInstance().getCurrentGroup().getExpensePosition(((TextView)v).getText().toString());
					System.out.println("expense position : "+position);
					Intent intent = new Intent(getApplicationContext(), NewExpenseActivity.class);
					intent.putExtra("expenseType", OLD_EXPENSE);
					intent.putExtra("current_expense", position);
					startActivity(intent);
				}
			});
		}
        
        
        /*
	    list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//String selectedItem = ((TextView) view).getText().toString();
				//TODO start newExpenseActivity pre-filled so user can modifiy items. 
				//Problem: we don't want to add a new expense, just modifiy it!
				//idea : define 2 static boolean variables : true if NewExpenseActivity starts after click on addExpense, false if it starts after clicking on an expense item
				//pass the variable in putExtra
				Intent intent = new Intent(getApplicationContext(), NewExpenseActivity.class);
				intent.putExtra("expenseType", OLD_EXPENSE);
				intent.putExtra("current_expense", position);
				startActivity(intent);
			}
	    	
	    });
	    */
        
        addExpenseButton.setOnClickListener(new View.OnClickListener() {		
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), NewExpenseActivity.class);
				intent.putExtra("expenseType", NEW_EXPENSE);
				startActivity(intent);
			}
		});
        /*
        returnButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AllGroupsActivity.class);
				startActivity(intent);
			}
		});
		*/
        
        balanceButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), BalanceActivity.class);
				startActivity(intent);
			}
		});
    }
    /*
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
    */
    
    private void updateList(){
    	LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		List<Expense> exp = GroupContainer.getInstance().getCurrentGroup().getExpenses();
		for (Expense e: exp){
			View view = inflater.inflate(R.layout.list_expense_item, null);
			//String s = e.getName()+"\n"+e.getMonth()+"-"+e.getDay()+"-"+e.getYear()+", "+e.getAmount()+" euros";
			((TextView)view.findViewById(R.id.expenseItemName)).setText(e.getName());
			viewsList.add(view);
			((LinearLayout) list).addView(view);
		}
    }
    
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	Intent intent = new Intent(getApplicationContext(), AllGroupsActivity.class);
			startActivity(intent);
	    }
	    return super.onKeyDown(keyCode, event);
	}
    
}