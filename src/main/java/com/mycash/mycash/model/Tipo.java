package com.mycash.mycash.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Tipo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "tipo")
	@JsonIgnore
	private Receita receita;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "tipo")
	@JsonIgnore
	private Despesa despesa;


}
