package com.project.easycounts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
	private static int OLD_EXPENSE = 2;
	private static int NEW_EXPENSE = 1;
	private int expenseType = 0;
	private int expenseIndex = 0;

	
	private List<String> membersNames;
	private Expense newExpense;
	private Expense oldExpense;
	private ListView participantsListView;
	
	//class variables for date picker
	private TextView mDateDisplay;
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;
	private DatePickerDialog.OnDateSetListener mDateSetListener;
	static final int DATE_DIALOG_ID = 0;
	
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private EditText editTextName;
	private EditText editTextAmount;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newexpense);
       

        Bundle extras = getIntent().getExtras();
        expenseType = extras.getInt("expenseType");
        if (expenseType == OLD_EXPENSE){
        	expenseIndex = extras.getInt("current_expense");
        	oldExpense = GroupContainer.getInstance().getCurrentGroup().getExpenses().get(expenseIndex);
        }
        
        //Instantiate a new expense
        newExpense = new Expense();
        
        //instantiate and get list of members' names
        membersNames = new ArrayList<String>();
        updateMembersNames();
        
        spinner = (Spinner) findViewById(R.id.paidBySpinner);
        final Button addExpenseButton = (Button) findViewById(R.id.addNewExpense);
        final Button cancelButton = (Button) findViewById(R.id.cancelAddExpense);
        editTextName = (EditText) findViewById(R.id.expenseName);
        editTextAmount = (EditText) findViewById(R.id.amount);
        final Button addFriendButton = (Button) findViewById(R.id.addFriend);
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        mPickDate = (Button) findViewById(R.id.pickDate);
        
        //spinner.setOnItemSelectedListener(new MyOnSpinnerItemSelectedListener());
        updateSpinner();
        
        if (expenseType == OLD_EXPENSE){
        	addExpenseButton.setText("Edit Expense");
        }
        
        
        addFriendButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				builder.setTitle("Enter new member's name");
				builder.setCancelable(true);
				final EditText input = new EditText(v.getContext());
				builder.setView(input);
				builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id) {
						Editable value = input.getText();
						String name = value.toString();
						
						if (name!=null){
							if (!membersNames.contains(name)){
							GroupContainer.getInstance().getCurrentGroup().addMember(name);
							updateSpinner();
							updateListView();
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
        
        
        mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
        
        if (expenseType == NEW_EXPENSE){
        	//get current date
        	final Calendar c = Calendar.getInstance();
        	mYear = c.get(Calendar.YEAR);
        	mMonth = c.get(Calendar.MONTH);
        	mDay = c.get(Calendar.DAY_OF_MONTH);
            //display the current date
            updateDateDisplay();
        }

        
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
        
        
        if (expenseType == OLD_EXPENSE){
        	preFill();
        }
        
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
				
					//get the date
					newExpense.setDate(mYear, mMonth, mDay);
				
					//get participants
					int count = participantsListView.getAdapter().getCount();
					int participantsCount = 0;
					for (int i=0; i<count; i++){
						if (participantsListView.isItemChecked(i)){
							newExpense.addParticipant(participantsListView.getItemAtPosition(i).toString());
							participantsCount ++;
						}
					}
					if (participantsCount > 0){
						//TODO get custom shares
						double pseudoTotal = 0;
						for (int i = 0; i<membersNames.size(); i++){
							if (newExpense.getParticipantsNames().contains(membersNames.get(i))){
								double s = 0;
								if (i == membersNames.size()-1){
									s = newExpense.getAmount()-pseudoTotal;
								}
								else {
									s = getRound((newExpense.getAmount())/(double)participantsCount);
									pseudoTotal += s;
								}
								newExpense.addShare(s);
							}
							else {
								newExpense.addShare(0);
							}
						}
						double sum = 0;
						for (int i=0; i<newExpense.getShares().size(); i++){
							sum += newExpense.getShares().get(i);
        				}
						if (sum != newExpense.getAmount()){
							Toast.makeText(v.getContext(), "sum of shares doesn't match total amount", Toast.LENGTH_LONG).show();
							return;
						}
							
						//add the expense to the current group or update old expense!
						if (expenseType == NEW_EXPENSE){
							GroupContainer.getInstance().getCurrentGroup().addExpense(newExpense);
						}
						else if (expenseType == OLD_EXPENSE){
							GroupContainer.getInstance().getCurrentGroup().getExpenses().set(expenseIndex, newExpense);
						}
						Intent intent = new Intent(v.getContext(), GroupActivity.class);
						startActivity(intent);
					}
				
					else {
						Toast.makeText(v.getContext(), "Please select at least one participant", Toast.LENGTH_LONG).show();
					}
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
    	updateMembersNames();
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, membersNames);
	    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_member_item, membersNames);
    	participantsListView.setAdapter(adapter);
	    //participantsListView.setTextFilterEnabled(true);
	    participantsListView.setItemsCanFocus(false);
	    participantsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

	private void updateMembersNames(){
    	//TODO : change to method using "cursor" to be in the right group (especially when parent Activity is AllGroupsActivity)
    	Group g = GroupContainer.getInstance().getCurrentGroup();
    	membersNames = g.getMembersNames();
    }
	
	private void updateSpinner(){
		updateMembersNames();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, membersNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
	}
    
	private double getRound(double x){
		//arrondir à 2 chiffres après la virgule)
		double arr = Math.round(x*100)/(double)100;
		return arr;
	}
	
	private void preFill(){
		//set expense date
		mYear = oldExpense.getYear();
		mMonth = oldExpense.getMonth();
		mDay = oldExpense.getDay();
		updateDateDisplay();
		
		//set expense name
		editTextName.setText(oldExpense.getName());
		
		//set expense amount
		editTextAmount.setText(Double.toString(oldExpense.getAmount()));
		
		//set spinner
		int pos = adapter.getPosition(oldExpense.getPayer());
		spinner.setSelection(pos);
		
		//set participants
		for (int i=0; i<membersNames.size(); i++){
			if (oldExpense.getParticipantsNames().contains(participantsListView.getItemAtPosition(i).toString())){
				participantsListView.setItemChecked(i, true);
			}
			else {
				participantsListView.setItemChecked(i, false);
			}
		}
	}
}
