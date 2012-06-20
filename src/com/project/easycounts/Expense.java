package com.project.easycounts;

import java.util.ArrayList;
import java.util.List;

public class Expense {
	private String name;
	private String payer;
	private double amount;
	private List<Member> participants;
	private List<Integer> shares;
	
	public Expense(){
		participants = new ArrayList<Member>();
		shares = new ArrayList<Integer>();
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	/*
	public Member getPayer(){
		return payer;
	}
	public void setPayer(Member payer){
		this.payer = payer;
	}
	*/
	public String getPayer(){
		return payer;
	}
	public void setPayer(String payer){
		this.payer = payer;
	}
	
	
	
	public double getAmount(){
		return amount;
	}
	public void setAmount(double amount){
		this.amount = amount;
	}
	
	public List<Member> getParticipants(){
		return participants;
	}
	public void setParticipants(List<Member> participants){
		this.participants = participants;
	}
	
	public List<Integer> getShares(){
		return shares;
	}
	public void setShares(List<Integer> shares){
		this.shares = shares;
	}
	
}
