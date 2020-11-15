package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {
	@Autowired
	private ProductoRepository productoRepo;
	
	@Transactional
	public int productoCount() {
		return (int) productoRepo.count();
	}
	@Transactional
	public Iterable<Producto> findAll() {
		return productoRepo.findAll();
		
	}
	@Transactional(readOnly=true)
	public Optional<Producto> findProductoById(int id) {
		return productoRepo.findById(id);
	}
	public void save(Producto producto) {
		productoRepo.save(producto);
	}
	public void delete(Producto producto) {
		productoRepo.delete(producto);
	}
}	

