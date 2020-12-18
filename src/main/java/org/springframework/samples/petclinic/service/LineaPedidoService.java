package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.repository.LineaPedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class LineaPedidoService {
	
	@Autowired
	private LineaPedidoRepository lineaPedidoRepo;
	
	public void save(LineaPedido lp) {
		lineaPedidoRepo.save(lp);
	}
}
