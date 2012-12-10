package br.com.wbmkt.mobpizza.servico;

import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONStringer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import br.com.wbmkt.mobpizza.dao.PizzaDao;
import br.com.wbmkt.mobpizza.modelo.Pizza;

public class Sincronismo {
	private String endereco = "http://www.caelum.com.br/mobile?dado=";
	private Context context;
	private ProgressDialog progress;
	private Toast aviso;
	
	public Sincronismo(Context context) {
		this.context = context;
	}
	
	@SuppressLint("ShowToast")
	public void sincronizar() {
		progress = ProgressDialog.show(context, "Aguarde...", "Enviando dados para a web!!!", true);
		aviso = Toast.makeText(context, "Dados enviados com sucesso!!!", Toast.LENGTH_LONG);
		
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(2000);
					
					PizzaDao pizzaDao = new PizzaDao(context);
					List<Pizza> pizzas = pizzaDao.listarTodos();
					
					JSONStringer j = new JSONStringer();
					j.object().key("alunos").array();
					
					for (Pizza pizza : pizzas) {
						j.value(pizza.toJson());
					}
					
					j.endArray().endObject();
					
					HttpClient httpClient = new DefaultHttpClient();
					String encode = endereco + URLEncoder.encode(j.toString());
					
					Log.i("envio", encode);
					
					HttpGet httpGet = new HttpGet(encode);
					HttpResponse response = httpClient.execute(httpGet);
					
					aviso.setText(EntityUtils.toString(response.getEntity()));
					progress.dismiss();
					aviso.show();
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
	}
}
