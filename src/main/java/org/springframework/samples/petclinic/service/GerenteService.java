package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.repository.GerenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class GerenteService {

	@Autowired
	private GerenteRepository gerenteRepo;
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private UserService userService;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(GerenteService.class);
	

	   @Transactional 
	    public int gerenteCount() {
		   log.info("Contando el número total de elementos");
	        return (int) gerenteRepo.count();
	    }
	    @Transactional
	    public Iterable<Gerente> findAll() {
	    	log.info("Buscando todos los elementos");
	        return gerenteRepo.findAll();
	        
	    }
	    
	    @Transactional(readOnly=true)
	    public Optional<Gerente> findGerenteById(int id) {
	    	log.info("Devolviendo elemento por su id");
	        return gerenteRepo.findById(id);
	    }
	    
	    @Transactional(readOnly = true)
		public Optional<Gerente> findGerenteByUsuario(String username) {
	    	log.info("Devolviendo elemento por su usuario");
			return gerenteRepo.findGerenteByUsuario(username);
		}
	    
	    @Transactional
	    public void save(Gerente gerente) {
	    	log.info("Guardando elemento");
	    	gerenteRepo.save(gerente);
	    	
	    	log.info("Creando usuario");
			userService.saveUser(gerente.getUser());
			
			log.info("Creando authorities");
			authoritiesService.saveAuthorities(gerente.getUser().getUsername(), "gerente");
	    }
	    
	    @Transactional
	    public void delete(Gerente gerente) {
	    	log.info("Eliminado un elemento");
	    	userService.delete(gerente.getUser());
	    	gerenteRepo.delete(gerente);
	    }
	
	
}