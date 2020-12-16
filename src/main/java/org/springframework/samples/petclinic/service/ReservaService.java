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
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.repository.ReservaRepository;
import org.springframework.samples.petclinic.web.ReservaValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {
	
	@Autowired
	private ReservaRepository ReservaRepo;
	
	@Transactional
	public int Reservascount() {
		return (int) ReservaRepo.count();
	}
	
	@Transactional
	public Iterable<Reserva> findAll() {
		return ReservaRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public Optional<Reserva>findReservaById(int id){
		return ReservaRepo.findById(id);
	}
	
	@Transactional
	public void save(Reserva reserva) {
		ReservaRepo.save(reserva);
	}
	
	@Transactional
	public void delete(Reserva reserva) {
		ReservaRepo.delete(reserva);
		
	}
	
	/*@Documented
	@Constraint(validatedBy = ReservaValidator.class)
	@Target( { ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ReservaConstraint {
	    String message() default "Para reservar como evento, deben ser mínimo 10 personas";
	    String Event();
	    String nPersons();
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
	}*/
}
