package com.wbmkt.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_PIZZA = "TBLPIZZA";
	public static final String COLUMN_ID = "_ID";
	public static final String COLUMN_PIZZA = "PIZZA";
	
	private static final String DATABASE_NAME = "TBLPIZZA.db";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table " +
			TABLE_PIZZA + "(" + COLUMN_ID +
			" integer primary key autoincrement, " + COLUMN_PIZZA +
			" text not null);";
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase dataBase) {
		dataBase.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase dataBase, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(), "Atualizada a vers‹o" + oldVersion +"para " +
				newVersion);
		dataBase.execSQL("DROP TABLE IF EXISTS " + TABLE_PIZZA);
		onCreate(dataBase);
	}

}
