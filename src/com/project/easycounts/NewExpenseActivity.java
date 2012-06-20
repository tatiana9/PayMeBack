package com.project.easycounts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewExpenseActivity extends Activity {
    /** Called when the activity is first created. */
	List<String> membersNames;
	Expense newExpense;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newexpense);
        
        //Instantiate a new expense
        newExpense = new Expense();
        
        //instantiate and get list of members' names
        membersNames = new ArrayList<String>();
        getMembersNames();
        
        final Spinner spinner = (Spinner) findViewById(R.id.paidBySpinner);
        final Button addExpenseButton = (Button) findViewById(R.id.addNewExpense);
        final Button cancelButton = (Button) findViewById(R.id.cancelAddExpense);
        final EditText editTextName = (EditText) findViewById(R.id.expenseName);
        final EditText editTextAmount = (EditText) findViewById(R.id.amount);

        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, membersNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(new MyOnSpinnerItemSelectedListener());
        
        
        addExpenseButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//get the expense's name
				String expenseName = editTextName.getText().toString();
				
				if (expenseName.length() >=1){
					
					//get the amount
					String amountChar = editTextAmount.getText().toString();
						
					newExpense.setName(expenseName);
					double amount = 0;

						try{
							amount = Double.parseDouble(amountChar);
						}
					   	catch (NumberFormatException ex){
					   		//TODO toast
				    		return;
				    	}
						newExpense.setAmount(amount);
						
						//get the payer from spinner
						String payer;
						if (spinner.getSelectedItem() != null){
							payer = spinner.getSelectedItem().toString();
						}		
						else {
							//there at least one member: alert if no members entered when group is created
							payer = membersNames.get(0);
						}
						newExpense.setPayer(payer);
				
						//TODO get the date?			
				
						//TODO get participants
				
						//TODO get shares
								
						//add the expense to the current group!
						//TODO with cursor position
						GroupContainer.getInstance().getLastGroup().addExpense(newExpense);
				
						Intent intent = new Intent(v.getContext(), GroupActivity.class);
						startActivity(intent);
					

				}
				else {
					//TODO toast no expense name
				}
			}
		});
        
        cancelButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(), GroupActivity.class);
				startActivity(intent);
			}
		});
		
    }
    
    public void getMembersNames(){
    	List<String> n = new ArrayList<String>();
    	//TODO : change to method using "cursor" to be in the right group (especially when parent Activity is AllGroupsActivity)
    	Group g = GroupContainer.getInstance().getLastGroup();
    	
    	for (Member m : g.getMembers()){
    		if (m != null){
    			n.add(m.getName());
    		}
    	}
    	membersNames = n;
    }
    
    /**
     * verifies that a string is actually an integer AND is not empty
     */
    public boolean isDouble(String s){
    	if (s.length() < 1) return false;
    	
    	try {
    		Double.parseDouble(s);
    	}
    	catch (NumberFormatException ex){
    		return false;
    	}
    	
    	char [] array = s.toCharArray();
    	for (char c : array){
    		if (! java.lang.Character.isDigit(c)){
    			return false;
    		}
    	}
    	return true;
    }
}
