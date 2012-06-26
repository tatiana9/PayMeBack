package com.project.easycounts;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PayMeBackBDD {
	private static final String DATABASE_NAME = "paymeback.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String GROUP_TABLE_NAME = "[groupTable]";
	private static final String MEMBER_TABLE_NAME = "[memberTable]";
	private static final String EXPENSE_TABLE_NAME = "[expenseTable]";
	private static final String PARTICIPANT_TABLE_NAME = "[participantTable]";
	
	private static final String COL_ID = "[_id]";
	private static final int NUM_COL_ID = 0;
	
	private static final String COL_NAME = "[Name]";
	private static final int NUM_COL_NAME = 1;

	private static final String COL_ID_G = "[Group]";
	private static final int NUM_COL_ID_G = 2;

	private static final String COL_ID_E = "[Expense]";
	private static final int NUM_COL_ID_E = 1;

	private static final String COL_ID_P = "[Participant]";
	private static final int NUM_COL_ID_P = 0;

	private static final String COL_PAYER= "[Payer]";
	private static final int NUM_COL_PAYER = 3;

	private static final String COL_AMOUNT = "[Amount]";
	private static final int NUM_COL_AMOUNT = 4;

	private static final String COL_DAY = "[Day]";
	private static final int NUM_COL_DAY = 5;

	private static final String COL_MONTH = "[Month]";
	private static final int NUM_COL_MONTH = 6;

	private static final String COL_YEAR = "[Year]";
	private static final int NUM_COL_YEAR = 7;

	private static final String COL_SHARE = "[Share]";
	private static final int NUM_COL_SHARE = 3;


	private SQLiteDatabase bdd;
	private PayMeBackDataBaseHelper dbHelper;
	
	public PayMeBackBDD(Context context){
		dbHelper = new PayMeBackDataBaseHelper(context);
	}
	
	public void open() throws SQLException{
		bdd = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		bdd.close();
	}
	
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	//for group table
	private ContentValues createContentValues(String groupName){
		ContentValues values = new ContentValues();
		values.put(COL_NAME, groupName);
		return values;
	}
	
	//for member table
	private ContentValues createContentValues(String memberName, long groupID){
		ContentValues values = new ContentValues();
		values.put(COL_NAME, memberName);
		values.put(COL_ID_G, groupID);
		return values;
	}
	
	//for expense table
	private ContentValues createContentValues(String expenseName, long groupID, String payer, double amount, int day, int month, int year){
		ContentValues values = new ContentValues();
		values.put(COL_NAME, expenseName);
		values.put(COL_ID_G, groupID);
		values.put(COL_PAYER, payer);
		values.put(COL_AMOUNT, amount);
		values.put(COL_DAY, day);
		values.put(COL_MONTH, month);
		values.put(COL_YEAR, year);
		return values;
	}
	
	//for participant table
	private ContentValues createContentValues(long participantID, long expenseID, long groupID, double share){
		ContentValues values = new ContentValues();
		values.put(COL_ID_P, participantID);
		values.put(COL_ID_E, expenseID);
		values.put(COL_ID_G, groupID);
		values.put(COL_SHARE, share);
		return values;
	}
	
	public long insertGroup(String groupName){
		ContentValues values = createContentValues(groupName);
		return bdd.insert(GROUP_TABLE_NAME, null, values);
	}
	
	public long insertMember(String memberName, long groupID){
		ContentValues values = createContentValues(memberName, groupID);
		return bdd.insert(MEMBER_TABLE_NAME, null, values);		
	}
	public long insertExpense(String expenseName, long groupID, String payer, double amount, int day, int month, int year){
		ContentValues values = createContentValues(expenseName, groupID, payer, amount, day, month, year);
		return bdd.insert(EXPENSE_TABLE_NAME, null, values);		
	}
	
	//NB: participantID would here be the position of participant in members table.
	public long insertParticipant(long participantID, long expenseID, long groupID, double share){
		ContentValues values = createContentValues(participantID, expenseID, groupID, share);
		return bdd.insert(PARTICIPANT_TABLE_NAME, null, values);		
	}
	
	
	public long updateGroup(long id, String groupName){
		ContentValues values = createContentValues(groupName);
		return bdd.update(GROUP_TABLE_NAME, values, COL_ID + " = " + id, null);
	}
	
	public long updateMember(long id, String memberName){
		ContentValues values = createContentValues(memberName);
		return bdd.update(MEMBER_TABLE_NAME, values, COL_ID + " = " + id, null);
	}
	public long updateExpense(long id, String expenseName, long groupID, String payer, double amount, int day, int month, int year){
		ContentValues values = createContentValues(expenseName, groupID, payer, amount, day, month, year);
		return bdd.update(EXPENSE_TABLE_NAME, values, COL_ID + " = " + id, null);
	}
	/*
	public long updateParticipant(int participantID, int expenseID, int groupID, double share){
		ContentValues values = createContentValues(participantID, expenseID, groupID, share);
		return bdd.insert(EXPENSE_TABLE_NAME, null, values);		
	}
	*/
	
	public int removeGroupWithId(long id){
		return bdd.delete(GROUP_TABLE_NAME, COL_ID + " = " + id, null);
	}
	public int removeMemberWithId(long id){
		return bdd.delete(MEMBER_TABLE_NAME, COL_ID + " = " + id, null);
	}
	public int removeExpenseWithId(long id){
		return bdd.delete(EXPENSE_TABLE_NAME, COL_ID + " = " + id, null);
	}
	public int removeParticipants(long expenseID, long groupID){
		return bdd.delete(PARTICIPANT_TABLE_NAME, COL_ID_E + " = " + expenseID + " AND " + COL_ID_G + " = " + groupID, null);
	}
	
/*	
	public List<Group> cursorToListGroup(Cursor c){
		List<Group> allGroups = new ArrayList<Group>();
		if (c.getCount() == 0) return allGroups;
		c.moveToFirst();
		Group firstgroup = new Group();
		firstgroup.setName(c.getString(NUM_COL_NAME));
		allGroups.add(firstgroup);
		while (c.moveToNext()){
			Group group = new Group();
			group.setName(c.getString(NUM_COL_NAME));
			allGroups.add(group);
		}
		c.close();
		return allGroups;
	}
	
	public List<Member> cursorToListMembers(Cursor c){
		List<Member> members = new ArrayList<Member>();
		if (c.getCount() == 0) return members;
		c.moveToFirst();
		Member firstMember = new Member();
		firstMember.setName(c.getString(NUM_COL_NAME));
		members.add(firstMember);
		while (c.moveToNext()){
			Member member = new Member();
			member.setName(c.getString(NUM_COL_NAME));
			members.add(member);
		}
		return members;
	}
	
	public List<Expense> cursorToListExpenses(Cursor c){
		List<Expense> expenses = new ArrayList<Expense>();
		if (c.getCount() == 0) return expenses;
		c.moveToFirst();
		Expense firstexpense = new Expense();
		firstexpense.setName(c.getString(NUM_COL_NAME));
		firstexpense.setDate(c.getInt(NUM_COL_YEAR), c.getInt(NUM_COL_MONTH), c.getInt(NUM_COL_DAY));
		firstexpense.setPayer(c.getString(NUM_COL_PAYER));
		firstexpense.setAmount(c.getDouble(NUM_COL_AMOUNT));
		expenses.add(firstexpense);
		while (c.moveToNext()){
			Expense expense = new Expense();
			expense.setName(c.getString(NUM_COL_NAME));
			expense.setDate(c.getInt(NUM_COL_YEAR), c.getInt(NUM_COL_MONTH), c.getInt(NUM_COL_DAY));
			expense.setPayer(c.getString(NUM_COL_PAYER));
			expense.setAmount(c.getDouble(NUM_COL_AMOUNT));
			expenses.add(expense);
		}
		return expenses;
	}
	
	//to load existing data into the model GroupContainer
	public void loadBDD(){
		//remove all existing data in model
		GroupContainer.getInstance().getAllGroups().clear();
		
		//copy whole database in model
		//goups
		Cursor c_groups = bdd.query(GROUP_TABLE_NAME, new String [] {COL_ID, COL_NAME}, null, null, null, null, null);
		List<Group> allGroups = cursorToListGroup(c_groups);
		
		//members
		int id = 0;
		for (int i = 0; i<allGroups.size(); i++){
			id = i+1;
			Cursor c_members = bdd.query(MEMBER_TABLE_NAME, new String [] {COL_ID, COL_NAME, COL_ID_G}, COL_ID_G + " = " + id, null, null, null, null);
			List<Member> members = cursorToListMembers(c_members);
			allGroups.get(i).setMembers(members);
		}
		
		//expenses
		int id_g = 0;
		for (int i = 0; i<allGroups.size(); i++){
			id_g = i+1;
			Cursor c_expenses = bdd.query(EXPENSE_TABLE_NAME, 
					new String [] {COL_ID, COL_NAME, COL_ID_G, COL_PAYER, COL_AMOUNT, COL_DAY, COL_MONTH, COL_YEAR},
					COL_ID_G + " = " + id_g, null, null, null, null);
			List<Expense> expenses = cursorToListExpenses(c_expenses);
			allGroups.get(i).setExpenses(expenses);
		}
		
		//participants
	}
	
	*/
	
}
