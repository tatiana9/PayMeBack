package com.project.easycounts;

import java.util.List;

public class Group {
	private String name;
	private List<Member> members;
	private List<Expense> expenses;
	
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
}
