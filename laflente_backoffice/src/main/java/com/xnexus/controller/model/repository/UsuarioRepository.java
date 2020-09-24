package com.xnexus.controller.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xnexus.controller.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
