package com.project.easycounts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewExpenseActivity extends Activity {
    /** Called when the activity is first created. */
	private List<String> membersNames;
	private Expense newExpense;
	private ListView participantsListView;
	
	//class variables for date picker
	private TextView mDateDisplay;
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;
	private DatePickerDialog.OnDateSetListener mDateSetListener;
	static final int DATE_DIALOG_ID = 0;
	
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
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        mPickDate = (Button) findViewById(R.id.pickDate);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, membersNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(new MyOnSpinnerItemSelectedListener());
        
        mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
        
        //get current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        //display the current date
        updateDateDisplay();
        
        //the callback received when the user "sets" the date in the dialog
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){

			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				mYear = year;
				mMonth = monthOfYear;
				mDay = dayOfMonth;
				updateDateDisplay();
			}
        	
        };
        
        //ListView
        participantsListView = (ListView) findViewById(R.id.participantsList);
        updateListView();
        
        addExpenseButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				
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
					   		Toast.makeText(v.getContext(), "The amount must be a number", Toast.LENGTH_LONG).show();
				    		return;
				    	}
						newExpense.setAmount(amount);
						
						//get the payer from spinner
						String payer;
						if (spinner.getSelectedItem() != null){
							payer = spinner.getSelectedItem().toString();
						}		
						else {
							//there's at least one member: alert if no members entered when group is created
							payer = membersNames.get(0);
						}
						newExpense.setPayer(payer);
				
						//TODO get the date
						newExpense.setDate(mYear, mMonth, mDay);
				
						//TODO get participants
						int count = participantsListView.getAdapter().getCount();
				
						//TODO get shares
								
						//add the expense to the current group!
						//TODO with cursor position
						GroupContainer.getInstance().getLastGroup().addExpense(newExpense);
				
						Intent intent = new Intent(v.getContext(), GroupActivity.class);
						startActivity(intent);
					

				}
				else {
			   		Toast.makeText(v.getContext(), "Please enter a name for your expense", Toast.LENGTH_LONG).show();
				}
			}
		});
        
        cancelButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), GroupActivity.class);
				startActivity(intent);
			}
		});
		
    }
    
    private void updateDateDisplay() {
		mDateDisplay.setText(
			new StringBuilder()
				//month is 0 based so add 1!
				.append(mMonth + 1).append("-")
				.append(mDay).append("-")
				.append(mYear).append(" "));
	}
    
    @Override
    protected Dialog onCreateDialog(int id){
    	switch (id) {
    	case DATE_DIALOG_ID:
    		return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
    	}
    	return null;
    }
    
    
    private void updateListView(){
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, membersNames);
	    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_member_item, membersNames);
    	participantsListView.setAdapter(adapter);
	    //participantsListView.setTextFilterEnabled(true);
	    participantsListView.setItemsCanFocus(false);
	    participantsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

	private void getMembersNames(){
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
    
}
