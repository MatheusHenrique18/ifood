package com.github.MatheusHenrique18.ifood.pedido;
import com.github.MatheusHenrique18.ifood.pedido.dto.PedidoRealizadoDTO;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PedidoDeserializer extends ObjectMapperDeserializer<PedidoRealizadoDTO> {

	public PedidoDeserializer() {
		super(PedidoRealizadoDTO.class);
	}

}
