package org.springframework.samples.petclinic.service;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Reclamacion;
import org.springframework.samples.petclinic.repository.ReclamacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class ReclamacionService {
	@Autowired
	private ReclamacionRepository reclamacionRepo;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(ReclamacionService.class);
	
	@Transactional
	public int reclamacionCount() {
		log.info("Contando el número total de elementos");
		return(int) reclamacionRepo.count();	}
	
	@Transactional
	public Iterable<Reclamacion> findAll() {
		log.info("Buscando todos los elementos");
		return reclamacionRepo.findAll();
	    }

    @Transactional(readOnly=true)
    public Optional<Reclamacion> findReclamacionById(int id) {
    	log.info("Devolviendo elemento por su id");
        return reclamacionRepo.findById(id);
    }
    
    @Transactional(readOnly = true)
	public Iterable<Reclamacion> findReclamacionByRestauranteId(Integer restauranteId) {

		log.info("Devolviendo elementos por su restaurante");
		return reclamacionRepo.findReclamacionByRestauranteId(restauranteId);
	}
	
	public void save(Reclamacion reclamacion) {
		log.info("Guardando elemento");
		reclamacionRepo.save(reclamacion);
	}
	
	@Transactional
	public void delete(Reclamacion reclamacion) {
		log.info("Eliminado un elemento");
		reclamacionRepo.delete(reclamacion);
	}
	
}
