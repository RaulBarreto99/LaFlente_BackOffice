package com.xnexus.model;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;


public class UsuarioDto {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long codigo;
		
		private String nome;
		
		private String status;
		
		private String senha;
		
		@Email
		private String email;
		
		@ManyToMany(fetch = FetchType.EAGER)
		private List<Perfil> perfis;

		public long getCodigo() {
			return codigo;
		}

		public void setCodigo(long codigo) {
			this.codigo = codigo;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public List<Perfil> getPerfis() {
			return perfis;
		}

		public void setPerfis(List<Perfil> perfis) {
			this.perfis = perfis;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		
		
		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

		public static UsuarioDto converterUsuario(Usuario usuario) {
			UsuarioDto usuarioDto = new UsuarioDto();
			usuarioDto.setCodigo(usuario.getCodigo());
			usuarioDto.setNome(usuario.getNome());
			usuarioDto.setEmail(usuario.getEmail());
			usuarioDto.setPerfis(usuario.getPerfis());
			
			return usuarioDto;
		}
}
