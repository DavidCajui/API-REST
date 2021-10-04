package com.cursoSpring.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cursoSpring.domain.model.Cliente;
import com.cursoSpring.domain.repository.ClienteRepository;
import com.cursoSpring.domain.service.CadastroCrudCliente;

@RestController
//eliminar o "/clientes " dos médotos abaixo, implementando esse comando ao início da classe controller
@RequestMapping("/clientes")
public class ClienteController {

	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroCrudCliente cadastroCrudCliente;
	
	
	@GetMapping
	public List<Cliente> listar() {
		
		////Retorna toda a lista.
		return clienteRepository.findAll();
		
		///Retorna nome exato da lista
		//return clienteRepository.findByNome("David");
		
		///Retorna pesquisa com parte do nome
		//return clienteRepository.findByNomeContaining("as");
	}
		//Buscar por cliente específico.
		@GetMapping("/{clienteId}")
		public ResponseEntity <Cliente> buscar(@PathVariable Long clienteId) {
			Optional<Cliente> cliente = clienteRepository.findById(clienteId);
			
			//Verifica se o cliente existe(Pelo ID). 
			if (cliente.isPresent()) {
				//Cria tipo de resposta
				return ResponseEntity.ok(cliente.get());
			}
			return ResponseEntity.notFound().build();
		}
		
		//@RequestBody - pega o corpo  da requisição (json) e transforma em um objeto cliente
		///Adicionar cliente(Método Post)
		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)  ///Coloca o Status de criação 201
		public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
			return cadastroCrudCliente.salvar(cliente);
		}
		
		///Atualiza cliente salvo no BD.
		@PutMapping("/{clienteId}")
		public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente){
			//Verifica se Cliente existe
			if (!clienteRepository.existsById(clienteId)) {
				return ResponseEntity.notFound().build();	
			}
			
			//atribui o ID do cliente que deseja atualizar
			cliente.setIdCliente(clienteId);
			//Salva as alterações
			cliente = cadastroCrudCliente.salvar(cliente);
			return ResponseEntity.ok(cliente);
		}
		
		//Deleta clientes
		@DeleteMapping("/{clienteId}")
		public ResponseEntity<Void> deletar(@PathVariable Long clienteId) {
			if (!clienteRepository.existsById(clienteId)) {
				return ResponseEntity.notFound().build();
			}
			
			cadastroCrudCliente.excluir(clienteId);
			return ResponseEntity.noContent().build();
			
		}
	}

