package com.project.easycounts;

import java.util.ArrayList;
import java.util.List;

public class Expense {
	private String name;
	private String payer;
	private double amount;
	private int year;
	private int month;
	private int day;
	private List<Member> participants;
	//shares is the list of the amounts the members are supposed to pay for the expense (0 if they are not participants)
	//shares.size() = members.size() !!
	private List<Double> shares;
	
	public Expense(){
		participants = new ArrayList<Member>();
		shares = new ArrayList<Double>();
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
	
	public int getYear(){
		return year;
	}
	public void setYear(int year){
		this.year = year;
	}
	public int getMonth(){
		return month;
	}
	public void setMonth(int month){
		this.month = month;
	}	
	public int getDay(){
		return day;
	}
	public void setDay(int day){
		this.day = day;
	}
	
	public void setDate (int year, int month, int day){
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public List<Member> getParticipants(){
		return participants;
	}
	public List<String> getParticipantsNames(){
		List<String> names = new ArrayList<String>();
		for (Member m : participants){
			names.add(m.getName());
		}
		return names;
	}
	public void setParticipants(List<Member> participants){
		this.participants = participants;
	}
	public void addParticipant(String name){
		this.participants.add(new Member(name));
	}
	
	public List<Double> getShares(){
		return shares;
	}
	public void setShares(List<Double> shares){
		this.shares = shares;
	}
	public void addShare(double s){
		shares.add(s);
	}

	public String toString(){
		String s = "";
		s += name + ", " + amount + ", " + payer;
		return s;
	}
}
