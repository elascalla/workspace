package br.com.wbmkt.mobpizza;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import br.com.wbmkt.mobpizza.adapter.PizzaAdapter;
import br.com.wbmkt.mobpizza.dao.PizzaDao;
import br.com.wbmkt.mobpizza.modelo.Pizza;
import br.com.wbmkt.mobpizza.servico.Sincronismo;

public class ListaPizzas extends Activity {
	private PizzaDao pizzaDao;
	private ListView listaPizzas;
	private Pizza pizzaSelecionada;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);
        
        pizzaDao = new PizzaDao(this);
        listaPizzas = (ListView)findViewById(R.id.listaPizzas);
        listaPizzas.setClickable(true);
        
        listaPizzas.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
				pizzaSelecionada = (Pizza)adapter.getItemAtPosition(posicao);
				Intent edicao = new Intent(ListaPizzas.this, Formulario.class);
				edicao.putExtra("pizzaSelecionada", pizzaSelecionada);
				startActivity(edicao);
			}
		});
        
        listaPizzas.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
				pizzaSelecionada = (Pizza)adapter.getItemAtPosition(posicao);
				registerForContextMenu(listaPizzas);
				return false;
			}
		});
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		carregarLista();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		createMenuItem(menu, 0, "Novo", R.drawable.novo);
		createMenuItem(menu, 1, "Sincronizar", R.drawable.planeta);
		createMenuItem(menu, 2, "Mapa", R.drawable.mapa);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final int NOVO = 0;
		final int SINCRONIZAR = 1;
		final int MAPA = 2;
		
		switch (item.getItemId()) {
			case NOVO:
				novo();
				break;
			case SINCRONIZAR:
				sincronizar();
				break;
			case MAPA:
				break;
			default:
				break;
		}
		
		return false;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		menu.add(0, 1, 0, "Deletar");
		menu.add(0, 2, 0, "Enviar E-mail");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		final int DELETAR = 0;
		final int EMAIL = 1;
		
		switch (item.getItemId()) {
			case DELETAR:
				deletar();
				break;
			case EMAIL:
				email();
				break;
			default:
				break;
		}
		
		return super.onContextItemSelected(item);
	}
	
	private void createMenuItem(Menu menu, int position, String name, int iconId) {
		MenuItem item = menu.add(0, position, 0, name);
		item.setIcon(iconId);
	}
	
	private void carregarLista() {
		List<Pizza> pizzas = pizzaDao.listarTodos();
		PizzaAdapter adapter = new PizzaAdapter(this, pizzas);
        listaPizzas.setAdapter(adapter);
	}
	
	
	private void novo() {
		startActivity(new Intent(this, Formulario.class));
	}
	
	private void sincronizar() {
		new Sincronismo(this).sincronizar();
	}
	
	private void deletar() {
		pizzaDao.deletar(pizzaSelecionada);
		carregarLista();
	}
	
	private void email() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"caelum@caelum.com.br"});
		startActivity(intent);
	}
}