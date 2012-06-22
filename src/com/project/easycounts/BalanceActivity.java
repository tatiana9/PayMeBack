package com.project.easycounts;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BalanceActivity extends Activity {
	private TextView globalBalance;
	private TextView expendituresBalance;
	private double[] total;
	private double[] realTotal;
	private List<String> membersNames;
	private Group group;
	
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance);
        
        final Button returnButton = (Button) findViewById(R.id.goBack);
        final Button transactionsButton = (Button) findViewById(R.id.transactions);
        globalBalance = (TextView) findViewById(R.id.globalBalance);
        expendituresBalance = (TextView) findViewById(R.id.expendituresBalance);
        
        //initialize group and membersNames
    	group = GroupContainer.getInstance().getCurrentGroup();
    	membersNames = group.getMembersNames();
        
        //DO NOT change order of those gets
        total = getGlobalBalance();
        realTotal = getExpendituresBalance();
        		
        setGlobalTextView();
        setExpendituresTextView();
        
        returnButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), GroupActivity.class);
				startActivity(intent);
			}
		});
        
        transactionsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), TransactionsActivity.class);
				intent.putExtra("amounts", realTotal);
				startActivity(intent);
			}
		});
    }
    
    public double[] getGlobalBalance(){
    	total = new double[membersNames.size()];
    	for (Expense e : group.getExpenses()){
    		for (int i=0; i<membersNames.size(); i++){
    			total[i] += e.getShares().get(i);
    		}
    	}
    	return total;
    }
    
    public double[] getExpendituresBalance(){
    	Group group = GroupContainer.getInstance().getCurrentGroup();
    	realTotal = new double[membersNames.size()];
    	for (int i=0; i<realTotal.length; i++){
        	realTotal[i] = -total[i];
    	}
    	for (Expense e : group.getExpenses()){
    		int p = group.getMemberPosition(e.getPayer());
    		realTotal[p] += e.getAmount();
    	}
    	return realTotal;
    }
    
    private void setGlobalTextView(){
    	String s = "";
    	for (int i=0; i<total.length; i++){
    		System.out.println(membersNames.get(i)+" pays "+total[i]);
    		s += membersNames.get(i) + ": " + total[i] + " euros" + "\n";
    	}
    	globalBalance.setText(s);
    }
    
    private void setExpendituresTextView(){
    	String s = "";
    	for (int i=0; i<total.length; i++){
    		if (realTotal[i] > 0){
        		s += membersNames.get(i) + " lent " + realTotal[i] + " euros" + "\n";
    		}
    		else{
    			double x = - realTotal[i];
        		s += membersNames.get(i) + " owes " + x + " euros" + "\n";
    		}
    	}
    	expendituresBalance.setText(s);
    }
    
}
