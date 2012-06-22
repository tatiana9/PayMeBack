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
	public List<Group> allGroups;
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
	
	public int getSize(){
		return allGroups.size();
	}
	
	public Group getGroupAt(int i){
		return allGroups.get(i);
	}
	
	public Group getCurrentGroup(){
		return allGroups.get(cursor);
	}
	
	public Group getLastGroup(){
		return allGroups.get(allGroups.size()-1);
	}
	
	public Group removeLastGroup(){
		Group g = this.getLastGroup();
		allGroups.remove(this.getSize()-1);
		return g;
	}
}
