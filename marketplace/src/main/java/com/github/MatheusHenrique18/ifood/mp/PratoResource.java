package com.github.MatheusHenrique18.ifood.mp;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

@Path("/pratos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PratoResource {

	@Inject
	PgPool pgPool;
	
    @GET
    public Multi<PratoDTO> buscarPratos() {
        return Prato.findAll(pgPool);
    }
}