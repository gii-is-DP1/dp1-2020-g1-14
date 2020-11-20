package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.repository.RestauranteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository RestaurantRepo;
	
	@Transactional
	public int Restaurantscount() {
		return (int)RestaurantRepo.count();
	}
	
	@Transactional
	public Iterable<Restaurante> findAll() {
		return RestaurantRepo.findAll();
	}
}
