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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public ResponseEntity<Produto> cadastrarProduto(@RequestBody @Valid Produto produto, UriComponentsBuilder uriBuilder) {
		produto.setStatus("ativo");
		produtoRepository.save(produto);

		URI uri = uriBuilder.path("/produto/{codigo}").buildAndExpand(produto.getCodigo()).toUri();

		return ResponseEntity.created(uri).body(produto);
	}

	@PutMapping(value = "/imagem/{codigo}-{nome}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Transactional
	public void uploadImagem(@RequestParam MultipartFile imagem, @PathVariable Long codigo, @PathVariable String nome,
			UriComponentsBuilder uriBuilder) {

		try {

				File ficheiro = new File("C:\\laflente\\");
				ficheiro.mkdirs();

				byte[] bytes = imagem.getBytes();

				Path path = Paths.get("C:\\laflente\\" + codigo + "_" + nome + "_" + imagem.getOriginalFilename());
				Files.write(path, bytes);
				
				Optional<Produto> optional = produtoRepository.findById(codigo);

				if (optional.isPresent()) {
					Produto produtos = optional.get();

					produtos.setImagem("C:\\laflente\\" +codigo + "_" + nome + "_" + imagem.getOriginalFilename());
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
	
	@GetMapping("/{codigo}")
	@Transactional
	public ResponseEntity<Produto> buscarProduto(@PathVariable Long codigo) {

		Optional<Produto> optional = produtoRepository.findById(codigo);

		if (optional.isPresent()) {
			Produto produtos = optional.get();

			return ResponseEntity.ok(produtos);
		}

		return ResponseEntity.notFound().build();
	}
	@GetMapping("/{nome}")
	@Transactional
	public ResponseEntity<Produto> buscarProdutoNome(@PathVariable String nome) {
		Optional<Produto> optional = produtoRepository.findByNome(nome);

		if (optional.isPresent()) {
			Produto produtos = optional.get();

			return ResponseEntity.ok(produtos);
		}

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{statusAtivo}")
	@Transactional
	public ResponseEntity<Produto> buscarProdutoStatusAtivo(@PathVariable String status,@RequestBody @Valid Produto produto) {
		produto.setStatus("ativo");
		Optional<Produto> optional = produtoRepository.findByStatus(status);

		if (optional.isPresent()) {
			Produto produtos = optional.get();
			return ResponseEntity.ok(produtos);
		}

		return ResponseEntity.notFound().build();
	}
	@GetMapping("/{statusInativo}")
	@Transactional
	public ResponseEntity<Produto> buscarProdutoStatusInativo(@PathVariable String status,@RequestBody @Valid Produto produto) {
		produto.setStatus("inativo");
		Optional<Produto> optional = produtoRepository.findByStatus(status);

		if (optional.isPresent()) {
			Produto produtos = optional.get();
			
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
	
	@GetMapping("/imagem/{codigo}")
	@ResponseBody
	public byte[] retornaImagem(@PathVariable Long codigo) {
		Optional<Produto> optional = produtoRepository.findById(codigo);

		if (optional.isPresent()) {
			Produto produtos = optional.get();

			File file = new File(produtos.getImagem());
			try {
				return Files.readAllBytes(file.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
	}

}
