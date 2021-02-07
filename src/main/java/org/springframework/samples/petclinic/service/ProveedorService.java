package org.springframework.samples.petclinic.service;

import java.util.Optional;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class ProveedorService {
	@Autowired
	private ProveedorRepository proveedorRepo;
	@Autowired
	private IngredienteService ingredienteService;
	@Autowired
	private RestauranteService restauranteService;

	private static final Logger log = (Logger) LoggerFactory.getLogger(ProductoService.class);

	@Transactional
	public int proveedorCount() {

		log.info("Contando el número total de elementos");
		return (int) proveedorRepo.count();
	}

	@Transactional
	public Iterable<Proveedor> findAll() {

		log.info("Buscando todos los elementos");
		return proveedorRepo.findAll();

	}

	@Transactional(readOnly = true)
	public Optional<Proveedor> findProveedorById(int id) {

		log.info("Devolviendo elemento por su id");
		return proveedorRepo.findById(id);
	}
	
//	@Transactional
//	public Iterable<Proveedor> findProveedoresByRestauranteId(Integer restauranteId){
//		log.info("Buscando proveedores por restaurante");
//		return proveedorRepo.findProveedoresByRestauranteId(restauranteId);
//	}

	@Transactional
	public void save(Proveedor proveedor) {

		log.info("Guardando elemento");
		proveedorRepo.save(proveedor);
	}

	@Transactional
	public void delete(Proveedor proveedor) {
		Set<Ingrediente> ingredientes = proveedor.getIngredientes();
		for(Ingrediente i: ingredientes) {
			Set<Proveedor> proveedores = i.getProveedores();
			proveedores.remove(proveedor);
			i.setProveedores(proveedores);
			ingredienteService.save(i);
		}
		Set<Restaurante> restaurantes = proveedor.getRestaurantes();
		for(Restaurante r: restaurantes) {
			Set<Proveedor> proveedores = r.getProveedores();
			proveedores.remove(proveedor);
			r.setProveedores(proveedores);
			restauranteService.save(r);
		}
		log.info("Eliminado un elemento");
		proveedorRepo.delete(proveedor);
	}
}
