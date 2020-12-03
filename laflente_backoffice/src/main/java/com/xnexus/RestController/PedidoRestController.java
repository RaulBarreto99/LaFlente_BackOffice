package com.xnexus.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.xnexus.model.Pedido;
import com.xnexus.model.StatusDto;
import com.xnexus.repository.PedidoRepository;

@RequestMapping("pedidosRest")
@RestController
public class PedidoRestController {

	
	@Autowired
	public PedidoRepository pedidoRepository;
	
	@GetMapping
	public List<Pedido> listarPedidos(){
		return pedidoRepository.findByOrderByDataVendaDesc();
	}
	
	@Transactional
	@PutMapping
	public ResponseEntity<?> alterarStatus(@RequestBody List<StatusDto> listaStatus){

		
		for (StatusDto statusDto : listaStatus) {
			Pedido pedido = pedidoRepository.getOne(statusDto.getId());
			
			pedido.setStatus(statusDto.getStatus());
			
			pedidoRepository.flush();
		}
		
		
		return ResponseEntity.ok().build();
	}
}
