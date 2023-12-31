package com.github.MatheusHenrique18.ifood.mp;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.MatheusHenrique18.ifood.mp.dto.PratoDTO;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

@Path("/restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestauranteResource {

	@Inject
	PgPool pgPool;
	
    @GET
    @Path("{idRestaurante}/pratos")
    public Multi<PratoDTO> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
        return Prato.findAll(pgPool, idRestaurante);
    }
    
}
