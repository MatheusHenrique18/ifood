package com.github.MatheusHenrique18.ifood.mp;

import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

public class Restaurante {

	public Long idRestaurante;
	public String nome;
	public Localizacao localizacao;
	
	@Override
	public String toString() {
		return "Restaurante [id=" + idRestaurante + ", nome=" + nome + ", localizacao=" + localizacao + "]";
	}

	public void persist(PgPool pgPool) {
		pgPool.preparedQuery("INSERT INTO localizacao (id, latitude, longitude) VALUES ($1, $2, $3)")
		.execute(Tuple.of(localizacao.idLocalizacao, localizacao.latitude, localizacao.longitude))
		.await().indefinitely();
		
		pgPool.preparedQuery("INSERT INTO restaurante (id, nome, localizacao_id) VALUES ($1, $2, $3)")
		.execute(Tuple.of(idRestaurante, nome, localizacao.idLocalizacao))
		.await().indefinitely();
		
	}
	
}
