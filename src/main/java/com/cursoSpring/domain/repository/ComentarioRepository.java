package com.cursoSpring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursoSpring.model.Comentario;



@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
	

}
