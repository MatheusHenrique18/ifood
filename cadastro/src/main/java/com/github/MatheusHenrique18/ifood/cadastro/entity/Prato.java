package com.github.MatheusHenrique18.ifood.cadastro.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "prato")
public class Prato extends PanacheEntityBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long idPrato;
	
	public String nome;
	
	public String descricao;
	
	@ManyToOne
	@JoinColumn(name = "idRestaurante")
	public Restaurante restaurante;
	
	public BigDecimal preco;
}
