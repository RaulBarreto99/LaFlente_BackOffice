package com.xnexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xnexus.controller.model.repository.ProdutoRepository;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	public ModelAndView hello() {
		ModelAndView mv = new ModelAndView("index.html");
		return mv;
	}
	
	@RequestMapping("/cadastrarProduto")
	@GetMapping
	public ModelAndView produto() {
		ModelAndView mv = new ModelAndView("cadastrarProduto.html");
		return mv;
	}
	
	@RequestMapping("/detalharProduto/{codigo}")
	@GetMapping
	public ModelAndView detalharProduto(@PathVariable Long codigo) {
		ModelAndView mv = new ModelAndView("detalharProduto.html");
		mv.addObject("codigo", codigo);
		return mv;
	}
	
	@RequestMapping("/cadastrarEntradaProduto")
	@GetMapping
	public ModelAndView cadastrarEntradaProduto() {
		ModelAndView mv = new ModelAndView("cadastrarEntradaProduto.html");
		return mv;
	}

	@RequestMapping("/estoque")
	@GetMapping
	public ModelAndView estoque() {
		ModelAndView mv = new ModelAndView("estoque.html");
		return mv;
	}
	
	@RequestMapping("/cadastrarUsuario")
	@GetMapping
	public ModelAndView usuario() {
		ModelAndView mv = new ModelAndView("cadastrarUsuario.html");
		return mv;
	}
	@RequestMapping("/preview/{codigo}")
	@GetMapping
	public ModelAndView preview(@PathVariable Long codigo) {
		ModelAndView mv = new ModelAndView("preview.html");
		return mv;
	}
}
