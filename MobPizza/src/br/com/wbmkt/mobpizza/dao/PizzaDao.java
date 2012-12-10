package br.com.wbmkt.mobpizza.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.wbmkt.mobpizza.modelo.Pizza;

public class PizzaDao extends SQLiteOpenHelper {
	
	private static final int VERSAO = 1;
	private static final String TABELA = "pizza";
	private static final String[] COLUMNS = new String[] {"id", "nome", "valor"};

	public PizzaDao(Context context) {
		super(context, TABELA, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABELA + " (" +
			"id integer primary key autoincrement, " +
			"nome text unique not null, " +
			"valor real);";
		
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "drop table if exists " + TABELA;
		db.execSQL(sql);
		onCreate(db);
	}
	
	public void inserir(Pizza pizza) {
		ContentValues values = toValues(pizza);
		getWritableDatabase().insert(TABELA, null, values);
		close();
	}
	
	public List<Pizza> listarTodos() {
		
		String selection = null;
		String[] selectionArgs = null;
		String groupBy = null;
		String having = null;
		String orderBy = null;
		
		Cursor cursor = getWritableDatabase().query(
			TABELA, 
			COLUMNS, 
			selection, 
			selectionArgs, 
			groupBy, 
			having, 
			orderBy
		);
		
		List<Pizza> todasAsPizzas = new ArrayList<Pizza>();
		
		try {
			cursor.moveToFirst();
			
			while (cursor.moveToNext()) {
				todasAsPizzas.add(new Pizza(
					cursor.getLong(0),
					cursor.getString(1),
					cursor.getDouble(2)));
			}
		}
		finally {
			cursor.close();
		}
		
		close();
		return todasAsPizzas;
	}
	
	public void deletar(Pizza pizza) {
		String whereClause = "id = ?";
		String[] whereArgs = new String[] {pizza.getId().toString()};
		getWritableDatabase().delete(TABELA, whereClause, whereArgs);
		close();
	}
	
	public void alterar(Pizza pizza) {
		ContentValues values = toValues(pizza);
		String whereClause = "id = ?";
		String[] whereArgs = new String[] {pizza.getId().toString()};
		getWritableDatabase().update(TABELA, values, whereClause, whereArgs);
		close();
	}
	
	private ContentValues toValues(Pizza pizza) {
		ContentValues values = new ContentValues();
		
		values.put("nome", pizza.getNome());
		values.put("valor", String.valueOf(pizza.getValor()));
		
		return values;
	}
}
