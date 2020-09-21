package com.xnexus.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
	public ResponseEntity<Produto> cadastrarProduto(@RequestBody @Valid Produto produto,
			@RequestParam MultipartFile imagem, UriComponentsBuilder uriBuilder) {
		produto.setStatus("ativo");
		produtoRepository.save(produto);

		URI uri = uriBuilder.path("/produto/{codigo}").buildAndExpand(produto.getCodigo()).toUri();

		return ResponseEntity.created(uri).body(produto);
	}

	@PutMapping("/imagem/{codigo}")
	@Transactional
	public void uploadImagem(@PathVariable Long codigo, @RequestParam MultipartFile imagem,
			UriComponentsBuilder uriBuilder) {

		try {
			
			Optional<Produto> optional = produtoRepository.findById(codigo);

			if (optional.isPresent()) {
				
				Produto produtos = optional.get();
				
				File ficheiro = new File("C:\\laflente\\");
				ficheiro.mkdirs();
				
				byte[] bytes = imagem.getBytes();
				
				Path path = Paths.get("C:\\laflente\\"+produtos.getCodigo()+" - "+imagem.getOriginalFilename());
				Files.write(path, bytes);

				produtos.setImagem("C:\\laflente\\"+produtos.getCodigo()+imagem.getOriginalFilename());


			}
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@GetMapping
	public ResponseEntity<List<Produto>> listarProdutos() {
		List<Produto> produtos = produtoRepository.findAll();

		return ResponseEntity.ok(produtos);
	}

	@PutMapping("/{codigo}")
	@Transactional
	public ResponseEntity<Produto> alterarProduto(@PathVariable Long codigo, @RequestBody @Valid Produto produto) {

		Optional<Produto> optional = produtoRepository.findById(codigo);

		if (optional.isPresent()) {
			Produto produtos = optional.get();

			produtos.setNome(produto.getNome());
			produtos.setPreco(produto.getPreco());
			produtos.setQuantidade(produto.getQuantidade());
			produtos.setPalavraChave(produto.getPalavraChave());
			produtos.setDescricaoCurta(produto.getDescricaoCurta());
			produtos.setDescricaoDetalhada(produto.getDescricaoDetalhada());
			produtos.setImagem(produto.getImagem());

			return ResponseEntity.ok(produtos);
		}

		return ResponseEntity.notFound().build();
	}

	@PatchMapping("/{codigo}")
	@Transactional
	public ResponseEntity<?> removerProduto(@PathVariable Long codigo, @RequestBody String status) {

		Optional<Produto> optional = produtoRepository.findById(codigo);

		if (optional.isPresent()) {
			Produto produtos = optional.get();

			produtos.setStatus(status);
			return ResponseEntity.ok(produtos);
		}

		return ResponseEntity.notFound().build();
	}

}
