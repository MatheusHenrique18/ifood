package com.github.MatheusHenrique18.ifood.cadastro.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.github.MatheusHenrique18.ifood.cadastro.entity.Restaurante;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

	@Mapping(target = "nome", source = "nomeFantasia")
	public Restaurante toRestaurante(CadastrarRestauranteDTO dto);
	
	@Mapping(target = "nome", source = "nomeFantasia")
	public void toRestaurante(AlterarRestauranteDTO dto, @MappingTarget Restaurante restaurante);
	
	@Mapping(target = "nomeFantasia", source = "nome")
	@Mapping(target = "dataCriacao", dateFormat = "dd/MM/yyyy HH:mm:ss")
	@Mapping(target = "dataAtualizacao", dateFormat = "dd/MM/yyyy HH:mm:ss")
	public RestauranteDTO toRestauranteDTO(Restaurante r);
}
