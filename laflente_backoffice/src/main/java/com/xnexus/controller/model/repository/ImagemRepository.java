package com.xnexus.controller.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xnexus.controller.model.Imagem;

public interface ImagemRepository extends JpaRepository<Imagem, Long>{

	public List<Imagem> findByCodigoProduto(long codigoProduto);
}
