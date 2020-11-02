package com.xnexus.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xnexus.model.PerguntaResposta;


public interface PerguntaRespostaRepository extends JpaRepository<PerguntaResposta, Integer>{

    //public List<PalavraChave> findByCodigoProduto(long codigoProduto);

    Optional<PerguntaResposta> findByCodigoProduto(long codigoProduto);
}