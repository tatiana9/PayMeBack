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
	
	public long updateMembersList(List<String> membersNames, long groupID){
		removeAllMembers(groupID);
		long lastID = 0;
		for (String name: membersNames){
			lastID = insertMember(name, groupID);
		}
		return lastID;
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
	public int removeAllMembers(long groupID){
		return bdd.delete(MEMBER_TABLE_NAME, COL_ID_G + " = " + groupID, null);
	}
	public int removeExpenseWithId(long id){
		return bdd.delete(EXPENSE_TABLE_NAME, COL_ID + " = " + id, null);
	}
	public int removeParticipants(long expenseID, long groupID){
		return bdd.delete(PARTICIPANT_TABLE_NAME, COL_ID_E + " = " + expenseID + " AND " + COL_ID_G + " = " + groupID, null);
	}
	
	
	public List<Group> cursorToListGroup(Cursor c){
		List<Group> allGroups = new ArrayList<Group>();
		System.out.println("nb de groupes: "+c.getCount());

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
		System.out.println("nb de membres: "+c.getCount());

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
		System.out.println("nb de depenses: "+c.getCount());

		if (c.getCount() == 0) return expenses;
		c.moveToFirst();
		Expense firstexpense = new Expense();
		firstexpense.setName(c.getString(NUM_COL_NAME));
		System.out.println("name: "+firstexpense.getName());
		firstexpense.setDate(c.getInt(NUM_COL_YEAR), c.getInt(NUM_COL_MONTH), c.getInt(NUM_COL_DAY));
		firstexpense.setPayer(c.getString(NUM_COL_PAYER));
		firstexpense.setAmount(c.getDouble(NUM_COL_AMOUNT));
		System.out.println("amount "+firstexpense.getAmount());

		expenses.add(firstexpense);
		while (c.moveToNext()){
			Expense expense = new Expense();
			expense.setName(c.getString(NUM_COL_NAME));
			System.out.println("name: "+expense.getName());

			expense.setDate(c.getInt(NUM_COL_YEAR), c.getInt(NUM_COL_MONTH), c.getInt(NUM_COL_DAY));
			expense.setPayer(c.getString(NUM_COL_PAYER));
			expense.setAmount(c.getDouble(NUM_COL_AMOUNT));
			System.out.println("amount: "+expense.getAmount());

			expenses.add(expense);
		}
		System.out.println("liste des depenses retournees: ");
		for (Expense e: expenses){
			System.out.println(e.toString());
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
		System.out.println("getting members");
		int id_g = 0;
		for (int i = 0; i<allGroups.size(); i++){
			id_g = i+1;
			System.out.println("query for groupID "+id_g);
			Cursor c_members = bdd.query(MEMBER_TABLE_NAME, new String [] {COL_ID, COL_NAME, COL_ID_G}, COL_ID_G + " = " + id_g, null, null, null, null);
			List<Member> members = cursorToListMembers(c_members);
			allGroups.get(i).setMembers(members);
		}
		
		//expenses
		System.out.println("getting expenses ");

		id_g = 0;
		for (int i = 0; i<allGroups.size(); i++){
			id_g = i+1;
			Cursor c_expenses = bdd.query(EXPENSE_TABLE_NAME, 
					new String [] {COL_ID, COL_NAME, COL_ID_G, COL_PAYER, COL_AMOUNT, COL_DAY, COL_MONTH, COL_YEAR},
					COL_ID_G + " = " + id_g, null, null, null, null);
			List<Expense> expenses = cursorToListExpenses(c_expenses);
			allGroups.get(i).setExpenses(expenses);
		}
		
		//participants
		System.out.println("getting participants ");

		id_g = 1;
		int id_e = 1;
		for (Group g: allGroups){
			List<Expense> expenses = g.getExpenses();
			id_e = 1;
			for (Expense e: expenses){
				List<Member> participants = new ArrayList<Member>();
				List<Double> shares = new ArrayList<Double>();
				for (int i=0; i<g.getMembers().size(); i++){
					shares.add(0.0);
				}
				System.out.println(" taille shares avant plein : "+shares.size());
				
				Cursor c = bdd.query(PARTICIPANT_TABLE_NAME,
						new String [] {COL_ID_P, COL_ID_E, COL_ID_G, COL_SHARE},
						COL_ID_E + " = " + id_e + " AND " + COL_ID_G + " = " + id_g,
						null, null, null, null);
				
				int participantsCount = c.getCount();
				
				System.out.println("nb de participants : "+participantsCount);
				
				if (!(participantsCount == 0)){
					c.moveToFirst();
					int firstId = c.getInt(NUM_COL_ID_P);
					System.out.println("participant id: "+ firstId);
					Member firstP = g.getMembers().get(firstId-1);
					System.out.println(firstP.getName());
					participants.add(firstP);
					//shares.add(firstId-1, c.getDouble(NUM_COL_SHARE));
					shares.set(firstId-1, c.getDouble(NUM_COL_SHARE));
					
					while (c.moveToNext()){
						System.out.println("un tour dans while");
						int id = c.getInt(NUM_COL_ID_P);
						System.out.println("participant id: "+ id);
						Member p = g.getMembers().get(id-1);
						System.out.println(p.getName());
						participants.add(p);
						shares.set(id-1, c.getDouble(NUM_COL_SHARE));
						System.out.println("Fini le tour");
					}
				}
				e.setParticipants(participants);
				e.setShares(shares);
				System.out.println("shares size : "+shares.size());
				
				try{
					int ig = id_g - 1;
					int ie = id_e - 1;
					System.out.println("id_g-1 : "+ig+", id_e-1 : "+ie);
					allGroups.get(ig).getExpenses().set(ie, e);
				} catch (IndexOutOfBoundsException exc){
					System.err.println(exc);
				}
				id_e ++;
			}
			id_g ++;
		}
		
		GroupContainer.getInstance().setAllGroups(allGroups);
		System.out.println("bdd loaded in model!!");
	}
	
	
	
}
