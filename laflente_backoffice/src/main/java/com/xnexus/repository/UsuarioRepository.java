package com.xnexus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xnexus.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String username);

}
