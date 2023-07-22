package com.github.MatheusHenrique18.ifood.cadastro.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CadastrarRestauranteDTO {

	@NotEmpty
	@NotNull
	public String proprietario;
	
	public String cnpj;
	
	@Size(min = 3, max = 50)
	public String nomeFantasia;
	
	public LocalizacaoDTO localizacao;
}
