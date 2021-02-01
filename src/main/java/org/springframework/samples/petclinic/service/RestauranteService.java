package org.springframework.samples.petclinic.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.repository.RestauranteRepository;
import org.springframework.samples.petclinic.web.ReservaController;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository RestaurantRepo;
	
	@Transactional
	public int Restaurantscount() {
		return (int) RestaurantRepo.count();
	}
	
	@Transactional
	public Iterable<Restaurante> findAll() {
		log.info("obtención de todos los restaurantes");
		return RestaurantRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public Optional<Restaurante>findRestauranteById(int id){
		log.info("obtención de restaurante concreto");
		return RestaurantRepo.findById(id);
	}
	
	@Transactional
	public void save(Restaurante restaurante) {
		RestaurantRepo.save(restaurante);
		log.info("guardar restaurante");
	}
	
	@Transactional
	public void delete(Restaurante restaurante) {
		RestaurantRepo.delete(restaurante);
		log.info("restaurante elimiinado");
	}
	/*@Documented
	@Constraint(validatedBy = AforoResValidator.class)
	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AforoResConstraint{
		String message() default "El aforo restante no puede ser mayor que el máximo";
		String afRes();
		String afMax();
		Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
		
	}*/

}
