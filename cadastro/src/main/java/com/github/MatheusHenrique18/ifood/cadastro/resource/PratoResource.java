package com.github.MatheusHenrique18.ifood.cadastro.resource;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.MatheusHenrique18.ifood.cadastro.entity.Prato;
import com.github.MatheusHenrique18.ifood.cadastro.entity.Restaurante;

@Path("/restaurantes/{idRestaurante}/pratos")
@Tag(name = "Prato")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoResource {

	@GET
	public List<Prato> buscar(@PathParam("idRestaurante") Long idRestaurante) {
		return Prato.list("idRestaurante", idRestaurante);
	}

	@POST
	@Transactional
	public Response cadastrar(@PathParam("idRestaurante") Long idRestaurante, Prato dto) {
		Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOp.isEmpty()) {
			throw new NotFoundException();
		}

		dto.restaurante = restauranteOp.get();
		dto.persist();

		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{idPrato}")
	@Transactional
	public void alterar(@PathParam("idRestaurante") Long idRestaurante, @PathParam("idPrato") Long idPrato, Prato dto) {
		Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOp.isEmpty()) {
			throw new NotFoundException();
		}

		Optional<Prato> pratoOp = Prato.findByIdOptional(idPrato);
		if (pratoOp.isEmpty()) {
			throw new NotFoundException();
		}

		Prato prato = pratoOp.get();
		prato.nome = dto.nome;
		prato.persist();
	}

	@DELETE
	@Path("{idPrato}")
	@Transactional
	public void excluir(@PathParam("idRestaurante") Long idRestaurante, @PathParam("idPrato") Long idPrato) {
		Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOp.isEmpty()) {
			throw new NotFoundException();
		}

		Optional<Prato> pratoOp = Prato.findByIdOptional(idPrato);
		pratoOp.ifPresentOrElse(Prato::delete, () -> {
			throw new NotFoundException();
		});
	}
	
}
