package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.repository.IngredienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredienteService {
	@Autowired
	private IngredienteRepository ingRep;
	
	@Transactional
	public int ingredienteCount() {
		return (int) ingRep.count();
	}
	@Transactional
	public Iterable<Ingrediente> findAll(){
		return ingRep.findAll();
	}
	/*@Transactional
	public Iterable<Ingrediente> findIngredientesByRestaurante(int id){
		return ingRep.findByRestaurante(id);
	}*/
	
	@Transactional(readOnly=true)
	public Optional<Ingrediente> findIngredienteById(int id) {
		return ingRep.findById(id);
	}
	
//	@Transactional(readOnly=true)
//	public Iterable<Ingrediente> findIngredienteByRestaurante(int id) {
//		return ingRep.findByRestaurante(id);
//	}
	
	@Transactional
	public void save(Ingrediente ingrediente) {
		ingRep.save(ingrediente);
	}
	
	@Transactional
	public void delete(Ingrediente ingrediente) {
		ingRep.delete(ingrediente);
	}
}
