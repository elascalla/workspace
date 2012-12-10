package br.com.wbmkt.mobpizza.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.wbmkt.mobpizza.R;
import br.com.wbmkt.mobpizza.modelo.Pizza;

public class PizzaAdapter extends BaseAdapter {
	private Context context;
	private List<Pizza> pizzas;
	
	public PizzaAdapter(Context context, List<Pizza> pizzas) {
		this.context = context;
		this.pizzas = pizzas;
	}
	
	public int getCount() {
		return pizzas.size();
	}

	public long getItemId(int posicao) {
		return pizzas.get(posicao).getId();
	}
	
	public Pizza getItem(int posicao) {
		return pizzas.get(posicao);
	}
	
	public View getView(int posicao, View convertView, ViewGroup parent) {
		
		Pizza pizza = pizzas.get(posicao);

		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.pizzaadapter, null);

		LinearLayout fundo = (LinearLayout) view.findViewById(R.id.fundo);
		
		if (posicao % 2 == 0) {
			fundo.setBackgroundColor(0xFF606060);
		} else {
			fundo.setBackgroundColor(0xFFB0B0B0);
		}

		TextView nome = (TextView) view.findViewById(R.id.nome);
		nome.setText(pizza.toString());
		
		return view;
	}	
}
