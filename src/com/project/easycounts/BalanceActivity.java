package com.project.easycounts;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        
        final Button transactionsButton = (Button) findViewById(R.id.transactions);
        globalBalance = (TextView) findViewById(R.id.globalBalance);
        expendituresBalance = (TextView) findViewById(R.id.expendituresBalance);
        
        //initialize group and membersNames
    	group = GroupContainer.getInstance().getCurrentGroup();
    	membersNames = group.getMembersNames();
    	System.out.println("oncreate balanceActivity : membersNames.size = " + membersNames.size());
        
        //DO NOT change order of those gets
        total = getGlobalBalance();
        realTotal = getExpendituresBalance();
        		
        setGlobalTextView();
        setExpendituresTextView();
        
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
    	    	System.out.println("getBlobBal dit : size shares = "+e.getShares().size());

    			if (i>=e.getShares().size()){
					Toast.makeText(getApplicationContext(), "index out of bounds in getGlobBalance()", Toast.LENGTH_LONG).show();
					return total;
    			}
    			total[i] += e.getShares().get(i);
    		}
    	}
    	for (int i=0; i<total.length; i++){
    		total[i] = getRound(total[i]);
    	}
    	return total;
    }
    
    public double[] getExpendituresBalance(){
    	realTotal = new double[membersNames.size()];
    	for (int i=0; i<realTotal.length; i++){
        	realTotal[i] = -total[i];
    	}
    	for (Expense e : group.getExpenses()){
    		System.out.println("payer: "+e.getPayer());
    		int p = group.getMemberPosition(e.getPayer());
    		System.out.println("payer position " +p);
			if (p<0){
				Toast.makeText(getApplicationContext(), "index out of bounds in getExpBalance()", Toast.LENGTH_LONG).show();
				return realTotal;
			}
			realTotal[p] += e.getAmount();
    	}
    	for (int i=0; i<realTotal.length; i++){
    		realTotal[i] = getRound(realTotal[i]);
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
    
	private double getRound(double x){
		//arrondir à 2 chiffres après la virgule)
		double arr = Math.round(x*100)/(double)100;
		return arr;
	}
    
}
