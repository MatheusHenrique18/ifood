package com.github.MatheusHenrique18.ifood.pedido;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.github.MatheusHenrique18.ifood.pedido.dto.PedidoRealizadoDTO;

@ApplicationScoped
public class PedidoRealizadoIncoming {

	@Incoming("pedidos")
	public void lerPedidos(PedidoRealizadoDTO dto) {
		System.out.println("*-----------------------*");
		System.out.println(dto);
	}
}
