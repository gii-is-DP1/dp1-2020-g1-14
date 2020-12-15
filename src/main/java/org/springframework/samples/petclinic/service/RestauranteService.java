package org.springframework.samples.petclinic.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.repository.RestauranteRepository;
import org.springframework.samples.petclinic.web.AforoResValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		return RestaurantRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public Optional<Restaurante>findRestauranteById(int id){
		return RestaurantRepo.findById(id);
	}
	
	@Transactional
	public void save(Restaurante restaurante) {
		RestaurantRepo.save(restaurante);
	}
	
	@Transactional
	public void delete(Restaurante restaurante) {
		RestaurantRepo.delete(restaurante);
		
	}
	@Documented
	@Constraint(validatedBy = AforoResValidator.class)
	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AforoResConstraint{
		String message() default "El aforo restante no puede ser mayor que el máximo";
		String afRes();
		String afMax();
		Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
		
	}
}
