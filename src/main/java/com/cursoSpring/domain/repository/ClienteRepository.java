package com.cursoSpring.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursoSpring.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	//pesquisar por nome exato
	List<Cliente> findByNome(String nome);
	
	//pesquisar parte do nome
	List<Cliente> findByNomeContaining(String nome);
	
	/*Consulta de email, será usada como regra de negócio,
	onde não habilita a criação de um cliente 
	com o email dele já cadastrado.
    */
	Cliente findByEmail(String email);
}
