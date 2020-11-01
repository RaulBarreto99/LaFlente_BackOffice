package com.xnexus.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


import com.xnexus.model.Usuario;
import com.xnexus.model.UsuarioDto;
import com.xnexus.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioRestController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody @Valid Usuario usuario,
			UriComponentsBuilder uriBuilder) {

		usuario.setStatus("ATIVO");
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		usuario.setSenha(bcrypt.encode(usuario.getPassword()));
		usuarioRepository.save(usuario);

		URI uri = uriBuilder.path("/usuario/{codigo}").buildAndExpand(usuario.getCodigo()).toUri();

		return ResponseEntity.created(uri).body(usuario);
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		List<Usuario> usuario = usuarioRepository.findAll();

		return ResponseEntity.ok(usuario);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<UsuarioDto> getUsuario(@PathVariable long codigo) {
		Optional<Usuario> optional = usuarioRepository.findById(codigo);

		if (optional.isPresent()) {
			Usuario usuario = optional.get();
			return ResponseEntity.ok(UsuarioDto.converterUsuario(usuario));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{codigo}")
	@Transactional
	public ResponseEntity<Usuario> alterarProduto(@PathVariable Long codigo,
			@RequestBody @Valid UsuarioDto usuarioDto) {

		Optional<Usuario> optional = usuarioRepository.findById(codigo);

		if (optional.isPresent()) {
			Usuario usuarioDB = optional.get();

			usuarioDB.setNome(usuarioDto.getNome());

			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			usuarioDB.setSenha(bcrypt.encode(usuarioDto.getSenha()));

			usuarioDB.setPerfis(usuarioDto.getPerfis());

			return ResponseEntity.ok(usuarioDB);
		}

		return ResponseEntity.notFound().build();
	}

	@PatchMapping("/{codigo}")
	@Transactional
	public ResponseEntity<?> removerUsuario(@PathVariable Long codigo, @RequestBody String status) {
		String email = "";

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			email = ((UserDetails) principal).getUsername();
		} else {
			email = principal.toString();
		}

		Optional<Usuario> optional = usuarioRepository.findById(codigo);

		if (optional.isPresent()) {

			Usuario usuario = optional.get();
			if (!email.equals(usuario.getEmail())) {

				usuario.setStatus(status);
				return ResponseEntity.ok(usuario);
			} else {
				return ResponseEntity.badRequest().build();
			}
		}

		return ResponseEntity.notFound().build();
	}

}
