package com.github.MatheusHenrique18.ifood.mp;

public class Restaurante {

	public Long id;
	public String nome;
	public Localizacao localizacao;
	
	@Override
	public String toString() {
		return "Restaurante [id=" + id + ", nome=" + nome + ", localizacao=" + localizacao + "]";
	}
	
}
