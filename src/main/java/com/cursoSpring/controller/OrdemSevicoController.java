package com.cursoSpring.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cursoSpring.domain.model.OrdemServico;
import com.cursoSpring.domain.repository.OrdemServicoRepository;
import com.cursoSpring.domain.service.GestaoOrdemServicoService;
import com.cursoSpring.model.OrdemServicoInput;
import com.cursoSpring.model.OrdemServicoRepresentModel;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemSevicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private OrdemServicoRepresentModel toModel(OrdemServico ordemServico) {
		return  modelMapper.map(ordemServico, OrdemServicoRepresentModel.class);
	}
	
	
	//converte uma colecão de ordem de servoço em uma coleção de ordem de serviço model
	private List<OrdemServicoRepresentModel> toCollectionRepresentModel(List<OrdemServico> ordensServico){
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoRepresentModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordemServico = toEntity(ordemServicoInput);
		return toModel(gestaoOrdemServico.criar(ordemServico));
	}
	
	@GetMapping
	public List<OrdemServicoRepresentModel> listar() {
		return toCollectionRepresentModel(ordemServicoRepository.findAll());
		
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoRepresentModel> buscar(@PathVariable Long ordemServicoId){
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
		
		if (ordemServico.isPresent()) {
			OrdemServicoRepresentModel ordemServicoModel = toModel(ordemServico.get());
			return ResponseEntity.ok(ordemServicoModel);
		}
		return ResponseEntity.notFound().build();
		
	}
	
	
	
	
	//finalizar ordem servico
	@PutMapping("/{ordemSevicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemSevicoId) {
		gestaoOrdemServico.finalizar(ordemSevicoId);
		
	}
	
	
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
}
