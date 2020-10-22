package com.xnexus.RestController;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.xnexus.model.Produto;
import com.xnexus.model.Usuario;
import com.xnexus.repository.UsuarioRepository;


@RestController
@RequestMapping("/usuario")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder) {
		
		usuario.setStatus("ATIVO");
		usuarioRepository.save(usuario);
		

		URI uri = uriBuilder.path("/usuario/{codigo}").buildAndExpand(usuario.getCodigo()).toUri();

		return ResponseEntity.created(uri).body(usuario);
	}
	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		List<Usuario> usuario = usuarioRepository.findAll();

		return ResponseEntity.ok(usuario);
	}

	@PatchMapping("/{codigo}")
	@Transactional
	public ResponseEntity<?> removerUsuario(@PathVariable Long codigo, @RequestBody String status) {

		Optional<Usuario> optional = usuarioRepository.findById(codigo);

		if (optional.isPresent()) {
			Usuario usuario = optional.get();

			usuario.setStatus(status);
			return ResponseEntity.ok(usuario);
		}

		return ResponseEntity.notFound().build();
	}

}
	

