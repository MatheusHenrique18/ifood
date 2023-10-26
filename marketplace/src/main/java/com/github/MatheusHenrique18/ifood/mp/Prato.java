package com.github.MatheusHenrique18.ifood.mp;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.StreamSupport;

import com.github.MatheusHenrique18.ifood.mp.dto.PratoDTO;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

public class Prato {

	public Long id;
	public String nome;
	public String descricao;
	public Restaurante restaurante;
	public BigDecimal preco;
	
	public static Multi<PratoDTO> findAll(PgPool pgPool) {
		Uni<RowSet<Row>> preparedQuery = pgPool.preparedQuery("select * from prato").execute();
		return uniToMulti(preparedQuery);
	}
	
	public static Multi<PratoDTO> findAll(PgPool pgPool, Long idRestaurante) {
		Uni<RowSet<Row>> preparedQuery = pgPool
				.preparedQuery("SELECT * FROM prato WHERE prato.restaurante_id = $1 ORDER BY nome ASC")
				.execute(Tuple.of(idRestaurante));
		return uniToMulti(preparedQuery);
	}
	
	public static Multi<PratoDTO> uniToMulti(Uni<RowSet<Row>> preparedQuery){
		return preparedQuery.onItem().transformToMulti(rowSet -> Multi.createFrom().items(() -> {
			return StreamSupport.stream(rowSet.spliterator(), false);
		})).onItem().transform(PratoDTO::from);
	}

	public static Uni<PratoDTO> findById(PgPool pgPool, Long id) {
		return pgPool.preparedQuery("SELECT * FROM prato WHERE id = $1")
				.execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> iterator.hasNext() ? PratoDTO.from(iterator.next()) : null);
	}
	
}
