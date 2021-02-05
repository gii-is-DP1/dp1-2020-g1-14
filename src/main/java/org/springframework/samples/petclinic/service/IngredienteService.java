package org.springframework.samples.petclinic.service;


import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.repository.IngredienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class IngredienteService {
	@Autowired
	private IngredienteRepository ingRep;
	
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
		log.info("Eliminado un elemento");
		ingRep.delete(ingrediente);
	}
}
