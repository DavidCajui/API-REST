package com.cursoSpring.model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioModel {
	
	private Long id;
	private String descricao;
	private OffsetDateTime dataEnvio;
	
	
	

}
