package com.wbmkt.mobpizza.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.wbmkt.helper.MySQLiteHelper;
import com.wbmkt.mobpizza.vo.Pizza;

public class PizzasDataSource {
	
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_PIZZA};
	
	public PizzasDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Pizza createPizza(String pizza) {
		
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_PIZZA, pizza);
	    
	    long insertId = database.insert(MySQLiteHelper.TABLE_PIZZA, null,
	        values);
	    
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_PIZZA,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    
	    cursor.moveToFirst();
	    
	    Pizza newPizza = cursorToPizza(cursor);
	    cursor.close();
	    
	    return newPizza;
	}
	
	public void deletePizza(Pizza pizza) {
	    
		long id = pizza.getId();
	    System.out.println("Pizza deletada com id: " + id);
	    database.delete(MySQLiteHelper.TABLE_PIZZA, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	}

	public List<Pizza> getAllPizzas() {
		  
	    List<Pizza> pizzas = new ArrayList<Pizza>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_PIZZA,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    
	    while (!cursor.isAfterLast()) {
	      Pizza pizza = cursorToPizza(cursor);
	      pizzas.add(pizza);
	      cursor.moveToNext();
	    }
	    
	    cursor.close();
	    return pizzas;
	}

	private Pizza cursorToPizza(Cursor cursor) {
	    
		Pizza pizza = new Pizza();
	    pizza.setId(cursor.getLong(0));
	    pizza.setPizza(cursor.getString(1));
	    
	    return pizza;
	}
}
