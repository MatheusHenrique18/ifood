package com.github.MatheusHenrique18.ifood.cadastro.dto;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.github.MatheusHenrique18.ifood.cadastro.entity.Restaurante;
import com.github.MatheusHenrique18.ifood.cadastro.infra.DTO;
import com.github.MatheusHenrique18.ifood.cadastro.infra.ValidDTO;

@ValidDTO
public class CadastrarRestauranteDTO implements DTO{

	@NotEmpty
	@NotNull
	public String proprietario;
	
	@CNPJ
	public String cnpj;
	
	@Size(min = 3, max = 50)
	public String nomeFantasia;
	
	public LocalizacaoDTO localizacao;
	
	@Override
	public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
		constraintValidatorContext.disableDefaultConstraintViolation();
		if(Restaurante.find("cnpj", cnpj).count() > 0) {
			constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ já cadastrado")
				.addPropertyNode("cnpj")
				.addConstraintViolation();
			
			return false;
		}
		
		return true;
	}
}
