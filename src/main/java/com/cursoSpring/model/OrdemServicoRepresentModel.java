package com.cursoSpring.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.cursoSpring.domain.model.StatusOrdemServico;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdemServicoRepresentModel {

	private Long id;
	private String nomeCliente;
	private String descricao;
	private BigDecimal preco;
	private StatusOrdemServico status;
	private OffsetDateTime dataAbertura;
	private OffsetDateTime dataFinalizacao;
	
	
}
