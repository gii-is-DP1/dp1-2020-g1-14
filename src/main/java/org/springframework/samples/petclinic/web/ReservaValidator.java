package org.springframework.samples.petclinic.web;


import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



public class ReservaValidator implements Validator {

	private static final String REQUIRED = "required";
	
	@Override
	public void validate(Object target, Errors errors) {
		Reserva reserva = (Reserva) target;
		Boolean evento = reserva.getEvento();
		Integer nPers = reserva.getnPersonas();
		
		// evento validation
		if(evento==null) {
			errors.rejectValue("evento", REQUIRED+"es necesario indicar si es evento", REQUIRED+"es necesario indicar si es evento");
		}
		if((evento && nPers<=10) || nPers==null) {
			errors.rejectValue("nPersonas", REQUIRED+"   Si es un evento, debe haber 10 personas o más", REQUIRED+"   Si es un evento, debe haber 10 personas o más");
		}
		
		// hora validation
		LocalTime HI = reserva.getHoraInicio();
		LocalTime HF = reserva.getHoraFin();
		if(HF.isBefore(HI) || HI==null || HF==null) {
			errors.rejectValue("horaFin", REQUIRED+"  La hora de inicio debe ser anterior a la hora de fin",REQUIRED+"  La hora de inicio debe ser anterior a la hora de fin");
		}
		
		// Aforo validation
		Restaurante res = reserva.getRestaurante();
		if(res == null) {
			
		}else if (nPers>res.getAforores()) {
			errors.rejectValue("nPers", REQUIRED+"no hay aforo suficiente", REQUIRED+"no hay aforo suficiente");
		}

			
	}
	
	/**
	 * This Validator validates *just* Reserva instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Reserva.class.isAssignableFrom(clazz);
	}

}
