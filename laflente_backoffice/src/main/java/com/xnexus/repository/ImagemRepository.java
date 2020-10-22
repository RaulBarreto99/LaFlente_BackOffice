package com.xnexus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xnexus.model.Imagem;

public interface ImagemRepository extends JpaRepository<Imagem, Long>{

	public List<Imagem> findByCodigoProduto(long codigoProduto);
}
