package com.github.MatheusHenrique18.ifood.mp;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.github.MatheusHenrique18.ifood.mp.dto.PedidoRealizadoDTO;
import com.github.MatheusHenrique18.ifood.mp.dto.PratoDTO;
import com.github.MatheusHenrique18.ifood.mp.dto.PratoPedidoDTO;
import com.github.MatheusHenrique18.ifood.mp.dto.RestauranteDTO;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;

@Path("carrinho")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarrinhoResource {

	private static final String CLIENTE = "a";
	
	@Inject
	PgPool pgPool;
	
	@Inject
	@Channel("pedidos")
	Emitter<PedidoRealizadoDTO> emitterPedido;
	
	@GET
	public Uni<List<PratoCarrinho>>  buscarCarrinho() {
		return PratoCarrinho.findCarrinho(pgPool, CLIENTE);
	}
	
    @POST
    @Path("/prato/{idPrato}")
    public Uni<String> adicionarPrato(@PathParam("idPrato") Long idPrato) {
    	//poderia retornar o pedido atual
    	PratoCarrinho pc = new PratoCarrinho();
    	pc.cliente = CLIENTE;
    	pc.prato = idPrato;
    	return PratoCarrinho.save(pgPool, CLIENTE, idPrato);
    	
    }
    
    @POST
    @Path("/realizar-pedido")
    public Uni<Boolean> finalizar() {
        PedidoRealizadoDTO pedido = new PedidoRealizadoDTO();
        String cliente = CLIENTE;
        pedido.cliente = cliente;
        List<PratoCarrinho> pratoCarrinho = PratoCarrinho.findCarrinho(pgPool, cliente).await().indefinitely();

        List<PratoPedidoDTO> pratos = pratoCarrinho.stream().map(pc -> from(pc)).collect(Collectors.toList());
        pedido.pratos = pratos;

        RestauranteDTO restaurante = new RestauranteDTO();
        restaurante.nome = "nome restaurante";
        pedido.restaurante = restaurante;
        emitterPedido.send(pedido);

        return PratoCarrinho.delete(pgPool, cliente);
    }

    private PratoPedidoDTO from(PratoCarrinho pratoCarrinho) {
        PratoDTO dto = Prato.findById(pgPool, pratoCarrinho.prato).await().indefinitely();
        return new PratoPedidoDTO(dto.nome, dto.descricao, dto.preco);
    }
	
}
