package com.project.easycounts;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TransactionsActivity extends Activity{
	private TextView transactionsView;
	private double[] trans;
	private List<String> membersNames;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactions);
        
        final Button returnButton = (Button) findViewById(R.id.goBack);
        transactionsView = (TextView) findViewById(R.id.transactions);
        
        //initialize membersNames
        Group group = GroupContainer.getInstance().getCurrentGroup();
        membersNames = group.getMembersNames();
        
        //TODO initialize trans with realTotal (getExtra)
        Bundle extras = getIntent().getExtras();
        trans = extras.getDoubleArray("amounts");
        
        setTransactionsView();
        
        returnButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), BalanceActivity.class);
				startActivity(intent);
			}
		});
    }
	
    private void setTransactionsView(){
    	String s = "";
    	while(!isRaz(trans)){
    		int indMax = getMaxInd(trans);
    		int indMin = getMinInd(trans);
    		double x = - trans[indMin];
    		s += membersNames.get(indMin) + " gives " + x + " to " + membersNames.get(indMax) + "\n";
    		trans[indMax] = trans[indMax] + trans[indMin];
    	    trans[indMin] = 0;
    	}
    	transactionsView.setText(s);
    }
    
    private boolean isRaz(double[] t){
    	//tests if each value in t is zero
    	if ((t != null)&&(t.length>0)){
    		for (int i=0; i<t.length; i++){
    			if (t[i] != 0.0){
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    public int getMaxInd(double[] t){
    	int ind = 0;
    	if ((t != null)&&(t.length>0)){
        	double aux = t[0];
        	for (int i=0; i<t.length; i++){
        		if (t[i]>aux){
        			aux = t[i];
        			ind = i;
        		}
        	}
    	}
    	return ind;
    }
    
    public int getMinInd(double[] t){
    	int ind = 0;
    	if ((t != null)&&(t.length>0)){
        	double aux = t[0];
        	for (int i=0; i<t.length; i++){
        		if (t[i]<aux){
        			aux = t[i];
        			ind = i;
        		}
        	}
    	}
    	return ind;
    }
}
