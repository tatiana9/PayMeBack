package com.project.easycounts;

import java.util.List;

public class Expense {
	private String name;
	private Member payer;
	private int amount;
	private List<Member> participants;
	private List<Integer> shares;
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public Member getPayer(){
		return payer;
	}
	public void setPayer(Member payer){
		this.payer = payer;
	}
	
	public int getAmount(){
		return amount;
	}
	public void setAmount(int amount){
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
