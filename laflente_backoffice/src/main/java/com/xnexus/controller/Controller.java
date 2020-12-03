package com.xnexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xnexus.model.Perfil;
import com.xnexus.repository.UsuarioRepository;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@SuppressWarnings("unchecked")
	@GetMapping
	public ModelAndView index() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Perfil> perfis = (List<Perfil>) ((UserDetails) principal).getAuthorities();

		String perfil = perfis.get(0).getNome();

		if (perfil.equals("Estoquista")) {
			ModelAndView mv = new ModelAndView("indexEstoquista.html");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("index.html");
			return mv;
		}

	}

	@RequestMapping("/cadastrarProduto")
	@GetMapping
	public ModelAndView produto() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Perfil> perfis = (List<Perfil>) ((UserDetails) principal).getAuthorities();

		String perfil = perfis.get(0).getNome();

		if (perfil.equals("Administrador")) {
			ModelAndView mv = new ModelAndView("cadastrarProduto.html");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("forbidden.html");
			return mv;
		}
		
		
	}
	
	@RequestMapping("/pedidos")
	@GetMapping
	public ModelAndView pedidos() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Perfil> perfis = (List<Perfil>) ((UserDetails) principal).getAuthorities();

		String perfil = perfis.get(0).getNome();

		if (perfil.equals("Estoquista")) {
			ModelAndView mv = new ModelAndView("pedidos.html");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("forbidden.html");
			return mv;
		}
		
		
	}

	@RequestMapping("/login")
	@GetMapping
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("login.html");
		return mv;
	}

	@RequestMapping("/detalharProduto/{codigo}")
	@GetMapping
	public ModelAndView detalharProduto(@PathVariable Long codigo) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Perfil> perfis = (List<Perfil>) ((UserDetails) principal).getAuthorities();

		String perfil = perfis.get(0).getNome();

		if (perfil.equals("Estoquista")) {
			ModelAndView mv = new ModelAndView("detalharProdutosEstoquista.html");
			mv.addObject("codigo", codigo);
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("detalharProduto.html");
			mv.addObject("codigo", codigo);
			return mv;
		}
		
	}

//	@RequestMapping("/cadastrarEntradaProduto")
//	@GetMapping
//	public ModelAndView cadastrarEntradaProduto() {
//		ModelAndView mv = new ModelAndView("cadastrarEntradaProduto.html");
//		return mv;
//	}

	@RequestMapping("/estoque")
	@GetMapping
	public ModelAndView estoque() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Perfil> perfis = (List<Perfil>) ((UserDetails) principal).getAuthorities();

		String perfil = perfis.get(0).getNome();

		if (perfil.equals("Administrador")) {
			ModelAndView mv = new ModelAndView("estoque.html");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("forbidden.html");
			return mv;
		}		
	}

	@RequestMapping("/cadastrarUsuario")
	@GetMapping
	public ModelAndView usuario() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Perfil> perfis = (List<Perfil>) ((UserDetails) principal).getAuthorities();

		String perfil = perfis.get(0).getNome();

		if (perfil.equals("Administrador")) {
			ModelAndView mv = new ModelAndView("cadastrarUsuario.html");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("forbidden.html");
			return mv;
		}
	}

	@RequestMapping("/preview/{codigo}")
	@GetMapping
	public ModelAndView preview(@PathVariable Long codigo) {
		ModelAndView mv = new ModelAndView("preview.html");
		return mv;
	}

}
