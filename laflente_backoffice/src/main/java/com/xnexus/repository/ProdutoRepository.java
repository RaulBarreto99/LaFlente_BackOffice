package com.xnexus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xnexus.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	Optional<Produto> findByNome(String nome);

	List<Produto> findByStatus(String status);
	
}
