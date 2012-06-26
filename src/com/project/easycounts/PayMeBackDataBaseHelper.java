package com.project.easycounts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PayMeBackDataBaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "paymeback.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String GROUP_TABLE_NAME = "[groupTable]";
	private static final String MEMBER_TABLE_NAME = "[memberTable]";
	private static final String EXPENSE_TABLE_NAME = "[expenseTable]";
	private static final String PARTICIPANT_TABLE_NAME = "[participantTable]";
	
	private static final String COL_ID = "[_id]";
	private static final String COL_NAME = "[Name]";
	private static final String COL_ID_G = "[Group]";
	private static final String COL_ID_E = "[Expense]";
	private static final String COL_ID_P = "[Participant]";
	private static final String COL_PAYER= "[Payer]";
	private static final String COL_AMOUNT = "[Amount]";
	private static final String COL_DAY = "[Day]";
	private static final String COL_MONTH = "[Month]";
	private static final String COL_YEAR = "[Year]";
	private static final String COL_SHARE = "[Share]";

	private static final String GROUP_TABLE_CREATE = "CREATE TABLE " + GROUP_TABLE_NAME + " (" 
			+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COL_NAME + " TEXT NOT NULL);";
	
	private static final String MEMBER_TABLE_CREATE = "CREATE TABLE " + MEMBER_TABLE_NAME + " ("
			+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_NAME + " TEXT NOT NULL, "
			+ COL_ID_G + " TEXT NOT NULL);";
	
	private static final String EXPENSE_TABLE_CREATE = "CREATE TABLE " + EXPENSE_TABLE_NAME + " ("
			+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_NAME + " TEXT NOT NULL, "
			+ COL_ID_G + " INTEGER, "
			+ COL_PAYER + " TEXT NOT NULL, "
			+ COL_AMOUNT + " REAL, "
			+ COL_DAY + " INTEGER, "
			+ COL_MONTH + " INTEGER, "
			+ COL_YEAR + " INTEGER);";
	
	private static final String PARTICIPANT_TABLE_CREATE = "CREATE TABLE " + PARTICIPANT_TABLE_NAME + " ("
			+ COL_ID_P + " INTEGER, "
			+ COL_ID_E + " INTEGER, "
			+ COL_ID_G + " INTEGER, "
			+ COL_SHARE + " REAL);";
	
	
	public PayMeBackDataBaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("onCreate");
		db.execSQL(GROUP_TABLE_CREATE);
		db.execSQL(MEMBER_TABLE_CREATE);
		db.execSQL(EXPENSE_TABLE_CREATE);
		db.execSQL(PARTICIPANT_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("onUpgrade");
		db.execSQL("DROP TABLE IF EXISTS GROUP_TABLE_NAME");
		db.execSQL("DROP TABLE IF EXISTS MEMBER_TABLE_NAME");
		db.execSQL("DROP TABLE IF EXISTS EXPENSE_TABLE_NAME");
		db.execSQL("DROP TABLE IF EXISTS PARTICIPANT_TABLE_NAME");
		System.out.println("upgrading");
		onCreate(db);
	}
}
