package com.github.MatheusHenrique18.ifood.cadastro.exception;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class ConstraintViolationImpl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Path do atributo, ex: dataInicio, pessoa.endereco.numero", required = false)
	private final String atributo;
	
	@Schema(description = "Mensagem descritiva do erro possivelmente associado ao path", required = true)
	private final String mensagem;
	
	private ConstraintViolationImpl(ConstraintViolation<?> violation) {
		this.atributo = Stream.of(violation.getPropertyPath().toString()).collect(Collectors.joining("."));
		this.mensagem = violation.getMessage();
	}
	
	public ConstraintViolationImpl(String atributo, String mensagem) {
		this.atributo = atributo;
        this.mensagem = mensagem;
    }

	public static ConstraintViolationImpl of(ConstraintViolation<?> violation) {
        return new ConstraintViolationImpl(violation);
    }

    public static ConstraintViolationImpl of(String violation) {
        return new ConstraintViolationImpl(null, violation);
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getAtributo() {
        return atributo;
    }
    
}
