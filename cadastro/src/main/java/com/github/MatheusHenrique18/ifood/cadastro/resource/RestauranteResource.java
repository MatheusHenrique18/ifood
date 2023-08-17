package com.github.MatheusHenrique18.ifood.cadastro.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
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

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.MatheusHenrique18.ifood.cadastro.dto.AlterarRestauranteDTO;
import com.github.MatheusHenrique18.ifood.cadastro.dto.CadastrarRestauranteDTO;
import com.github.MatheusHenrique18.ifood.cadastro.dto.RestauranteDTO;
import com.github.MatheusHenrique18.ifood.cadastro.dto.RestauranteMapper;
import com.github.MatheusHenrique18.ifood.cadastro.entity.Restaurante;
import com.github.MatheusHenrique18.ifood.cadastro.exception.ConstraintViolationResponse;

@Path("/restaurantes")
@Tag(name = "Restaurante")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteResource {

	@Inject
	RestauranteMapper restauranteMapper;
	
	@GET
    public List<RestauranteDTO> buscar() {
        Stream<Restaurante> restaurantes = Restaurante.streamAll();
        return restaurantes.map(r -> restauranteMapper.toRestauranteDTO(r)).collect(Collectors.toList());
    }
	
	@POST
    @Transactional
    @APIResponse(responseCode = "201", description = "Caso o restaurante seja cadastrado com sucesso.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
    public Response cadastrar(@Valid CadastrarRestauranteDTO dto) {
		Restaurante restaurante = restauranteMapper.toRestaurante(dto);
		restaurante.persist();
    	return Response.status(Status.CREATED).build();
    }
    
    @PUT
    @Path("{id}")
    @Transactional
    public void alterar(@PathParam("id") Long id, AlterarRestauranteDTO dto) {
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
    	if(restauranteOp.isEmpty()) {
    		throw new NotFoundException();
    	}
    	
    	Restaurante restaurante = restauranteOp.get();
    	restauranteMapper.toRestaurante(dto, restaurante);
    	
    	restaurante.persist();
    }
    
    @DELETE
    @Path("{id}")
    @Transactional
    public void excluir(@PathParam("id") Long id) {
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
		restauranteOp.ifPresentOrElse(Restaurante::delete, () -> {
			throw new NotFoundException();
		});
    }
    
}