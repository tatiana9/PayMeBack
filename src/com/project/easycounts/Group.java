package com.project.easycounts;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private String name;
	private List<Member> members;
	private List<Expense> expenses;
	
	public Group(){
		members = new ArrayList<Member>();
		expenses = new ArrayList<Expense>();
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public List<Member> getMembers(){
		return members;
	}
	public void setMembers(List<Member> members){
		this.members = members;
	}
	
	public List<Expense> getExpenses(){
		return expenses;
	}
	public void setExpenses(List<Expense> expenses){
		this.expenses = expenses;
	}
	
	public void addMember(String name){
		Member newMember = new Member(name);
		this.members.add(newMember);
	}
	public void addMember(Member member){
		this.members.add(member);
	}
	
	
	public void addExpense(Expense newExpense){
		this.expenses.add(newExpense);
	}
}
