package com.xnexus.RestController;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.xnexus.model.EntradaProduto;
import com.xnexus.model.Produto;
import com.xnexus.repository.EntradaProdutoRepository;
import com.xnexus.repository.ProdutoRepository;

@RestController
@RequestMapping("/cadastrarEntrada")
public class EntradaProdutoRestController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EntradaProdutoRepository entradaProdutoRespository;
	
	@PutMapping("/{codigoProduto}")
    @Transactional

    public ResponseEntity<Produto> alterarEntrada(@PathVariable Long codigoProduto, @RequestBody @Valid EntradaProduto produto){

        Optional<Produto> optional = produtoRepository.findById(codigoProduto);

        
        if(optional.isPresent()){
        	Produto produtos = optional.get();

        	produtos.setQuantidade(produtos.getQuantidade() + produto.getQuantidade());

            return ResponseEntity.ok(produtos);
        }

        return ResponseEntity.notFound().build();
    }
	
	@PostMapping
    @Transactional
    public ResponseEntity<EntradaProduto> cadastrarEntrada(@RequestBody @Valid EntradaProduto produto, UriComponentsBuilder uriBuilder){
		
		produto.setData(new Date());
		entradaProdutoRespository.save(produto);

        URI uri = uriBuilder.path("/cadastrarEntradaProduto/{codigo}").buildAndExpand(produto.getCodigo()).toUri();
        
        return ResponseEntity.created(uri).body(produto);
    }
	
	@GetMapping
    public ResponseEntity<List<EntradaProduto>> listarEntrada(){
        List<EntradaProduto> produto = entradaProdutoRespository.findAll();

        return ResponseEntity.ok(produto);
    }
}
