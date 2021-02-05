package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.repository.OfertaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class OfertaService {
	
	@Autowired
	private OfertaRepository ofertaRepo;

	private static final Logger log = (Logger) LoggerFactory.getLogger(OfertaService.class);

	@Transactional
	public int ofertaCount() {

		log.info("Contando el número total de elementos");
		return (int) ofertaRepo.count();
	}

	@Transactional
	public Iterable<Oferta> findAll() {

		log.info("Buscando todos los elementos");
		return ofertaRepo.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Oferta> findOfertaById(int id) {

		log.info("Devolviendo elemento por su id");
		return ofertaRepo.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Oferta> findOfertasByRestauranteId(Integer restauranteId) {

		log.info("Devolviendo elemento por su usuario");
		return ofertaRepo.findOfertasByRestauranteId(restauranteId);
	}
	
	@Transactional
	public void save(Oferta oferta) {

		log.info("Guardando elemento");
		ofertaRepo.save(oferta);
	}
	@Transactional
	public void delete(Oferta oferta) {

		log.info("Eliminado un elemento");
		ofertaRepo.delete(oferta);
	}

}
