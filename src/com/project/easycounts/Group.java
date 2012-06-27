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
	public List<String> getExpensesNames(){
		List<String> names = new ArrayList<String>();
		for (Expense e : expenses){
			names.add(e.getName());
		}
		return names;
	}
	public int getExpensePosition(String name){
		List<String> names = getExpensesNames();
		return names.indexOf(name);
	}
	
	public void addMember(String name){
		Member newMember = new Member(name);
		this.members.add(newMember);
	}
	public void addMember(Member member){
		this.members.add(member);
	}
	public List<String> getMembersNames(){
		List<String> names = new ArrayList<String>();
		for (Member m : members){
			names.add(m.getName());
		}
		return names;
	}
	public int getMemberPosition(String name){
		List<String> names = getMembersNames();
		return names.indexOf(name);
	}
	public void deleteMember(String name){
		members.remove(getMembersNames().indexOf(name));
	}
	
	public void addExpense(Expense newExpense){
		this.expenses.add(newExpense);
	}
	
	public String toString(){
		String s = "";
		s += "Group: "+name+"\n";
		s += "Members: <";
		for (Member m: members){
			s += m.getName()+" / ";
		}
		s += "> \n Expenses: <";
		for (Expense e: expenses){
			s += e.getName() + ", " + e.getAmount() + ", " + e.getPayer() + " / ";
		}
		s+= ">";
		return s;
	}
}
