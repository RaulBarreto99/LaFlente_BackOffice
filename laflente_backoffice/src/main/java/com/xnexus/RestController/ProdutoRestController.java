package com.xnexus.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.xnexus.controller.model.Produto;
import com.xnexus.controller.model.repository.ProdutoRepository;

@RestController
@RequestMapping("/produto")
public class ProdutoRestController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@PostMapping
    @Transactional
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody @Valid Produto produto, UriComponentsBuilder uriBuilder){
		
		produtoRepository.save(produto);

        URI uri = uriBuilder.path("/produto/{codigo}").buildAndExpand(produto.getCodigo()).toUri();
        
        return ResponseEntity.created(uri).body(produto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos(){
        List<Produto> produtos = produtoRepository.findAll();

        return ResponseEntity.ok(produtos);
    }
    
    @PutMapping("/{codigo}")
    @Transactional
    public ResponseEntity<Produto> alterarProduto(@PathVariable Long codigo, @RequestBody @Valid Produto produto){

        Optional<Produto> optional = produtoRepository.findById(codigo);

        
        if(optional.isPresent()){
        	Produto dbProduto = optional.get();

        	dbProduto.setNome(produto.getNome());
        	dbProduto.setPreco(produto.getPreco());
        	dbProduto.setQuantidade(produto.getQuantidade());
        	dbProduto.setPalavraChave(produto.getPalavraChave());
        	dbProduto.setDescricaoCurta(produto.getDescricaoCurta());
        	dbProduto.setDescricaoDetalhada(produto.getDescricaoDetalhada());
        	dbProduto.setImagem(produto.getImagem());

            return ResponseEntity.ok(dbProduto);
        }

        return ResponseEntity.notFound().build();
    }

	
}
