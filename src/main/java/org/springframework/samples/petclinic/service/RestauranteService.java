package org.springframework.samples.petclinic.service;

import java.util.Optional;

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
	
	@Transactional
	public Optional<Restaurante>findRestauranteById(int id){
		return RestaurantRepo.findById(id);
	}
	
	@Transactional
	public void save(Restaurante restaurante) {
		RestaurantRepo.save(restaurante);
	}

	public void delete(Restaurante restaurante) {
		RestaurantRepo.delete(restaurante);
		
	}
}
