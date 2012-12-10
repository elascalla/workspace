package br.com.wbmkt.mobpizza.modelo;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONStringer;

public class Pizza implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private Double valor;
	
	public Pizza(Long id, String nome, Double valor) {
		super();
		this.id = id;
		this.nome = nome;
		this.valor = valor;
	}
	
	public String toJson() throws JSONException {
		JSONStringer j = new JSONStringer();
		
		j.object()
			.key("id").value(id)
			.key("id").value(id)
			.key("id").value(id)
		.endObject();
		
		return j.toString();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return nome;
	}
	
}
