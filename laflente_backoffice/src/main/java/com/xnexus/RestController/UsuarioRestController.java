package com.xnexus.RestController;

import java.net.URI;


import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


import com.xnexus.controller.model.Usuario;
import com.xnexus.controller.model.repository.UsuarioRepository;


@RestController
@RequestMapping("/usuario")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<Usuario> cadastrarProduto(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder) {
		
		
		usuarioRepository.save(usuario);
		

		URI uri = uriBuilder.path("/usuario/{codigo}").buildAndExpand(usuario.getCodigo()).toUri();

		return ResponseEntity.created(uri).body(usuario);
	}
	
}
