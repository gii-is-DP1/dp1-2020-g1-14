package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.repository.LineaPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LineaPedidoService {
	
	@Autowired
	private LineaPedidoRepository lineaPedidoRepo;
	
	public int pedidoCount() {
		return (int) lineaPedidoRepo.count();
	}
	
	@Transactional(readOnly=true)
	public Optional<LineaPedido> findLineaPedidoById(int id){
		return lineaPedidoRepo.findById(id);
		
	}
	@Transactional
	public Iterable<LineaPedido> findAll() {
		return lineaPedidoRepo.findAll();
	}
	@Transactional
	public void save(LineaPedido lp) {
		lineaPedidoRepo.save(lp);
	}
	@Transactional
	public void delete(LineaPedido lp) {
		lineaPedidoRepo.delete(lp);
	}
	public List<Producto> getAllProductos() {
		return lineaPedidoRepo.getAllProductos();
	}
	public List<String> getAllProductosName() {
		return lineaPedidoRepo.getAllProductosName();
	}
}
