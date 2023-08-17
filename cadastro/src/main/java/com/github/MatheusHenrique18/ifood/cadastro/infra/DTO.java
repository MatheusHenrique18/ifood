package com.github.MatheusHenrique18.ifood.cadastro.infra;

import javax.validation.ConstraintValidatorContext;

public interface DTO {

	default boolean isValid(ConstraintValidatorContext ConstraintValidatorContext) {
		return true;
	}
}
