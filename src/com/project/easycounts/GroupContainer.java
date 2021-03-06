package com.project.easycounts;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author tatianaperaldi
 *
 *Implements the design pattern "Singleton" 
 */
public final class GroupContainer {
	private static volatile GroupContainer instance = null;
	private List<Group> allGroups;
	private int cursor;
	
	private GroupContainer(){
		super();
		allGroups = new ArrayList<Group>();
		cursor = 0;		
	}
	
	/**
	 * Method that returns an instance of the class GroupContainer
	 */
	public final static GroupContainer getInstance(){
		if (GroupContainer.instance == null){
			synchronized(GroupContainer.class){
				if (GroupContainer.instance == null){
					GroupContainer.instance = new GroupContainer();
				}
			}
		}
		return GroupContainer.instance;
	}
	
	public int getCursor(){
		return cursor;
	}
	
	public void setCursor(int i){
		cursor = i;
	}
	public void setCursorAtEnd(){
		cursor = allGroups.size()-1;
	}
	
	public void addGroupToContainer(Group newGroup){
		allGroups.add(newGroup);
	}
	
	public List<Group> getAllGroups(){
		return allGroups;
	}
	public void setAllGroups(List<Group> groups){
		this.allGroups = groups;
	}
	
	public List<String> getGroupsNames(){
		List<String> names = new ArrayList<String>();
		for (Group g: allGroups){
			names.add(g.getName());
		}
		return names;
	}
	
	public int getSize(){
		return allGroups.size();
	}
	
	public Group getGroupAt(int i){
		return allGroups.get(i);
	}
	
	public Group getCurrentGroup(){
		return allGroups.get(cursor);
	}
	
	public int getGroupPosition(String name){
		return getGroupsNames().indexOf(name);
	}
	
	public Group getLastGroup(){
		return allGroups.get(allGroups.size()-1);
	}
	
	public Group removeLastGroup(){
		Group g = this.getLastGroup();
		allGroups.remove(this.getSize()-1);
		return g;
	}
	
	public double getTotal(String groupName){
		double x = 0;
		int i = getGroupPosition(groupName);
		Group group = getGroupAt(i);
		for (Expense e: group.getExpenses()){
			x += e.getAmount();
		}
		return getRound(x);
	}
	
	private double getRound(double x){
		//arrondir � 2 chiffres apr�s la virgule)
		double arr = Math.round(x*100)/(double)100;
		return arr;
	}
}
