package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.repository.OfertaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfertaService {
	@Autowired
	private OfertaRepository ofertaRepo;
	
	@Transactional
	public int ofertaCount() {
		return (int) ofertaRepo.count();
	}
	
	@Transactional
	public Iterable<Oferta> findAll() {
		return ofertaRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public Optional<Oferta> findOfertaById(int id) {
		return ofertaRepo.findById(id);
	}
	
	public void save(Oferta oferta) {
		ofertaRepo.save(oferta);
	}
	
	public void delete(Oferta oferta) {
		ofertaRepo.delete(oferta);
	}
	
}
