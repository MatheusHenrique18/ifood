package com.github.MatheusHenrique18.ifood.cadastro.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.github.MatheusHenrique18.ifood.cadastro.entity.Prato;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

	public Prato toPrato(CadastrarPratoDTO dto);
	
	public void toPrato(AlterarPratoDTO dto, @MappingTarget Prato prato);
	
	public PratoDTO toPratoDTO(Prato prato);
}
