package br.com.wbmkt.mobpizza;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.wbmkt.mobpizza.dao.PizzaDao;
import br.com.wbmkt.mobpizza.modelo.Pizza;

public class Formulario extends Activity {
	
	private PizzaDao pizzaDao;
	private Long idPizza;
	
	private EditText nome;
	private EditText valor;
	private Button botao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		nome = (EditText)findViewById(R.id.nome);
		valor = (EditText)findViewById(R.id.valor);
		botao = (Button)findViewById(R.id.botao);
		
		pizzaDao = new PizzaDao(this);
		Pizza pizza = (Pizza)getIntent().getSerializableExtra("pizzaSelecionada");
		
		if (pizza != null) {
			idPizza = pizza.getId();
			botao.setText("Alterar");
			nome.setText(pizza.getNome());
			valor.setText(String.valueOf(pizza.getValor()));
		}
		
		botao.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				boolean novaPizza = (idPizza == null);
				
				Pizza pizza = new Pizza(
					idPizza,
					nome.getText().toString(),
					Double.parseDouble(valor.getText().toString())
				);
				
				if (novaPizza) {
					pizzaDao.inserir(pizza);
				} else {
					pizzaDao.alterar(pizza);
				}
				
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
