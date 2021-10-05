package com.cursoSpring.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.cursoSpring.domain.model.Cliente;
import com.cursoSpring.domain.model.OrdemServico;
import com.cursoSpring.domain.model.StatusOrdemServico;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@Entity
public class Comentario {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private OrdemServico ordemServico;
	
	private String descricao;
	private OffsetDateTime dataEnvio;
	
	
	
}
