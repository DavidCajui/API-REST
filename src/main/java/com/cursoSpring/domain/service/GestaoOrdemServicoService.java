package com.cursoSpring.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursoSpring.domain.exception.EntidadeNaoEncontException;
import com.cursoSpring.domain.exception.NegocioException;
import com.cursoSpring.domain.model.Cliente;
import com.cursoSpring.domain.model.OrdemServico;
import com.cursoSpring.domain.model.StatusOrdemServico;
import com.cursoSpring.domain.repository.ClienteRepository;
import com.cursoSpring.domain.repository.ComentarioRepository;
import com.cursoSpring.domain.repository.OrdemServicoRepository;
import com.cursoSpring.model.Comentario;

@Service
public class GestaoOrdemServicoService {

	//injetar repositorio
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository; 
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getIdCliente())
				.orElseThrow(() -> new NegocioException("Cliente não Encontrado"));
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		 OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
		
	}
	
	//finalizar ordem de serviço
	public void finalizar(Long ordemServicoId) {
		 OrdemServico ordemServico = buscar(ordemServicoId);
		 
		 ordemServico.finalizar();
		 
		 ordemServicoRepository.save(ordemServico);
		
		
	}

	//extract method usar no finalizar ordem de serviço // adicionar comentário
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				 .orElseThrow(() -> new EntidadeNaoEncontException("Ordem de Serviço não encontrada"));
	}
}
