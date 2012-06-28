package com.project.easycounts;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionsActivity extends Activity{
	private TextView transactionsView;
	private double[] trans;
	private List<String> membersNames;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactions);
        
        transactionsView = (TextView) findViewById(R.id.transactions);
        
        //initialize membersNames
        Group group = GroupContainer.getInstance().getCurrentGroup();
        membersNames = group.getMembersNames();
        
        //TODO initialize trans with realTotal (getExtra)
        Bundle extras = getIntent().getExtras();
        trans = extras.getDoubleArray("amounts");
        
        setTransactionsView();

    }
	
    private void setTransactionsView(){
    	String s = "";
    	int i=0;
    	while((!isRaz(trans))&&(i<=membersNames.size())){
    		int indMax = getMaxInd(trans);
    		int indMin = getMinInd(trans);
    		double x =0.0;
    		if (trans[indMin] < 0){
        		x = - trans[indMin];
    		}
    		else{
    			x = trans[indMin];
    		}
    		System.out.println("min: "+x);

    		s += membersNames.get(indMin) + " gives " + x + "Û to " + membersNames.get(indMax) + "\n";
    		trans[indMax] = getRound(trans[indMax] + trans[indMin]);
    	    trans[indMin] = 0.0;
    	    i++;
    	}
    	if (i > membersNames.size()){
    		Toast.makeText(getApplicationContext(), "infinite loop, pb in shares", Toast.LENGTH_LONG).show();
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
        		if ((t[i]>aux) &&(t[i]!=0.0)){
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
        		if ((t[i]<aux)&&(t[i]!=0.0)){
        			aux = t[i];
        			ind = i;
        		}
        	}
    	}
    	return ind;
    }
    
	private double getRound(double x){
		//arrondir ˆ 2 chiffres aprs la virgule)
		double arr = Math.round(x*100)/(double)100;
		return arr;
	}
}
