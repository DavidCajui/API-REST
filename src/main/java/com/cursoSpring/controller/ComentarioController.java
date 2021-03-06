package com.cursoSpring.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cursoSpring.domain.exception.EntidadeNaoEncontException;
import com.cursoSpring.domain.model.OrdemServico;
import com.cursoSpring.domain.repository.OrdemServicoRepository;
import com.cursoSpring.domain.service.GestaoOrdemServicoService;
import com.cursoSpring.model.Comentario;
import com.cursoSpring.model.ComentarioInput;
import com.cursoSpring.model.ComentarioModel;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	//Listar Comentario
	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId).
				orElseThrow(() -> new EntidadeNaoEncontException("Ordem de serviço não encontrada"));
		
		return tocollectionModel(ordemServico.getComentarios());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@PathVariable Long ordemServicoId,
			@Valid @RequestBody ComentarioInput comentarioInputModel) {
		
		Comentario comentario = gestaoOrdemServico.adicionarComentario(ordemServicoId, 
				comentarioInputModel.getDescricao());
		
		return toModel(comentario);
		
	}
	
	private ComentarioModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioModel.class);
	}
	
	
	private List <ComentarioModel> tocollectionModel(List<Comentario>comentarios) {
		return comentarios.stream()
				.map(comentario -> toModel(comentario))
				.collect(Collectors.toList());
		
	}

	
	
}
