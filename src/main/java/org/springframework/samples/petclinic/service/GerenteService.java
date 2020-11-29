package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.repository.GerenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GerenteService {

	@Autowired
	private GerenteRepository gerenteRepo;

	   @Transactional 
	    public int gerenteCount() {
	        return (int) gerenteRepo.count();
	    }
	    @Transactional
	    public Iterable<Gerente> findAll() {
	        return gerenteRepo.findAll();
	        
	    }
	    
	    @Transactional(readOnly=true)
	    public Optional<Gerente> findGerenteById(int id) {
	        return gerenteRepo.findById(id);
	    }
	    
	    @Transactional
	    public void save(Gerente gerente) {
	    	gerenteRepo.save(gerente);
	    }
	    
	    @Transactional
	    public void delete(Gerente gerente) {
	    	gerenteRepo.delete(gerente);
	    }
	
	
}
