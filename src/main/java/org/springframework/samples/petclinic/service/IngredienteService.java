package org.springframework.samples.petclinic.service;


import java.util.Optional;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.repository.IngredienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class IngredienteService {
	@Autowired
	private IngredienteRepository ingRep;
	@Autowired
	private ProductoService productoService;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(IngredienteService.class);
	
	@Transactional
	public int ingredienteCount() {
		log.info("Contando el número total de elementos");
		return (int) ingRep.count();
	}
	@Transactional
	public Iterable<Ingrediente> findAll(){
		log.info("Buscando todos los elementos");
		return ingRep.findAll();
	}
	
	@Transactional
	public Iterable<Ingrediente> findIngredientesByRestauranteId(Integer restauranteId){
		log.info("Buscando Ingredientes por restaurante");
		return ingRep.findIngredientesByRestauranteId(restauranteId);
	}
	
	@Transactional(readOnly=true)
	public Optional<Ingrediente> findIngredienteById(int id) {
		log.info("Devolviendo elemento por su id");
		return ingRep.findById(id);
	}
	
	@Transactional
	public void save(Ingrediente ingrediente) {
		log.info("Guardando elemento");
		ingRep.save(ingrediente);
	}
	
	@Transactional
	public void delete(Ingrediente ingrediente) {
		ingrediente.setRestaurante(null);
		Set<Producto> productos = ingrediente.getProductos();
		for(Producto p:productos) {
			Set<Ingrediente> ingredientes = p.getIngredientes();
			ingredientes.remove(ingrediente);
			p.setIngredientes(ingredientes);
			productoService.save(p);
		}
		log.info("Eliminado un elemento");
		ingRep.delete(ingrediente);
	}
}
