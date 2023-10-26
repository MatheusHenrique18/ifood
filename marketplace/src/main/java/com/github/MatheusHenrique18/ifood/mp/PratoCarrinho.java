package com.github.MatheusHenrique18.ifood.mp;

import java.util.ArrayList;
import java.util.List;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

public class PratoCarrinho {

	public String cliente;
	public Long prato;
	
	
	public static Uni<String> save(PgPool pgPool, String cliente, Long prato) {
        return pgPool.preparedQuery("INSERT INTO prato_cliente (cliente, prato) VALUES ($1, $2) RETURNING (cliente)")
        		.execute(Tuple.of(cliente, prato))
                .map(pgRowSet -> pgRowSet.iterator().next().getString("cliente"));
    }
	
	public static Uni<List<PratoCarrinho>> findCarrinho(PgPool pgPool, String cliente) {
		return pgPool.preparedQuery("SELECT * FROM prato_cliente WHERE cliente = $1")
				.execute(Tuple.of(cliente))
				.map(pgRowSet -> {
					List<PratoCarrinho> list = new ArrayList<PratoCarrinho>();
					for(Row row: pgRowSet) {
						list.add(toPratoCarrinho(row));
					}
					
					return list;
				});
	}

	private static PratoCarrinho toPratoCarrinho(Row row) {
		PratoCarrinho pc = new PratoCarrinho();
		pc.cliente = row.getString("cliente");
		pc.prato = row.getLong("prato");
		return pc;
	}
	
    public static Uni<Boolean> delete(PgPool pgPool, String cliente) {
        return pgPool.preparedQuery("DELETE FROM prato_cliente WHERE cliente = $1")
        		.execute(Tuple.of(cliente))
                .map(pgRowSet -> pgRowSet.rowCount() == 1);

    }
	
}


