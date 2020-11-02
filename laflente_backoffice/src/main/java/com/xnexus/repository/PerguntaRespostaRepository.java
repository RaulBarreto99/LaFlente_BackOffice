package com.xnexus.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xnexus.model.PalavraChave;


public interface PerguntaRespostaRepository extends JpaRepository<PalavraChave, Integer>{

    //public List<PalavraChave> findByCodigoProduto(long codigoProduto);

    Optional<PalavraChave> findByCodigoProduto(long codigoProduto);
}