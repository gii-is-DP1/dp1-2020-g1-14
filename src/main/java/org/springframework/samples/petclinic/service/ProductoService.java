package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.repository.ProductoRepository;
import org.springframework.samples.petclinic.service.exceptions.WrongDataProductosException;
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
	public void save(Producto producto) throws WrongDataProductosException {
		if((producto.getName().length() < 3 || producto.getName().length() > 50) || !producto.getAlergenos().matches("^[a-zA-Z,.!? ]*$")||producto.getPrecio() <= 0) {
			throw new WrongDataProductosException();
		}else
		productoRepo.save(producto);
	}
	public void delete(Producto producto) {
		productoRepo.delete(producto);
	}
	
	@Transactional(readOnly = true)
	public Collection<Producto> findProductoByPrecio(Double precio) {
		return productoRepo.findByPrecio(precio);
	}
	public Collection<Producto> findProductoByName(String name) {
		return productoRepo.findByName(name);
	}
}	

