package com.github.MatheusHenrique18.ifood.cadastro.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "restaurante")
public class Restaurante extends PanacheEntityBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long idRestaurante;
	
	public String proprietario;
	
	public String cnpj;
	
	public String nome;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idLocalizacao")
	public Localizacao localizacao;
	
	@CreationTimestamp
	public Date dataCriacao;
	
	@UpdateTimestamp
	public Date dataAtualizacao;
}
